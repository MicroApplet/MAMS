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

package com.asialjim.microapplet.wx.applet.infrastructure.config;

import com.asialjim.microapplet.app.cloud.ApplicationCloud;
import com.asialjim.microapplet.wx.applet.infrastructure.repository.WeChatApplicationRepositoryWithAppAdaptor;
import com.asialjim.microapplet.wx.core.application.WeChatApplicationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatAppConf {

    @Bean
    public WeChatApplicationRepository weChatApplicationRepository(ApplicationCloud applicationCloud){
        return new WeChatApplicationRepositoryWithAppAdaptor(applicationCloud);
    }
}
