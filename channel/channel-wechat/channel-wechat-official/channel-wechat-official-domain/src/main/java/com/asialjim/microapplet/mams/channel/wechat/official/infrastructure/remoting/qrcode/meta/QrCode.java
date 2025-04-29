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

package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.qrcode.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Asial Jim &nbsp;&nbsp; <span>Email:<a href="mailto:asialjim@hotmail.com">asialjim@hotmail.com</a> &nbsp;&nbsp; <a href="asialjim@qq.com">asialjim@qq.com</a></span>
 * @version 1.0.0
 * @since 2021/3/31   &nbsp;&nbsp; JDK 8
 */
@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     * get qr code by qr code ticket api resposne
     * @param response {@link CreateQrCodeResponse api response}
     * @return {@link QrCode qr code info}
     * @since 2021/3/31 17:17
     */
    @SneakyThrows
    public static QrCode createQrCode(CreateQrCodeResponse response){
        String ticket = response.getTicket();
        String ticketUrl = String.format("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s",URLEncoder.encode(ticket, StandardCharsets.UTF_8.name()));
        return new QrCode(response.getUrl(),ticket,ticketUrl);
    }
}