/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.service;

import com.asialjim.microapplet.wechat.applet.meta.PhoneInfo;
import com.asialjim.microapplet.wechat.applet.meta.UserAuthorizationCode;
import com.asialjim.microapplet.wechat.applet.user.WeChatAppletUserRemoting;
import com.asialjim.microapplet.wechat.applet.user.meta.GetUserPhoneNumberRes;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 微信小程序用户信息服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/15, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Service
public class WxAppletUserInfoService {

    @Resource
    private WeChatAppletUserRemoting weChatAppletUserRemoting;

    public String userPurePhoneNumber(String wxAppid, String code, String encryptedData, String iv) {
        GetUserPhoneNumberRes getUserPhoneNumberRes = this.weChatAppletUserRemoting.userPhoneNumber(wxAppid, new UserAuthorizationCode().setCode(code));
        log.info("小程序：{} 根据用户授权码：{} 密文：{} 向量：{} 获取用户手机号信息结果:{}", wxAppid, code, encryptedData, iv, getUserPhoneNumberRes);
        // 查询接口获取到的手机号
        return Optional.ofNullable(getUserPhoneNumberRes)
                .map(GetUserPhoneNumberRes::getPhoneInfo)
                .map(PhoneInfo::getPurePhoneNumber)
                .filter(StringUtils::isNotBlank)
                .orElse(StringUtils.EMPTY);
    }
}