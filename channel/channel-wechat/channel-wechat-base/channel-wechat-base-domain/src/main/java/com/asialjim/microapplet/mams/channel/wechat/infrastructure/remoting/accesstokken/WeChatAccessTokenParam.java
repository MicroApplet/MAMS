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
package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.accesstokken;

import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.WeChatApiRes;
import com.asialjim.microapplet.remote.annotation.RemoteLifeCycle;
import com.asialjim.microapplet.remote.annotation.Retryable;
import com.asialjim.microapplet.remote.context.*;
import com.asialjim.microapplet.remote.http.annotation.lifecycle.AbstractHttpQueryLifeCycle;
import com.asialjim.microapplet.remote.lifecycle.callback.Before;
import com.asialjim.microapplet.remote.lifecycle.callback.OnRetry;
import com.asialjim.microapplet.remote.lifecycle.callback.RetryWhen;
import com.asialjim.microapplet.remote.lifecycle.callback.SuccessWhen;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * 微信公众平台API AccessToken 参数交换器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@RemoteLifeCycle(WeChatAccessTokenParam.WeChatAccessTokenLifeCycle.class)
public @interface WeChatAccessTokenParam {

    final class WeChatAccessTokenLifeCycle implements Before, SuccessWhen, RetryWhen, OnRetry, RemoteLifeCycle.LifeCycleHandler<WeChatAccessTokenParam> {
        private static final GenericKey<RemoteMethodParameter> ACCESS_TOKEN_CONFIG_KEY = GenericKey.keyOf("ACCESS_TOKEN_CONFIG");

        @Override
        public void doInit(RemoteMethodConfig methodConfig, RemoteMethodParameter methodParameter, WeChatAccessTokenParam annotation) {
            methodConfig.config(ACCESS_TOKEN_CONFIG_KEY, methodParameter);
        }

        @Override
        public void before(Object data, RemoteMethodConfig methodConfig, RemoteReqContext req, RemoteResContext res, Object[] args) {
            req.put(Retryable.RETRY_ABLE_KEY, true);
            RemoteMethodParameter parameter = methodConfig.config(ACCESS_TOKEN_CONFIG_KEY);
            String wechatIndex = (String) args[parameter.getIndex()];
            String accessToken = doQueryAccessToken(wechatIndex);

            Map<String, String> query = Optional.ofNullable(req.get(AbstractHttpQueryLifeCycle.HTTP_QUERY_VALUE)).orElseGet(HashMap::new);
            query.put("access_token", accessToken);

            req.put(AbstractHttpQueryLifeCycle.HTTP_QUERY_VALUE, query);
        }

        @Override
        public void onRetry(Object data, RemoteMethodConfig methodConfig, RemoteReqContext req, RemoteResContext res, Object[] args) {
            RemoteMethodParameter parameter = methodConfig.config(ACCESS_TOKEN_CONFIG_KEY);
            String wechatIndex = (String) args[parameter.getIndex()];

            WeChatAccessTokenRepositoryHolder.repository().refreshAccessToken(wechatIndex);
        }

        @Override
        public boolean retryWhen(Object data, RemoteMethodConfig methodConfig, RemoteReqContext req, RemoteResContext res, Object[] args) {
            if (Objects.isNull(data) || !(data instanceof WeChatApiRes))
                return false;
            WeChatApiRes apiRes = (WeChatApiRes) data;
            RemoteMethodParameter parameter = methodConfig.config(ACCESS_TOKEN_CONFIG_KEY);
            String weChatIndex = (String) args[parameter.getIndex()];
            String accessToken = doQueryAccessToken(weChatIndex);
            // access token 即用户传入的值
            if (StringUtils.equals(weChatIndex, accessToken))
                return false;

            // access token 错误：过期，或者错误等
            switch (apiRes.apiResultEnumeration()) {
                case CODE_40001:
                case CODE_40014:
                case CODE_42001:
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean success(Object data, RemoteMethodConfig methodConfig, RemoteReqContext req, RemoteResContext res, Object[] args) {
            if (Objects.isNull(data) || !(data instanceof WeChatApiRes))
                return false;
            WeChatApiRes weChatApiRes = (WeChatApiRes) data;
            return weChatApiRes.success();
        }

        private String doQueryAccessToken(final String weChatIndex) {
            try {
                String accessToken = WeChatAccessTokenRepositoryHolder.repository().accessToken(weChatIndex);
                if (StringUtils.isBlank(accessToken)) {
                    WeChatAccessTokenRepositoryHolder.repository().refreshAccessToken(weChatIndex);
                    accessToken = WeChatAccessTokenRepositoryHolder.repository().accessToken(weChatIndex);
                }

                if (StringUtils.isNotBlank(accessToken))
                    return accessToken;
            } catch (Throwable t) {
                if (log.isDebugEnabled())
                    log.warn("获取微信：【{}】AccessToken失败：{}", weChatIndex, t.getMessage(), t);
                else
                    log.warn("获取微信：【{}】AccessToken失败：{}", weChatIndex, t.getMessage());
            }

            return weChatIndex;
        }
    }
}