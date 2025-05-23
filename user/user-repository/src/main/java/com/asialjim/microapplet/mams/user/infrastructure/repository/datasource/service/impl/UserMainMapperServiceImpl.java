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

package com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.service.impl;

import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.mapper.UserMainBaseMapper;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.po.UserMainPo;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.service.UserMainMapperService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 用户主账户存储服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/14, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserMainMapperServiceImpl
        extends ServiceImpl<UserMainBaseMapper, UserMainPo>
        implements UserMainMapperService {
}