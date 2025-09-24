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

package com.asialjim.microapplet.mams.user.domain.strategy;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.channel.base.ChlTypeResCode;
import com.asialjim.microapplet.mams.user.command.UserLoginCommand;
import com.asialjim.microapplet.mams.user.command.UserRegCommand;
import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import com.asialjim.microapplet.mams.user.domain.agg.UserAggRoot;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserChlRepository;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserMainRepository;
import com.asialjim.microapplet.mams.user.pojo.UserMain;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;


/**
 * 用户认证策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@AllArgsConstructor
public abstract class BaseUserAuthenticateStrategy {
    protected final UserMainRepository userMainRepository;
    protected final UserChlRepository userChlRepository;

    /**
     * 注册
     *
     * @param command {@link UserRegCommand command}
     * @since 2025/4/10
     */
    public void registration(UserRegCommand command) {
        ChlType chlType = command.regChlType();
        if (!StringUtils.equals(chlType.getCode(), supportType().getCode()))
            ChlTypeResCode.ChlTypeMismatch.thr();

        // 自注册：如果当前会话用户编号为空，则注册一个主用户
        UserAggRoot userAgg = command.getUserAgg();
        String chlAppId = command.getReq().getChlAppId();
        ChlAppType chlAppType = command.getReq().getChlAppType();
        String appletId = command.getReq().getAppletId();
        // 当前会话
        SessionUser sessionUser = userAgg.getSessionUser();
        // 当前会话用户编号
        String userid = sessionUser.getUserid();
        // 当前会话用户已存在用户编号
        if (StringUtils.isNotBlank(userid))
            return;

        UserMain userMain = new UserMain();
        userMain.setAppletId(appletId);
        userid = this.userMainRepository.saveAndGetId(userMain);
        sessionUser.setUserid(userid);
        sessionUser.setChlType(chlType.getCode());
        sessionUser.setChlAppid(chlAppId);
        sessionUser.setChlAppType(chlAppType.getCode());

        doRegistration(command);
    }

    /**
     * 支持的渠道
     */
    @NonNull
    public abstract ChlType supportType();


    /**
     * 执行用户注册功能
     *
     * @param command {@link UserRegCommand 用户注册命令}
     * @since 2025/4/10
     */
    protected abstract void doRegistration(UserRegCommand command);

    /**
     * 用户登录
	 * @param command {@link UserLoginCommand 用户登录命令}
     * @return {@link SessionUser }
     * @since 2025/4/10
     */
    public abstract SessionUser login(UserLoginCommand command);
}