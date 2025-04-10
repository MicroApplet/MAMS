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

package com.asialjim.microapplet.mams.user.vo;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户注册请求
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class UserRegReq implements Serializable {
    private static final long serialVersionUID = -6188159651042396287L;

    /**
     * 应用编号
     */
    private String appletId;

    /**
     * 渠道类型
     */
    private ChlType chlType;

    /**
     * 渠道应用编号
     */
    private String chlAppId;

    /**
     * 渠道应用类型
     */
    private ChlAppType chlAppType;

    /**
     * 登录用户名
     */
    private String username;
    /**
     * 登录用户认证码：
     * <ul>
     *     <li>H5登录：密码</li>
     *     <li>WeChat登录：用户授权码</li>
     * </ul>
     */
    private String code;
}