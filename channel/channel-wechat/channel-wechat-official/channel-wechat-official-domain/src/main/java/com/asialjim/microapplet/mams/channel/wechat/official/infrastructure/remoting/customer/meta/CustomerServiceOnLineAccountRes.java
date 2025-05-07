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

package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.customer.meta;

import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.BaseWeChatApiRes;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 获取在线的客服列表
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/22, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CustomerServiceOnLineAccountRes extends BaseWeChatApiRes {


    private static final long serialVersionUID = -7425790042448896670L;

    @JsonProperty("kf_online_list")
    private List<OnLineAccount> kfOnLineList;

    @Data
    public static class OnLineAccount{
        @JsonProperty("kf_account")
        private String kfAccount;
        private Integer status;
        @JsonProperty("kf_id")
        private String kfId;
        @JsonProperty("accepted_case")
        private Integer acceptedCase;
    }
}
