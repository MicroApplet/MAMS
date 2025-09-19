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

package com.asialjim.microapplet.mams.user.application;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.user.domain.agg.UserAggRoot;
import com.asialjim.microapplet.mams.user.pojo.UserIdCard;
import com.asialjim.microapplet.mams.user.res.UserResCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户证件服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class UserIdCardService {


    /**
     * 用户实名认证：通过上传证件照
     *
     * @param file {@link MultipartFile file}
     * @return {@link UserIdCard }
     * @since 2025/4/10
     */
    public UserIdCard idCardRegByFile(MultipartFile file) {
        UserAggRoot userAgg = App.beanOrThrow(UserAggRoot.class, UserResCode.UserNotLogin::bizException);
        return userAgg.idCardRegByFile(file);

    }

    /**
     * 用户证件冲突检查
     *
     * @param user {@link UserIdCard user}
     * @since 2025/4/10
     */
    public void userIdCardConflictCheck(UserIdCard user) {
        App.beanAndThenOrThrow(UserAggRoot.class, userAgg -> userAgg.userIdCardConflictCheck(user), UserResCode.UserNotLogin::bizException);
    }
}