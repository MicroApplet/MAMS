/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.user.chl.wechat.strategy;

import com.asialjim.microapplet.common.utils.PasswordStorage;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.channel.base.ChlTypeResCode;
import com.asialjim.microapplet.mams.user.command.UserLoginCommand;
import com.asialjim.microapplet.mams.user.command.UserRegCommand;
import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import com.asialjim.microapplet.mams.user.domain.agg.UserAggRoot;
import com.asialjim.microapplet.mams.user.domain.strategy.BaseUserAuthenticateStrategy;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserChlRepository;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserMainRepository;
import com.asialjim.microapplet.mams.user.pojo.UserChl;
import com.asialjim.microapplet.mams.user.vo.UserRegOrLoginReq;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 微信渠道用户认证策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatUserAuthenticationStrategy extends BaseUserAuthenticateStrategy {
    private final Map<ChlAppType, WeChatUserLoginStrategy> strategyMap;

    public WeChatUserAuthenticationStrategy(UserMainRepository userMainRepository,
                                            UserChlRepository userChlRepository,
                                            List<WeChatUserLoginStrategy> weChatUserAuthenticationStrategies) {

        super(userMainRepository, userChlRepository);
        this.strategyMap = new HashMap<>();
        if (CollectionUtils.isEmpty(weChatUserAuthenticationStrategies))
            return;

        for (WeChatUserLoginStrategy item : weChatUserAuthenticationStrategies) {
            ChlAppType chlAppType = item.chlAppType();
            if (Objects.nonNull(this.strategyMap.get(chlAppType)))
                throw new IllegalStateException("微信渠道：" + chlAppType + "存在多个用户注册策略，请合并该渠道策略");
            this.strategyMap.put(item.chlAppType(), item);
        }
    }

    @NonNull
    @Override
    public ChlType supportType() {
        return ChlType.WeChat;
    }

    @Override
    protected void doRegistration(UserRegCommand command) {
        WeChatUserLoginStrategy strategy = this.strategyMap.get(command.regChlAppType());
        if (Objects.isNull(strategy))
            ChlTypeResCode.ChlTypeNotSupport.throwBiz();

        UserAggRoot userAgg = command.getUserAgg();
        // 当前会话
        SessionUser sessionUser = userAgg.getSessionUser();
        // 当前会话用户编号
        String userid = sessionUser.getUserid();

        UserRegOrLoginReq req = command.getReq();
        UserChl target = strategy.registration(command);

        // 合并都主用户
        target.setUserid(userid);
        target.setAppletId(req.getAppletId());
        target.setChlType(supportType());
        target.setChlAppType(req.getChlAppType());
        target.setChlAppId(req.getChlAppId());
        target.setChlUserCode(PasswordStorage.createHash(req.getCode()));

        // 注册渠道用户
        String id = this.userChlRepository.saveAndGetId(target);
        target.setId(id);
    }

    @Override
    public SessionUser login(UserLoginCommand command) {
        WeChatUserLoginStrategy strategy = this.strategyMap.get(command.regChlAppType());
        if (Objects.isNull(strategy))
            ChlTypeResCode.ChlTypeNotSupport.throwBiz();

        UserRegOrLoginReq req = command.getReq();
        SessionUser login = strategy.login(command);
        // TODO

        return null;
    }
}