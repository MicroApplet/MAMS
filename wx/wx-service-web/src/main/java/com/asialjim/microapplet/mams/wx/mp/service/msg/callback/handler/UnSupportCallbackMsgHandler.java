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

package com.asialjim.microapplet.mams.wx.mp.service.msg.callback.handler;

import com.asialjim.microapplet.wechat.official.service.msg.CallbackMsgHandler;
import com.asialjim.microapplet.wechat.official.service.msg.WeChatOfficialMsgCallbackEvent;
import com.asialjim.microapplet.wechat.official.service.msg.reply.WxMpXmlOutMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 不支持的处理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/25, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
public class UnSupportCallbackMsgHandler implements CallbackMsgHandler {

    @Override
    public boolean support(String msgType) {
        return false;
    }

    @Override
    public WxMpXmlOutMessage handle(WeChatOfficialMsgCallbackEvent event) {
        return null;
    }
}
