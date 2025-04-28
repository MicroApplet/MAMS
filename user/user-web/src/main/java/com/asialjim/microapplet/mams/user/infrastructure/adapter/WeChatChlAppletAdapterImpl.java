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

package com.asialjim.microapplet.mams.user.infrastructure.adapter;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.user.chl.wechat.infrastructure.adapter.WeChatChlAppletAdapter;
import com.asialjim.microapplet.mams.user.chl.wechat.po.WeChatAppChl;
import org.springframework.stereotype.Component;

/**
 * 微信小程序渠道应用适配器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatChlAppletAdapterImpl implements WeChatChlAppletAdapter {

    @Override
    public WeChatAppChl queryByAppidAndType(String chlAppId, ChlAppType chlAppType) {
        // TODO
        return null;
    }
}