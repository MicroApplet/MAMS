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

package com.asialjim.microapplet.user.component.sms.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SendSMSBizValidator {
	private List<SMSSendValidator> validators;

	@Autowired
	public void setValidators(List<SMSSendValidator> validators) {
		this.validators = validators;
	}

	/**
	 * @param user  {@link String 登录用户}
	 * @param biz   {@link String 业务标识}
	 * @param phone {@link String 手机号}
	 * @since 2024/6/26
	 */
	public void smsSendBizValid(String user, String biz, String phone, Map<String,String> params) {
		this.validators.forEach(item -> item.validSend(biz,user,phone,params));
	}
}