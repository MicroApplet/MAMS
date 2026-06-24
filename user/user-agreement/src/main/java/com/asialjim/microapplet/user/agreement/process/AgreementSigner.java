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

package com.asialjim.microapplet.user.agreement.process;

import com.asialjim.microapplet.app.cloud.AppAgreementCloud;
import com.asialjim.microapplet.app.code.AgreementType;
import com.asialjim.microapplet.app.entity.web.AppAgreementVo;
import com.asialjim.microapplet.session.Session;
import com.asialjim.microapplet.session.SessionCtx;
import com.asialjim.microapplet.user.agreement.entity.web.req.SimpleSignAgreementReq;
import com.asialjim.microapplet.user.agreement.infrastructure.repository.AgreementRecordRepository;
import com.asialjim.microapplet.user.agreement.infrastructure.repository.po.AgreementRecordPo;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class AgreementSigner {
    private final MultiValueMap<AgreementType, AgreementProcessor> map = new LinkedMultiValueMap<>();

    @Resource
    private SessionCtx sessionCtx;
    @Resource
    private List<AgreementProcessor> agreementProcessors;
    @Resource
    private AppAgreementCloud appAgreementCloud;
    @Resource
    private AgreementRecordRepository agreementRecordRepository;


    public List<AgreementProcessor> typeOf(AgreementType agreement) {
        List<AgreementProcessor> list = this.map.get(agreement);
        if (CollectionUtils.isNotEmpty(list)) return list;

        for (AgreementProcessor process : this.agreementProcessors) {
            if (process.support(agreement)) {
                this.map.addIfAbsent(agreement, process);
            }
        }

        return map.get(agreement);
    }

    public void authorize(List<SimpleSignAgreementReq> list) {
        Session session = this.sessionCtx.currentLoginSession();
        for (SimpleSignAgreementReq meta : list) {
            String code = meta.getAgreementCode();
            String signFileId = meta.getSignFileId();
            AgreementType agreementType = AgreementType.codeOf(code);
            List<AgreementProcessor> processors = typeOf(agreementType);
            for (AgreementProcessor processor : processors) {
                processor.sign(session, signFileId);
            }
        }
    }

    public List<AppAgreementVo> authorizableList() {
        Session userSession = this.sessionCtx.currentLoginSession();
        return this.appAgreementCloud.effectiveAgreementList(
                userSession.getPlatType(),
                userSession.getAppid()
        );
    }

    public List<AppAgreementVo> authorized() {
        Session userSession = this.sessionCtx.currentLoginSession();

        List<AgreementRecordPo> pos = this.agreementRecordRepository.lastWithoutType(
                userSession.getPlatType(),
                userSession.getAppid(),
                userSession.getOpenid());

        return pos.stream()
                .map(item -> this.appAgreementCloud.effectiveAgreement(
                        userSession.platformAppType().getPlatformType().getCode(),
                        userSession.getAppid(),
                        item.getAgreementCode()
                )).toList();
    }

    public void revoke() {
        Session userSession = this.sessionCtx.currentLoginSession();
        this.agreementRecordRepository.revoke(
                userSession.getPlatType(),
                userSession.getAppid(),
                userSession.getOpenid()
        );
    }
}