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

package com.asialjim.microapplet.mams.wx.mp.web;

import com.asialjim.microapplet.mams.wx.mp.api.WeChatMpOAuthPageApi;
import com.asialjim.microapplet.mams.wx.mp.service.oauth.WeChatMpOAuthPageService;
import com.asialjim.microapplet.mams.wx.mp.vo.oauth.BuildWeChatOAuthPageVo;
import com.asialjim.microapplet.mams.wx.mp.vo.oauth.WeChatMpOAuthHandlerVo;
import com.asialjim.microapplet.web.mvc.annotation.RwIgnore;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@Controller
@RequestMapping(WeChatMpOAuthPageApi.path)
public class WxMpOAuthPageController implements WeChatMpOAuthPageApi {
    // String path = "/{appid}/oauth/page";

    @Resource
    private WeChatMpOAuthPageService weChatMpOAuthPageService;

    @RwIgnore
    @GetMapping("/{handler}")
    public Object handle(@PathVariable("appid") String appid,
                         @PathVariable("handler") String handler,
                         @RequestParam String code,
                         @RequestParam String state, Model model) {

        return this.weChatMpOAuthPageService.page(appid, handler, code, state, model);
    }

    @Override
    public List<WeChatMpOAuthHandlerVo> handlers() {
        return this.weChatMpOAuthPageService.handlers();
    }

    @Override
    public String build(BuildWeChatOAuthPageVo req) {
        return this.weChatMpOAuthPageService.build(
                req.getHandler(),
                req.getAppid(),
                req.getUrl(),
                Boolean.TRUE.equals(req.getManual()),
                req.getParameter(),
                req.duration(),
                req.getMaxClickTimes());
    }
}