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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用渠道应用类型
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum NormalChlAppType implements ChlAppType {
    H5("H5", "H5平台、包括PC端", NormalChlType.H5),
    Phone("Phone", "移动电话渠道", NormalChlType.Phone);

    public final String code;
    public final String desc;
    public final ChlType chlType;
}
