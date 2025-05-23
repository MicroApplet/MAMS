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


import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.channel.wechat.domain.WeChatAppAgg;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 微信公众平台API 访问令牌仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatAccessTokenRepository {

    @PostConstruct
    public void init() {
        WeChatAccessTokenRepositoryHolder.repository(this);
    }

    /**
     * 获取微信 API 访问令牌
     *
     * @param weChatIndex {@link String 微信公众号应用编号}
     * @return {@link String API 访问令牌}
     * @since 2023/1/28
     */
    public String accessToken(String weChatIndex) {
        WeChatAppAgg agg = App.beanOrNull(WeChatAppAgg.class);
        return agg.withId(weChatIndex).accessToken();
    }

    /**
     * 刷新微信 API 访问令牌缓存
     *
     * @param weChatIndex {@link String 微信公众号应用编号}
     * @since 2023/1/28
     */
    public void refreshAccessToken(String weChatIndex) {
        WeChatAppAgg agg = App.beanOrNull(WeChatAppAgg.class);
        agg.withId(weChatIndex).refreshAccessToken();
    }
}