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

import com.asialjim.microapplet.mams.callback.wx.mp.oauth.WeChatMpOAuthPageService;
import com.asialjim.microapplet.web.mvc.annotation.RwIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信网页授权页面回调
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RwIgnore
@RestController
@RequiredArgsConstructor
@RequestMapping("/{appid}/oauth/page")
public class WxMpOAuthPageController {
    private final WeChatMpOAuthPageService pageService;

    @GetMapping("/{handler}")
    public ResponseEntity<Void> page(@PathVariable String appid,
                                     @PathVariable String handler,
                                     @RequestParam String code,
                                     @RequestParam(required = false) String state) {
        return pageService.page(appid, handler, code, state);
    }
}
