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

package com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.po;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.user.pojo.UserChl;
import com.asialjim.microapplet.mams.user.pojo.UserMain;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 渠道用户表
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("user_chl")
@Accessors(chain = true)
public class UserChlPo implements Serializable {
    private static final long serialVersionUID = 1271798506170356564L;


    /**
     * 全局主键
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
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
    private String chlType;

    /**
     * 所属渠道应用编号
     */
    private String chlAppId;
    /**
     * 所属渠道应用类型
     */
    private String chlAppType;

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

    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;
    @Column(onInsertValue = "now()",onUpdateValue = "now()")
    private LocalDateTime updateTime;

    public static UserChl fromPo(UserChlPo po) {
        if (Objects.isNull(po))
            return null;
        UserChl chl = new UserChl();
        chl.setId(po.getId());
        chl.setUserid(po.getUserid());
        chl.setAppletId(po.getAppletId());
        chl.setChlType(ChlType.codeOf(po.getChlType()));
        chl.setChlAppType(ChlAppType.codeOf(po.getChlAppType()));
        chl.setChlAppId(po.getChlAppId());
        chl.setChlUserId(po.getChlUserId());
        chl.setChlUnionId(po.getChlUnionId());
        chl.setChlUserCode(po.getChlUserCode());
        chl.setChlUserToken(po.getChlUserToken());
        chl.setCreateTime(po.getCreateTime());
        chl.setUpdateTime(po.getUpdateTime());
        return chl;
    }

    public static UserChlPo toPo(UserChl po) {
        if (Objects.isNull(po))
            return null;
        UserChlPo chl = new UserChlPo();
        chl.setId(po.getId());
        chl.setUserid(po.getUserid());
        chl.setAppletId(po.getAppletId());
        chl.setChlType(po.getChlType().getCode());
        chl.setChlAppType(po.getChlAppType().getCode());
        chl.setChlAppId(po.getChlAppId());
        chl.setChlUserId(po.getChlUserId());
        chl.setChlUnionId(po.getChlUnionId());
        chl.setChlUserCode(po.getChlUserCode());
        chl.setChlUserToken(po.getChlUserToken());
        chl.setCreateTime(po.getCreateTime());
        chl.setUpdateTime(po.getUpdateTime());
        return chl;
    }
}