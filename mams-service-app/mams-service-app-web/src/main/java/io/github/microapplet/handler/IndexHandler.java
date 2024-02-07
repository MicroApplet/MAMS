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

package io.github.microapplet.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @since 2024/2/6, &nbsp;&nbsp; <em>version:</em>
 */
@Component
@RefreshScope
public class IndexHandler {
    @Value("${aaaa}")
    private String aaaa;

    public Mono<ServerResponse> index(ServerRequest request){
        List<String> requestChanel = request.headers().header("X-Request-Channel");
        System.out.println(requestChanel);
        System.out.println(aaaa);
        return ServerResponse
                .ok()
                .header("X-MAMS-CODE","200")
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("index"));
    }

    public Mono<ServerResponse> health(ServerRequest request){
        List<String> requestChanel = request.headers().header("X-Request-Channel");
        System.out.println(requestChanel);
        System.out.println(aaaa);
        return ServerResponse
                .ok()
                .header("X-MAMS-CODE", "200")
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("what else"));
    }
}
