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

package com.asialjim.microapplet.user.api;

import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.entity.web.req.ChlUserLinkPhoneReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 渠道用户API
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface ChlUserApi {
    String path = "/chl-user";

    /**
     * 渠道用户绑定手机号: 微信、支付宝等渠道用户绑定平台账户
     * 授权手机号处理，而非三要素实名认证
     * 一要素认证
     *
     * @param body {@link ChlUserLinkPhoneReq body}
     * @return {@link Boolean }
     * @since {@code }
     */
    @PostMapping("/auth-phone")
    Boolean chlUserHadLinkPhone(@RequestBody ChlUserLinkPhoneReq body);

    /**
     * 注册渠道用户
     *
     * @param body   {@link ChlUserVo body}
     * @return {@link ChlUserVo }
     * @since 2026/3/5
     */
    @PostMapping("/register")
    ChlUserVo register(@RequestBody ChlUserVo body);

    /**
     * 获取渠道用户信息
     *
     * @return {@link ChlUserVo }
     * @since 2026/3/5
     */
    @GetMapping("/current")
    ChlUserVo current();

    /**
     * 获取渠道用户信息
     *
     * @param id {@link String id}
     * @return {@link ChlUserVo }
     * @since 2026/3/5
     */
    @GetMapping("/by-id/{id}")
    ChlUserVo queryById(@PathVariable String id);

    @GetMapping("/by-chl/{chlType}/by-openid/{openid}")
    ChlUserVo queryByChlTypeAndOpenid(@PathVariable String chlType, @PathVariable String openid);

    /**
     * 根据主用户编号获取渠道用户列表
     *
     * @param userid {@link String userid}
     * @return {@link List<ChlUserVo> }
     * @since 2026/3/5
     */
    @GetMapping("/by-userid/{userid}")
    List<ChlUserVo> queryByUserid(@PathVariable String userid);


    /**
     * 根据unionid获取渠道用户列表
     *
     * @param unionid {@link String unionid}
     * @return {@link List<ChlUserVo> }
     * @since 2026/3/5
     */
    @GetMapping("/by-unionid/{unionid}")
    List<ChlUserVo> queryByUnionid(@PathVariable String unionid);
}