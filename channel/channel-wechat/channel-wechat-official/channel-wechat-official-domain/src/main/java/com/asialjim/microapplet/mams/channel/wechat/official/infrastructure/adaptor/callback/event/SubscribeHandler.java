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

package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.event;

import com.asialjim.microapplet.common.event.EventUtil;
import com.asialjim.microapplet.mams.channel.wechat.WeChatOfficialCons;
import com.asialjim.microapplet.mams.channel.wechat.official.cors.event.WeChatOfficialScanEvent;
import com.asialjim.microapplet.mams.channel.wechat.official.cors.event.WeChatOfficialSubscribeEvent;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackMsg;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.CallMsgHandler;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.CallbackMsgProcessor;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.qrcode.SceneQrCodeHandlerContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;

import jakarta.annotation.Resource;

/**
 * 关注事件处理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@CallMsgHandler(
        msgType = WeChatOfficialCons.XmlMsgType.Event,
        eventType = WeChatOfficialCons.XmlEventType.Subscribe
)
public class SubscribeHandler implements CallbackMsgProcessor {
    @Resource
    private SceneQrCodeHandlerContext sceneQrCodeHandlerContext;


    @Override
    public ObjectNode process(WeChatOfficialCallbackMsg callBackMsg) {
        final String openid = callBackMsg.fromUserName().asText();
        final String subjectId = callBackMsg.toUserName().asText();
        final Long subTime = callBackMsg.createTime().longValue();
        final String ticket = callBackMsg.textNodeValue(WeChatOfficialCons.XmlMsgTag.ticket);

        String scene = callBackMsg.textNodeValue(WeChatOfficialCons.XmlMsgTag.eventKey);

        final WeChatOfficialSubscribeEvent subscribeEvent = new WeChatOfficialSubscribeEvent(openid, subjectId, subTime, scene, ticket);
        EventUtil.push(subscribeEvent);
        // 没有业务场景
        if (StringUtils.isBlank(scene))
            return null;

        scene = StringUtils.replace(scene, "qrscene_", StringUtils.EMPTY);
        final WeChatOfficialScanEvent scanEvent = new WeChatOfficialScanEvent(openid, subjectId, subTime, scene, ticket);
        EventUtil.push(scanEvent);
        return this.sceneQrCodeHandlerContext.handle(scanEvent);
    }
}