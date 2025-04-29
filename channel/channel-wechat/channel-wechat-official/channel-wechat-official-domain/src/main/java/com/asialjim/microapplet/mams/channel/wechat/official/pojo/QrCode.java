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

package com.asialjim.microapplet.mams.channel.wechat.official.pojo;

import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.qrcode.meta.CreateQrCodeResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * 公众号二维码
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class QrCode implements Serializable {
    private static final long serialVersionUID = -8619383854167411715L;

    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    private String url;

    /**
     * 二维码票据
     */
    private String ticket;

    /**
     * 获取二维码ticket后，开发者可用ticket换取二维码图片
     */
    private String ticketUrl;

    public static QrCode createQrCode(CreateQrCodeResponse response) {
        QrCode qrCode = new QrCode();

        Optional.ofNullable(response).ifPresent(item -> {
            qrCode.setUrl(item.getUrl());
            qrCode.setTicket(item.getTicket());
        });
        String ticketUrl = String.format("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s", qrCode.getTicket());
        qrCode.setTicketUrl(ticketUrl);
        return qrCode;
    }
}