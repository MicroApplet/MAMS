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

package io.github.microapplet.web.filter;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * MamsBusinessCodeFilter
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/6, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@SuppressWarnings("unused")
public class MamsBusinessCodeFilter implements WebFilter {
    @Override
    @SuppressWarnings("NullableProblems")
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpResponse original = exchange.getResponse();
        // 创建新的response装饰对象，并传入原始response对象，然后重写writeWith方法
        ServerHttpResponseDecorator newResponse = new ServerHttpResponseDecorator(original) {

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                Mono<DataBuffer> mono = DataBufferUtils.join(body)
                        .map(buffer -> {
                            try {
                                String data = buffer.toString(StandardCharsets.UTF_8);
                                // String mutatedData = mutate(data); // 此处修改返回内容
                                byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
                                getHeaders().setContentLength(bytes.length); // ①
                                return this.bufferFactory().wrap(bytes); // ②
                            } finally {
                                DataBufferUtils.release(buffer); // ③
                            }
                        });

                return super.writeWith(mono);
            }
        };
        return chain.filter(exchange.mutate().response(newResponse).build());
    }
}
