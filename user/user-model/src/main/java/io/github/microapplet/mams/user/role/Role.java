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

package io.github.microapplet.mams.user.role;

/**
 * 角色
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/2/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface Role {

    /**
     * 角色代码:
     * <pre>
     * 角色代码生成规则：
     * 使用 1 向左移位的计算方式，来指定角色代码，禁止直接使用数字指定，便于角色计算
     * </pre>
     */
    int getCode();

    /**
     * 角色名称
     */
    String getName();

    /**
     * 角色描述
     */
    String getDesc();

    /**
     * 判断是否有某个特定角色
     */
    default boolean hasRole(int role) {
        return (role & getCode()) > getCode();
    }
}