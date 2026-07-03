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

import com.asialjim.microapplet.app.cloud.ApplicationCloud;
import com.asialjim.microapplet.app.code.AppCode;
import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.commons.chl.PlatformAppType;
import com.asialjim.microapplet.commons.chl.PlatformType;
import com.asialjim.microapplet.session.*;
import com.asialjim.microapplet.spring.App;
import com.asialjim.microapplet.user.api.ChlUserApi;
import com.asialjim.microapplet.user.code.ChlUserCode;
import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.event.ChlUserLoginEvent;
import com.asialjim.microapplet.user.service.login.PlatformAppLoginHandler;
import com.asialjim.microapplet.web.client.MamsHttpHeaders;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class LoginService {
    private static final Map<PlatformAppType, PlatformAppLoginHandler> handlerMap = new ConcurrentHashMap<>();

    @Resource
    private List<PlatformAppLoginHandler> platformAppLoginHandlers;
    @Resource
    private ApplicationCloud applicationCloud;
    @Resource
    private ChlUserApi chlUserApi;
    @Resource
    private SessionCtx sessionCtx;


    @Profile("dev")
    public String login(String chlType, String code) {
        String token = SessionTokenBean.create();

        ChlUserVo chlUserVo = this.chlUserApi.queryByChlTypeAndOpenid(chlType, code);
        if (Objects.isNull(chlUserVo)) ChlUserCode.NoSuchChannelUser.thr();

        Session userSession = new Session();
        String platformType = chlUserVo.getPlatformType();
        String appType = chlUserVo.getAppType();
        PlatformAppType platformAppType = PlatformAppType.codeOf(platformType, appType);
        userSession.setPlatType(platformType);
        userSession.setAppid(chlUserVo.getAppId());
        userSession.setOpenid(chlUserVo.getOpenid());
        userSession.setUnionid(chlUserVo.getUnionid());
        userSession.setSessionKey(chlUserVo.getUserToken());

        userSession.setId(platformAppType.uniCode() + ":" + UUID.randomUUID().toString().replace("-", StringUtils.EMPTY));
        userSession.setToken(token);
        userSession.setTrace(MDC.get(MamsHttpHeaders.TRACE_ID));
        LoginReqVo req = new LoginReqVo();
        req.setCode(code);
        AppVo app = this.applicationCloud.queryAppByAppidAndHostOrPlatformType(StringUtils.EMPTY, platformType, chlUserVo.getAppId(), "暂不支持该APPID登录", "请指定登录渠道");
        App.publish(new ChlUserLoginEvent(userSession, req, app));
        this.sessionCtx.login(userSession);
        return token;
    }

    public String login(String requestChannel, String appid, LoginReqVo req) {
        AppVo app = this.applicationCloud.queryAppByAppidAndHostOrPlatformType(StringUtils.EMPTY, requestChannel, appid, "暂不支持该APPID登录", "请指定登录渠道");
        if (Objects.isNull(app))
            AppCode.NoSuchAppidErr.thr(requestChannel,appid);
        PlatformType platformType = PlatformType.of(app.getPlatformType());
        PlatformAppType platformAppType = PlatformAppType.codeOf(platformType.getCode(), app.getAppType());

        PlatformAppLoginHandler handler = this.handlerOf(platformAppType);
        Session session = handler.login(appid, app, req);
        log.info("""
                
                开放平台应用: {}
                用户登录参数: {}
                用户登录结果: {}
                """, app, req, session);
        if (Objects.isNull(session))
            SessionResCode.LoginFailure.thr();

        String token = SessionTokenBean.create();
        session.setId(platformAppType.uniCode() + ":" + UUID.randomUUID().toString().replace("-", StringUtils.EMPTY));
        session.setTrace(MDC.get(MamsHttpHeaders.TRACE_ID));
        session.setToken(token);
        session.setPlatType(platformType.getCode());
        session.setAppid(app.getAppId());
        session.setAppType(platformAppType.getCode());
        session.setLoginTime(LocalDateTime.now());
        session.setExpireAt(LocalDateTime.now().minusMinutes(30));

        App.publish(new ChlUserLoginEvent(session, req, app));
        this.sessionCtx.login(session);
        return session.getToken();
    }

    private PlatformAppLoginHandler handlerOf(PlatformAppType platformAppType) {
        return handlerMap.computeIfAbsent(platformAppType,
                type -> this.platformAppLoginHandlers.stream()
                        .filter(Objects::nonNull)
                        .filter(item -> item.support(type))
                        .findFirst()
                        .orElseThrow(() -> SessionResCode.UnSupportPlatformAppType.exWithData("登录业务"))
        );
    }
}