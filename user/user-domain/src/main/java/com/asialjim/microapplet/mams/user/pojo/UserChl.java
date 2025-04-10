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

package com.asialjim.microapplet.mams.user.pojo;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 渠道用户信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class UserChl implements Serializable {
    private static final long serialVersionUID = 2581501257753639548L;

    /**
     * 全局主键
     */
    private String id;

    /**
     * 用户编号
     *
     * @see UserMain#getId()
     */
    private String userid;

    /**
     * 用户所属机构号
     *
     * @see UserMain#getAppletId()
     */
    private String appletId;

    /**
     * 所属渠道
     */
    private ChlType chlType;

    /**
     * 所属渠道应用类型
     */
    private ChlAppType chlAppType;
    /**
     * 所属渠道应用编号
     */
    private String chlAppId;

    /**
     * 渠道应用编号
     * <ul>
     *     <li>H5:username</li>
     *     <li>Phone:手机号</li>
     *     <li>WeChat:OpenId</li>
     *     <li>Email:邮箱</li>
     *     <li>...</li>
     * </ul>
     */
    private String chlUserId;
    /**
     * 渠道应用编号
     * <ul>
     *     <li>H5:userid</li>
     *     <li>Phone:idNo</li>
     *     <li>WeChat:UnionId</li>
     *     <li>Email:Userid</li>
     *     <li>...</li>
     * </ul>
     */
    private String chlUnionId;
    /**
     * 渠道应用认证码
     * <ul>
     *     <li>H5:密码</li>
     *     <li>Phone:短信验证码</li>
     *     <li>WeChat:用户授权码</li>
     *     <li>Email:邮箱验证码</li>
     *     <li>...</li>
     * </ul>
     */
    private String chlUserCode;

    /**
     * 渠道应用用户认证码:access-token,sessionKey...
     */
    private String chlUserToken;
}