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

package com.asialjim.microapplet.mams.user.infrastructure.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Knife文档配置
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/2/26, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Configuration
public class Knife4jConfig {
    @Resource
    private SpringDocConfigProperties springDocConfigProperties;

    private String apiDocPath() {
        return this.springDocConfigProperties.getApiDocs().getPath() + "/**";
    }

    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi ->
                openApi.getPaths().forEach((s, pathItem) -> {
                    for (Operation readOperation : pathItem.readOperations()) {
                        List<SecurityRequirement> security = readOperation.getSecurity();
                        if (Objects.isNull(security))
                            readOperation.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
                    }
                });
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("用户认证系统API")
                        .version("1.0")
                        .description("用户认证过系统")
                        .termsOfService("https://asialjim.cn")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
                ).addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .components(
                        new Components()
                                .addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                                        new SecurityScheme()
                                                .name(HttpHeaders.AUTHORIZATION)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                )
                );
    }


    @Bean
    public GroupedOpenApi userAuthOpenApi() {
        String[] paths = {"/**"};
        return GroupedOpenApi.builder()
                .group("User")
                .addOpenApiCustomiser(openApi -> openApi.info(new Info().title("UserAuth API").version("v1.0")))
                .pathsToMatch(paths)
                .pathsToExclude(apiDocPath())
                .build();
    }
}