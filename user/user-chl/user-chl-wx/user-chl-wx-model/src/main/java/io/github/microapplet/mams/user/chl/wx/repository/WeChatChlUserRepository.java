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

package io.github.microapplet.mams.user.chl.wx.repository;

import io.github.microapplet.mams.user.chl.wx.model.WeChatChlUser;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 微信渠道用户数据仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface WeChatChlUserRepository {

    /**
     * 用户注册
     *
     * @param condition {@link WeChatChlUser condition}
     * @return {@link WeChatChlUser }
     * @since 2025/3/13
     */
    WeChatChlUser register(WeChatChlUser condition);

    /**
     * 根据openid ，查询微信渠道用户信息
     *
     * @param openid {@link String openid}
     * @return {@link WeChatChlUser }
     * @since 2025/3/13
     */
    WeChatChlUser queryByOpenid(String openid);

    /**
     * 根据openid查询，如果查询不到则注册
     *
     * @param condition {@link WeChatChlUser condition}
     * @return {@link WeChatChlUser }
     * @since 2025/3/13
     */
    default WeChatChlUser queryOrRegister(WeChatChlUser condition) {
        if (Objects.isNull(condition) || StringUtils.isBlank(condition.getOpenid()))
            return condition;

        WeChatChlUser weChatChlUser = queryByOpenid(condition.getOpenid());
        if (Objects.nonNull(weChatChlUser))
            return weChatChlUser;

        return register(condition);
    }

    void update(WeChatChlUser wxChlUser);
}