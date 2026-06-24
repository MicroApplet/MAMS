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

package com.asialjim.microapplet.user.api;

import com.asialjim.microapplet.user.entity.vo.FacialRecAuthViewObj;
import com.asialjim.microapplet.user.entity.web.req.facial_rec.FacialRecAuthReq;
import com.asialjim.microapplet.user.entity.web.res.facial_rec.FacialRecAuthVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 人脸核身API
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface FacialRecAuthApi {
    String path = "/facial-rec-auth";

    /**
     * 获取会话最后的人脸核身记录
     *
     * @param sessionId {@link String sessionId}
     * @return {@link FacialRecAuthViewObj }
     * @since 2026/3/5
     */
    @GetMapping("/query-by-session/{sessionId}/last")
    FacialRecAuthViewObj queryLastBySessionId(@PathVariable String sessionId);

    /**
     * 获取会话的人脸核身记录表
     *
     * @param sessionId {@link String sessionId}
     * @return {@link List<FacialRecAuthViewObj> }
     * @since 2026/3/5
     */
    @GetMapping("/query-by-session/{sessionId}")
    List<FacialRecAuthViewObj> queryBySessionId(@PathVariable String sessionId);


    /**
     * 获取请求的人脸核身记录
     *
     * @param traceId {@link String traceId}
     * @return {@link FacialRecAuthViewObj }
     * @since 2026/3/5
     */
    @GetMapping("/query-by-trace-id/{traceId}")
    FacialRecAuthViewObj queryByTraceId(@PathVariable String traceId);

    /**
     * 添加人脸核身记录
     *
     * @param sessionId {@link String sessionId}
     * @param traceId   {@link String traceId}
     * @param body      {@link FacialRecAuthViewObj body}
     * @return {@link FacialRecAuthViewObj }
     * @since 2026/3/5
     */
    @PostMapping("/{sessionId}/{traceId}")
    FacialRecAuthViewObj additional(@PathVariable String sessionId, @PathVariable String traceId, @RequestBody FacialRecAuthViewObj body);

    @PostMapping("/save")
    FacialRecAuthVo save(FacialRecAuthReq facialRecAuthReq);

}