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

import com.asialjim.microapplet.commons.chl.PlatformType;
import com.asialjim.microapplet.session.Session;
import com.asialjim.microapplet.session.SessionCtx;
import com.asialjim.microapplet.user.api.FacialRecAuthApi;
import com.asialjim.microapplet.user.component.facial_rec.FacialRecAuthBean;
import com.asialjim.microapplet.user.component.facial_rec.FacialRecAuthenticator;
import com.asialjim.microapplet.user.entity.vo.FacialRecAuthViewObj;
import com.asialjim.microapplet.user.entity.web.code.CustomerSecondaryAuthenticationCode;
import com.asialjim.microapplet.user.entity.web.req.facial_rec.FacialRecAuthReq;
import com.asialjim.microapplet.user.entity.web.req.facial_rec.FacialRecAuthTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.res.facial_rec.FacialRecAuthTokenObtainRes;
import com.asialjim.microapplet.user.entity.web.res.facial_rec.FacialRecAuthVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * 用户人脸核身服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Service
@AllArgsConstructor
public class FacialRecAuthService {
    private final FacialRecAuthApi facialRecAuthApi;
    private final SessionCtx sessionCtx;
    private final FacialRecAuthBean facialRecAuthBean;

    /**
     * 人脸核身结果检查，并留痕
     *
     * @param body {@link FacialRecAuthReq body}
     * @return {@link FacialRecAuthVo }
     * @since 2026/3/6
     */
    public FacialRecAuthVo save(FacialRecAuthReq body) {
        // 人脸核身未通过
        if (!Boolean.TRUE.equals(body.getMatchStatus()))
            CustomerSecondaryAuthenticationCode.FacialRecAuthFailure.thrWithMsg(Collections.singletonList("人脸核身结果标志不通过"));

        Session userSession = this.sessionCtx.currentSession();


        String sessionId = userSession.getId();
        String traceId = userSession.getTrace();

        FacialRecAuthViewObj facialRecAuthReq = buildAdditionalFacialRecAuthParam(body, userSession);
        FacialRecAuthViewObj facialRecAuthRes = this.facialRecAuthApi.additional(sessionId, traceId, facialRecAuthReq);

        FacialRecAuthVo res = new FacialRecAuthVo();
        res.setId(facialRecAuthRes.getId());
        res.setFacialRecAuthTime(facialRecAuthReq.getFacialRecAuthTime());
        res.setCreateTime(facialRecAuthReq.getCreateTime());

        return res;
    }

    private FacialRecAuthViewObj buildAdditionalFacialRecAuthParam(FacialRecAuthReq body, Session userSession) {
        FacialRecAuthViewObj facialRecAuthReq = new FacialRecAuthViewObj();
        facialRecAuthReq.setPlatformType(userSession.platformTypeCode());
        //facialRecAuthReq.setPlatformId(userSession.getPlatType().getPlatformId());
        facialRecAuthReq.setAppId(userSession.getAppid());
        facialRecAuthReq.setAppType(userSession.platformAppType().getCode());
        facialRecAuthReq.setOpenid(userSession.getOpenid());
        facialRecAuthReq.setUnionid(userSession.getUnionid());

        copy(body, facialRecAuthReq);

        return facialRecAuthReq;
    }

    private void copy(FacialRecAuthReq body, FacialRecAuthViewObj facialRecAuthReq) {
        // TODO 使用 MapStruct 进行重构
        //noinspection DuplicatedCode
        facialRecAuthReq.setSource(body.getSource());
        facialRecAuthReq.setRequest(body.getRequest());
        facialRecAuthReq.setResponse(body.getResponse());
        facialRecAuthReq.setNameCipher(body.getNameCipher());
        facialRecAuthReq.setIdNoCipher(body.getIdNoCipher());
        facialRecAuthReq.setTargetNameCipher(body.getTargetNameCipher());
        facialRecAuthReq.setTargetIdNoCipher(body.getTargetIdNoCipher());
        facialRecAuthReq.setMatchStatus(body.getMatchStatus());
        facialRecAuthReq.setFileId(body.getFileId());
        facialRecAuthReq.setFacialRecAuthTime(body.getFacialRecAuthTime());
    }

    /**
     * 获取人脸认证令牌
     *
     * @param body {@link FacialRecAuthTokenObtainReq body}
     * @return {@link FacialRecAuthTokenObtainRes }
     * @since 2026/3/9
     */
    public FacialRecAuthTokenObtainRes obtainToken(FacialRecAuthTokenObtainReq body) {
        Session userSession = this.sessionCtx.currentSession();
        // 人脸核身场景, 场景决定是否需要下载图片、视频
        String scene = body.getScene();
        // 人脸核身子场景
        String subScene = body.getSubScene();

        // 根据人脸场景，判断是否需要人脸图片, 人脸视频
        this.facialRecAuthBean.facialMediaCheckByScene(scene, subScene, body::setFacialImageRequire, body::setFacialVideoRequire);

        PlatformType platformType = userSession.platformAppType().getPlatformType();
        boolean facialImageRequire = Optional.ofNullable(body.getFacialImageRequire()).orElse(false);
        boolean facialVideoRequire = Optional.ofNullable(body.getFacialVideoRequire()).orElse(false);

        FacialRecAuthenticator facialRecAuthenticator = this.facialRecAuthBean.authenticator(platformType, facialImageRequire, facialVideoRequire);
        return facialRecAuthenticator.obtainToken(body);
    }
}