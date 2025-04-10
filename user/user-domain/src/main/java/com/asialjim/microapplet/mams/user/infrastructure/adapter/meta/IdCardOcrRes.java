/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.user.infrastructure.adapter.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * 证件OCR响应结果
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class IdCardOcrRes implements Serializable {
    private static final long serialVersionUID = 2870332758528087756L;

    /**
     * 证件正反面
     */
    private String type;

    /**
     * 姓名
     */
    private String name;

    /**
     * 证件号
     */
    private String id;

    /**
     * 证件地址
     */
    private String addr;

    /**
     * 性别
     */
    private String gender;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 证件有效期
     */
    @JsonProperty("valid_date")
    private String validDate;

    /**
     * 正面证件
     *
     * @return {@link Boolean }
     * @since 2025/3/13
     */
    public boolean front() {
        return StringUtils.equalsIgnoreCase(getType(), "front");
    }

    /**
     * 证件反面
     *
     * @return {@link Boolean }
     * @since 2025/3/13
     */
    public boolean back() {
        return StringUtils.equals(getType(), "back");
    }

    /**
     * 有效期始
     *
     * @return {@link LocalDate}
     * @since 2025/3/13
     */
    public LocalDate validStart() {
        if (StringUtils.isBlank(getValidDate()))
            return null;

        String[] split = getValidDate().split("-");
        if (ArrayUtils.getLength(split) != 2)
            return null;

        String start = split[0];
        return LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 证件有效期至
     *
     * @return {@link LocalDate }
     * @since 2025/3/13
     */
    public LocalDate validEnd() {
        if (StringUtils.isBlank(getValidDate()))
            return null;

        String[] split = getValidDate().split("-");
        if (ArrayUtils.getLength(split) != 2)
            return null;

        String end = split[1];
        if (StringUtils.equalsIgnoreCase(end, "长期"))
            return LocalDate.of(9999, Month.DECEMBER, 31);
        return LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}