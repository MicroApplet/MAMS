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

package com.asialjim.microapplet.mams.user.domain.agg;

import com.asialjim.microapplet.common.concurrent.ConcurrentRunner;
import com.asialjim.microapplet.common.human.Gender;
import com.asialjim.microapplet.common.human.IdCardType;
import com.asialjim.microapplet.common.human.Nationality;
import com.asialjim.microapplet.mams.user.infrastructure.adapter.CurrentUserAdapter;
import com.asialjim.microapplet.mams.user.infrastructure.adapter.FileUploadAdapter;
import com.asialjim.microapplet.mams.user.infrastructure.adapter.IdCardOcrAdapter;
import com.asialjim.microapplet.mams.user.infrastructure.adapter.meta.IdCardOcrRes;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserChlRepository;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserIdCardRepository;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserMainRepository;
import com.asialjim.microapplet.mams.user.pojo.UserChl;
import com.asialjim.microapplet.mams.user.pojo.UserIdCard;
import com.asialjim.microapplet.mams.user.pojo.UserMain;
import com.asialjim.microapplet.mams.user.res.UserResCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Supplier;

/**
 * 用户聚合根
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@Scope("request")
@SuppressWarnings("unused")
public class UserAggRoot {
    @Getter
    private final SessionUser sessionUser;// 用户会话,标识当前用户聚合根
    private final UserMainRepository userMainRepository;// 主用户账户存储
    private final UserChlRepository userChlRepository;// 渠道用户存储
    private final UserIdCardRepository userIdCardRepository;// 用户证件存储
    private final IdCardOcrAdapter idCardOcrAdapter;// 证件OCR适配器
    private final FileUploadAdapter fileUploadAdapter;// 文件上传服务器
    private final Map<IdCardType, UserIdCard> userIdCardMap;// 用户证件本地缓存 用户证件信息:允许用户添加多类型证件，但是同一证件仅允许有一个证件信息

    private UserMain userMain;// 用户主账号
    private List<UserChl> userChlList;// 用户渠道账号

    public UserAggRoot(final UserMainRepository userMainRepository,
                       final UserChlRepository userChlRepository,
                       final UserIdCardRepository userIdCardRepository,
                       final IdCardOcrAdapter idCardOcrAdapter,
                       final FileUploadAdapter fileUploadAdapter) {

        this.userMainRepository = userMainRepository;
        this.userChlRepository = userChlRepository;
        this.userIdCardRepository = userIdCardRepository;
        this.idCardOcrAdapter = idCardOcrAdapter;
        this.fileUploadAdapter = fileUploadAdapter;
        this.sessionUser = CurrentUserAdapter.current(null, null);
        this.userIdCardMap = new HashMap<>();
    }

    /**
     * 查询用户指定证件类型的证件信息
     *
     * @param idCardType {@link IdCardType idCardType}
     * @return {@link UserIdCard }
     * @since 2025/4/10
     */
    public UserIdCard userIdCard(IdCardType idCardType) {
        UserIdCard userIdCard = this.userIdCardMap.get(idCardType);
        if (Objects.nonNull(userIdCard))
            return userIdCard;

        UserMain userMain = userMainOrThrow(null);
        if (Objects.isNull(userMain))
            return null;

        userIdCard = this.userIdCardRepository.queryByUseridOfIdTypeForApplet(userMain.getId(), idCardType, userMain.getAppletId());
        this.userIdCardMap.put(idCardType, userIdCard);
        return userIdCard;
    }

    /**
     * 用户渠道用户列表
     *
     * @return {@link List<UserChl>}
     * @since 2025/4/10
     */
    public List<UserChl> userChlList() {
        if (Objects.nonNull(this.userChlList))
            return this.userChlList;

        UserMain userMain = userMainOrThrow(null);
        if (Objects.isNull(userMain))
            return Collections.emptyList();

        this.userChlList = this.userChlRepository.queryByUseridOfApplet(userMain.getId(), userMain.getAppletId());
        return this.userChlList;
    }


    /**
     * 获取用户主账户信息或者抛出错误
     *
     * @param supplier {@link Supplier supplier}
     * @return {@link UserMain}
     * @since 2025/4/10
     */
    public UserMain userMainOrThrow(Supplier<RuntimeException> supplier) {
        if (Objects.nonNull(this.userMain))
            return this.userMain;

        this.userMain = this.userMainRepository.queryBySession(sessionUser);
        if (Objects.isNull(this.userMain) && Objects.nonNull(supplier))
            throw supplier.get();

        return this.userMain;
    }

    /**
     * 通过上传文件，注册用户的证件信息
     *
     * @param file {@link MultipartFile file}
     * @return {@link UserIdCard }
     * @since 2025/4/10
     */
    public UserIdCard idCardRegByFile(MultipartFile file) {
        final UserIdCard user = new UserIdCard();
        // 获取当前会话用户
        SessionUser current = CurrentUserAdapter.current(sessionUser -> user.setUserid(sessionUser.getUserid()).setAppletId(sessionUser.getAppid()), UserResCode.UserNotLogin::bizException);

        // 证件影像上传
        // 证件识别
        ConcurrentRunner.runAllTask(() -> user.setFileId(fileUploadAdapter.upload(file)), () -> ocrIdCard(file, user));

        // 用户已存在检查
        userIdCardConflictCheck(user);

        // 合并保存数据
        this.userIdCardRepository.mergeAndSave(user);
        // 添加到本地缓存
        this.userIdCardMap.put(user.getIdType(), user);
        return user;
    }

    /**
     * 用户证件冲突检查
     *
     * @param user {@link UserIdCard user}
     * @since 2025/4/10
     */
    public void userIdCardConflictCheck(UserIdCard user) {
        if (StringUtils.isBlank(user.getIdNo()))
            return;

        UserIdCard exist = userIdCardRepository.queryByIdNoOfTypeForApplet(user.getIdNo(), user.getIdType(), user.getAppletId());
        if (Objects.isNull(exist))
            return;

        if (StringUtils.equals(exist.getUserid(), user.getUserid()))
            UserResCode.UserHasExist.throwBiz();
    }

    /**
     * 证件OCR
     *
     * @param file {@link MultipartFile file}
     * @param user {@link UserIdCard user}
     * @since 2025/4/10
     */
    private void ocrIdCard(MultipartFile file, UserIdCard user) {
        SessionUser current = getSessionUser();
        String userid = current.getUserid();
        if (StringUtils.isBlank(userid))
            UserResCode.UserNotLogin.throwBiz();

        String appid = current.getChlAppid();
        IdCardOcrRes idCardCVOcrRes = idCardOcrAdapter.ocr(appid, file);
        boolean front = idCardCVOcrRes.front();
        user.setFront(front);
        user.setUserid(userid);
        user.setIdNo(idCardCVOcrRes.getId());
        user.setIdType(IdCardType.ResidentIdentityCard);
        user.setName(idCardCVOcrRes.getName());
        user.setGender(Gender.descOf(idCardCVOcrRes.getGender()));
        user.setNationality(Nationality.cnNameOf(idCardCVOcrRes.getNationality()));
        user.setAddress(idCardCVOcrRes.getAddr());
        user.setIssueDate(idCardCVOcrRes.validStart());
        user.setIssueExpires(idCardCVOcrRes.validEnd());
    }
}