/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.user.chl.wx.session;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信渠道登录用户
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class WeChatChlLoginUser implements Serializable {
    private static final long serialVersionUID = -5030753457104268490L;

    /**
     * 微信公众平台用户编号
     */
    private String openid;
    /**
     * 微信公众平台应用编号
     */
    private String wxAppid;
    /**
     * 微信公众平台应用类型
     */
    private String wxAppType;
    /**
     * 微信公众平台应用联合编号
     */
    private String unionId;
    /**
     * 微信公众平台用户授权手机号
     */
    private String phone;

}