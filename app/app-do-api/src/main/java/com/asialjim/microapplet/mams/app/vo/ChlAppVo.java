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

package com.asialjim.microapplet.mams.app.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 渠道应用视图对象
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 05 01, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class ChlAppVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 2990685338624903882L;

    /**
     * 渠道应用编号:主键
     */
    private String id;
    /**
     * 应用编号
     *
     * @see AppVo#getId()
     */
    private String appid;

    /**
     * 所属组织机构代码
     *
     * @see AppVo#getOrgId()
     * @see OrgVo#getId()
     */
    private String orgId;

    /**
     * 渠道类型
     */
    private String chlType;

    /**
     * 渠道应用编号
     */
    private String chlAppId;
    /**
     * 渠道应用类型
     */
    private String chlAppType;
    /**
     * 渠道应用密钥
     */
    private String chlAppSecret;

    /**
     * 渠道应用名称
     */
    private String chlAppName;

    /**
     * 同微信公众平台subjectId
     */
    private String chlSubjectId;
    /**
     * 同企业微信:   agentId
     */
    private String chlAgentId;

    /**
     * 开发者配置的令牌
     */
    private String chlToken;

    /**
     * 加密密钥:aesKey,rsaKey,M2Key..
     */
    private String chlEncKey;

    /**
     * 加密方式：
     */
    private String chlEncType;

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
}