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

import com.asialjim.microapplet.user.api.IdCardUserApi;
import com.asialjim.microapplet.user.entity.vo.IdCardUserVo;
import com.asialjim.microapplet.web.client.annotation.HttpExchangeClient;
import com.asialjim.microapplet.user.cons.UserCons;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url = IdCardUserApi.path)
@HttpExchangeClient(UserCons.serverName)
public interface IdCardUserCloud extends IdCardUserApi {

    @GetExchange("/query")
    IdCardUserVo query(@RequestParam String platformType,
                       @RequestParam String appid,
                       @RequestParam String openid,
                       @RequestParam String idType);

    @GetExchange("/queryList")
    List<IdCardUserVo> queryList(@RequestParam String platformType,
                                 @RequestParam String appid,
                                 @RequestParam String openid);

    /**
     * 通过 openid 进行证件绑定
     * 如果绑定失败则会抛出异常
     * 需要短信验证码处理
     * 需要
     * <pre>
     *     uam id       维护
     *     ecif id      维护
     *     SESSION-ID   维护
     * </pre>
     *
     * @param body   {@link IdCardUserVo body}
     * @return {@link Boolean }
     */
    @GetExchange("/link")
    IdCardUserVo linkIdCard(@RequestBody IdCardUserVo body);
}