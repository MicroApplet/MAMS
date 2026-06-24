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

package com.asialjim.microapplet.user.agreement.aop;


import com.asialjim.microapplet.app.code.AgreementType;
import com.asialjim.microapplet.app.entity.web.AppAgreementVo;
import com.asialjim.microapplet.session.Session;
import com.asialjim.microapplet.session.SessionCtx;
import com.asialjim.microapplet.user.agreement.annotation.Agreement;
import com.asialjim.microapplet.user.agreement.code.AgreementResCode;
import com.asialjim.microapplet.user.agreement.process.AgreementProcessor;
import com.asialjim.microapplet.user.agreement.process.AgreementSigner;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Slf4j
@Aspect
@Component
public class AgreementAdvice {
    private final MultiValueMap<AgreementType, AgreementProcessor> map = new LinkedMultiValueMap<>();
    @Resource
    private AgreementSigner agreementSigner;

    @Resource
    private SessionCtx sessionCtx;
    @Resource
    private List<AgreementProcessor> agreementProcessors;

    @Around("@within(com.asialjim.microapplet.user.agreement.annotation.Agreement)")
    public Object classAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Agreement classAgreement = Optional.of(joinPoint).map(JoinPoint::getTarget).map(Object::getClass).map(item -> item.getAnnotation(Agreement.class)).orElse(null);
        if (Objects.isNull(classAgreement)) return joinPoint.proceed();
        return agreementAround(joinPoint, classAgreement, null);
    }

    @Around("@annotation(com.asialjim.microapplet.user.agreement.annotation.Agreement)")
    public Object methodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        if (Objects.isNull(signature) || !(signature instanceof MethodSignature methodSignature))
            return joinPoint.proceed();

        Agreement methodAgreement = Optional.of(methodSignature).map(MethodSignature::getMethod).map(item -> item.getAnnotation(Agreement.class)).orElse(null);
        if (Objects.isNull(methodAgreement)) return joinPoint.proceed();
        return agreementAround(joinPoint, null, methodAgreement);
    }


    public Object agreementAround(ProceedingJoinPoint joinPoint, Agreement classAgreement, Agreement methodAgreement) throws Throwable {
        if (Objects.isNull(classAgreement) && Objects.isNull(methodAgreement)) return joinPoint.proceed();

        Set<AgreementType> beforeType = new HashSet<>();
        Set<AgreementType> afterType = new HashSet<>();
        collectAgreement(classAgreement, methodAgreement, beforeType, afterType);
        if (CollectionUtils.isEmpty(beforeType) && CollectionUtils.isEmpty(afterType)) return joinPoint.proceed();

        List<String> errorMsgList = new ArrayList<>();
        List<String> updateMsgList = new ArrayList<>();
        List<AppAgreementVo> agreementList = new ArrayList<>();

        process(beforeType, errorMsgList, updateMsgList, agreementList);

        if (CollectionUtils.isNotEmpty(updateMsgList)) AgreementResCode.VersionChanged.thrWithMsg(updateMsgList);
        if (CollectionUtils.isNotEmpty(errorMsgList)) AgreementResCode.UserNotSign.thrWithMsg(errorMsgList);
        if (CollectionUtils.isNotEmpty(agreementList))
            AgreementResCode.UserAuthExpired.thrWithMsg(agreementList.stream().map(AppAgreementVo::getAgreementCode).toList());

        Object proceed = joinPoint.proceed();

        process(afterType, errorMsgList, updateMsgList, agreementList);

        if (CollectionUtils.isNotEmpty(updateMsgList))
            AgreementResCode.VersionChanged.thrWithDataAndMsg(proceed, updateMsgList);
        if (CollectionUtils.isNotEmpty(errorMsgList))
            AgreementResCode.UserNotSign.thrWithDataAndMsg(proceed, errorMsgList);
        if (CollectionUtils.isNotEmpty(agreementList))
            AgreementResCode.UserAuthExpired.thrWithDataAndMsg(proceed, agreementList.stream().map(AppAgreementVo::getAgreementCode).toList());
        return proceed;
    }

    private void process(Set<AgreementType> agreementSet, List<String> errMsgList, List<String> updateMsgList, List<AppAgreementVo> agreementList) {
        Session current = this.sessionCtx.currentSession();
        if (CollectionUtils.isEmpty(agreementSet)) return;

        for (AgreementType agreement : agreementSet) {
            List<AgreementProcessor> processes = this.agreementSigner.typeOf(agreement);
            if (CollectionUtils.isEmpty(processes)) continue;

            for (AgreementProcessor process : processes) {
                process.process(current, errMsgList, updateMsgList, true, agreementList);
            }
        }
    }

    private void collectAgreement(Agreement classAgreement, Agreement methodAgreement, Set<AgreementType> beforeType, Set<AgreementType> afterType) {
        collectAgreement(classAgreement, beforeType, afterType);
        collectAgreement(methodAgreement, beforeType, afterType);
    }

    private void collectAgreement(Agreement agreement, Set<AgreementType> beforeType, Set<AgreementType> afterType) {
        if (Objects.nonNull(agreement)) {
            AgreementType[] before = agreement.before();
            if (ArrayUtils.isNotEmpty(before)) {
                for (AgreementType type : before) {
                    if (Objects.isNull(type)) continue;
                    beforeType.add(type);
                }
            }
            AgreementType[] after = agreement.after();
            if (ArrayUtils.isNotEmpty(after)) {
                for (AgreementType type : after) {
                    if (Objects.isNull(type)) continue;
                    afterType.add(type);
                }
            }
        }
    }
}