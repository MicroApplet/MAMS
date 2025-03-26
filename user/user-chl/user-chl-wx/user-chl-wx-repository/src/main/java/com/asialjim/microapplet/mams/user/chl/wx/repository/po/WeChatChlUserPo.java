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

package com.asialjim.microapplet.mams.user.chl.wx.repository.po;

import com.asialjim.microapplet.mams.user.chl.wx.model.WeChatChlUser;
import com.asialjim.microapplet.mams.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 微信渠道用户表映射
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/2/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("user_wx_chl")
public class WeChatChlUserPo implements Serializable {
    private static final long serialVersionUID = -7873164140224362646L;

    /**
     * 微信公众平台用户编号
     */
    @Id(keyType = KeyType.None)
    private String openid;

    /**
     * 所属公众平台应用用户的联合id
     */
    private String unionId;

    /**
     * 用户所属微信公众平台应用
     */
    private String appid;

    /**
     * 微信公众平台应用类型:
     * <ul>
     *     <li>1. 公众号</li>
     *     <li>2. 小程序</li>
     * </ul>
     */
    private String appType;

    /**
     * 用户编号
     *
     * @see User#getId()
     */
    private String userid;

    private String sessionKey;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    public static WeChatChlUser fromPo(WeChatChlUserPo po) {
        if (Objects.isNull(po))
            return null;
        WeChatChlUser usr = new WeChatChlUser();
        //noinspection DuplicatedCode
        usr.setOpenid(po.getOpenid());
        usr.setUnionId(po.getUnionId());
        usr.setAppid(po.getAppid());
        usr.setAppType(po.getAppType());
        usr.setUserid(po.getUserid());
        usr.setCreateTime(po.getCreateTime());
        usr.setUpdateTime(po.getUpdateTime());
        return usr;
    }

    public static WeChatChlUserPo toPo(WeChatChlUser user) {
        if (Objects.isNull(user))
            return null;
        WeChatChlUserPo po = new WeChatChlUserPo();
        //noinspection DuplicatedCode
        po.setOpenid(user.getOpenid());
        po.setUnionId(user.getUnionId());
        po.setAppid(user.getAppid());
        po.setAppType(user.getAppType());
        po.setUserid(user.getUserid());
        po.setCreateTime(user.getCreateTime());
        po.setUpdateTime(user.getUpdateTime());
        return po;
    }
}