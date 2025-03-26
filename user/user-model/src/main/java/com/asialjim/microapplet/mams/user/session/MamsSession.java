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

package com.asialjim.microapplet.mams.user.session;

import java.util.Date;

/**
 * 会话
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/26, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface MamsSession {

    /**
     * 会话编号
     */
    String getId();

    /**
     * 用户认证令牌
     */
    String getAuthorization();

    /**
     * 登录应用编号:Application@getId
     */
    String getAppid();

    /**
     * 用户编号
     */
    String getUserid();

    /**
     * 登录渠道编号:H5/wechat/Telecom
     */
    String getChlCode();

    /**
     * 登录渠道应用编号:H5/wechat-appid/
     */
    String getChlAppid();

    /**
     * 登录渠道应用类型:H5/wechat-app-type
     */
    String getChlAppType();

    String getChlOpenid();
    String getChlUnionId();

    /**
     * 用户昵称
     */
    String getNickname();

    /**
     * 用户编号：username, phone, email
     */
    String getUsername();

    /**
     * 用户令牌：password, smsToken, code
     */
    String getUserCode();

    /**
     * 用户登录时间
     */
    Date getIssueAt();
    void logout();
}