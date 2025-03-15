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

package io.github.microapplet.mams.user.login;

import io.github.microapplet.mams.user.config.JwtConfProperty;
import io.github.microapplet.mams.user.jwt.JwtTokenCache;
import io.github.microapplet.mams.user.parameter.LoginParameter;
import io.github.microapplet.mams.user.res.UserResCode;
import io.github.microapplet.mams.user.session.SessionUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * 用户登录服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class LoginService {
    private Map<String, ChannelLoginService> channelLoginServiceMap;
    @Resource
    private JwtConfProperty jwtConfProperty;
    @Resource
    private JwtTokenCache jwtTokenCache;

    @PostConstruct
    public void init() {
        if (Objects.isNull(this.channelLoginServiceMap))
            this.channelLoginServiceMap = new HashMap<>();
    }

    @Autowired(required = false)
    public void setChannelLoginServices(List<ChannelLoginService> services) {
        init();
        if (CollectionUtils.isEmpty(services))
            return;
        for (ChannelLoginService service : services) {
            this.channelLoginServiceMap.put(service.chlCode(), service);
        }
    }

    public SessionUser login(LoginParameter loginParameter) {
        String chlCode = loginParameter.getChlCode();
        ChannelLoginService service = this.channelLoginServiceMap.get(chlCode);
        if (Objects.isNull(service))
            UserResCode.UnSupportLoginChl.throwBiz();
        SessionUser login = service.login(loginParameter);
        login.setChlCode(chlCode);
        login.setChlAppid(loginParameter.getChlAppid());
        login.setChlAppType(loginParameter.getChlAppType());
        login.setUserCode(loginParameter.getUserCode());
        login.setIssueAt(new Date(System.currentTimeMillis()));
        login.authorization(StringUtils.EMPTY, this.jwtConfProperty, this.jwtTokenCache);
        return login;
    }
}