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

package com.asialjim.microapplet.mams.wx.mp.service.oauth;

import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.mams.app.api.ChlAppApi;
import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import com.asialjim.microapplet.mams.user.api.UserSessionApi;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import com.asialjim.microapplet.wechat.application.WeChatApplication;
import com.asialjim.microapplet.wechat.application.WeChatApplicationRepository;
import com.asialjim.microapplet.wechat.official.remoting.user.WeChatPublicAccountUserRemoting;
import com.asialjim.microapplet.wechat.official.remoting.user.meta.WeChatPublicAccountUserAccessTokenRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 公众号 授权链接登录
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
//@Component
@RequiredArgsConstructor
public class WeChatMpOAuthLoginService {

    private final WeChatPublicAccountUserRemoting weChatPublicAccountUserRemoting;
    private final WeChatApplicationRepository.Aggregator aggregator;
    private final UserSessionApi userSessionApi;
    private final ChlAppApi chlAppApi;


    public MamsSession login(String appid, String code) {
        log.info("微信公众号: {} 用户授权登录进入,参数: {}", appid, code);
        WeChatApplication app = this.aggregator.appByIndexThrowable(appid);

        WeChatPublicAccountUserAccessTokenRes accessTokenRes = this.weChatPublicAccountUserRemoting.userAccessToken(app.getAppid(), app.getSecret(), code);
        log.info("微信公众号: {} 用户: {} 登录腾讯结果: {}", appid, code, accessTokenRes);

        ChlAppVo chlAppVo = this.chlAppApi.queryByChlAndChlIndex(ChannelType.WeChat.getCode(), appid);
        log.info("微信公众号: {} 所属渠道应用信息: {}", appid, chlAppVo);

        ChlUserVo chlUser = new ChlUserVo();
        chlUser.setAppid(chlAppVo.getAppid());
        chlUser.setChlType(ChannelType.WeChat.getCode());
        chlUser.setChlAppId(appid);
        chlUser.setChlAppType(ChannelAppType.WeChatMp.getCode());
        chlUser.setChlUserId(accessTokenRes.getOpenid());
        chlUser.setChlUnionId(accessTokenRes.getUnionid());
        chlUser.setChlUserCode(code);
        chlUser.setChlUserToken(accessTokenRes.getAccessToken());

        log.info("构建渠道用户登录参数: {}", chlUser);
        MamsSession login = this.userSessionApi.login(chlUser);
        log.info("渠道用户登录结果: {}", login);
        return login;
    }
}