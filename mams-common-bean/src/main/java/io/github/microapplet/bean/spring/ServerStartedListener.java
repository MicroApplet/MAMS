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

package io.github.microapplet.bean.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

/**
 * 服务器正常启动监听器
 *
 * @author Copyright © <a href="mailto:asialjim@hotmail.com">Asial Jim</a>   Co., LTD
 * @version 4.0
 * @since 2023/8/4, &nbsp;&nbsp; <em>version:4.0</em>, &nbsp;&nbsp; <em>java version:8</em>
 */
@Slf4j
@Component
public class ServerStartedListener implements ApplicationListener<ContextRefreshedEvent> {
    private static volatile boolean started = false;
    @Override
    public void onApplicationEvent(@SuppressWarnings("NullableProblems") ContextRefreshedEvent event) {
        if (started)
            return;

        Object source = event.getSource();
        if (!(source instanceof ApplicationContext ctx))
            return;

        String id = ctx.getId();
        String appName = ctx.getEnvironment().getProperty("spring.application.name");
        if (StringUtils.isBlank(appName))
            return;

        String applicationName = StringUtils.upperCase( appName + " Application Context Refreshed: " + id);

        if (started) {
            log.info("\r\n\t\r\n\t{}\r\n",applicationName);
            return;
        }

        String line = StringUtils.repeat('-', StringUtils.length(applicationName));
        StringJoiner sj = new StringJoiner("\r\n\t");
        sj.add(line).add(applicationName).add(line);

        log.info("\r\n\t\r\n\t{}\r\n", sj);
        started = true;
    }
}