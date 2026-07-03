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

package com.asialjim.microapplet.wx.core.interceptor;

import com.asialjim.microapplet.commons.standard.utils.JsonUtil;
import com.asialjim.microapplet.wx.core.context.BaseWeChatApiRes;
import com.asialjim.microapplet.wx.core.token.WeChatAccessTokenRepository;
import com.asialjim.microapplet.web.restclient.CachedClientHttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import org.springframework.http.HttpRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * 微信 access_token 拦截器
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WeChatAccessTokenInterceptor implements ClientHttpRequestInterceptor {
    private final WeChatAccessTokenRepository accessTokenRepository;

    @Override
    @SuppressWarnings("NullableProblems")
    public ClientHttpResponse intercept(@NonNull HttpRequest request, byte @NonNull [] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        String appid = WeChatApiAttributes.appid(request);
        if (StringUtils.isBlank(appid) || WeChatApiAttributes.skipAccessToken(request))
            return execution.execute(request, body);

        CachedClientHttpResponse response = new CachedClientHttpResponse(executeWithToken(request, body, execution, appid));
        if (!isAccessTokenInvalid(response))
            return response;

        if (log.isDebugEnabled())
            log.debug("微信 access_token 已失效，刷新后重试：{}", appid);

        accessTokenRepository.refreshAccessToken(appid);
        return new CachedClientHttpResponse(executeWithToken(request, body, execution, appid));
    }

    private ClientHttpResponse executeWithToken(HttpRequest request, byte[] body, ClientHttpRequestExecution execution, String appid) throws IOException {
        String token = accessTokenRepository.accessToken(appid);
        return execution.execute(new WeChatAccessTokenHttpRequest(request, token), body);
    }

    private boolean isAccessTokenInvalid(CachedClientHttpResponse response) {
        try {
            byte[] buffer = response.buffer();
            if (ArrayUtils.isEmpty(buffer)) return false;

            MediaType contentType = response.getHeaders().getContentType();
            boolean json = Optional.ofNullable(contentType)
                    .map(item -> MediaType.TEXT_PLAIN.includes(item) || MediaType.APPLICATION_JSON.includes(item) || item.toString().contains("+json"))
                    .orElse(false);
            if (!json) return false;

            BaseWeChatApiRes res = JsonUtil.instance.toBean(buffer, BaseWeChatApiRes.class);
            return res.accessTokenInvalid();
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("解析微信响应错误码失败，跳过 access_token 重试判断：{}", e.getMessage(), e);
            return false;
        }
    }
}
