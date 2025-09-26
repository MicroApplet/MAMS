/*
 *    Copyright 2014-$year.today <a href="mailto:asialjim@qq.com">Asial Jim</a>
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

package com.asialjim.microapplet.mams.wx.mp.infrastructure.config;

import lombok.Setter;
//import org.springframework.ai.tool.ToolCallback;
//import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * MCP 客户端配置
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/12, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Configuration
public class McpClientConfiguration implements CommandLineRunner , ApplicationContextAware {
    @Setter
    private ApplicationContext applicationContext;
   /*
    @Resource
    private List<ToolCallbackProvider> mcpToolCallbacks;
    */
//    @Autowired
//    @Qualifier("loadbalancedMcpAsyncToolCallbacks")
//    private ToolCallbackProvider tools;

    @Override
    public void run(String... args) {
        System.out.println("======================");
        System.out.println("McpClientConfiguration");
        System.out.println("======================");

        /*ToolCallback[] toolCallbacks = tools.getToolCallbacks();
        for (ToolCallback item : toolCallbacks) {
            System.out.print("MCP-TOOL:\t");
            System.out.println(item.getToolDefinition());
        }*/

        /*
        for (ToolCallbackProvider tool : mcpToolCallbacks) {
            ToolCallback[] tools = tool.getToolCallbacks();
            for (ToolCallback item : tools) {
                System.out.print("MCP-TOOL:\t");
                System.out.println(item);
            }
        }
        */
    }
}
