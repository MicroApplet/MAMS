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

package com.asialjim.microapplet.mams.channel.wechat.official.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * 微信公众号消息/事件回调AI模式会话
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/5/7, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class WeChatOfficialCallbackAISession implements Serializable {
    private static final long serialVersionUID = -8956151969548434596L;

    /**
     * 用户编号
     */
    private String openid;
    /**
     * 会话编号
     */
    private String sessionId;
    /**
     * 会话创建时间
     */
    private Long createTime;

    /**
     * 判断是否是空会话
	 * @param session {@link WeChatOfficialCallbackAISession session}
     * @return {@link Boolean }
     * @since 2025/5/7
     */
    public static boolean emptySession(WeChatOfficialCallbackAISession session){
        return Objects.isNull(session) || StringUtils.isBlank(session.getOpenid());
        //return Objects.isNull(session) || StringUtils.isBlank(session.getSessionId());
    }
}