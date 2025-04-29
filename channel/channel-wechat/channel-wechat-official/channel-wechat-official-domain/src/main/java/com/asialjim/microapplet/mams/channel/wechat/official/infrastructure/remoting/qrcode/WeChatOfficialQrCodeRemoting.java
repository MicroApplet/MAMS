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
package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.qrcode;

import com.asialjim.microapplet.mams.channel.wechat.constant.WeChatCons;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.accesstokken.WeChatAccessTokenParam;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.qrcode.meta.CreateQrCodeRequest;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.qrcode.meta.CreateQrCodeResponse;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.qrcode.meta.QrCodeProperty;
import com.asialjim.microapplet.remote.http.annotation.HttpMapping;
import com.asialjim.microapplet.remote.http.annotation.HttpMethod;
import com.asialjim.microapplet.remote.http.annotation.body.JsonBody;
import com.asialjim.microapplet.remote.net.annotation.Server;

import java.time.Duration;

/**
 * 微信公众号二维码网络客户端
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Server(
        supplier = WeChatCons.Supplier.Tencent,
        namespace = WeChatCons.Namespace.WeChat,
        schema = WeChatCons.Api.DefaultSchema,
        host = WeChatCons.Api.DefaultWeChatHost,
        port = WeChatCons.Api.DefaultWeChatPort
)
public interface WeChatOfficialQrCodeRemoting {

    /**
     * 创建微信公众号二维码
     *
     * @param weChatIndexOrAccessToken {@link String 公众号索引}
     * @param body                     {@link CreateQrCodeRequest body}
     * @return {@link CreateQrCodeResponse }
     * @since 2024/2/7
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/cgi-bin/qrcode/create")
    CreateQrCodeResponse createQrCode(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @JsonBody CreateQrCodeRequest body);

    /**
     * 创建临时二维码
     *
     * @param index {@link String index}
     * @param scene {@link String 业务场景}
     * @return {@link CreateQrCodeResponse }
     */
    default CreateQrCodeResponse create(String index, String scene, Duration duration) {
        QrCodeProperty req = QrCodeProperty.builder()
                .expireSeconds((int) duration.getSeconds())
                .sceneStr(scene)
                .actionName(QrCodeProperty.QrCodeActionName.QR_STR_SCENE.name())
                .build();

        return createQrCode(index, req.createApiReqParam());
    }

    /**
     * 创建临时二维码
     *
     * @param index {@link String index}
     * @param scene {@link Integer 业务场景}
     * @return {@link CreateQrCodeResponse }
     * @since 2025/4/29
     */
    default CreateQrCodeResponse create(String index, int scene, Duration duration) {
        QrCodeProperty req = QrCodeProperty.builder()
                .expireSeconds((int) duration.getSeconds())
                .sceneId(scene)
                .actionName(QrCodeProperty.QrCodeActionName.QR_STR_SCENE.name())
                .build();

        return createQrCode(index, req.createApiReqParam());
    }


    /**
     * 创就永久二维码
     *
     * @param index {@link String index}
     * @param scene {@link String scene}
     * @return {@link CreateQrCodeResponse }
     * @since 2025/4/29
     */
    default CreateQrCodeResponse create(String index, String scene) {
        QrCodeProperty req = QrCodeProperty.builder()
                .sceneStr(scene)
                .actionName(QrCodeProperty.QrCodeActionName.QR_LIMIT_STR_SCENE.name())
                .build();

        return createQrCode(index, req.createApiReqParam());
    }

    /**
     * 创建永久二维码
     *
     * @param index {@link String 公众号索引}
     * @param scene {@link Integer 业务场景}
     * @return {@link CreateQrCodeResponse }
     * @since 2025/4/29
     */
    default CreateQrCodeResponse create(String index, int scene) {
        QrCodeProperty req = QrCodeProperty.builder()
                .sceneId(scene)
                .actionName(QrCodeProperty.QrCodeActionName.QR_LIMIT_SCENE.name())
                .build();

        return createQrCode(index, req.createApiReqParam());
    }
}