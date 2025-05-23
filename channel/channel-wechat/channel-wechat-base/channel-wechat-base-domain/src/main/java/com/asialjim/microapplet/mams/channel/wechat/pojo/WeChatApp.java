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

package com.asialjim.microapplet.mams.channel.wechat.pojo;

import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlEncType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 微信应用
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 3.0
 * @since 2025/4/25, &nbsp;&nbsp; <em>version:3.0</em>
 */
@Data
@Accessors(chain = true)
public class WeChatApp implements Serializable {
    private static final long serialVersionUID = -4318694778345128619L;
    /**
     * 主键
     */
    private String id;
    /**
     * 所属应用编号
     */
    private String appletId;

    /**
     * 渠道应用编号
     */
    private String appid;
    /**
     * 渠道应用类型
     */
    private ChlAppType appType;
    /**
     * 渠道应用密钥
     */
    private String appSecret;

    /**
     * 渠道应用名称
     */
    private String name;

    /**
     * 同微信公众平台subjectId
     */
    private String subjectId;
    /**
     * 同企业微信:   agentId
     */
    private String agentId;

    /**
     * 开发者配置的令牌
     */
    private String token;

    /**
     * 加密密钥:aesKey,rsaKey,M2Key..
     */
    private String aesKey;

    /**
     * 加密方式：
     */
    private ChlEncType encType;

    /**
     * 处理器链接, 即： redirectUrl
     */
    private String url;

    /**
     * 管理者 用户编号 列表, 以 ‘，’ 分隔
     */
    private String manager;

    /**
     * 描述
     */
    private String description;

    /**
     * 逻辑删除标识
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public final ChlType getChlType() {
        return ChlType.WeChat;
    }
    public static void populateFromChlAppletToWeChatApp(WeChatApp app, ChlApplet applet) {
        app.setAppletId(applet.getAppletId());
        app.setId(applet.getId());
        app.setAppid(applet.getChlAppId());
        app.setAppType(applet.getChlAppType());
        app.setAppSecret(applet.getChlAppSecret());
        app.setName(applet.getChlAppName());
        app.setSubjectId(applet.getChlSubjectId());
        app.setAgentId(applet.getChlAgentId());
        app.setToken(applet.getChlToken());
        app.setAesKey(applet.getChlEncKey());
        app.setEncType(applet.getChlEncType());
        app.setUrl(applet.getUrl());
        app.setManager(applet.getManager());
        app.setDescription(applet.getDescription());
        app.setDeleted(applet.getDeleted());
        app.setCreateTime(applet.getCreateTime());
        app.setUpdateTime(applet.getUpdateTime());
    }
}