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

package com.asialjim.microapplet.mams.callback.wx.mp.controller;

import com.asialjim.microapplet.mams.callback.wx.mp.crypto.WeChatOfficialMsgCryptService;
import com.asialjim.microapplet.mams.callback.wx.mp.message.WxMpMsgCallbackService;
import com.asialjim.microapplet.web.mvc.annotation.RwIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信公众号消息回调
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RwIgnore
@RestController
@RequiredArgsConstructor
@RequestMapping("/{appid}/msg/callback")
public class WeChatOfficialCallBackMessageController {
    private final WeChatOfficialMsgCryptService cryptService;
    private final WxMpMsgCallbackService callbackService;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> get(@PathVariable String appid,
                                      @RequestParam String signature,
                                      @RequestParam String timestamp,
                                      @RequestParam String nonce,
                                      @RequestParam String echostr) {
        String res = cryptService.verify(appid, signature, timestamp, nonce, echostr);
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(res);
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> post(@PathVariable String appid,
                                       @RequestParam String signature,
                                       @RequestParam String timestamp,
                                       @RequestParam String nonce,
                                       @RequestParam(required = false) String openid,
                                       @RequestParam(name = "encrypt_type", required = false) String encryptType,
                                       @RequestParam(name = "msg_signature", required = false) String msgSignature,
                                       @RequestBody String body) {
        String res = callbackService.post(appid, signature, timestamp, nonce, openid, encryptType, msgSignature, body);
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(res);
    }
}
