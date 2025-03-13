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

package io.github.microapplet.mams.user.register;

import io.github.microapplet.mams.user.model.User;
import io.github.microapplet.mams.user.parameter.RegisterUserParameter;
import io.github.microapplet.mams.user.res.UserResCode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 用户登录服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserRegisterService {
    private Map<String, ChannelUserRegisterService> registerServiceMap;

    @PostConstruct
    public void init() {
        if (Objects.isNull(this.registerServiceMap))
            this.registerServiceMap = new HashMap<>();
    }

    @Autowired(required = false)
    public void setChannelLoginServices(List<ChannelUserRegisterService> services) {
        init();
        if (CollectionUtils.isEmpty(services))
            return;
        for (ChannelUserRegisterService service : services) {
            this.registerServiceMap.put(service.chlCode(), service);
        }
    }

    public User register(RegisterUserParameter parameter) {
        String chlCode = parameter.getChlCode();
        ChannelUserRegisterService service = this.registerServiceMap.get(chlCode);
        if (Objects.isNull(service))
            UserResCode.UnSupportLoginChl.throwBiz();
        User user = service.register(parameter);
        user.setPassword("");
        return user;
    }
}