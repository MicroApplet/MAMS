/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
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

package com.asialjim.microapplet.mams.user.infrastructure.datasource.po;

import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 渠道用户信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("chl_user")
public class ChlUserPo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1136670509141987856L;

    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    private String appid;
    /**
     * 主用户编号
     *
     * @see UserPo#getId()
     */
    private String userid;

    /**
     * 渠道类型
     */
    private String chlType;

    /**
     * 渠道应用编号
     */
    private String chlAppid;
    /**
     * 渠道应用类型
     */
    private String chlAppType;
    /**
     * 渠道用户编号
     */
    private String chlUserid;
    /**
     * 渠道联合用户标识
     */
    private String chlUnionid;
    /**
     * 用户授权码
     */
    private String chlUserCode;
    /**
     * 渠道用户信息获取令牌
     */
    private String chlUserToken;
    /**
     * 渠道用户删除标志
     */
    private Boolean deleted;

    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    public static ChlUserVo toVo(ChlUserPo po) {
        if (Objects.isNull(po))
            return null;

        ChlUserVo vo = new ChlUserVo();
        vo.setId(po.getId());
        vo.setUserid(po.getUserid());
        vo.setAppid(po.getAppid());
        vo.setChlType(po.getChlType());
        vo.setChlAppId(po.getChlAppid());
        vo.setChlAppType(po.getChlAppType());
        vo.setChlUserId(po.getChlUserid());
        vo.setChlUnionId(po.getChlUnionid());
        vo.setChlUserCode(po.getChlUserCode());
        vo.setChlUserToken(po.getChlUserToken());
        return vo;
    }
}