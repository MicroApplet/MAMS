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

package com.asialjim.microapplet.user.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 渠道用户信息
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/4, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class ChlUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -6321867072850539747L;
    /**
     * 渠道用户编号：对应数据库主键
     */
    private String id;

    /**
     * 关联主用户编号
     */
    private String userId;

    /**
     * 渠道类型
     * <ol>
     *     <li>WECHAT: 微信</li>
     *     <li>ALIPAY: 支付宝</li>
     *     <li>douyin: 抖音</li>
     * </ol>
     */
    private String platformType;

    /**
     * 平台编号:开放平台编号后，unionid就一定存在了
     */
    private String platformId;


    /**
     * 渠道应用编号
     */
    private String appId;

    /**
     * 渠道应用类型
     */
    private String appType;

    /**
     * 渠道用户编号
     * <ol>
     *     <li>MOBILE: 手机号</li>
     *     <li>WECHAT: openid</li>
     *     <li>ALIPAY: userid</li>
     *     <li>douyin: openid</li>
     * </ol>
     */
    private String openid;

    /**
     * 用户在UAM系统用户编号
     */
    private String uamId;

    /**
     * 渠道用户联合唯一编号
     * 多个用户出现相同的unionid是，标识为同一个人，可以合并账号
     */
    private String unionid;

    /**
     * 渠道用户授权码
     */
    private String userCode;

    /**
     * 渠道用户令牌
     */
    private String userToken;

    /**
     * 用户创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 用户更新时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime updateTime;
}