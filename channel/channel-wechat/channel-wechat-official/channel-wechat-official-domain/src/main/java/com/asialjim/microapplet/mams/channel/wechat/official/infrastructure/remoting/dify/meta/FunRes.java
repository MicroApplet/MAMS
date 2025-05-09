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

package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Data
public class FunRes implements Serializable {
    private String task_id;
    private String workflow_run_id;
    private FunData data;

    public static String content(FunRes res){
        //noinspection ConstantValue
        return Optional.ofNullable(res)
                .map(FunRes::getData)
                .map(FunData::getOutputs)
                .filter(Objects::nonNull)
                .map(item -> item.get("result"))
                .orElse("AI智能体未返回数据");
    }
}

@Data
class FunData{
    private String id;
    private String workflow_id;
    private String status;
    private Map<String,String> outputs;
    private Object error;
    private Double elapsed_time;
    private Integer total_tokens;
    private Integer total_steps;
    private Long created_at;
    private Long finished_at;
}