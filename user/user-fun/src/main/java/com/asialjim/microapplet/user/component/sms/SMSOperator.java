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
package com.asialjim.microapplet.user.component.sms;

import com.asialjim.microapplet.commons.standard.context.Res;
import com.asialjim.microapplet.session.SessionResCode;
import com.asialjim.microapplet.user.code.PhoneAuthCode;
import com.asialjim.microapplet.user.component.sms.validator.SendSMSBizValidator;
import com.asialjim.microapplet.user.component.sms.validator.VerifySMSBizValidator;
import com.asialjim.microapplet.user.entity.sms.SMS;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
//@SuppressWarnings("unchecked")
public class SMSOperator implements InitializingBean {
    private static int maxErrorCheckTime = 3;                // 时间限制，当前用户输入错误验证码次数超过此值时，用户获取验证码资格将会被锁定
    private static int maxErrorCheckNo = 3;                // 一定时间内，用户输入短信验证码错误次数超过此属性值，则锁定该用户获取验证码的资格一段时间

    private static int lockHoursWhenSMSError = 1;                // 一定时间内，用户输入短信验证码错误次数超过 maxErrorCheckNo 值， 则该用户验证码获取资格锁定 此值属性的时间，单位：分钟
    private static int gapMinutesBetweenSMS = 1;                // 用户、手机号 一分钟内只允许获取一次验证码

    private static int maxUserSMSNo = 20;               // 单个用户在 指定时间内 最大允许获取验证码次数
    private static int maxUserSMSTime = 120;              // 单个用户 在 当前值 时间内，最大允许获取验证码 maxUserSMSNo 次数的 时间限制, 单位：分钟

    private static int maxTelSMSNo = 10;               // 单个手机号在指定时间内，最大允许获取验证码次数
    private static int maxTelSMSTime = 120;              // 单个手机号 在当前值 时间内，最大允许获取验证码 maxTelSMSNo 次数的 时间限制, 单位：分钟

    private static int maxBizSMSNo = 3;                // 单用户业务，在单位时间内， 最大允许发送短信验证码次数
    private static int maxBizSMSTime = 10;               // 单用户业务，在单位时间内，最大允许发送短信验证码次数的 时间限制， 单位：分钟

    private static int smsValidTime = 3;                // 短信验证码创建后，可验证的时间, 单位：分钟

    private static final String SMS_LOCK = "mb:SMS:%s:LOCK";
    private static final String USR_REC = "mb:SMS:%s:USR-REC";
    private static final String TEL_REC = "mb:SMS:%s:TEL-REC";
    private static final String ERR_REC = "mb:SMS:%s:ERR-REC";
    private static final String TEL_LAST_SMS = "mb:SMS:%s:%s:TEL";
    private static final String SMS_SUCCESS = "mb:SMS-SUCCESS:%s:%s:%s";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private VerifySMSBizValidator verifySMSBizValidator;
    @Resource
    private SendSMSBizValidator sendSMSBizValidator;
    @Resource
    private SMSProperty smsProperty;

    @Override
    public void afterPropertiesSet() {
        if (Objects.isNull(this.smsProperty))
            return;

        maxErrorCheckTime = smsProperty.getMaxErrorCheckTime();
        maxErrorCheckNo = smsProperty.getMaxErrorCheckNo();
        lockHoursWhenSMSError = smsProperty.getLockHoursWhenSMSError();
        gapMinutesBetweenSMS = smsProperty.getGapMinutesBetweenSMS();
        maxUserSMSNo = smsProperty.getMaxUserSMSNo();
        maxUserSMSTime = smsProperty.getMaxUserSMSTime();
        maxTelSMSNo = smsProperty.getMaxTelSMSNo();
        maxTelSMSTime = smsProperty.getMaxTelSMSTime();
        maxBizSMSNo = smsProperty.getMaxBizSMSNo();
        maxBizSMSTime = smsProperty.getMaxBizSMSTime();
        smsValidTime = smsProperty.getSmsValidTime();
    }

    public void create(String user, String biz, String phone, String smsToken, Map<String, String> params) {
        // 用户未登录
        if (StringUtils.isBlank(user))
            SessionResCode.Forbid4Tourist.thr();

        if (StringUtils.isBlank(phone))
            Res.ParameterEmptyEx.thr("未提供手机号");

        this.sendSMSBizValidator.smsSendBizValid(user, biz, phone, params);

        // ////////////////////////////////  前置检查
        // 1. 判断当前用户在输入错误验证码次数超过限制，用户被锁定无法获取验证码后是否解锁
        if (Objects.nonNull(redisTemplate.opsForValue().get(String.format(SMS_LOCK, user))))
            PhoneAuthCode.SmsCheckTimeErr.thr(maxErrorCheckNo, lockHoursWhenSMSError);

        // ////////////////////////////////  用户、手机 获取验证码频率是否超限检查
        // 当前时间
        long currentTimeMillis = System.currentTimeMillis();
        // 当前手机号上次获取验证码时间
        Long telLastSMSTime = Optional.ofNullable((Long) redisTemplate.opsForValue().get(String.format(TEL_LAST_SMS, phone, biz))).orElse(0L);


        // 手机上次获取验证码后，最近可再次获取的时间
        long telLastSMSExpiresTime = telLastSMSTime + TimeUnit.MINUTES.toMillis(gapMinutesBetweenSMS);
        if (!Objects.equals(0L, telLastSMSTime) && telLastSMSExpiresTime > currentTimeMillis)
            PhoneAuthCode.RetryAfterAWhile.thr(((telLastSMSExpiresTime - currentTimeMillis) / 1000));


        // 获取当前用户已创建的验证码记录
        String usrRecKey = String.format(USR_REC, user);
        // 获取当前手机号已创建的验证码记录
        String telRecKey = String.format(TEL_REC, phone);

        //noinspection unchecked
        List<SMS> usrRecs = Optional.ofNullable((List<SMS>) redisTemplate.opsForValue().get(usrRecKey)).orElseGet(ArrayList::new);
        //noinspection unchecked
        List<SMS> telRecs = Optional.ofNullable((List<SMS>) redisTemplate.opsForValue().get(telRecKey)).orElseGet(ArrayList::new);

        // 处理过期数据
        deleteInvalidRec(currentTimeMillis, usrRecs, telRecs);

        // 判断当前用户在规定时间内，获取的验证码次数是否超过限制
        long usrRecNo = usrRecs.stream().filter(item -> item.getUsrWindowTime() > currentTimeMillis).count();
        if (usrRecNo >= maxUserSMSNo)
            PhoneAuthCode.RetryTimesLimited.thr(maxUserSMSTime, maxUserSMSNo, maxUserSMSTime);

        // 判断当前用户业务在规定时间内，获取验证码次数是否超过限制
        long bizRecNo = usrRecs.stream().filter(item -> biz.equals(item.getBiz())).filter(item -> item.getBizWindowTime() > currentTimeMillis).count();
        if (bizRecNo >= maxBizSMSNo)
            PhoneAuthCode.RetryTimesLimitedOnBiz.thr(maxBizSMSTime, maxBizSMSNo, maxBizSMSTime);

        // 判断当前手机号在规定时间内，获取验证码次数是否超过限制
        long telRecNo = telRecs.stream().filter(item -> item.getTelWindowTime() > currentTimeMillis).count();
        if (telRecNo >= maxTelSMSNo)
            PhoneAuthCode.RetryTimesLimitedOnPhone.thr(maxTelSMSTime, maxTelSMSNo, maxTelSMSTime);

        // 新建验证码记录
        SMS sms = SMS.builder()
                .biz(biz)                                                                                           // 业务标记
                .tell(phone)
                .token(smsToken)                                                                                       // 验证码
                .createTime(currentTimeMillis)                                                                      // 创建时间
                .valid(currentTimeMillis + TimeUnit.MINUTES.toMillis(smsValidTime))                                 // 验证码过期时间
                .usrWindowTime(currentTimeMillis + TimeUnit.MINUTES.toMillis(maxUserSMSTime))                       // 验证码用户口径存活时间，超过此时间，此验证码将不被统计在用户最大允许发送验证码次数中
                .telWindowTime(currentTimeMillis + TimeUnit.MINUTES.toMillis(maxTelSMSTime))                        // 验证码手机口径存活时间，超过此时间，此验证码将不被统计在手机最大允许发送验证码次数中
                .bizWindowTime(currentTimeMillis + TimeUnit.MINUTES.toMillis(maxBizSMSTime))                        // 验证码业务口径存活时间，超过此时间，此验证码将不被统计在业务最大允许发送验证码次数中
                .expires(currentTimeMillis + TimeUnit.MINUTES.toMillis(Math.max(maxUserSMSTime, maxTelSMSTime)))    // 缓存活期时间，超过此时间后，下次获取验证码时，此记录将被清除
                .build();

        usrRecs.add(sms);
        telRecs.add(sms);
        redisTemplate.opsForValue().set(usrRecKey, usrRecs,Duration.ofDays(2));
        redisTemplate.opsForValue().set(telRecKey, telRecs,Duration.ofDays(2));
        redisTemplate.opsForValue().set(String.format(TEL_LAST_SMS, phone, biz), currentTimeMillis,Duration.ofMinutes(30));
    }


    public String preCheck(String user,
                           @NotBlank(message = "未指定短信验证码类型") String biz,
                           @NotBlank(message = "未指定短信验证码手机号") String phone,
                           @NotBlank(message = "未指定短信验证码") String token) {

        // 用户未登录
        if (StringUtils.isBlank(user))
            SessionResCode.Forbid4Tourist.thr();

        String preKey = "mb:SMS:pre-check:%s:%s:%s:%s".formatted(user, phone, biz, token);
        String checked = (String) this.redisTemplate.opsForValue().get(preKey);
        if (StringUtils.isNotBlank(checked))
            return checked;

        // 当时间
        long currentTimeMillis = System.currentTimeMillis();
        String usrKey = String.format(USR_REC, user);

        // 获取当前用户验证码创建记录
        //noinspection unchecked
        List<SMS> usrRec = Optional.ofNullable((List<SMS>) redisTemplate.opsForValue().get(usrKey)).orElseGet(ArrayList::new);

        // 清理过期数据
        deleteInvalidRec(currentTimeMillis, usrRec, null);

        // 筛选指定业务且尚未过期的短信验证码
        List<SMS> smsList = usrRec.stream()
                .filter(Objects::nonNull)
                .filter(item -> biz.equals(item.getBiz()))
                .filter(item -> currentTimeMillis <= item.getValid())
                .toList();

        if (CollectionUtils.isEmpty(smsList))
            throw PhoneAuthCode.SmsTokenHadExpired.ex();

        for (SMS sms : smsList) {
            // 验证码验证成功
            if (validateSMS(token, phone, sms)) {
                // 验证成功增加随机串返回，用于部分业务流程需要二次验证
                String s = UUID.randomUUID().toString().replace("-", StringUtils.EMPTY);
                this.redisTemplate.opsForValue().set(preKey, s, Duration.ofMinutes(10));
                return s;
            }
        }

        return null;
    }

    public String validate(String user, String biz, String phone, String token, Map<String, String> params) {
        // 用户未登录
        if (StringUtils.isBlank(user))
            SessionResCode.Forbid4Tourist.thr();

        // ////////////////////////////////  前置检查
        // 1. 判断当前用户在输入错误验证码次数超过限制，用户被锁定无法获取验证码后是否解锁
        if (Objects.nonNull(redisTemplate.opsForValue().get(String.format(SMS_LOCK, user))))
            PhoneAuthCode.SmsCheckTimeErr.thr(maxErrorCheckNo, lockHoursWhenSMSError);

        verifySMSBizValidator.valid(phone, user, token, biz, params);

        // 当时间
        long currentTimeMillis = System.currentTimeMillis();

        // 验证
        String preCheck = this.preCheck(user, biz, phone, token);
        if (StringUtils.isNotBlank(preCheck)) {
            redisTemplate.opsForValue().set(String.format(SMS_SUCCESS, user, biz, preCheck), phone,Duration.ofMinutes(30));
            return preCheck;
        }

        // 验证失败
        String errKey = String.format(ERR_REC, user);
        //noinspection unchecked
        List<SMS> errRecSources = Optional.ofNullable((List<SMS>) redisTemplate.opsForValue().get(errKey)).orElseGet(ArrayList::new);
        List<SMS> errRecs = new ArrayList<>();
        for (SMS rec : errRecSources) {
            long expires = rec.getExpires();
            if (expires > currentTimeMillis)
                errRecs.add(rec);
        }

        // 验证码次数超过指定次数，锁定验证码获取资格
        if (errRecs.size() > maxErrorCheckNo) {
            String lockKey = String.format(SMS_LOCK, user);
            // 锁定用户继续获取短信验证码资格一段时间
            redisTemplate.opsForValue().set(lockKey, "lock", Duration.ofHours(lockHoursWhenSMSError));
        }

        // 新增错误验证码记录
        SMS record = SMS.builder().token(token).biz(biz).expires(currentTimeMillis + TimeUnit.MINUTES.toMillis(maxErrorCheckTime)).build();
        errRecs.add(record);

        // 设置错误验证码记录并保存
        redisTemplate.opsForValue().set(errKey, errRecs,Duration.ofDays(1));
        throw PhoneAuthCode.SmsTokenErr.ex();
    }

    private boolean validateSMS(String token, String phone, SMS sms) {
        // 不检查手机号
       /*
        if (StringUtils.isBlank(phone))
            return StringUtils.equals(token, sms.getToken());
        */

        // 检查手机号
        // 验证码匹配，且手机号匹配
        return token.equals(sms.getToken()) && phone.equals(sms.getTell());
    }

    /**
     * 验证短信验证码是否验证成功
     *
     * @param token {@link String token}
     */
    public boolean validateSMSSuccess(String phone, String token, String openId, String bizCode) {
        if (StringUtils.isEmpty(phone))
            throw PhoneAuthCode.SmsTokenAndPhoneMismatch.ex();
        Object o = redisTemplate.opsForValue().get(String.format(SMS_SUCCESS, openId, bizCode, token));
        if (Objects.isNull(o))
            throw PhoneAuthCode.SmsTokenAndPhoneMismatch.ex();
        if (!phone.equals(String.valueOf(o)))
            throw PhoneAuthCode.SmsTokenAndPhoneMismatch.ex();
        return true;
    }

    /**
     * 删除缓存已过期的验证码记录
     *
     * @param currentTimeMillis {@link Long 当前时间}
     * @param usrRecs           {@link List 用户创建验证码记录}
     * @param telRecs           {@link List 手机创建验证码记录}
     * @since 2023/4/14
     */
    private void deleteInvalidRec(long currentTimeMillis, List<SMS> usrRecs, List<SMS> telRecs) {
        deleteInvalidRec(currentTimeMillis, usrRecs);
        deleteInvalidRec(currentTimeMillis, telRecs);
    }

    private void deleteInvalidRec(long currentTimeMillis, List<SMS> recs) {
        if (CollectionUtils.isNotEmpty(recs)) {
            List<SMS> temps = new ArrayList<>(recs);
            recs.clear();
            for (SMS rec : temps) {
                // 记录缓存过期时间
                long expires = rec.getExpires();
                // 记录过期，删除记录
                if (expires > currentTimeMillis)
                    recs.add(rec);
            }
            recs.sort(SMS::compareTo);
        }
    }

}