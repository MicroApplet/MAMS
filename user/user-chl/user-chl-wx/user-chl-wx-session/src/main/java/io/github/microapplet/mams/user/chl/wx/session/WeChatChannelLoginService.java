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

package io.github.microapplet.mams.user.chl.wx.session;

import io.github.microapplet.mams.user.chl.wx.WxUserChlRes;
import io.github.microapplet.mams.user.login.ChannelLoginService;
import io.github.microapplet.mams.user.parameter.LoginParameter;
import io.github.microapplet.mams.user.session.SessionUser;
import io.github.microapplet.wechat.application.WeChatApplication;
import io.github.microapplet.wechat.application.WeChatApplicationRepository;
import io.github.microapplet.wechat.application.WeChatApplicationType;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 微信渠道用户登录服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/12, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatChannelLoginService implements ChannelLoginService, ApplicationContextAware {
    private final Map<WeChatApplicationType, WeChatChlUserLoginService> loginServiceMap;

    public WeChatChannelLoginService() {
        this.loginServiceMap = new HashMap<>();
    }

    @Setter
    private ApplicationContext applicationContext;

    @Resource
    private WeChatApplicationRepository.Aggregator aggregator;

    private WeChatChlUserLoginService wxLoginService(LoginParameter loginParameter) {
        WeChatApplication weChatApplication = this.aggregator.appByIndex(loginParameter.getChlAppid());
        if (Objects.isNull(weChatApplication))
            WxUserChlRes.WeChatPlatformAppNotSupport.throwBiz();

        String appType = weChatApplication.getAppType();
        WeChatApplicationType weChatApplicationType = WeChatApplicationType.valueOf(appType);
        WeChatChlUserLoginService service = this.loginServiceMap.get(weChatApplicationType);

        synchronized (WeChatChannelLoginService.class){
            if (Objects.isNull(service)) {
                String[] names = this.applicationContext.getBeanNamesForType(WeChatChlUserLoginService.class);
                for (String name : names) {
                    WeChatChlUserLoginService bean = this.applicationContext.getBean(name, WeChatChlUserLoginService.class);
                    WeChatApplicationType type = bean.appType();
                    this.loginServiceMap.put(type, bean);
                    if (Objects.equals(type, weChatApplicationType)) {
                        service = bean;
                    }
                }
            }
        }

        if (Objects.isNull(service))
            WxUserChlRes.WeChatAppTypeNotSupport.throwBiz();

        return service;
    }


    @Override
    public String chlCode() {
        return "wechat";
    }

    @Override
    public SessionUser login(LoginParameter loginParameter) {
        WeChatChlUserLoginService service = wxLoginService(loginParameter);
        return service.login(loginParameter);
    }
}