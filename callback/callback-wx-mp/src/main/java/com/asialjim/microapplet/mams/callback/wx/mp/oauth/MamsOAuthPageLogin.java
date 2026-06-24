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

package com.asialjim.microapplet.mams.callback.wx.mp.oauth;

import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.commons.chl.SupportPlatformAppType;
import com.asialjim.microapplet.mams.chl.wx.core.application.WeChatApplication;
import com.asialjim.microapplet.mams.chl.wx.core.application.WeChatApplicationRepository;
import com.asialjim.microapplet.session.LoginReqVo;
import com.asialjim.microapplet.session.Session;
import com.asialjim.microapplet.session.SessionRepository;
import com.asialjim.microapplet.session.SessionTokenBean;
import com.asialjim.microapplet.spring.App;
import com.asialjim.microapplet.user.cloud.ChlUserCloud;
import com.asialjim.microapplet.user.cloud.ChlUserLoginLogCloud;
import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.event.ChlUserLoginEvent;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * MAMS 微信网页授权登录
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class MamsOAuthPageLogin implements OAuthPageLogin {
    @Resource
    private WeChatApplicationRepository applicationRepository;
    @Resource
    private ChlUserLoginLogCloud chlUserLoginLogCloud;
    @Resource
    private SessionRepository sessionRepository;
    @Resource
    private ChlUserCloud chlUserCloud;

    @Override
    public String login(String appid, String code, WeChatOAuthAccessTokenRes accessToken) {
        WeChatApplication app = applicationRepository.resolve(appid);
        SupportPlatformAppType appType = SupportPlatformAppType.WeChatOfficial;
        ChlUserVo body = new ChlUserVo();
        body.setPlatformType(appType.getPlatformType().getCode());
        body.setPlatformId(app.getSubjectId());
        body.setAppId(app.getAppid());
        body.setAppType(appType.getCode());
        body.setOpenid(accessToken.getOpenid());
        body.setUnionid(accessToken.getUnionid());
        body.setUserCode(code);
        body.setUserToken(accessToken.getAccessToken());

        ChlUserVo register = chlUserCloud.register(body);
        Session session = new Session();
        session.setId(appType.uniCode() + ":" + UUID.randomUUID().toString().replace("-", StringUtils.EMPTY));
        session.setToken(SessionTokenBean.create());
        session.setUserid(register.getUserId());
        session.setUserCode(register.getId());
        session.setPlatType(appType.getPlatformType().getCode());
        session.setAppid(app.getAppid());
        session.setAppType(appType.getCode());
        session.setOpenid(accessToken.getOpenid());
        session.setUnionid(accessToken.getUnionid());
        session.setSessionKey(accessToken.getAccessToken());

        AppVo appVo = new AppVo();
        appVo.setId(app.getId());
        appVo.setName(app.getName());
        appVo.setPlatformType(session.getPlatType());
        appVo.setPlatformId(app.getSubjectId());
        appVo.setAppId(app.getAppid());
        appVo.setAppType(session.getAppType());
        appVo.setSubjectId(app.getSubjectId());
        LoginReqVo req = new LoginReqVo();
        req.setCode(code);
        //App.publish(new ChlUserLoginEvent(session, req, appVo));

        String lastLoginTime = chlUserLoginLogCloud.record(session.platformTypeCode(), session.getAppid(), session.getOpenid());
        if (StringUtils.isNotBlank(lastLoginTime)) {
            session.setLastLoginTime(LocalDateTime.parse(lastLoginTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        }
        sessionRepository.save(session);
        return session.getToken();
    }
}
