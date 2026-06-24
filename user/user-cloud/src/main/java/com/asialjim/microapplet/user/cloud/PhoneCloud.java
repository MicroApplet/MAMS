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

package com.asialjim.microapplet.user.cloud;

import com.asialjim.microapplet.user.api.PhoneApi;
import com.asialjim.microapplet.user.entity.web.req.PhoneSmsTokenCheckReq;
import com.asialjim.microapplet.user.entity.web.req.PhoneSmsTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.res.PhoneSmsTokenObtainRes;
import com.asialjim.microapplet.web.client.annotation.HttpExchangeClient;
import com.asialjim.microapplet.user.cons.UserCons;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = PhoneApi.path)
@HttpExchangeClient(UserCons.serverName)
public interface PhoneCloud extends PhoneApi {

    /**
     * 获取短信验证码
     *
     * @param body {@link PhoneSmsTokenObtainReq body}
     * @return {@link PhoneSmsTokenObtainRes }
     * @since 2026/3/5
     */
    @PostExchange("/obtain")
    PhoneSmsTokenObtainRes obtain(@RequestBody PhoneSmsTokenObtainReq body);

    /**
     * 手机号验证
     * @param body {@link PhoneSmsTokenCheckReq body}
     * @since 2026/3/5
     */
    @PostExchange("/check")
    String check(@RequestBody PhoneSmsTokenCheckReq body);

    @PostExchange("/preCheck")
    String preCheck(@RequestBody PhoneSmsTokenCheckReq preBody);
}