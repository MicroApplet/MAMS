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

package io.github.microapplet.mams.user.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.github.microapplet.mams.user.model.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户信息数据库表映射
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/2/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("user")
public class UserPo implements Serializable {
    private static final long serialVersionUID = 657217150912107981L;

    /**
     * 用户信息编号
     * {@link User#getId()}
     */
    @Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
    private String id;

    /**
     * 用户所属应用编号
     */
    private String appid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名: 全局唯一, 可为空
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户创建时间
     */
    @Column(onInsertValue = "now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 用户更新时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;


    public static User fromPo(UserPo po){
        if (Objects.isNull(po))
            return null;
        User user = new User();
        //noinspection DuplicatedCode
        user.setId(po.getId());
        user.setAppid(po.getAppid());
        user.setNickname(po.getNickname());
        user.setUsername(po.getUsername());
        user.setPassword(po.getPassword());
        user.setCreateTime(po.getCreateTime());
        user.setUpdateTime(po.getUpdateTime());
        return user;
    }

    public static UserPo toPo(User user){
        if (Objects.isNull(user))
            return null;
        UserPo po = new UserPo();
        //noinspection DuplicatedCode
        po.setId(user.getId());
        po.setAppid(user.getAppid());
        po.setNickname(user.getNickname());
        po.setUsername(user.getUsername());
        po.setPassword(user.getPassword());
        po.setCreateTime(user.getCreateTime());
        po.setUpdateTime(user.getUpdateTime());
        return po;
    }
}