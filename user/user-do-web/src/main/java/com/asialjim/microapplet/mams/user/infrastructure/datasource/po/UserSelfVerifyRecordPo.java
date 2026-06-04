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

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户本人认证记录（人脸核身、视频核身等证明用户本人与证件匹配的记录）
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/16, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
@Table("user_self_verify_record")
public class UserSelfVerifyRecordPo implements Serializable {
    @Serial
    private static final long serialVersionUID = -31579827670489208L;

    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    @Column(value = "appid", comment = "所属应用编号")
    private String appid;

    @Column(value = "userid", comment = "所属主用户表编号")
    private String userid;

    @Column(value = "id_type", comment = "证件类型")
    private String idType;

    @Column(value = "id_no", comment = "证件号")
    private String idNo;

    @Column(value = "name", comment = "证件姓名")
    private String name;

    @Column(value = "v_id_no", comment = "认证证件号")
    private String vIdNo;

    @Column(value = "v_name", comment = "认证证件名")
    private String vName;

    @Column(value = "v_time", onInsertValue = "NOW()", comment = "认证时间")
    private LocalDateTime vTime;

    @Column(value = "v_channel", comment = "认证渠道")
    private String vChannel;

    @Column(value = "v_req", comment = "认证请求参数")
    private String vReq;

    @Column(value = "v_res", comment = "认证响应结果")
    private String vRes;

    @Column(value = "verified", comment = "认证是否成功，是否是本人")
    private Boolean verified;

    @Column(value = "deleted", comment = "逻辑删除")
    private Boolean deleted;

    @Column(value = "create_time", onInsertValue = "NOW()", comment = "创建时间")
    private LocalDateTime createTime;

    @Column(value = "update_time", onUpdateValue = "NOW()", comment = "更新时间")
    private String updateTime;
}