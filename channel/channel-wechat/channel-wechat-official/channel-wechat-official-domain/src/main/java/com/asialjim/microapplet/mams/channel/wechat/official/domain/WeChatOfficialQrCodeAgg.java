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

package com.asialjim.microapplet.mams.channel.wechat.official.domain;

import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.qrcode.QrCodeScene;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.qrcode.SceneQrCodeHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 微信公众号二维码聚合根组件
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class WeChatOfficialQrCodeAgg {
    private final List<SceneQrCodeHandler> qrCodeHandlers;

    /**
     * 场景列表
     *
     * @return {@link List<QrCodeScene.Meta>}
     * @since 2025/4/29
     */
    public List<QrCodeScene.Meta> sceneList() {
        return qrCodeHandlers.stream()
                .map(SceneQrCodeHandler::getClass)
                .map(item -> item.getAnnotation(QrCodeScene.class))
                .map(item -> new QrCodeScene.Meta().setName(item.value()).setDesc(item.desc()))
                .collect(Collectors.toList());
    }

}