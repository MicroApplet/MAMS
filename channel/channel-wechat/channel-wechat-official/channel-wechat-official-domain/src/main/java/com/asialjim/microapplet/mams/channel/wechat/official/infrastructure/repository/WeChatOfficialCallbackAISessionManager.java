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

package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.repository;

import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackAISession;

/**
 * 公众号消息/事件回调AI会话管理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/5/7, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface WeChatOfficialCallbackAISessionManager {
    /**
     * 查询指定用于的AI会话
	 * @param openid {@link String openid}
     * @return {@link WeChatOfficialCallbackAISession }
     * @since 2025/5/7
     */
    WeChatOfficialCallbackAISession sessionOfUser(String openid);
}