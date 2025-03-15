/*
 * Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.microapplet.mams.user.service;

import io.github.microapplet.common.context.IORes;
import io.github.microapplet.common.context.Result;
import io.github.microapplet.mams.file.api.FileApi;
import io.github.microapplet.mams.user.cons.Gender;
import io.github.microapplet.mams.user.cons.IdCardType;
import io.github.microapplet.mams.user.cons.Nationality;
import io.github.microapplet.mams.user.model.IdentificationUser;
import io.github.microapplet.mams.user.model.User;
import io.github.microapplet.mams.user.repository.IdentificationUserCache;
import io.github.microapplet.mams.user.repository.IdentificationUserRepository;
import io.github.microapplet.mams.user.session.CurrentUserBean;
import io.github.microapplet.wechat.remoting.WeChatCVRemoting;
import io.github.microapplet.wechat.remoting.meta.cv.IdCardCVOcrRes;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * 证件用户服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class IdentificationUserServiceImpl implements IdentificationUserService {
    @Resource
    private IdentificationUserRepository identificationUserRepository;
    @Resource
    private IdentificationUserCache identificationUserCache;
    @Resource
    private WeChatCVRemoting weChatCVRemoting;
    @Resource
    private FileApi fileApi;
    @Resource
    private Executor executor;

    @Override
    public IdentificationUser authenticate(MultipartFile file) {
        User current = CurrentUserBean.current(null);
        IdentificationUser user = new IdentificationUser();
        CountDownLatch countDownLatch = new CountDownLatch(2);

        // 文件底片留痕
        executor.execute(() -> {
            try {
                Result<String> fileIdRes = fileApi.upload(file);
                Optional.ofNullable(fileIdRes).map(Result::getData).ifPresent(user::setFileId);
            } finally {
                countDownLatch.countDown();
            }
        });

        // 证件识别
        executor.execute(() -> {
            try {
                String appid = current.getChlAppid();
                IdCardCVOcrRes idCardCVOcrRes = weChatCVRemoting.idCard(appid, file);
                boolean front = idCardCVOcrRes.front();
                user.setFront(front);
                user.setUserId(current.getId());
                user.setIdNo(idCardCVOcrRes.getId());
                user.setIdType(IdCardType.ResidentIdentityCard.getCode());
                user.setName(idCardCVOcrRes.getName());
                Gender gender = Gender.descOf(idCardCVOcrRes.getGender());
                user.setGender(gender);
                user.setNationality(Nationality.ChineseNationality.descOf(idCardCVOcrRes.getNationality()));
                user.setAddress(idCardCVOcrRes.getAddr());
                user.setIssueDate(idCardCVOcrRes.validStart());
                user.setIssueExpires(idCardCVOcrRes.validEnd());
            } finally {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            IORes.TimeoutErr.create(Collections.singletonList("文件处理超时")).throwBiz();
        }

        this.identificationUserCache.merge(user);
        // 正反面都有
        if (user.frontAndBack()) {
            user.setUserId(current.getId());
            this.identificationUserRepository.save(user);
        }
        return user;
    }
}