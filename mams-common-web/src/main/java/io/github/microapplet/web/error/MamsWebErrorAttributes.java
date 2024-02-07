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

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * <pre>
 * MAMS 网络异常属性
 * Micro Applet Management Service Web Error Attributes
 * </pre>
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/6, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
//@Primary
//@Component
public class MamsWebErrorAttributes implements ErrorAttributes {
    private static final String CODE_HEADER_NAME = "X-MAMS-CODE";
    private static final String MESSAGE_HEADER_NAME = "X-MAMS-MESSAGE";
    private static final String CN_MESSAGE_HEADER_NAME = "X-MAMS-MSG-CN";

    private static final String ERROR_INTERNAL_ATTRIBUTE = MamsWebErrorAttributes.class.getName() + ".ERROR";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, options.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE));
        if (!options.isIncluded(ErrorAttributeOptions.Include.EXCEPTION)) {
            errorAttributes.remove("exception");
        }
        if (!options.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE)) {
            errorAttributes.remove("trace");
        }
        if (!options.isIncluded(ErrorAttributeOptions.Include.MESSAGE) && errorAttributes.get("message") != null) {
            errorAttributes.remove("message");
        }
        if (!options.isIncluded(ErrorAttributeOptions.Include.BINDING_ERRORS)) {
            errorAttributes.remove("errors");
        }
        return errorAttributes;
    }

    private Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("X-MAMS-TIMESTAMP", new Date());
        errorAttributes.put("X-MAMS-PATH", request.path());
        Throwable error = getError(request);
        MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations.from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
        HttpStatus errorStatus = determineHttpStatus(error, responseStatusAnnotation);
        errorAttributes.put("X-MAMS-STATUS", errorStatus.value());
        errorAttributes.put("X-MAMS-PHRASE", errorStatus.getReasonPhrase());
        errorAttributes.put(CODE_HEADER_NAME, determineCode(error, errorStatus));
        errorAttributes.put(MESSAGE_HEADER_NAME, determineMessage(error, responseStatusAnnotation));
        errorAttributes.put(CN_MESSAGE_HEADER_NAME,determineChMessage(error));
        errorAttributes.put("X-MAMS-TRACE", request.exchange().getRequest().getId());
        handleException(errorAttributes, determineException(error), includeStackTrace);
        return errorAttributes;
    }

    private String determineCode(Throwable error, HttpStatus errorStatus) {
        if (error instanceof MamsBizHttpResException e)
            return e.getCode();
        if (Objects.nonNull(errorStatus))
            return String.valueOf(errorStatus.value());
        return "200";
    }

    private Object determineChMessage(Throwable error) {
        if (error instanceof MamsBizHttpResException e)
            return e.getMsgCn();
        return StringUtils.EMPTY;
    }

    private HttpStatus determineHttpStatus(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation) {
        if (error instanceof ResponseStatusException responseStatusException) {
            HttpStatus httpStatus = HttpStatus.resolve(responseStatusException.getStatusCode().value());
            if (httpStatus != null) {
                return httpStatus;
            }
        }
        return responseStatusAnnotation.getValue("code", HttpStatus.class).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String determineMessage(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation) {
        if (error instanceof MamsBizHttpResException e){
            return e.getMsg();
        }
        if (error instanceof BindingResult) {
            return error.getMessage();
        }
        if (error instanceof ResponseStatusException responseStatusException) {
            return responseStatusException.getReason();
        }
        String reason = responseStatusAnnotation.getValue("reason", String.class).orElse("");
        if (StringUtils.isNotBlank(reason)) {
            return reason;
        }
        return (error.getMessage() != null) ? error.getMessage() : "";
    }

    private Throwable determineException(Throwable error) {
        if (error instanceof ResponseStatusException) {
            return (error.getCause() != null) ? error.getCause() : error;
        }
        return error;
    }

    private void addStackTrace(Map<String, Object> errorAttributes, Throwable error) {
        StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));
        stackTrace.flush();
        errorAttributes.put("trace", stackTrace.toString());
    }

    private void handleException(Map<String, Object> errorAttributes, Throwable error, boolean includeStackTrace) {
        errorAttributes.put("exception", error.getClass().getName());
        if (includeStackTrace) {
            addStackTrace(errorAttributes, error);
        }
        if (error instanceof BindingResult result) {
            if (result.hasErrors()) {
                errorAttributes.put("errors", result.getAllErrors());
            }
        }
    }

    @Override
    public Throwable getError(ServerRequest request) {
        Optional<Object> error = request.attribute(ERROR_INTERNAL_ATTRIBUTE);
        return (Throwable) error.orElseThrow(() -> new IllegalStateException("Missing exception attribute in ServerWebExchange"));
    }


    @Override
    public void storeErrorInformation(Throwable error, ServerWebExchange exchange) {
        exchange.getAttributes().putIfAbsent(ERROR_INTERNAL_ATTRIBUTE, error);
    }
}