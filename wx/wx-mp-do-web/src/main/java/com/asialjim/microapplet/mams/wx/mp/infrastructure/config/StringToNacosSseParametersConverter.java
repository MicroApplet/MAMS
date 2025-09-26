///*
// *    Copyright 2014-$year.today <a href="mailto:asialjim@qq.com">Asial Jim</a>
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
//package com.asialjim.microapplet.mams.wx.mp.infrastructure.config;
//
//
//import com.alibaba.cloud.ai.autoconfigure.mcp.client.NacosMcpSseClientProperties;
//import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//@ConfigurationPropertiesBinding // 这个注解很重要，表明这是用于配置属性绑定的转换器
//public class StringToNacosSseParametersConverter implements Converter<String, NacosMcpSseClientProperties.NacosSseParameters> {
//    @Override
//    public NacosMcpSseClientProperties.NacosSseParameters convert(String source) {
//         String[] parts = source.split(":");
//         return new NacosMcpSseClientProperties.NacosSseParameters(parts[0], parts[1]);
//    }
//}
