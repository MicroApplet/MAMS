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

import com.asialjim.microapplet.user.api.ChlUserApi;
import com.asialjim.microapplet.user.cons.UserCons;
import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.entity.web.req.ChlUserLinkPhoneReq;
import com.asialjim.microapplet.web.client.annotation.HttpExchangeClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = ChlUserApi.path)
@HttpExchangeClient(UserCons.serverName)
public interface ChlUserCloud extends ChlUserApi {

    /**
     * 渠道用户绑定手机号: 微信、支付宝等渠道用户绑定平台账户
     * 授权手机号处理，而非三要素实名认证
     * 一要素认证
     *
     * @param body {@link ChlUserLinkPhoneReq body}
     * @return {@link Boolean }
     * @since {@code }
     */
    @PostExchange("/auth-phone")
    Boolean chlUserHadLinkPhone(@RequestBody ChlUserLinkPhoneReq body);

    /**
     * 注册渠道用户
     *
     * @param body   {@link ChlUserVo body}
     * @return {@link ChlUserVo }
     * @since 2026/3/5
     */
    @PostExchange("/register")
    ChlUserVo register(@RequestBody ChlUserVo body);

    /**
     * 获取渠道用户信息
     *
     * @return {@link ChlUserVo }
     * @since 2026/3/5
     */
    @GetExchange("/current")
    ChlUserVo current();

    /**
     * 获取渠道用户信息
     *
     * @param id {@link String id}
     * @return {@link ChlUserVo }
     * @since 2026/3/5
     */
    @GetExchange("/by-id/{id}")
    ChlUserVo queryById(@PathVariable String id);

    @GetExchange("/by-chl/{chlType}/by-openid/{openid}")
    ChlUserVo queryByChlTypeAndOpenid(@PathVariable String chlType, @PathVariable String openid);


    /**
     * 根据主用户编号获取渠道用户列表
     *
     * @param userid {@link String userid}
     * @return {@link List <ChlUserVo> }
     * @since 2026/3/5
     */
    @GetExchange("/by-userid/{userid}")
    List<ChlUserVo> queryByUserid(@PathVariable String userid);


    /**
     * 根据unionid获取渠道用户列表
     *
     * @param unionid {@link String unionid}
     * @return {@link List<ChlUserVo> }
     * @since 2026/3/5
     */
    @GetExchange("/by-unionid/{unionid}")
    List<ChlUserVo> queryByUnionid(@PathVariable String unionid);
}