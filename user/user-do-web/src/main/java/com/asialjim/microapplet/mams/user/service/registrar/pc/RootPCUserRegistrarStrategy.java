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
import com.asialjim.microapplet.commons.security.Role;
import com.asialjim.microapplet.mams.app.api.AppApi;
import com.asialjim.microapplet.mams.app.api.ChlAppApi;
import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import com.asialjim.microapplet.mams.user.context.UserRs;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.ChlUserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.UserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.ChlUserRepository;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.UserRepository;
import com.asialjim.microapplet.mams.user.service.registrar.ChlUserRegistrarStrategy;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * PC 超管用户注册策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@RequiredArgsConstructor
public class RootPCUserRegistrarStrategy implements ChlUserRegistrarStrategy {
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
        return ChannelAppType.ROOT;
    }

    @Override
    public ChlUserVo register(ChlUserVo user) {
        // 超管存在
        UserPo root = this.userRepository.queryById("root");
        if (Objects.nonNull(root))
            UserRs.UserRegisterPermissionMiss.thr();
        ChlUserVo chlUserVo = this.chlUserRepository.queryById("root");
        // 超管存在
        if (Objects.nonNull(chlUserVo))
            UserRs.UserRegisterPermissionMiss.thr();

        String password = PasswordStorage.createHash(user.getChlUserCode());
        AppVo rootApp = this.appApi.getRootApp();
        String appid = rootApp.getId();
        user.setAppid(appid);

        UserPo mainUser = new UserPo();
        mainUser.setId("root");
        mainUser.setAppid(user.getAppid());
        mainUser.setOrgId(rootApp.getOrgId());
        mainUser.setNickname("root");
        mainUser.setUsername("root");
        mainUser.setDeleted(false);
        mainUser.setCreateTime(LocalDateTime.now());
        mainUser.setUpdateTime(LocalDateTime.now());
        try {
            // 注册主用户
            mainUser = this.userRepository.create(mainUser);
        } catch (Throwable t) {
            UserRs.UserRegisterPermissionMiss.thr();
        }

        ChlAppVo rootChlApp = this.chlAppApi.queryRootByAppid(appid);
        user.setId("root");
        user.setUserid(mainUser.getId());
        user.setAppid(rootApp.getId());
        user.setChlType(ChannelType.PC.getCode());
        user.setChlAppId(rootChlApp.getChlAppId());
        user.setChlAppType(ChannelAppType.ROOT.getCode());
        user.setChlUserId("root");
        user.setChlUnionId("root");
        user.setRoleBit(Role.Root.getBit());
        user.setChlUserCode(password);
        user.setChlUserToken("");// 双因素认证
        // 注册渠道用户
        return this.chlUserRepository.save(user);
    }

    public Result<Void> rootExist() {
        UserPo root = this.userRepository.queryById("root");
        if (Objects.nonNull(root))
            return Res.OK.create();
        return Res.UsernameOfPasswordIllegal.create();
    }
}