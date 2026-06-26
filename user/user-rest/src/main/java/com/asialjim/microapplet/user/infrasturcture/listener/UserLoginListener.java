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

package com.asialjim.microapplet.user.infrasturcture.listener;

import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.session.*;
import com.asialjim.microapplet.user.cloud.ChlUserCloud;
import com.asialjim.microapplet.user.cloud.ChlUserLoginLogCloud;
import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.event.ChlUserLoginEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Component
public class UserLoginListener implements ApplicationListener<ChlUserLoginEvent> {
    @Resource
    private ChlUserLoginLogCloud chlUserLoginLogCloud;
    @Resource
    private SessionCtx sessionCtx;
    @Resource
    private ChlUserCloud chlUserCloud;

    @Override
    @SuppressWarnings("NullableProblems")
    public void onApplicationEvent(ChlUserLoginEvent event) {

        if (Objects.isNull(event))
            return;
        Session userSession = event.session();
        if (Objects.isNull(userSession))
            return;

        LoginReqVo req = event.req();
        AppVo app = event.app();

        String code = req.getCode();
        String anonymousCode = req.getAnonymousCode();
        if (StringUtils.isNotBlank(code)) {
            String openid = userSession.getOpenid();
            if (StringUtils.isBlank(openid))
                SessionResCode.LoginFailure.thr(userSession.platformAppType().getPlatformType().getName() + "用户登录失败");

            ChlUserVo body = new ChlUserVo();
            body.setPlatformType(app.getPlatformType());
            body.setPlatformId(app.getPlatformId());
            body.setAppId(app.getAppId());
            body.setAppType(app.getAppType());
            body.setOpenid(openid);
            body.setUnionid(userSession.getUnionid());
            body.setUserCode(req.getCode());
            body.setUserToken(userSession.getSessionKey());
            ChlUserVo register = this.chlUserCloud.register(body);

            log.info("注册渠道用户成功：{}", register);
            userSession.setUserid(register.getUserId());
            userSession.setUserCode(register.getId());

            // 计算用户上次登录时间
            String platformTypeCode = userSession.platformTypeCode();
            String appid = userSession.getAppid();
            String lastLoginTime = this.chlUserLoginLogCloud.record(platformTypeCode, appid, openid);
            if (StringUtils.isNotBlank(lastLoginTime)) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                LocalDateTime loginTime = LocalDateTime.parse(lastLoginTime, dateTimeFormatter);
                userSession.setLastLoginTime(loginTime);
            }
        }
        if (StringUtils.isNotBlank(anonymousCode)) {
            log.info("当前用户：{} 以匿名方式登录，不注册渠道用户", userSession);
        }
        this.sessionCtx.save(userSession);
    }
}