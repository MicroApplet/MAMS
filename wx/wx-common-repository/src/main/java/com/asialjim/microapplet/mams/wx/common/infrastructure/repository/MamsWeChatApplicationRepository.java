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

package com.asialjim.microapplet.mams.wx.common.infrastructure.repository;

import com.asialjim.microapplet.mams.app.api.ChlAppApi;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import com.asialjim.microapplet.wechat.application.WeChatApplication;
import com.asialjim.microapplet.wechat.application.WeChatApplicationRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 微信应用仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/23, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
@AllArgsConstructor
public class MamsWeChatApplicationRepository implements WeChatApplicationRepository {
    private final ChlAppApi chlAppApi;


    /**
     * 所有应用程序
     *
     * @return {@link List<WeChatApplication>}
     */
    @Override
    public List<WeChatApplication> allApps() {
        List<ChlAppVo> vos = chlAppApi.queryByChl(ChannelType.WeChat.getCode());
        log.info("MAMS 微信应用表：{}", vos);
        return Optional.ofNullable(vos)
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(item -> {
                    WeChatApplication app = new WeChatApplication();
                    app.setId(item.getId());
                    app.setName(item.getChlAppName());
                    app.setSubjectId(item.getChlSubjectId());
                    app.setAgentId(item.getChlAgentId());
                    app.setAppid(item.getChlAppId());
                    app.setSecret(item.getChlAppSecret());
                    app.setToken(item.getChlToken());
                    app.setAesKey(item.getChlEncKey());
                    app.setEncType(item.getChlEncType());
                    app.setAppType(item.getChlAppType());
                    app.setUrl(item.getUrl());
                    app.setManager(item.getManager());
                    app.setDescription(item.getDescription());
                    return app;
                }).toList();
    }
}