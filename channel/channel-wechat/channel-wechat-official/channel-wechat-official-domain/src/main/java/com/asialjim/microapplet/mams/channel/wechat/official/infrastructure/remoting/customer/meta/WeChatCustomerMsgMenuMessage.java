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

import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.customer.meta.item.MsgMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;




/**
 * we-chat customer message menu
 *
 * @author Asial Jim &nbsp;&nbsp; <span>Email:<a href="mailto:asialjim@hotmail.com">asialjim@hotmail.com</a> &nbsp;&nbsp; <a href="asialjim@qq.com">asialjim@qq.com</a></span>
 * @version 1.0.0
 * @since 2021/3/8   &nbsp;&nbsp; JDK 8
 */
@Data
@SuppressWarnings("unused")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public final class WeChatCustomerMsgMenuMessage extends WeChatCustomerMessage<WeChatCustomerMsgMenuMessage> {


    private static final long serialVersionUID = 7658402551106775542L;
	private MsgMenu msgmenu;

    public WeChatCustomerMsgMenuMessage() {
        super.msgtype = "msgmenu";
    }

    public WeChatCustomerMsgMenuMessage(MsgMenu msgmenu) {
        super.msgtype = "msgmenu";
        this.msgmenu = msgmenu;
    }

    @Override
    public String getMsgtype() {
        return "msgmenu";
    }
}