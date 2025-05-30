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

package com.asialjim.microapplet.mams.applet;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.applet.cons.AppletConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 小程序网络应用APP
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 04 20, &nbsp;&nbsp; <em>version:1.0</em>
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AppletApplication {
    public static void main(String[] args) {
        App.voidStart(AppletConstant.appName, AppletConstant.contextPath, AppletApplication.class, args);
    }
}