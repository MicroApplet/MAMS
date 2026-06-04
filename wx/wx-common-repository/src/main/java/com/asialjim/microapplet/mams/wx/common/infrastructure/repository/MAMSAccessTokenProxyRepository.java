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

package com.asialjim.microapplet.mams.wx.common.infrastructure.repository;

import com.asialjim.microapplet.remote.net.context.RemoteNetNodeKey;
import com.asialjim.microapplet.wechat.annotation.access_token.AccessTokenProxyRepository;
import org.springframework.stereotype.Component;

@Component
public class MAMSAccessTokenProxyRepository implements AccessTokenProxyRepository {
    @Override
    public RemoteNetNodeKey proxy() {
        return RemoteNetNodeKey.builder().host("wx-token-proxy.aliyun.asialjim.cn").build();
    }
}
