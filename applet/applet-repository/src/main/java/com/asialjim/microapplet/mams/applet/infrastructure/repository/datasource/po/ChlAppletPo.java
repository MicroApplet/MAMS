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

package com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.po;

import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlEncType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * 渠道应用ORM
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/15, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("chl_app")
public class ChlAppletPo implements Serializable {
    @Serial
    private static final long serialVersionUID = 6124621276650010767L;

    /**
     * 渠道应用编号:主键
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    /**
     * 应用编号
     *
     * @see Applet#getId()
     */
    private String appletId;

    /**
     * 所属组织机构代码
     *
     * @see Applet#getOrgId()
     * @see com.asialjim.microapplet.mams.org.Organization#getId()
     */
    @SuppressWarnings("JavadocReference")
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
     * 同企业微信: agentId
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

    public static ChlAppletPo toPo(ChlApplet body) {
        if (Objects.isNull(body))
            return null;
        ChlAppletPo po = new ChlAppletPo();
        po.setId(body.getId());
        po.setAppletId(body.getAppletId());
        po.setOrgId(body.getOrgId());
        po.setChlType(Optional.of(body).map(ChlApplet::getChlType).map(ChlType::getCode).orElse(""));
        po.setChlAppId(body.getChlAppId());
        po.setChlAppType(Optional.of(body).map(ChlApplet::getChlAppType).map(ChlAppType::getCode).orElse(""));
        po.setChlAppSecret(body.getChlAppSecret());
        po.setChlAppName(body.getChlAppName());
        po.setChlSubjectId(body.getChlSubjectId());
        po.setChlAgentId(body.getChlAgentId());
        po.setChlToken(body.getChlToken());
        po.setChlEncKey(body.getChlEncKey());
        po.setChlEncType(Optional.of(body).map(ChlApplet::getChlEncType).map(ChlEncType::getCode).orElse(""));
        po.setUrl(body.getUrl());
        po.setManager(body.getManager());
        po.setDescription(body.getDescription());
        po.setDeleted(body.getDeleted());
        po.setCreateTime(body.getCreateTime());
        po.setUpdateTime(body.getUpdateTime());

        return po;
    }

    public static ChlApplet fromPo(ChlAppletPo body) {
        if (Objects.isNull(body))
            return null;
        ChlApplet po = new ChlApplet();
        po.setId(body.getId());
        po.setAppletId(body.getAppletId());
        po.setOrgId(body.getOrgId());
        po.setChlType(ChlType.codeOf(body.getChlType()));
        po.setChlAppId(body.getChlAppId());
        ChlAppType chlAppType = ChlAppType.codeOf(body.getChlAppType());
        po.setChlAppType(chlAppType);
        po.setChlAppSecret(body.getChlAppSecret());
        po.setChlAppName(body.getChlAppName());
        po.setChlSubjectId(body.getChlSubjectId());
        po.setChlAgentId(body.getChlAgentId());
        po.setChlToken(body.getChlToken());
        po.setChlEncKey(body.getChlEncKey());
        ChlEncType encType = ChlEncType.codeOf(body.getChlEncType());
        po.setChlEncType(encType);
        po.setUrl(body.getUrl());
        po.setManager(body.getManager());
        po.setDescription(body.getDescription());
        po.setDeleted(body.getDeleted());
        po.setCreateTime(body.getCreateTime());
        po.setUpdateTime(body.getUpdateTime());

        return po;
    }
}