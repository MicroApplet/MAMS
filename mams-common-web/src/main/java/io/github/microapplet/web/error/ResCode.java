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


import java.util.Collections;
import java.util.List;

/**
 * Http Response Code
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/18, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@SuppressWarnings("unused")
public interface ResCode {
    String getCode();

    String getMsg();

    String getMsgCn();

    /**
     * 构建 MAMS HTTP 响应业务异常
     * just create mams business http response exception
     *
     * @return {@link MamsBizHttpResException }
     * @since 2024/2/18
     */
    default MamsBizHttpResException mamsBizHttpResException() {
        return new MamsBizHttpResException(getCode(), getMsg(), getMsgCn(), Collections.emptyList());
    }

    /**
     * 构建 MAMS HTTP 响应业务异常
     * just create mams business http response exception
     *
     * @return {@link MamsBizHttpResException }
     * @since 2024/2/18
     */
    default MamsBizHttpResException mamsBizHttpResException(List<Object> errs) {
        return new MamsBizHttpResException(getCode(), getMsg(), getMsgCn(), errs);
    }

    /**
     * 抛出 MAMS HTTP 响应业务异常
     * throw mams business http response exception
     *
     * @since 2024/2/18
     */
    default void castMamsBizHttpResException() {
        throw mamsBizHttpResException();
    }

    /**
     * 抛出 MAMS HTTP 响应业务异常
     * throw mams business http response exception
     *
     * @since 2024/2/18
     */
    default void castMamsBizHttpResException(List<Object> errs) {
        throw mamsBizHttpResException(errs);
    }
}