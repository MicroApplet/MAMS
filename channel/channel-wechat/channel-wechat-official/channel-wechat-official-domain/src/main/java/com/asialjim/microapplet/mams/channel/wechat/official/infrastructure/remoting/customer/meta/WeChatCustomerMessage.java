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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * we-chat customer message
 *
 * @author Asial Jim &nbsp;&nbsp; <span>Email:<a href="mailto:asialjim@hotmail.com">asialjim@hotmail.com</a> &nbsp;&nbsp; <a href="asialjim@qq.com">asialjim@qq.com</a></span>
 * @version 1.0.0
 * @since 2021/3/8   &nbsp;&nbsp; JDK 8
 */
@Data
public abstract class WeChatCustomerMessage<T extends WeChatCustomerMessage<?>>
        implements Serializable {

    
    private static final long serialVersionUID = 6476058797183234088L;

    /**
     * open id for we-chat customer message receiver
     */
    protected String touser;

    /**
     * message type
     */
    protected String msgtype;

    @JsonProperty("customerservice")
    protected CustomerService customerService;

    @SuppressWarnings("unused")
    public T withToUser(String touser) {
        setTouser(touser);
        //noinspection unchecked
        return (T) this;
    }

    @SuppressWarnings("unused")
    public T withKfAccount(String kfAccount) {
        setCustomerService(CustomerService.builder().kfAccount(kfAccount).build());
        //noinspection unchecked
        return (T) this;
    }

    /**
     * set message type
     */
    protected void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerService {
        @JsonProperty("kf_account")
        private String kfAccount;
    }
}