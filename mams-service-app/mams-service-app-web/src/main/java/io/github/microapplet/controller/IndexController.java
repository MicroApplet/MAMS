/*
 * Copyright 2014-2024 <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
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

package io.github.microapplet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @since 2024/2/18, &nbsp;&nbsp; <em>version:</em>
 */
@RestController
@RequestMapping
public class IndexController {

    @GetMapping("/health")
    public Integer health() {
        return 200;
    }

    @GetMapping("/index")
    public String index() {
        return """
                <!DOCTYPE html>
                <html lang="zh_CH">
                <head>
                    <meta charset="utf-8"/>
                    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <meta http-equiv="Cache-Control" content="no-store"/>
                    <meta http-equiv="Pragma" content="no-cache"/>
                    <title>Micro Applet Management Service</title>
                </head>
                <body style="margin: auto; align-items: center;">
                <div style="height: 100%; align-items: center;">
                    <div style="text-align: center; align-items: center; ">
                        <div style="font-size: 50px; font-weight: 1000; color: green">欢迎访问小微应用管理服务应用服务系统</div>
                        <br/><br/>
                        <div style="font-size: 30px; font-weight: 800; color: black">Welcome to Application Service Index for Micro Applet Management Service</div>
                    </div>
                </div>
                </body>
                </html>""";
    }
}