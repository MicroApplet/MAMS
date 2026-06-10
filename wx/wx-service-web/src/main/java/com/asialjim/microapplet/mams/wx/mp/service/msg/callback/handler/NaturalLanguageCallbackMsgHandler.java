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

import com.asialjim.microapplet.common.concurrent.ConcurrentRunner;
import com.asialjim.microapplet.wechat.official.service.msg.CallbackMsgHandler;
import com.asialjim.microapplet.wechat.official.service.msg.WeChatOfficialMsgCallbackEvent;
import com.asialjim.microapplet.wechat.official.service.msg.reply.WxMpXmlOutMessage;
import com.asialjim.microapplet.wechat.official.remoting.ai.WeChatAIVoiceRemoting;
import com.asialjim.microapplet.wechat.official.remoting.ai.meta.WeChatQueryRecoResultForTextRes;
import com.asialjim.microapplet.wechat.remoting.message.customer.WeChatCustomerMessageRemoting;
import com.asialjim.microapplet.wechat.remoting.message.customer.meta.WeChatCustomerTextMessage;
import com.asialjim.microapplet.wechat.remoting.message.customer.meta.item.Text;
import com.asialjim.microapplet.wechat.remoting.context.BaseWeChatApiRes;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 自然语言处理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/25, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
public class NaturalLanguageCallbackMsgHandler implements CallbackMsgHandler {
    @Resource
    private WeChatAIVoiceRemoting weChatAIVoiceRemoting;

    @Resource
    private WeChatCustomerMessageRemoting weChatCustomerMessageRemoting;

    @Override
    public boolean support(String msgType) {
        // natural language
        return StringUtils.equalsAny(msgType, "text", "voice");
    }

    @Override
    public WxMpXmlOutMessage handle(WeChatOfficialMsgCallbackEvent event) {
        String msgType = event.getMsgType();
        String content;
        if (StringUtils.equalsAny(msgType, "voice")) {
            String mediaId = event.nodeTextValue("MediaId");
            WeChatQueryRecoResultForTextRes textRes = this.weChatAIVoiceRemoting.queryRecoResultForText(event.getAppid(), mediaId);
            content = Optional.ofNullable(textRes)
                    .map(WeChatQueryRecoResultForTextRes::getResult)
                    .orElse(StringUtils.EMPTY);
        } else {
            content = event.nodeTextValue("Content");
        }
        log.info("用户发送文本信息:{}", content);

        WxMpXmlOutMessage out = new WxMpXmlOutMessage();
        out.setMsgType("text");
        out.setContent("你好");

        ConcurrentRunner.runAllTask(() -> {
            WeChatCustomerTextMessage msg = new WeChatCustomerTextMessage();
            msg.setTouser(event.getOpenid());
            msg.setText(Text.builder().content("Are you OK?").build());
            BaseWeChatApiRes apiRes = weChatCustomerMessageRemoting.sendCustomerMsg(event.getAppid(), msg);
            log.info("\r\n发送客服消息：{}\r\n结果：{}", msg, apiRes);
        });

        return out;
    }
}
