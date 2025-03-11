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

package io.github.microapplet.mams.user.cons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * 民族
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/2/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface Nationality {
    /**
     * 民族代码
     */
    int getCode();
    /**
     * 描述/汉语名称
     */
    String getDesc();

    @Getter
    @AllArgsConstructor
    enum ChineseNationality implements Nationality{
        Han(0,"汉")
        ;
        private final int code;
        private final String desc;

        public static Nationality codeOf(Integer code) {
            return Arrays.stream(values()).filter(item -> Objects.equals(item.getCode(), code)).findFirst().orElse(Han);
        }

        public static Nationality descOf(String desc) {
            return Arrays.stream(values()).filter(item -> StringUtils.equals(item.getDesc(), desc)).findFirst().orElse(Han);
        }
    }
}