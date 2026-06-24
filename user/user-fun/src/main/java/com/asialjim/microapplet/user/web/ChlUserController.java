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

package com.asialjim.microapplet.user.web;

import com.asialjim.microapplet.user.api.ChlUserApi;
import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.entity.web.req.ChlUserLinkPhoneReq;
import com.asialjim.microapplet.user.service.ChlUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 渠道用户入口
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/6, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@AllArgsConstructor
@RequestMapping(ChlUserApi.path)
public class ChlUserController implements ChlUserApi {
    private final ChlUserService chlUserService;

    /**
     * 渠道用户绑定手机号: 微信、支付宝等渠道用户绑定平台账户
     * 授权手机号处理，而非三要素实名认证
     * 一要素认证
     *
     * @param body {@link ChlUserLinkPhoneReq body}
     * @return {@link Boolean }
     * @since {@code }
     */
    @Override
    public Boolean chlUserHadLinkPhone(ChlUserLinkPhoneReq body) {
        // TODO
        return null;
    }

    /**
     * 注册渠道用户
     *
     * @param body {@link ChlUserVo body}
     * @return {@link ChlUserVo }
     * @since 2026/3/5
     */
    @Override
    public ChlUserVo register(ChlUserVo body) {
        return this.chlUserService.register(body);
    }

    /**
     * 获取渠道用户信息
     *
     * @return {@link ChlUserVo }
     * @since 2026/3/5
     */
    @Override
    public ChlUserVo current() {
        return this.chlUserService.current();
    }

    /**
     * 获取渠道用户信息
     *
     * @param id {@link String id}
     * @return {@link ChlUserVo }
     * @since 2026/3/5
     */
    @Override
    public ChlUserVo queryById(String id) {
        return this.chlUserService.queryById(id);
    }

    @Override
    public ChlUserVo queryByChlTypeAndOpenid(String chlType, String openid) {
        return this.chlUserService.queryByChlTypeAndOpenid(chlType,openid);
    }

    /**
     * 根据主用户编号获取渠道用户列表
     *
     * @param userid {@link String userid}
     * @return {@link List <ChlUserVo> }
     * @since 2026/3/5
     */
    @Override
    public List<ChlUserVo> queryByUserid(String userid) {
        return List.of();
    }

    /**
     * 根据unionid获取渠道用户列表
     *
     * @param unionid {@link String unionid}
     * @return {@link List <ChlUserVo> }
     * @since 2026/3/5
     */
    @Override
    public List<ChlUserVo> queryByUnionid(String unionid) {
        return List.of();
    }
}
