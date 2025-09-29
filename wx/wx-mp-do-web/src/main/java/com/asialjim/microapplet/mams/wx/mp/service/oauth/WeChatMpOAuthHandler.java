/*
 *    Copyright 2014-$year.today <a href="mailto:asialjim@qq.com">Asial Jim</a>
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

package com.asialjim.microapplet.mams.wx.mp.service.oauth;

import com.asialjim.microapplet.mams.wx.mp.vo.oauth.OAuthPageVo;
import com.asialjim.microapplet.mams.wx.mp.vo.oauth.WeChatMpOAuthHandlerVo;
import jakarta.validation.constraints.NotBlank;

import java.net.URI;
import java.time.Duration;
import java.util.Map;

/**
 * 公众号授权链接处理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface WeChatMpOAuthHandler {

    @NotBlank
    String handler();

    @NotBlank
    String description();

    /**
     * 获取链接
     *
     * @param appid {@link String appid}
     * @param state {@link String state}
     * @return {@link URI }
     * @since 2025/9/5
     */
    URI page(String appid, String state);

    /**
     * 构建授权链接
     *
     * @param appid         {@link String appid}
     * @param url           {@link String url}
     * @param parameter     {@link Map 查询参数}
     * @param duration      {@link Duration 链接有效期: 为空表示不过期}
     * @param maxClickTimes {@link Long 链接最大允许的点击次数: 为空表示不限制点击次数}
     * @return {@link String 返回的 state}
     * @since 2025/9/5
     */
    String build(String appid, String url, Map<String, String> parameter, Duration duration, Long maxClickTimes);

    OAuthPageVo create(OAuthPageVo vo);

    default WeChatMpOAuthHandlerVo vo() {
        WeChatMpOAuthHandlerVo vo = new WeChatMpOAuthHandlerVo();
        return vo.setHandlerName(this.handler()).setHandlerDesc(this.description());
    }
}