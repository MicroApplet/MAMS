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
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.accesstoken.WeChatAccessTokenRes;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.repository.WeChatAppRepository;
import com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * 微信公众平台API 访问令牌仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatAccessTokenRepository {
    @Resource
    private WeChatAppRepository weChatAppRepository;


    @PostConstruct
    public void init() {
        WeChatAccessTokenRepositoryHolder.repository(this);
    }

    @Resource
    private WeChatAccessTokenCache accessTokenCache;
    @Resource
    private WeChatAccessTokenRemoting accessTokenRemoting;

    /**
     * 获取微信 API 访问令牌
     *
     * @param weChatIndex {@link String 微信公众号应用编号}
     * @return {@link String API 访问令牌}
     * @since 2023/1/28
     */
    public String accessToken(String weChatIndex) {
        WeChatApp app = this.weChatAppRepository.queryByIndex(weChatIndex);

        String appid = app.getAppid();
        String secret = app.getAppSecret();
        String accessToken = accessTokenCache.get(appid);
        if (StringUtils.isNotBlank(accessToken))
            return accessToken;

        // 获取锁
        String tag;
        String lockTag;
        do {
            tag = UUID.randomUUID().toString();
            lockTag = accessTokenCache.getLockTag(appid, tag);
            if (StringUtils.equals(tag, lockTag))
                break;
            //ThreadUtils.sleep(Duration.ofMillis(500));
        } while (!StringUtils.equals(tag, lockTag));

        // 执行网络查询
        WeChatAccessTokenRes weChatAccessTokenRes = accessTokenRemoting.accessToken(appid, secret);
        String token;
        int index = 1;
        do {
            token = Optional.ofNullable(weChatAccessTokenRes)
                    .map(WeChatAccessTokenRes::getAccess_token)
                    .orElse(StringUtils.EMPTY);
            if (StringUtils.isNotBlank(token)) {
                accessTokenCache.set(appid, token);
                accessTokenCache.cached(appid);
                accessTokenCache.delLockTag(appid);
            }
            index++;
        } while (StringUtils.isBlank(token) && index <= 3);

        return token;
    }

    /**
     * 刷新微信 API 访问令牌缓存
     *
     * @param weChatIndex {@link String 微信公众号应用编号}
     * @since 2023/1/28
     */
    public void refreshAccessToken(String weChatIndex) {
        WeChatApp app = this.weChatAppRepository.queryByIndex(weChatIndex);
        if (Objects.isNull(app))
            return;
        String appid = app.getAppid();
        accessTokenCache.remove(appid);
    }
}