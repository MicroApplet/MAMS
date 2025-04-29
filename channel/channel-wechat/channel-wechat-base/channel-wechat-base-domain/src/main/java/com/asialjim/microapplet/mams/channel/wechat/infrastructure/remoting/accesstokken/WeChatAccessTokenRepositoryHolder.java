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
 package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.accesstokken;

 import java.util.Objects;

 /**
  * 微信公众平台API令牌仓库
  *
  * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
  * @version 1.0
  * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
  */
 public class WeChatAccessTokenRepositoryHolder {
     private static WeChatAccessTokenRepository tokenRepository;

     public static void repository(WeChatAccessTokenRepository repository) {
         tokenRepository = repository;
     }

     public static WeChatAccessTokenRepository repository() {
         if (Objects.isNull(tokenRepository))
             throw new IllegalStateException("微信接口令牌数据仓库未初始化");
         return tokenRepository;
     }
 }