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

package com.asialjim.microapplet.mams.web;

import com.asialjim.microapplet.mams.user.api.UserApi;
import com.asialjim.microapplet.mams.user.vo.UpdateAvatarReq;
import com.asialjim.microapplet.mams.user.vo.UpdateNicknameReq;
import jakarta.annotation.Resource;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

/**
 * 用户会话控制器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequestMapping
public class UserController {
    @Resource
    private UserApi userApi;


    @GetMapping("/phone")
    public String currentUserPhone() {
        return this.userApi.currentUserPhone();
    }

    @GetMapping("/nickname")
    public String currentNickname() {
        return this.userApi.currentNickname();
    }

    @PutMapping("/nickname")
    public String updateNickname(@RequestBody UpdateNicknameReq req) {
        return this.userApi.updateNickname(req);
    }

    @GetMapping("/avatar")
    public String currentAvatar() {
        return this.userApi.currentAvatar();
    }

    @PutMapping("/avatar")
    public String updateAvatar(@RequestBody UpdateAvatarReq req) {
        return this.userApi.updateAvatar(req);
    }

    @PostMapping(value = "/avatar")
    public String uploadAvatarFormData(@RequestPart("avatar") MultipartFile data) {
        try {
            byte[] byteArray = IOUtils.toByteArray(data.getInputStream());
            String avatar = Base64.getEncoder().encodeToString(byteArray);
            return this.userApi.updateAvatar(new UpdateAvatarReq().setAvatar(avatar));
        } catch (Throwable ignored) {
        }
        return currentAvatar();
    }
}