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
import com.asialjim.microapplet.user.agreement.infrastructure.repository.AgreementRecordRepository;
import com.asialjim.microapplet.user.agreement.infrastructure.repository.po.AgreementRecordPo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public abstract class AgreementProcessor {
    @Resource
    private AgreementRecordRepository agreementRecordRepository;
    @Resource
    private AppAgreementCloud appAgreementCloud;

    public abstract AgreementType type();

    public void sign(Session userSession, String signedFileIds) {
        AppAgreementVo appAgreement = this.appAgreementCloud.effectiveAgreement(
                userSession.platformAppType().getCode(),
                userSession.getAppid(),
                type().getCode()
        );

        if (Objects.isNull(appAgreement))
            return;

        String openid = userSession.getOpenid();
        if (StringUtils.isBlank(openid))
            return;

        AgreementRecordPo po = new AgreementRecordPo();
        po.setPlatformType(userSession.platformAppType().getCode());
        po.setAppid(userSession.getAppid());
        po.setOpenid(openid);
        po.setAgreementCode(appAgreement.getAgreementCode());
        po.setAgreementVersion(appAgreement.getVersion());
        po.setAgreementName(appAgreement.getAgreementName());
        po.setSignTime(LocalDateTime.now());
        po.setSignIp(userSession.clientIp());
        po.setSignedFileIds(signedFileIds);

        this.agreementRecordRepository.add(po);
    }

    public void process(Session userSession,
                        List<String> errMsgList,
                        List<String> updateMsgList,
                        boolean checkLogin,
                        List<AppAgreementVo> agreementList) {

        AppAgreementVo appAgreement = this.appAgreementCloud.effectiveAgreement(
                userSession.platformTypeCode(),
                userSession.getAppid(),
                type().getCode()
        );

        if (Objects.isNull(appAgreement))
            return;

        Long durationMin = appAgreement.getDurationMin();
        LocalDateTime now = LocalDateTime.now();
        if (checkLogin) {
            LocalDateTime lastLoginDate = userSession.getLastLoginTime();
            if (Objects.isNull(lastLoginDate)) {
                agreementList.add(appAgreement);
                return;
            }

            Duration between = Duration.between(lastLoginDate, now).abs();
            long minutes = between.toMinutes();
            if (Objects.nonNull(durationMin) && minutes > durationMin) {
                agreementList.add(appAgreement);
                return;
            }
        }

        String agreementType = appAgreement.getAgreementCode();
        Integer version = appAgreement.getVersion();
        AgreementRecordPo record = this.agreementRecordRepository.last(
                agreementType,
                version,
                userSession.platformTypeCode(),
                userSession.getAppid(),
                userSession.getOpenid()
        );


        // 3 如果两个版本不匹配，则需要重新授权
        if (Objects.isNull(record)) {
            errMsgList.add(appAgreement.getAgreementCode());
            return;
        }

        Integer agreementVersion = record.getAgreementVersion();
        Integer targetVersion = appAgreement.getVersion();

        if (!Objects.equals(agreementVersion, targetVersion)) {
            updateMsgList.add(appAgreement.getAgreementCode());
            return;
        }

        doProcess(appAgreement, userSession, errMsgList, updateMsgList, checkLogin, record);
    }

    void doProcess(AppAgreementVo appAgreement,
                   Session current,
                   List<String> errMsgList,
                   List<String> updateMsgList,
                   boolean checkLogin,
                   AgreementRecordPo record) {
        // do nothing here
    }


    public final boolean support(AgreementType type) {
        return Objects.equals(type(), type);
    }
}