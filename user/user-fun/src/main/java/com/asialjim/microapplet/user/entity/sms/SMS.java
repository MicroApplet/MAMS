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
package com.asialjim.microapplet.user.entity.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SMS implements Serializable, Comparable<SMS> {


    @Serial
    private static final long serialVersionUID = 2793869588146597094L;
    /**
     * 业务代码
     */
    private String biz;
    private String tell;

    /**
     * 验证码,或是用户输入的错误验证码
     */
    private String token;

    /**
     * 验证码创建时间
     */
    private long createTime;

    /**
     * 验证码验证过期时间
     */
    private long valid;

    /**
     * 验证码 用户口径滑动窗口时间
     * <br/>
     * 当前时间小于此属性值时，当前验证码将被统计在 单位时间内用户创建验证码次数中
     * <br/>
     * 若当前时间大于此属性值时， 当前验证码将不被统计在 单位时间内用户创建验证码次数中
     */
    private long usrWindowTime;

    /**
     * 验证码 手机口径滑动窗口时间
     * <br/>
     * 当前时间小于此属性值时，当前验证码将被统计在 单位时间内手机号创建验证码次数中
     * <br/>
     * 当前时间大于此属性值时，当前验证码将不被统计在单位时间内手机号创建验证码次数中
     */
    private long telWindowTime;

    /**
     * 验证码 业务口径滑动窗口时间
     * <br/>
     * 当前时间小于此属性值时，当前验证码将被统计在 单位时间用户业务创建的验证码次数统计口径中
     * <br/>
     * 当前时间大于此属性值时，当前验证码将不被统计在单位时间内用户业务创建的验证码次数统计口径中
     */
    private long bizWindowTime;

    /**
     * 验证码缓存过期时间
     */
    private long expires;

    @Override
    public int compareTo(@SuppressWarnings("NullableProblems") SMS o) {
        if (Objects.isNull(o))
            return 0;

        //noinspection ConstantValue
        if (Objects.isNull(getValid()) || Objects.isNull(o.getValid()))
            return 0;
        return (int) (o.getValid() - this.getValid());
    }
}