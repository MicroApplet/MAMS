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

package com.asialjim.microapplet.mams.applet.vo;

import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlEncType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 渠道应用视图对象
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 05 01, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class ChlAppletVo implements Serializable {
    private static final long serialVersionUID = 2990685338624903882L;

    /**
     * 渠道应用编号:主键
     */
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

    @SuppressWarnings("DuplicatedCode")
    public static ChlAppletVo to(ChlApplet body){
        ChlAppletVo vo = new ChlAppletVo();
        vo.setId(body.getId());
        vo.setAppletId(body.getAppletId());
        vo.setOrgId(body.getOrgId());
        vo.setChlType(Optional.of(body).map(ChlApplet::getChlType).map(ChlType::getCode).orElse(""));
        vo.setChlAppId(body.getChlAppId());
        vo.setChlAppType(Optional.of(body).map(ChlApplet::getChlAppType).map(ChlAppType::getCode).orElse(""));
        vo.setChlAppSecret(body.getChlAppSecret());
        vo.setChlAppName(body.getChlAppName());
        vo.setChlSubjectId(body.getChlSubjectId());
        vo.setChlAgentId(body.getChlAgentId());
        vo.setChlToken(body.getChlToken());
        vo.setChlEncKey(body.getChlEncKey());
        vo.setChlEncType(Optional.of(body).map(ChlApplet::getChlEncType).map(ChlEncType::getCode).orElse(""));
        vo.setUrl(body.getUrl());
        vo.setManager(body.getManager());
        vo.setDescription(body.getDescription());
        vo.setDeleted(body.getDeleted());
        vo.setCreateTime(body.getCreateTime());
        vo.setUpdateTime(body.getUpdateTime());
        return vo;
    }

    @SuppressWarnings("DuplicatedCode")
    public static ChlApplet from(ChlAppletVo body){
        ChlApplet vo = new ChlApplet();
        vo.setId(body.getId());
        vo.setAppletId(body.getAppletId());
        vo.setOrgId(body.getOrgId());
        vo.setChlType(ChlType.codeOf(body.getChlType()));
        vo.setChlAppId(body.getChlAppId());
        vo.setChlAppType(ChlAppType.codeOf(body.getChlAppType()));
        vo.setChlAppSecret(body.getChlAppSecret());
        vo.setChlAppName(body.getChlAppName());
        vo.setChlSubjectId(body.getChlSubjectId());
        vo.setChlAgentId(body.getChlAgentId());
        vo.setChlToken(body.getChlToken());
        vo.setChlEncKey(body.getChlEncKey());
        vo.setChlEncType(ChlEncType.codeOf(body.getChlEncType()));
        vo.setUrl(body.getUrl());
        vo.setManager(body.getManager());
        vo.setDescription(body.getDescription());
        vo.setDeleted(body.getDeleted());
        vo.setCreateTime(body.getCreateTime());
        vo.setUpdateTime(body.getUpdateTime());
        return vo;
    }
}