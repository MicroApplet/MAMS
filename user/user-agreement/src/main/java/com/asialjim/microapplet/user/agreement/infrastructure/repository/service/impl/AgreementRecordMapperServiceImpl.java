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

package com.asialjim.microapplet.user.agreement.infrastructure.repository.service.impl;

import com.asialjim.microapplet.spring.App;
import com.asialjim.microapplet.user.agreement.infrastructure.cache.AgreementCache;
import com.asialjim.microapplet.user.agreement.infrastructure.repository.mapper.AgreementRecordBaseMapper;
import com.asialjim.microapplet.user.agreement.infrastructure.repository.po.AgreementRecordPo;
import com.asialjim.microapplet.user.agreement.infrastructure.repository.service.AgreementRecordMapperService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@link AgreementRecordMapperService} 的实现类，负责协议记录的数据访问和缓存管理。
 * <p>
 * 该类继承自 MyBatis-Flex 的 {@code ServiceImpl}，提供基础的 CRUD 操作，
 * 同时集成了 Spring Cache 进行数据缓存，并支持缓存的主动清除。
 * </p>
 *
 * @author Asial Jim
 * @version 1.0.0
 * @since 2025-01-01
 * @see AgreementRecordMapperService
 * @see AgreementRecordBaseMapper
 * @see AgreementRecordPo
 */
@Repository
public class AgreementRecordMapperServiceImpl
        extends ServiceImpl<AgreementRecordBaseMapper, AgreementRecordPo>
        implements AgreementRecordMapperService {

    /**
     * 保存协议记录，并清除相关缓存。
     * <p>
     * 该方法在保存成功后会自动清除以以下格式组成的缓存键：
     * {@code agreementCode:agreementVersion:platformType:appid:openid}
     * </p>
     *
     * @param entity 要保存的协议记录实体
     * @return 保存是否成功
     */
    @Override
    @CacheEvict(
            value = AgreementCache.userAgreementRecord,
            key = "#entity.getAgreementCode() +':' + #entity.getAgreementVersion() + ':' + #entity.getPlatformType() + ':' + #entity.getAppid() + ':' + #entity.getOpenid()"
    )
    public boolean save(AgreementRecordPo entity) {
        return super.save(entity);
    }

    /**
     * 根据协议类型、版本号、平台类型、应用ID和用户ID查询最近一次有效的协议签署记录。
     * <p>
     * 该方法使用缓存来提高查询性能，缓存键格式为：
     * {@code agreementType:version:platformTypeCode:appid:openid}
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
    @Override
    @Cacheable(
            value = AgreementCache.userAgreementRecord,
            key = "#agreementType +':' + #version + ':' + #platformTypeCode + ':' + #appid + ':' + #openid"
    )
    public AgreementRecordPo last(String agreementType,
                                  Integer version,
                                  String platformTypeCode,
                                  String appid,
                                  String openid) {

        return queryChain()
                .where(AgreementRecordPo::getAgreementCode).eq(agreementType)
                .where(AgreementRecordPo::getPlatformType).eq(platformTypeCode)
                .where(AgreementRecordPo::getAppid).eq(appid)
                .where(AgreementRecordPo::getOpenid).eq(openid)
                .where(AgreementRecordPo::getRevokeTime).isNotNull()
                .orderBy(AgreementRecordPo::getSignTime).desc()
                .one();
    }

    /**
     * 获取指定用户（appid + openid）下每个协议类型的最新版本、最近一次授权记录。
     * <p>
     * 该方法通过 CTE（公共表表达式）实现复杂查询：
     * <ol>
     *   <li>内层查询：按 agreement_code 分组，获取每个协议的最新版本号（max_version）</li>
     *   <li>关联查询：关联内层查询结果，获取完整的协议记录信息</li>
     *   <li>行号排序：对每个协议类型按签署时间降序排列，选取最新的一条记录</li>
     * </ol>
     * 最终返回每个协议类型的最新版本授权记录列表，按签署时间倒序排列。
     * </p>
     * <p>
     * 注意：此方法不使用缓存，每次调用都会直接查询数据库。
     * </p>
     *
     * @param platformType 平台类型（当前未使用，可能是遗留参数）
     * @param appid        应用ID，用于标识应用
     * @param openid       用户OpenID，用于标识用户
     * @return 包含每个协议最新版本授权记录的列表，按签署时间倒序排列
     * @see AgreementRecordPo
     */
    @Override
    public List<AgreementRecordPo> lastWithoutType(String platformType, String appid, String openid) {
        /*
        WITH latest_version_records AS (
            SELECT t1.*
            FROM weixin.agreement_record AS t1
            INNER JOIN (
                SELECT agreement_code, MAX(agreement_version) AS max_version
                FROM weixin.agreement_record
                WHERE appid = 'debug' AND openid = 'debug:openid:1'
                GROUP BY agreement_code
            ) AS t2
                ON t1.agreement_code = t2.agreement_code AND t1.agreement_version = t2.max_version
            WHERE t1.appid = 'debug' AND t1.openid = 'debug:openid:1'
        ),
        ranked_records AS (
            SELECT
                *,
                ROW_NUMBER() OVER (PARTITION BY agreement_code ORDER BY sign_time DESC
        ) AS rn
        FROM latest_version_records
       )
        SELECT
            id, platform_type, appid, openid, agreement_code, agreement_version, agreement_name, sign_time, sign_ip, signed_file_ids
        FROM ranked_records
        WHERE rn = 1
        ORDER BY sign_time DESC;
         */
        QueryWrapper innerJoinWrapper = queryChain()
                .select(QueryMethods.column("agreement_code"), QueryMethods.max("agreement_version").as("max_version"))
                .from(AgreementRecordPo.class)
                .where(AgreementRecordPo::getPlatformType).eq(platformType)
                .where(AgreementRecordPo::getAppid).eq(appid)
                .where(AgreementRecordPo::getOpenid).eq(openid)
                .where(AgreementRecordPo::getRevokeTime).isNotNull()
                .groupBy(AgreementRecordPo::getAgreementCode)
                .toQueryWrapper();

        QueryWrapper with = queryChain().select("t1.*")
                .from(AgreementRecordPo.class).as("t1")
                .innerJoin(innerJoinWrapper).as("t2")
                .on("t1.agreement_code = t2.agreement_code")
                .and("t1.agreement_version = t2.max_version")
                .where("t1.appid = ?", appid)
                .where("t1.openid = ?", openid)
                .toQueryWrapper();

        QueryWrapper rankWrapper = queryChain()
                .select("*", "  ROW_NUMBER() OVER (PARTITION BY agreement_code ORDER BY sign_time DESC) AS rn")
                .from("last_version_records").toQueryWrapper();


        QueryChain<AgreementRecordPo> wrapper = queryChain().with("last_version_records")
                .asSelect(with)
                .with("ranked_records")
                .asSelect(rankWrapper)
                .select("id, platform_type, appid, openid, agreement_code, agreement_version, agreement_name, sign_time, sign_ip, signed_file_ids")
                .from("ranked_records")
                .where("rn = 1")
                .orderBy("sign_time desc");
        return wrapper.list();
    }

    /**
     * 撤销指定用户的所有有效协议签署记录。
     * <p>
     * 该方法会将 revokeTime 设置为当前时间，实现逻辑删除。
     * 撤销成功后，会自动清除该用户所有协议记录的缓存。
     * </p>
     *
     * @param platformType 平台类型
     * @param appid        应用ID
     * @param openid       用户OpenID
     * @see AgreementRecordMapperService#revoke(String, String, String)
     */
    @Override
    public void revoke(String platformType, String appid, String openid) {
        LocalDateTime now = LocalDateTime.now();
        boolean update = updateChain()
                .set(AgreementRecordPo::getRevokeTime, now)
                .where(AgreementRecordPo::getPlatformType).eq(platformType)
                .where(AgreementRecordPo::getAppid).eq(appid)
                .where(AgreementRecordPo::getOpenid).eq(openid)
                .update();

        if (update) {
            App.beanOpt(AgreementRecordMapperService.class)
                    .ifPresent(service -> service.queryChain()
                            .where(AgreementRecordPo::getPlatformType).eq(platformType)
                            .where(AgreementRecordPo::getAppid).eq(appid)
                            .where(AgreementRecordPo::getOpenid).eq(openid)
                            .list().forEach(item -> {
                                service.cleanCache(item.getAgreementCode(), item.getAgreementVersion(), item.getPlatformType(), item.getAppid(), item.getOpenid());
                            }));
        }
    }

    /**
     * 根据协议标识清除对应的缓存。
     *
     * @param agreementCode      协议代码
     * @param agreementVersion   协议版本号
     * @param platformType       平台类型
     * @param appid              应用ID
     * @param openid             用户OpenID
     * @see AgreementRecordMapperService#cleanCache(String, Integer, String, String, String)
     */
    @Override
    @CacheEvict(
            value = AgreementCache.userAgreementRecord,
            key = "#agreementCode +':' + #agreementVersion + ':' + #platformType + ':' + #appid + ':' + #openid"
    )
    public void cleanCache(String agreementCode, Integer agreementVersion, String platformType, String appid, String openid) {

    }

    /**
     * 根据协议记录实体清除对应的缓存。
     *
     * @param entity 协议记录实体
     * @see AgreementRecordMapperService#cleanCache(AgreementRecordPo)
     */
    @Override
    @CacheEvict(
            value = AgreementCache.userAgreementRecord,
            key = "#entity.getAgreementCode() +':' + #entity.getAgreementVersion() + ':' + #entity.getPlatformType() + ':' + #entity.getAppid() + ':' + #entity.getOpenid()"
    )
    public void cleanCache(AgreementRecordPo entity) {

    }
}