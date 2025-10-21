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

package com.asialjim.microapplet.mams.wx.common.api;

import com.asialjim.microapplet.mams.wx.common.vo.UpdateAvatarRequest;
import com.asialjim.microapplet.mams.wx.common.vo.UpdateNicknameRequest;
import com.asialjim.microapplet.wechat.user.WeChatUserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 微信用户API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/20, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface WeChatUserApi {
    String path = "/wx/common/user";

    @GetMapping("/{id}")
    WeChatUserVo queryByOpenid(@PathVariable("id") String id);

    @PutMapping("/{id}/avatar")
    WeChatUserVo updateAvatarByOpenid(@PathVariable("id") String id, @RequestBody UpdateAvatarRequest req);

    @PutMapping("/{id}/nickname")
    WeChatUserVo updateNicknameByOpenid(@PathVariable("id") String id, @RequestBody UpdateNicknameRequest req);
}