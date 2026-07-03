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

package com.asialjim.microapplet.wx.core.client;


import org.springframework.http.MediaType;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class WeChatResponseHttpMessageConverter extends JacksonJsonHttpMessageConverter {

    public WeChatResponseHttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        // 增加对 text/plain 的支持
        mediaTypes.add(MediaType.TEXT_PLAIN);
        // 保留对 application/json 的原有支持
        mediaTypes.add(MediaType.APPLICATION_JSON);
        setSupportedMediaTypes(mediaTypes);
    }


}
