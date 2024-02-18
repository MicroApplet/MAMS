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

package io.github.microapplet.bean.config.thread;

import org.slf4j.MDC;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.task.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * micro applet management service thread configuration
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/18, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@Configuration
public class MamsThreadConfiguration {
    @EnableAsync
    @Configuration
    static class ExecutorConfig implements InitializingBean {
        @Autowired
        private TaskDecorator taskDecorator;
        @Autowired
        private RejectedExecutionHandler rejectedExecutionHandler;
        @Autowired(required = false)
        private List<SimpleAsyncTaskExecutor> simpleAsyncTaskExecutors;
        @Autowired(required = false)
        private List<ThreadPoolTaskExecutor> threadPoolTaskExecutors;
        @Autowired(required = false)
        private List<ThreadPoolTaskScheduler> threadPoolTaskSchedulers;
        @Autowired(required = false)
        private List<SimpleAsyncTaskScheduler> simpleAsyncTaskSchedulers;

        @Override
        public void afterPropertiesSet() {
            //虚拟线程
            if (!CollectionUtils.isEmpty(this.simpleAsyncTaskExecutors))
                this.simpleAsyncTaskExecutors.forEach(item -> item.setTaskDecorator(taskDecorator));
            if (!CollectionUtils.isEmpty(this.simpleAsyncTaskSchedulers))
                this.simpleAsyncTaskSchedulers.forEach(item -> item.setTaskDecorator(taskDecorator));

            //平台线程
            if (!CollectionUtils.isEmpty(this.threadPoolTaskSchedulers))
                this.threadPoolTaskSchedulers.forEach(item -> item.setRejectedExecutionHandler(rejectedExecutionHandler));
            if (!CollectionUtils.isEmpty(this.threadPoolTaskExecutors))
                this.threadPoolTaskExecutors.forEach(item -> {
                    item.setTaskDecorator(taskDecorator);
                    item.setRejectedExecutionHandler(rejectedExecutionHandler);
                });
        }
    }

    @Bean
    public TaskDecorator taskDecorator() {
        return runnable -> {
            try {
                // 将日志上下文加入子线程
                Map<String, String> context = MDC.getCopyOfContextMap();

                // 将网络请求上下文加入子线程
                RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
                return () -> {
                    try {
                        RequestContextHolder.setRequestAttributes(requestAttributes);
                        MDC.setContextMap(context);
                        runnable.run();
                    } finally {
                        RequestContextHolder.resetRequestAttributes();
                        MDC.clear();
                    }
                };
            } catch (IllegalStateException e) {
                return runnable;
            }
        };
    }


    @Bean
    public RejectedExecutionHandler rejectedExecutionHandler() {
        return (r, executor) -> {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException ignored) {
            }
        };
    }
}