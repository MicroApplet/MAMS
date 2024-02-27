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

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 门面API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2024/2/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Setter
@RestController
@RequestMapping
public class IndexController implements EnvironmentAware, ApplicationContextAware {
    private Environment environment;
    private ApplicationContext applicationContext;

    @GetMapping("/health")
    public Integer health() {
        return 200;
    }

    @GetMapping("/index")
    public String index() {
        return applicationName() + " Index".toUpperCase();
    }

    private String applicationName() {
        String name = Optional.ofNullable(this.environment)
                .map(item -> item.getProperty("spring.application.name"))
                .orElse(StringUtils.EMPTY);

        if (StringUtils.isBlank(name))
            name = Optional.ofNullable(applicationContext)
                    .map(EnvironmentCapable::getEnvironment)
                    .map(item -> item.getProperty("spring.application.name"))
                    .orElse(StringUtils.EMPTY);

        return name;
    }
}