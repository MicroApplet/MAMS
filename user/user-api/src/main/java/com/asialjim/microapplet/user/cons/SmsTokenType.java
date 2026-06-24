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

package com.asialjim.microapplet.user.cons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum SmsTokenType {

    DEFAULT("default", "005030", "小程序业务操作"),

    AUTH("auth", "005030", "小程序实名"),
    ThreeFactorRealNameAuth("RealNameAuthBy3Factor","005030","小程序三要素实名"),

    CHANGE_AUTH_BIND("changeAuthBind", "005030", "小程序换绑"),

    ONE_LOGIN("oneLogin", "005030", "小程序验证登录"),

    BIND("bind", "005030", "小程序签约加挂卡"),

    REST_PWD("reSetPwd", "005030", "小程序重置密码"),

    CHANGE_BIND("changeBind", "005030", "小程序更换绑定卡"),

    CHANGE_PHONE("changePhone", "005030", "小程序更换手机号"),

    UNBIND("unbind", "005030", "小程序解约"),

    WITHDRAW("withdraw", "005030", "小程序提现"),

    UPDATEONEPHONE("updateOnePhone", "005030", "小程序修改预留手机号"),

    UPDATEONENO("updateOneNo", "005030", "小程序更换二类户绑定卡"),

    OPENSECONDCARD("openSecondCard", "005030", "小程序开立二类户"),

    DEBITCARD("debitCard", "005030", "小程序借记卡申请"),

    APPOINTMENTAPPLY("appointMentApply", "005030", "小程序预约办卡"),

    BOOKINGCARDAPPLY("bookingCardApply", "005030", "小程序预约办卡"),

    RECOMMEND("recommend", "005030", "小程序推荐有礼活动"),

    APPLY_GEEK_LOAN("applyGeekLoan", "005030", "小程序极客贷申请"),

    BOOKING_COMMIT("bookingCommit", "005030", "小程序网点预约"),

    APPKY_CARD_BIND_INFO("appkyCardBindInfo", "005030", "小程序建立预约办卡绑定关系"),

    MGM_CREDIT_APPLY("creditApply", "005030", "小程序申请信用卡绑定关系"),

    NEW_USER("recommendAuth", "005030", "小程序参加推荐有礼活动"),

    DZ_PHONE("dzPhone", "005030", "小程序金融终端有礼活动参与"),

    PANDA_LOAN("pandaLoan", "005030", "小程序活动参与"),

    TFSHOP_LOAN("tfShopLoan", "005030", "小程序活动参与"),

    TFHOME_LOAN("tfHomeLoan", "005030", "小程序活动参与"),

    LOGOFF_BOOKING("LOGOFF_BOOKING", "005030", "小程序用户注销"),

    YXF("yxf", "005030", "小程序参与推荐悦享福会员有礼活动"),


    BaZhongCar("bazhongCarVoucher", "005030", "登录小程序申报消费劵活动"),

    NON_COUNTER_QUOTA("nonCounterQuota", "005030", "非柜限额业务")

    ;

    private final String typeName;
    private final String templateNo;
    private final String msgTemplate;

    private static final Map<String, SmsTokenType> TYPE_MAP = new HashMap<>();

    public static SmsTokenType typeOf(String type) {
        if (StringUtils.isBlank(type))
            return DEFAULT;

        SmsTokenType smsTokenType = TYPE_MAP.get(type);
        if (Objects.nonNull(smsTokenType))
            return smsTokenType;

        smsTokenType = Arrays.stream(SmsTokenType.values())
                .filter(Objects::nonNull)
                .filter(item -> StringUtils.equalsIgnoreCase(item.getTypeName(), type))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(smsTokenType)) {
            TYPE_MAP.put(type, smsTokenType);
        } else smsTokenType = DEFAULT;

        return smsTokenType;
    }
}