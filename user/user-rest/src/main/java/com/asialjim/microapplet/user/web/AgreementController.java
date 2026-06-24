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


import com.asialjim.microapplet.app.entity.web.AppAgreementVo;
import com.asialjim.microapplet.user.agreement.entity.web.req.SimpleSignAgreementReq;
import com.asialjim.microapplet.user.agreement.process.AgreementSigner;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 个保法：
 * 个人授权协议相关功能
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/4/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequestMapping("/agreement")
public class AgreementController {
    @Resource
    private AgreementSigner agreementSigner;

    /**
     * 用户授权协议
     *
     * @param body {@link List<SimpleSignAgreementReq> body}
     * @since 2026/4/13
     */
    @PostMapping(value = "/authorize")
    public void authorize(@RequestBody List<SimpleSignAgreementReq> body) {
        this.agreementSigner.authorize(body);
    }

    /**
     * 查询可签约的协议表
     *
     * @return {@link List<AppAgreementVo> }
     * @since 2026/4/2
     */
    @GetMapping(value = "/authorizable/list")
    public List<AppAgreementVo> authorizableList() {
        return this.agreementSigner.authorizableList();
    }


    /**
     * 查询当前用户已授权协议表
     *
     * @return {@link List<AppAgreementVo> }
     * @since 2026/4/2
     */
    @GetMapping(value = "/authorized/list")
    public List<AppAgreementVo> authorized() {
        return this.agreementSigner.authorized();
    }

    /**
     * 撤销授权
     */
    @PostMapping(value = "/revoke")
    public void revoke() {
        this.agreementSigner.revoke();
    }
}