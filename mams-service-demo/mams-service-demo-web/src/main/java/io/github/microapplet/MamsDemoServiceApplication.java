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

package io.github.microapplet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <pre>
 * MAMS 应用服务启动器
 * MAMS Application Service Bootstrap
 * </pre>
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @since 2024/2/6, &nbsp;&nbsp; <em>version:</em>
 */
@SpringBootApplication
public class MamsDemoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MamsDemoServiceApplication.class);
    }
}
