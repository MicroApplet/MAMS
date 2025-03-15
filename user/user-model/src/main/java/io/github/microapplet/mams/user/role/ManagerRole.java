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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理角色
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/2/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum ManagerRole implements Role{
    Tourist(0,"游客","未登录用户"),
    Login(1,"登录用户","已登录用户"),
    Employee(1<<1,"行员","具有行内工号的用户"),
    Manager(1<<2,"客户经理","客户经理"),

    System(1<<30,"系统管理员","系统管理员"),
    Root(1<<31,"超级管理员","超级管理员");

    private final int code;
    private final String name;
    private final String desc;
}