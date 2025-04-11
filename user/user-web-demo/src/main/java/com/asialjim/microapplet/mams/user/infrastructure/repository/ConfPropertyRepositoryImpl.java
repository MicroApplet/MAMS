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

package com.asialjim.microapplet.mams.user.infrastructure.repository;

import com.asialjim.microapplet.commons.config.core.ConfProperty;
import com.asialjim.microapplet.commons.config.core.ConfPropertyRepository;
import com.asialjim.microapplet.commons.config.core.ConfType;
import com.asialjim.microapplet.commons.config.core.Env;
import org.springframework.stereotype.Component;

import java.util.TreeSet;

@Component
public class ConfPropertyRepositoryImpl implements ConfPropertyRepository {
    @Override
    public TreeSet<ConfProperty> query(ConfType type, String business, String code, Env env) {
        return null;
    }

    @Override
    public void put(ConfProperty confProperty) {

    }
}
