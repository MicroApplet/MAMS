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

package com.asialjim.microapplet.user.agreement.infrastructure.repository;

import com.asialjim.microapplet.user.agreement.infrastructure.repository.mapper.AgreementRecordBaseMapper;
import com.asialjim.microapplet.user.agreement.infrastructure.repository.po.AgreementRecordPo;
import com.asialjim.microapplet.user.agreement.infrastructure.repository.service.AgreementRecordMapperService;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

/**
 * 协议记录仓储层，负责协议签署记录的业务领域操作。
 * <p>
 * 该类作为仓储接口，封装了与 {@link AgreementRecordMapperService} 的交互细节，
 * 提供领域层所需的协议记录操作能力。内部默认版本号为 0。
 * </p>
 *
 * @author Asial Jim
 * @version 1.0.0
 * @since 2025-01-01
 * @see AgreementRecordMapperService
 * @see AgreementRecordPo
 */
@Configuration
@MapperScan(basePackageClasses = AgreementRecordBaseMapper.class)
public class AgreementRecordRepository {
    @Resource
    private AgreementRecordMapperService agreementRecordMapperService;

    /**
     * 根据协议类型、版本号、平台类型、应用ID和用户ID查询最近一次有效的协议签署记录。
     *
     * @param agreementType     协议类型/协议代码
     * @param version           协议版本号，如果为 null 则默认为 0
     * @param platformTypeCode  平台类型代码
     * @param appid             应用ID
     * @param openid            用户OpenID
     * @return 符合条件的协议记录，如果不存在则返回 null
     */
    public AgreementRecordPo last(String agreementType,
                                  Integer version,
                                  String platformTypeCode,
                                  String appid,
                                  String openid) {
        if (Objects.isNull(version))
            version = 0;

        return this.agreementRecordMapperService
                .last(agreementType, version, platformTypeCode, appid, openid);
    }

    /**
     * 保存协议记录。
     *
     * @param po 协议记录实体
     */
    public void add(AgreementRecordPo po) {
        this.agreementRecordMapperService.save(po);
    }

    /**
     * 获取指定用户下每个协议类型的最新版本授权记录。
     *
     * @param platformType 平台类型
     * @param appid        应用ID
     * @param openid       用户OpenID
     * @return 包含每个协议最新版本授权记录的列表
     */
    public List<AgreementRecordPo> lastWithoutType(String platformType, String appid, String openid) {
       return this.agreementRecordMapperService.lastWithoutType(platformType, appid, openid);
    }

    /**
     * 撤销指定用户的所有有效协议签署记录。
     *
     * @param platformType 平台类型
     * @param appid        应用ID
     * @param openid       用户OpenID
     */
    public void revoke(String platformType, String appid, String openid) {
        this.agreementRecordMapperService.revoke(platformType,appid,openid);
    }
}