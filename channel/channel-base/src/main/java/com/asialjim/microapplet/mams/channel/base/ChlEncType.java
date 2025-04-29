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

package com.asialjim.microapplet.mams.channel.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 渠道加密方式
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface ChlEncType {

    /**
     * 所属渠道类型
     */
    ChlType getChlType();

    /**
     * 加密类型编号
     */
    String getCode();

    /**
     * 加密类型描述
     */
    String getDesc();

    @Data
    @NoArgsConstructor
    @Accessors(chain = true)
    class Meta implements ChlEncType{
        private String code;
        private String desc;
        private String chlTypeCode;
        private String chlTypeDesc;

        public Meta(String code, String desc, ChlType chlType) {
            this.code = code;
            this.desc = desc;
            this.chlTypeCode = chlType.getCode();
            this.chlTypeDesc = chlType.getDesc();
        }

        @Override
        public ChlType getChlType() {
            return new ChlType.Meta().setCode(getChlTypeCode()).setDesc(getChlTypeDesc());
        }
    }

    static ChlEncType codeOf(String code) {
        return new Meta().setCode(code);
    }
}