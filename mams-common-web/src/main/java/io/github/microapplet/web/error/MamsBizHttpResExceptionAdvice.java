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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Mams Business Http Response Exception Handler Controller Advice
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/18, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@Slf4j
@RestControllerAdvice
@SuppressWarnings("UastIncorrectHttpHeaderInspection")
public class MamsBizHttpResExceptionAdvice {
    public static final String CODE_HEADER_NAME = "X-MAMS-CODE";
    public static final String MESSAGE_HEADER_NAME = "X-MAMS-MESSAGE";
    public static final String CN_MESSAGE_HEADER_NAME = "X-MAMS-MSG-CN";
    public static final String DEBUG_ERR_LINES_NAME = "X-MAMS-DEBUG-LINES";
    public static final String DEBUG_ENABLE_NAME = "X-MAMS-DEBUG-ENABLE";

    @ExceptionHandler(value = MamsBizHttpResException.class)
    public void handleMamsBizHttpResException(MamsBizHttpResException e,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {

        response.setHeader(CODE_HEADER_NAME, e.getCode());
        response.setHeader(MESSAGE_HEADER_NAME, e.getMsg());
        response.setHeader(CN_MESSAGE_HEADER_NAME,e.getMsgCn());
        String enableDebug = request.getHeader(DEBUG_ENABLE_NAME);
        if (StringUtils.equalsIgnoreCase(enableDebug,Boolean.TRUE.toString())){
            List<Object> errs = e.getErrs();
            if (CollectionUtils.isNotEmpty(errs)){
                String errLines = errs.stream().map(String::valueOf).collect(Collectors.joining("\r\n"));
                response.setHeader(DEBUG_ERR_LINES_NAME, errLines);
            }
        }
    }

    @ExceptionHandler(value = Throwable.class)
    public void handleThrowable(Throwable throwable,
                                HttpServletRequest request,
                                HttpServletResponse response){

        response.setHeader(CODE_HEADER_NAME,"-500");
        response.setHeader(MESSAGE_HEADER_NAME,"The System is Busy!");
        response.setHeader(CN_MESSAGE_HEADER_NAME,"系统繁忙!");
        String enableDebug = request.getHeader(DEBUG_ENABLE_NAME);
        if (StringUtils.equalsIgnoreCase(enableDebug,Boolean.TRUE.toString())){
            StringJoiner sj = new StringJoiner("\r\n");
            String message = throwable.getMessage();
            sj.add(message);
            Throwable[] suppressed = throwable.getSuppressed();
            for (Throwable t : suppressed) {
                sj.add("\t" + t.getMessage());
            }

            response.setHeader(DEBUG_ERR_LINES_NAME, sj.toString());
        }
    }
}