/*
 *    Copyright 2014-$year.today <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.app.infrastructure.datasource.po;

import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.asialjim.microapplet.mybatis.flex.MyBatisFlexIdUtils.SNOW_FLAKE_ID_KEY_GENERATOR;

/**
 * chl_app
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("chl_app")
@Accessors(chain = true)
public class ChlAppPo implements Serializable {

    @Serial
    private static final long serialVersionUID = 8150722772961566010L;

    @Id(keyType = KeyType.None)
    private String id;

    public String getId() {
        if (StringUtils.isNotBlank(this.id))
            return this.id;

        return String.valueOf(SNOW_FLAKE_ID_KEY_GENERATOR.nextId());
    }
    private String appid;

    private String orgId;
    private String chlType;
    private String chlAppid;
    private String chlAppType;
    private String chlAppSecret;
    private String chlAppName;
    private String chlSubjectId;
    private String chlAgentId;
    private String chlToken;
    private String chlEncKey;
    private String chlEncType;
    private String url;
    private String manager;
    private String description;
    private Boolean deleted;
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    public static ChlAppVo toVo(ChlAppPo po){
        if(Objects.isNull(po))
            return null;

        ChlAppVo vo = new ChlAppVo();
        vo.setId(po.getId());
        vo.setAppid(po.getAppid());
        vo.setOrgId(po.getOrgId());
        vo.setChlType(po.getChlType());
        vo.setChlAppId(po.getChlAppid());
        vo.setChlAppType(po.getChlAppType());
        vo.setChlAppSecret(po.getChlAppSecret());
        vo.setChlAppName(po.getChlAppName());
        vo.setChlSubjectId(po.getChlSubjectId());
        vo.setChlAgentId(po.getChlAgentId());
        vo.setChlToken(po.getChlToken());
        vo.setChlEncKey(po.getChlEncKey());
        vo.setChlEncType(po.getChlEncType());
        vo.setUrl(po.getUrl());
        vo.setManager(po.getManager());
        vo.setDescription(po.getDescription());
        vo.setDeleted(po.getDeleted());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return vo;
    }

    public static ChlAppPo fromVo(ChlAppVo vo) {
        if(Objects.isNull(vo))
            return null;

        ChlAppPo po = new ChlAppPo();
        po.setId(vo.getId());
        po.setAppid(vo.getAppid());
        po.setOrgId(vo.getOrgId());
        po.setChlType(vo.getChlType());
        po.setChlAppid(vo.getChlAppId());
        po.setChlAppType(vo.getChlAppType());
        po.setChlAppSecret(vo.getChlAppSecret());
        po.setChlAppName(vo.getChlAppName());
        po.setChlSubjectId(vo.getChlSubjectId());
        po.setChlAgentId(vo.getChlAgentId());
        po.setChlToken(vo.getChlToken());
        po.setChlEncKey(vo.getChlEncKey());
        po.setChlEncType(vo.getChlEncType());
        po.setUrl(vo.getUrl());
        po.setManager(vo.getManager());
        po.setDescription(vo.getDescription());
        po.setDeleted(vo.getDeleted());
        po.setCreateTime(vo.getCreateTime());
        po.setUpdateTime(vo.getUpdateTime());
        return po;
    }
}