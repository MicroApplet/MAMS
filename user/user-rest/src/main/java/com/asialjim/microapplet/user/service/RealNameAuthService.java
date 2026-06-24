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

package com.asialjim.microapplet.user.service;


import com.asialjim.microapplet.commons.chl.PlatformAppType;
import com.asialjim.microapplet.session.Session;
import com.asialjim.microapplet.session.SessionCtx;
import com.asialjim.microapplet.user.api.IdCardUserApi;
import com.asialjim.microapplet.user.api.PhoneApi;
import com.asialjim.microapplet.user.cloud.FacialRecAuthCloud;
import com.asialjim.microapplet.user.code.RealNameAuthCode;
import com.asialjim.microapplet.user.cons.SmsTokenType;
import com.asialjim.microapplet.user.entity.vo.IdCardUserVo;
import com.asialjim.microapplet.user.entity.web.req.PhoneSmsTokenCheckReq;
import com.asialjim.microapplet.user.entity.web.req.PhoneSmsTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.req.SmsTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.req.ThreeFactorRealNameAuthReq;
import com.asialjim.microapplet.user.entity.web.req.facial_rec.FacialRecAuthReq;
import com.asialjim.microapplet.user.entity.web.res.PhoneSmsTokenObtainRes;
import com.asialjim.microapplet.user.entity.web.res.SmsTokenObtainRes;
import com.asialjim.microapplet.user.entity.web.res.ThreeFactorRealNameAuthQueryRes;
import com.asialjim.microapplet.user.entity.web.res.facial_rec.FacialRecAuthVo;
import com.asialjim.microapplet.user.event.Session3FactorRealNameAuthSucEvent;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 实名认证服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Service
public class RealNameAuthService implements ApplicationContextAware {
    @Setter
    private ApplicationContext applicationContext;
    @Resource
    private FacialRecAuthCloud facialRecAuthCloud;
    @Resource
    private IdCardUserApi idCardUserApi;
    @Resource
    private SessionCtx sessionCtx;
    @Resource
    private PhoneApi phoneApi;

    /**
     * 查询当前会话的用户的三要素实名认证信息
     *
     * @return {@link ThreeFactorRealNameAuthQueryRes 结果}
     * @since 2026/3/5
     */
    public ThreeFactorRealNameAuthQueryRes queryBySession(String idType) {
        Session userSession = this.sessionCtx.currentLoginSession();
        return doQueryBySession(idType, userSession);
    }


    public List<ThreeFactorRealNameAuthQueryRes> queryListBySession() {
        Session session = this.sessionCtx.currentLoginSession();

        List<IdCardUserVo> list = this.idCardUserApi.queryList(session.platformTypeCode(), session.getAppid(), session.getOpenid());

        return list.stream()
                .filter(Objects::nonNull)
                .map(item -> ThreeFactorRealNameAuthQueryRes.builder()
                        .name(item.getName())
                        .idType(item.getIdType())
                        .idNumber(item.getIdNumber())
                        .phone(item.getPhone())
                        .build())
                .toList();
    }

    /**
     * 三要素实名认证
     *
     * @param body {@link ThreeFactorRealNameAuthReq body}
     * @return {@link ThreeFactorRealNameAuthQueryRes }
     * @since 2026/3/5
     */
    public ThreeFactorRealNameAuthQueryRes realNameBy3Factor(ThreeFactorRealNameAuthReq body) {
        Session userSession = this.sessionCtx.currentLoginSession();

        // 已实名，直接返回
        if (userHad3FactoryRealNameCheck(body.getIdType(), userSession))
            return queryBySession(body.getIdType());

        // 获取用户会话开放平台应用类型，并校验是否支持该类型
        PlatformAppType platformAppType = PlatformAppType.check(userSession.platformAppType());

        // 人脸核身
        FacialRecAuthVo facialRecAuthRes = doFacialRecAuth(body);

        // 手机号验证
        doPhoneCheck(body, userSession.getUserCode());

        // 构建3要素认证请求参数
        IdCardUserVo idCardUserVo = buildLinkIdCardParam(body, platformAppType, userSession, userSession.getOpenid(), facialRecAuthRes);

        // 三要素实名认证数据落地，并补充用户信息:UAM-ID,ECIF-ID
        return doLinkIdCard(idCardUserVo, userSession);
    }

    private boolean userHad3FactoryRealNameCheck(String idType, Session userSession) {
        ThreeFactorRealNameAuthQueryRes res = doQueryBySession(idType, userSession);
        // 用户已经三要素实名
        if (ThreeFactorRealNameAuthQueryRes.hadThreeFactorRealNameAuth(res))
            throw RealNameAuthCode.UserHad3FactorRealNameAuth.ex();

        if (ThreeFactorRealNameAuthQueryRes.hadTwoFactorRealNameAuth(res)) {
            log.warn("用户：{} 为二要素实名用户，需要添加手机号，但是新系统应该永远不会走到这里", res.getId());
            return true;
        }

        return false;
    }

    private ThreeFactorRealNameAuthQueryRes doLinkIdCard(IdCardUserVo idCardUserVo, Session userSession) {
        // 保存实名认证信息，合并客户信息，更新UAM-ID， 添加客户号
        IdCardUserVo linkIdCard = this.idCardUserApi.linkIdCard(idCardUserVo);
        if (Objects.isNull(linkIdCard))
            throw RealNameAuthCode.ThreeFactorRealNameAuthFail.exWithData("添加成功，但查询不到实名信息");

        Session3FactorRealNameAuthSucEvent event = new Session3FactorRealNameAuthSucEvent(userSession, linkIdCard);
        this.applicationContext.publishEvent(event);
        return queryBySession(idCardUserVo.getIdType());

        // 永远不会走到这里, 因为上面的接口会抛异常
        //throw RealNameAuthCode.ThreeFactorRealNameAuthFail.ex("Customer FaaS 三要素实名返回结果不符合预期");
    }

    private void doPhoneCheck(ThreeFactorRealNameAuthReq body, String userCode) {
        // 短信验证码验证
        PhoneSmsTokenCheckReq phoneCheckReq = PhoneSmsTokenCheckReq.builder()
                .userid(userCode)
                .phone(body.getPhone())
                .tokenType(SmsTokenType.ThreeFactorRealNameAuth.getTypeName())
                .smsToken(body.getSmsToken())
                .checkTime(LocalDateTime.now())
                .build();
        this.phoneApi.check(phoneCheckReq);
    }

    public SmsTokenObtainRes threeFactorRealNameAuthSmsToken(SmsTokenObtainReq body) {
        Session userSession = this.sessionCtx.currentLoginSession();
        PhoneSmsTokenObtainReq req = PhoneSmsTokenObtainReq.builder()
                .userCode(userSession.getUserCode())
                .phone(body.getPhone())
                .tokenType(SmsTokenType.ThreeFactorRealNameAuth.getTypeName())
                .params(body.getParams())
                .build();

        PhoneSmsTokenObtainRes obtain = this.phoneApi.obtain(req);

        return SmsTokenObtainRes.builder()
                .id(obtain.getId())
                .userid(obtain.getUserid())
                .phone(obtain.getPhone())
                .tokenType(obtain.getTokenType())
                .smsToken(obtain.getSmsToken())
                .createTime(obtain.getCreateTime())
                .build();
    }

    private IdCardUserVo buildLinkIdCardParam(ThreeFactorRealNameAuthReq body, PlatformAppType platformAppType, Session userSession, String openid, FacialRecAuthVo facialRecAuthRes) {
        IdCardUserVo idCardUserVo = new IdCardUserVo();
        idCardUserVo.setPlatformType(platformAppType.getPlatformType().getCode());
        //idCardUserVo.setPlatformId(userSession.getPlatformId());
        idCardUserVo.setAppId(userSession.getAppid());
        idCardUserVo.setOpenid(openid);
        idCardUserVo.setUnionid(userSession.getUnionid());
        idCardUserVo.setName(body.getName());
        idCardUserVo.setIdType(body.getIdType());// 证件类型校验
        idCardUserVo.setIdNumber(body.getIdNumber());
        idCardUserVo.setPhone(body.getPhone());
        idCardUserVo.setVersion(1);

        if (Objects.nonNull(facialRecAuthRes)) {
            idCardUserVo.setFacialReqAuthId(facialRecAuthRes.getId());
            idCardUserVo.setFacialReqAuthTime(facialRecAuthRes.getFacialRecAuthTime());
        }

        return idCardUserVo;
    }

    private FacialRecAuthVo doFacialRecAuth(ThreeFactorRealNameAuthReq body) {
        FacialRecAuthReq facialRecAuthReq = new FacialRecAuthReq();
        //  使用 MapStruct 进行重构
        facialRecAuthReq.setSource(body.getSource());
        facialRecAuthReq.setRequest(body.getRequest());
        facialRecAuthReq.setResponse(body.getResponse());
        facialRecAuthReq.setNameCipher(body.getNameCipher());
        facialRecAuthReq.setIdNoCipher(body.getIdNoCipher());
        facialRecAuthReq.setTargetNameCipher(body.getTargetNameCipher());
        facialRecAuthReq.setTargetIdNoCipher(body.getTargetIdNoCipher());
        facialRecAuthReq.setMatchStatus(body.getMatchStatus());
        facialRecAuthReq.setFileId(body.getFacialRecAuthFileId());

        // 保存的人脸核身结果
        return this.facialRecAuthCloud.save(facialRecAuthReq);
    }

    private ThreeFactorRealNameAuthQueryRes doQueryBySession(String idType, Session session) {
        //noinspection ConstantValue
        return doQueryIdCardUser(idType, session)
                .filter(Objects::nonNull)
                .map(item -> ThreeFactorRealNameAuthQueryRes.builder()
                        .name(item.getName())
                        .idType(item.getIdType())
                        .idNumber(item.getIdNumber())
                        .phone(item.getPhone())
                        .build()
                )
                .orElse(ThreeFactorRealNameAuthQueryRes.NONE);
    }

    private Optional<IdCardUserVo> doQueryIdCardUser(String idType, Session session) {
        if (StringUtils.isBlank(idType))
            return Optional.empty();

        if (Objects.isNull(session))
            return Optional.empty();

        String platformType = session.getPlatType();

        IdCardUserVo vo = this.idCardUserApi.query(platformType, session.getAppid(), session.getOpenid(), idType);
        return Optional.ofNullable(vo);

    }
}