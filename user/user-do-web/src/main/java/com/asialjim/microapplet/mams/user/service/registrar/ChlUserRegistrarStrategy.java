/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.user.service.registrar;

import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;

/**
 * 渠道用户注册策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface ChlUserRegistrarStrategy {

    /**
     * 注册渠道
     */
    ChannelType registerChannel();

    /**
     * 注册渠道应用类型
     * @return {@link ChannelAppType }
     * @since 2025/9/29
     */
    ChannelAppType chlAppType();

    /**
     * 用户注册业务逻辑
	 * @param user {@link ChlUserVo user}
     * @return {@link ChlUserVo }
     * @since 2025/9/29
     */
    ChlUserVo register(ChlUserVo user);
}