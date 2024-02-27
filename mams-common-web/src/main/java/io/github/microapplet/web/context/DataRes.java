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

package io.github.microapplet.web.context;

import io.github.microapplet.web.error.ResCode;
import lombok.Data;

/**
 * 带有业务响应结果的响应对象
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/26, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@Data
public final class DataRes<T> implements ResCode {
    private String code, msg, msgCn;
    private T data;

    @SuppressWarnings("unused")
    public DataRes<T> withData(T t) {
        setData(t);
        return this;
    }

    public static <T> DataRes<T> of(ResCode resCode) {
        DataRes<T> res = new DataRes<>();
        res.setCode(resCode.getCode());
        res.setMsg(resCode.getMsg());
        res.setMsgCn(resCode.getMsgCn());
        return res;
    }

    public static <T> DataRes<T> of(ResCode resCode, T data) {
        DataRes<T> res = new DataRes<>();
        res.setCode(resCode.getCode());
        res.setMsg(resCode.getMsg());
        res.setMsgCn(resCode.getMsgCn());
        res.setData(data);
        return res;
    }

    public static <T> DataRes<T> of(String code, String msg, String msgCn) {
        DataRes<T> res = new DataRes<>();
        res.setCode(code);
        res.setMsg(msg);
        res.setMsgCn(msgCn);
        return res;
    }

    public static <T> DataRes<T> of(String code, String msg, String msgCn, T data) {
        DataRes<T> res = new DataRes<>();
        res.setCode(code);
        res.setMsg(msg);
        res.setMsgCn(msgCn);
        res.setData(data);
        return res;
    }
}