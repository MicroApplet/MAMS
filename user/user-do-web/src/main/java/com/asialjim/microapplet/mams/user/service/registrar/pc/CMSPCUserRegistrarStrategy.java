/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.user.service.registrar.pc;

import com.asialjim.microapplet.common.context.Res;
import com.asialjim.microapplet.common.context.Result;
import com.asialjim.microapplet.common.utils.PasswordStorage;
import com.asialjim.microapplet.mams.app.api.AppApi;
import com.asialjim.microapplet.mams.app.api.ChlAppApi;
import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.app.context.AppRs;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import com.asialjim.microapplet.mams.user.context.UserRs;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.UserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.ChlUserRepository;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.UserRepository;
import com.asialjim.microapplet.mams.user.service.registrar.ChlUserRegistrarStrategy;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * PC 后管用户注册策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@RequiredArgsConstructor
public class CMSPCUserRegistrarStrategy implements ChlUserRegistrarStrategy {
    private final ChlUserRepository chlUserRepository;
    private final UserRepository userRepository;
    private final ChlAppApi chlAppApi;
    private final AppApi appApi;

    @Override
    public ChannelType registerChannel() {
        return ChannelType.PC;
    }

    @Override
    public ChannelAppType chlAppType() {
        return ChannelAppType.CMS;
    }

    @Override
    public ChlUserVo register(ChlUserVo user) {
        String userid = user.getUserid();
        String appid = user.getAppid();
        String chlAppId = user.getChlAppId();
        String chlUserId = user.getChlUserId();

        // 注册超管
        if (createRoot(userid, appid, chlAppId, chlUserId))
            return registerRoot(user);

        // 注册普通用户
        return registerUser(user);
    }


    public Result<Void> rootExist() {
        UserPo root = this.userRepository.queryById("root");
        if (Objects.nonNull(root))
            return Res.OK.create();
        return Res.UsernameOfPasswordIllegal.create();
    }

    private ChlUserVo registerUser(ChlUserVo user) {
        String appid = user.getAppid();
        AppVo appVo = this.appApi.queryById(appid);
        if (Objects.isNull(appVo))
            AppRs.NoSuchApp.thr();

        String chlType = user.getChlType();
        String chlAppId = user.getChlAppId();
        ChlAppVo chlAppVo = this.chlAppApi.queryByAppidAndChlAndChlAppid(appid, chlType, chlAppId);
        if (Objects.isNull(chlAppVo))
            AppRs.NoSuchChlApp.thr();

        String password = PasswordStorage.createHash(user.getChlUserCode());

        String userid = user.getUserid();
        if (StringUtils.isBlank(userid)) {
            UserPo mainUser = new UserPo();
            mainUser.setAppid(user.getAppid());
            mainUser.setOrgId(appVo.getOrgId());
            mainUser.setUsername(user.getChlUserId());
            mainUser.setPassword(password);
            mainUser.setDeleted(false);
            mainUser.setCreateTime(LocalDateTime.now());
            mainUser.setUpdateTime(LocalDateTime.now());
            try {
                mainUser = this.userRepository.create(mainUser);
                userid = mainUser.getId();
            } catch (Throwable t) {
                UserRs.UserRegisterPermissionMiss.thr();
            }
        }
        user.setUserid(userid);
        user.setAppid(appVo.getId());
        user.setChlType(ChannelType.PC.getCode());
        user.setChlAppId(chlAppVo.getChlAppId());
        user.setChlAppType(ChannelAppType.CMS.getCode());
        user.setChlUserId(user.getChlUserId());
        user.setChlUnionId(user.getChlUnionId());
        user.setChlUserCode(password);
        user.setChlUserToken(user.getChlUserToken());// 双因素认证
        return this.chlUserRepository.save(user);
    }

    private ChlUserVo registerRoot(ChlUserVo user) {
        UserPo root = this.userRepository.queryById("root");
        if (Objects.nonNull(root))
            UserRs.UserRegisterPermissionMiss.thr();

        AppVo rootApp = this.appApi.getRootApp();
        String appid = rootApp.getId();
        user.setAppid(appid);

        UserPo mainUser = new UserPo();
        mainUser.setId("root");
        mainUser.setAppid(user.getAppid());
        mainUser.setOrgId(rootApp.getOrgId());
        mainUser.setNickname("root");
        mainUser.setUsername("root");
        mainUser.setPassword(PasswordStorage.createHash(user.getChlUserCode()));
        mainUser.setDeleted(false);
        mainUser.setCreateTime(LocalDateTime.now());
        mainUser.setUpdateTime(LocalDateTime.now());
        try {
            mainUser = this.userRepository.create(mainUser);
        } catch (Throwable t) {
            t.printStackTrace();
            UserRs.UserRegisterPermissionMiss.thr();
        }

        ChlAppVo rootChlApp = this.chlAppApi.queryRootByAppid(appid);
        user.setId("root");
        user.setUserid(mainUser.getId());
        user.setAppid(rootApp.getId());
        user.setChlType(ChannelType.PC.getCode());
        user.setChlAppId(rootChlApp.getChlAppId());
        user.setChlAppType(ChannelAppType.CMS.getCode());
        user.setChlUserId("root");
        user.setChlUnionId("root");
        user.setChlUserCode(mainUser.getPassword());
        user.setChlUserToken("");// 双因素认证
        return this.chlUserRepository.save(user);
    }

    private boolean createRoot(String userid, String appid, String chlAppid, String chlUserid) {
        return StringUtils.equals(userid, "root")
                && StringUtils.equals(appid, "root")
                && StringUtils.equals(chlAppid, "root")
                && StringUtils.equals(chlUserid, "root");
    }

}