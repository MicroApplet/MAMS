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

package io.github.microapplet.web.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * <pre>
 * MAMS HTTP 接口业务异常
 * Micro Applet Management Service Business HTTP Response Exception
 * </pre>
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/6, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@Getter
@AllArgsConstructor
public class MamsBizHttpResException extends RuntimeException {
    private final String code;
    private final String msg;
    private final String msgCn;
    private final List<Object> errs;

    @Override
    public String getMessage() {
        if (StringUtils.isNoneBlank(this.msg,this.msgCn))
            return this.msgCn + "[" + this.msg + "]";
        if (StringUtils.isNotBlank(this.msg))
            return this.msg;
        if (StringUtils.isNotBlank(this.msgCn))
            return this.msgCn;
        return "Empty Error Message";
    }


}