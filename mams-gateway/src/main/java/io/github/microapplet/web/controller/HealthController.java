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

package io.github.microapplet.web.controller;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Health Check API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/6, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@RestController
@RequestMapping
public class HealthController implements InitializingBean {
    @Resource private ConfigurableEnvironment configurableEnvironment;
    private String name;

    @GetMapping("/index")
    public Mono<String> index(){
        return Mono.just(name + " index");
    }

    @GetMapping("/health")
    public Mono<Integer> health(){
        return Mono.just(200);
    }


    @Override
    public void afterPropertiesSet() {
        String name = this.configurableEnvironment.getProperty("spring.application.name");
        this.name = StringUtils.isNotBlank(name) ? name : "Micro Applet Management Service Gateway Application";
    }
}