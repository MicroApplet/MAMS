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

package com.asialjim.microapplet.mams.user.chl.wx.session;

import com.asialjim.microapplet.mams.user.chl.wx.WxUserChlRes;
import com.asialjim.microapplet.mams.user.chl.wx.model.WeChatChlUser;
import com.asialjim.microapplet.mams.user.chl.wx.repository.WeChatChlUserRepository;
import com.asialjim.microapplet.mams.user.login.ChannelLoginService;
import com.asialjim.microapplet.mams.user.model.User;
import com.asialjim.microapplet.mams.user.parameter.LoginParameter;
import com.asialjim.microapplet.mams.user.repository.UserRepository;
import com.asialjim.microapplet.mams.user.session.SessionUser;
import com.asialjim.microapplet.wechat.application.WeChatApplication;
import com.asialjim.microapplet.wechat.application.WeChatApplicationRepository;
import com.asialjim.microapplet.wechat.application.WeChatApplicationType;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
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
    @Resource
    private WeChatChlUserRepository weChatChlUserRepository;
    @Resource
    private UserRepository userRepository;


    @Override
    public String chlCode() {
        return "wechat";
    }

    @Override
    public SessionUser login(LoginParameter loginParameter) {
        WeChatApplication weChatApplication = appOfLoginParameter(loginParameter);
        WeChatChlUserLoginService service = wxLoginService(weChatApplication);
        WeChatChlLoginUser login = service.login(weChatApplication, loginParameter);

        WeChatChlUser condition = new WeChatChlUser();
        condition.setOpenid(login.getOpenid());
        condition.setUnionId(login.getUnionId());
        condition.setAppid(login.getWxAppid());
        condition.setAppType(login.getWxAppType());
        WeChatChlUser wxChlUser = this.weChatChlUserRepository.queryOrRegister(condition);

        String userid = wxChlUser.getUserid();
        SessionUser sessionUser = new SessionUser();

        User user;
        if (StringUtils.isBlank(userid)) {
            user = this.userRepository.createInAppid(loginParameter.getAppid(), wxChlUser.getOpenid());
            userid = user.getId();
            wxChlUser.setUserid(userid);
            this.weChatChlUserRepository.update(wxChlUser);
        } else {
            user = this.userRepository.queryByUserid(userid);
            // TODO 如果查询不到该 userid 的用户信息，做补偿处理
        }

        sessionUser.setUserid(userid);
        sessionUser.setNickname(user.getNickname());
        sessionUser.setUsername(user.getUsername());
        return sessionUser;
    }

    private WeChatApplication appOfLoginParameter(LoginParameter loginParameter) {
        WeChatApplication weChatApplication = this.aggregator.appByIndex(loginParameter.getChlAppid());
        if (Objects.isNull(weChatApplication))
            WxUserChlRes.WeChatPlatformAppNotSupport.throwBiz();
        return weChatApplication;
    }

    private WeChatChlUserLoginService wxLoginService(WeChatApplication weChatApplication) {
        String appType = weChatApplication.getAppType();
        WeChatApplicationType weChatApplicationType = WeChatApplicationType.valueOf(appType);
        WeChatChlUserLoginService service = this.loginServiceMap.get(weChatApplicationType);
        if (Objects.nonNull(service))
            return service;

        synchronized (WeChatChannelLoginService.class) {
            service = this.loginServiceMap.get(weChatApplicationType);
            if (Objects.nonNull(service))
                return service;

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

        if (Objects.isNull(service))
            WxUserChlRes.WeChatAppTypeNotSupport.throwBiz();

        return service;
    }
}