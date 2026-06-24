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

package com.asialjim.microapplet.user.agreement.infrastructure.repository.service;

import com.asialjim.microapplet.user.agreement.infrastructure.repository.po.AgreementRecordPo;
import com.asialjim.microapplet.user.agreement.infrastructure.repository.service.impl.AgreementRecordMapperServiceImpl;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 协议记录数据访问服务接口，提供协议签署记录的基础 CRUD 操作以及缓存管理能力。
 * <p>
 * 该接口继承自 MyBatis-Flex 的 {@link IService}，默认提供新增、删除、修改、查询等基础功能，
 * 并扩展了协议记录特有的业务方法。
 * </p>
 *
 * @author Asial Jim
 * @version 1.0.0
 * @since 2025-01-01
 * @see AgreementRecordPo
 * @see AgreementRecordMapperServiceImpl
 */
public interface AgreementRecordMapperService
        extends IService<AgreementRecordPo> {

    /**
     * 根据协议类型、版本号、平台类型、应用ID和用户ID查询最近一次有效的协议签署记录。
     * <p>
     * 查询结果仅返回未被撤销的记录（revokeTime 为 null），并按签署时间降序排列。
     * </p>
     *
     * @param agreementType     协议类型/协议代码
     * @param version           协议版本号
     * @param platformTypeCode  平台类型代码
     * @param appid             应用ID
     * @param openid            用户OpenID
     * @return 符合条件的协议记录，如果不存在则返回 null
     */
    AgreementRecordPo last(String agreementType, Integer version, String platformTypeCode, String appid, String openid);

    /**
     * 获取指定用户（appid + openid）下每个协议类型的最新版本、最近一次授权记录。
     * <p>
     * 最终返回每个协议类型的最新版本授权记录列表，按签署时间倒序排列。
     * </p>
     *
     * @param platformType 平台类型
     * @param appid        应用ID，用于标识应用
     * @param openid       用户OpenID，用于标识用户
     * @return 包含每个协议最新版本授权记录的列表
     */
    List<AgreementRecordPo> lastWithoutType(String platformType, String appid, String openid);

    /**
     * 撤销指定用户的所有有效协议签署记录。
     * <p>
     * 该方法会将指定用户的协议记录的 revokeTime 设置为当前时间，实现逻辑删除。
     * 撤销成功后，会自动清除该用户所有协议记录的缓存。
     * </p>
     *
     * @param platformType 平台类型
     * @param appid        应用ID
     * @param openid       用户OpenID
     */
    void revoke(String platformType, String appid, String openid);

    /**
     * 根据协议标识清除对应的缓存。
     * <p>
     * 缓存键格式为：{@code agreementCode:agreementVersion:platformType:appid:openid}
     * </p>
     *
     * @param agreementCode      协议代码
     * @param agreementVersion   协议版本号
     * @param platformType       平台类型
     * @param appid              应用ID
     * @param openid             用户OpenID
     */
    void cleanCache(String agreementCode, Integer agreementVersion, String platformType, String appid, String openid);

    /**
     * 根据协议记录实体清除对应的缓存。
     * <p>
     * 从实体中提取协议标识组成缓存键并清除。
     * </p>
     *
     * @param entity 协议记录实体
     */
    void cleanCache(AgreementRecordPo entity);
}