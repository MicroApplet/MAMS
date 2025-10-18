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

package com.asialjim.microapplet.mams.web;

import com.asialjim.microapplet.mams.service.WxAppletUserInfoService;
import com.asialjim.microapplet.mams.wx.applet.api.WxAppletUserInfoApi;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序用户信息API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/15, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@AllArgsConstructor
@RequestMapping(WxAppletUserInfoApi.path)
public class WxAppletUserInfoController implements WxAppletUserInfoApi {
    private final WxAppletUserInfoService wxAppletUserInfoService;

    @Override
    public String userPurePhoneNumber(String wxAppid, String code, String encryptedData, String iv) {
        return this.wxAppletUserInfoService.userPurePhoneNumber(wxAppid, code, encryptedData, iv);
    }
}