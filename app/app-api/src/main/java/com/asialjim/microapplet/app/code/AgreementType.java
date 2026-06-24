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

package com.asialjim.microapplet.app.code;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum AgreementType {
    AppletService("applet_service_agreement", "小程序服务协议"),
    DisclaimerStatement("disclaimer_statement", "三方免责声明"),
    PersonalInformationInventory("personal_information_inventory", "个人信息清单"),
    PrivacyPolicyAgreement("privacy_policy_agreement", "隐私政策协议"),
    GeoLocationPermission("geo_location_permission", "地理位置授权"),
    UserInfoAuthAgreement("userinfo_authorization_agreement", "用户信息授权协议"),
    ExternalInformationInventory("external_information_inventory", "三方信息共享清单");

    private final String code;
    private final String name;

    public String contentType(){
        return "text";
    }

    public static AgreementType codeOf(String code){
        if (StringUtils.isBlank(code))
            return null;
        return Arrays.stream(values())
                .filter(Objects::nonNull)
                .filter(item -> code.equals(item.getCode()))
                .findFirst()
                .orElse(null);
    }
}