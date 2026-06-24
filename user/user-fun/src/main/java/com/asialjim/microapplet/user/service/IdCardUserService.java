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

package com.asialjim.microapplet.user.service;

import com.asialjim.microapplet.user.code.ChlUserCode;
import com.asialjim.microapplet.user.code.IdCardUserResCode;
import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.entity.vo.IdCardUserVo;
import com.asialjim.microapplet.user.infrastructure.repository.IdCardUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class IdCardUserService {
    private final IdCardUserRepository idCardUserRepository;
    private final ChlUserService chlUserService;


    /**
     * 通过 openid 进行证件绑定
     * 如果绑定失败则会抛出异常
     * 需要短信验证码处理
     * 需要
     * <pre>
     *     uam id       维护
     *     ecif id      维护
     *     SESSION-ID   维护
     * </pre>
     */
    public IdCardUserVo linkIdCard(IdCardUserVo body) {
        ChlUserVo chlUserVo = this.chlUserService.current();
        if (Objects.isNull(chlUserVo)) ChlUserCode.NoSuchChannelUser.thr();
        String openid = chlUserVo.getOpenid();

        // 绑定证件
        IdCardUserVo idCardUserVo = this.idCardUserRepository.queryByPlatformTypeAndAppidAndOpenidAndIdType(
                body.getPlatformType(), body.getAppId(), body.getOpenid(), body.getIdType()
        );
        if (Objects.nonNull(idCardUserVo))
            IdCardUserResCode.OpenidHadLinkedIdCard.thr("用户已绑定[" + body.getIdType() + "]证件");

        nameAndIdCardMatchTest(body.getName(), body.getIdType(), body.getIdNumber());

        return this.idCardUserRepository.linkIdCard(body);
    }


    public void nameAndIdCardMatchTest(String name, @SuppressWarnings("unused") String idType, String idNumber) {

    }


    public IdCardUserVo query(String platformType, String appid, String openid, String idType) {
        return this.idCardUserRepository.queryByPlatformTypeAndAppidAndOpenidAndIdType(
                platformType, appid, openid, idType
        );


    }

    public List<IdCardUserVo> queryList(String platformType, String appid, String openid) {
        return this.idCardUserRepository.queryByPlatformTypeAndAppidAndOpenid(
                platformType, appid, openid
        );

    }

}