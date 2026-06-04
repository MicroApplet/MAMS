# AI Gateway 任务清单

> AIMAMS 后端服务，位于 `MAMS/aigateway/`

---

## 阶段一：项目基础

- [ ] **P1-BASE-01：项目脚手架**
  - 确认 `pom.xml` 配置完整（依赖、构建插件）
  - 创建 `AiGatewayApplication.java` 启动类
  - 验证 MAMS 父 POM 编译通过
  - 配置 `application.yml`（端口、Nacos、日志）

- [ ] **P1-BASE-02：SSE 端点骨架**
  - 实现 `AiChatController.java`
  - 实现 `SseEvent.java`（事件模型）
  - 实现 `SseEmitterHelper.java`（Flux 发射）
  - 验证 curl 测试能收到 SSE 流

- [ ] **P1-BASE-03：会话上下文基础**
  - 实现 `Session.java`（会话模型）
  - 实现 `IntentContext.java`（意图上下文）
  - 实现 `SessionManager.java`（会话管理，内存版）
  - 支持创建/获取/过期清理

---

## 阶段二：意图识别引擎

- [ ] **P2-INTENT-01：核心模型**
  - 实现 `IntentRecognizer.java`（识别器接口）
  - 实现 `IntentResult.java`（识别结果）
  - 实现 `Channel.java`（执行通道枚举）

- [ ] **P2-INTENT-02：引擎骨架**
  - 实现 `IntentRecognitionEngine.java`（入口）
  - 实现 `Scheduler.java`（调度器，默认全并行）
  - 实现 `Judge.java`（综合判定器，默认最高置信度）

- [ ] **P2-INTENT-03：规则引擎识别器**
  - 实现 `RuleRecognizer.java`
  - 基于关键词/正则匹配的简单意图识别
  - 可配置规则（YAML 或 Nacos）

- [ ] **P2-INTENT-04：LLM 直连识别器**
  - 实现 `LlmRecognizer.java`
  - 调用大模型 API 进行意图分类
  - 支持自定义模型配置

- [ ] **P2-INTENT-05：引擎集成测试**
  - Session → IntentRecognitionEngine → IntentResult 通路验证
  - 多识别器并行测试
  - 超时处理测试

---

## 阶段三：路由层

- [ ] **P3-ROUTER-01：路由核心**
  - 实现 `Router.java`
  - 实现 `RouterRule.java`
  - 规则匹配逻辑（配置覆盖 → defaultChannel）

- [ ] **P3-ROUTER-02：Nacos 配置监听**
  - 实现 `NacosRouterConfig.java`
  - 监听 Nacos `aimams-router.yaml` 配置变更
  - 本地缓存 + 热加载

- [ ] **P3-ROUTER-03：配置验证**
  - Nacos 配置下发后路由生效
  - 配置错误时的降级处理（走 defaultChannel）
  - 无配置时的回退行为

---

## 阶段四：执行通道

- [ ] **P4-CHAN-01：通道接口**
  - 实现 `ChannelExecutor.java`（通道执行器接口）
  - 返回 `Flux<SseEvent>`

- [ ] **P4-CHAN-02：直调 MCP Tool**
  - 实现 `DirectMcpExecutor.java`
  - 调用 MCP Server 工具
  - 返回结果转为 SSE 事件（text / data / ui）
  - 发射 tool_call / tool_result 过程事件

- [ ] **P4-CHAN-03：直连 AI 引擎**
  - 实现 `AiDirectExecutor.java`
  - 组装 LLM prompt
  - 流式调用 LLM API
  - LLM 调用 MCP Tools（function calling 模式）
  - 逐 token/事件流式返回

- [ ] **P4-CHAN-04：委托 Dify**
  - 实现 `DifyDelegateExecutor.java`
  - 提取意图级上下文
  - 调用 Dify API（不传历史消息）
  - 透传 Dify 流式响应
  - Dify 异常处理与降级

---

## 阶段五：MCP Server 自动发现

- [ ] **P5-MCP-01：发现服务**
  - 实现 `McpDiscoveryService.java`
  - 监听 Nacos 服务列表
  - 筛选 metadata.mcp.enabled 的服务
  - 拼接 MCP 端点

- [ ] **P5-MCP-02：工具调用器**
  - 实现 `McpInvoker.java`
  - 通过 MCP 协议调用工具
  - 工具列表缓存
  - 可用性探测

- [ ] **P5-MCP-03：集成测试**
  - 注册模拟 MCP Server → 自动发现 → 工具调用
  - 服务下线 → 自动移除
  - 端点不可用 → 降级处理

---

## 阶段六：上下文管理完善

- [ ] **P6-CTX-01：跨意图桥接**
  - 实现 `CrossIntentBridge.java`
  - 显式声明跨意图数据引用
  - 安全校验（只读指定 key）

- [ ] **P6-CTX-02：上下文持久化**
  - 可选：基于 Redis 的会话持久化
  - 会话过期策略

- [ ] **P6-CTX-03：上下文审计**
  - 会话操作日志
  - 意图隔离验证

---

## 阶段七：端到端联调

- [ ] **P7-E2E-01：与前端 SDK 联调**
  - SSE 事件流格式验证（所有事件类型）
  - 异常场景测试（断连、超时、错误）
  - 动态 UI Schema 数据验证

- [ ] **P7-E2E-02：与 Nacos 联调**
  - 路由配置动态下发
  - MCP Server 自动发现

- [ ] **P7-E2E-03：与 MAMS 联调**
  - 通过 user-interface 查询用户
  - 通过 applet-interface 查询应用
  - 权限校验链路

- [ ] **P7-E2E-04：与 Dify 联调**
  - Dify 委托执行
  - 上下文不维护验证（Dify 内部无记忆）

---

## 阶段八：完善与发布

- [ ] **P8-DOC-01：API 文档**
  - SSE 端点说明
  - Nacos 配置说明
  - MCP Server 接入说明

- [ ] **P8-DOC-02：部署文档**
  - Dockerfile
  - 环境变量说明
  - 启动脚本

- [ ] **P8-TEST-01：压力测试**
  - 并发会话测试
  - 长连接稳定性测试
  - 内存泄漏检查

---

## 优先级说明

| 标识 | 含义 |
|------|------|
| P1 | 必须优先完成（项目骨架） |
| P2 | 高优先级（意图识别是核心） |
| P3 | 高优先级（路由是流程中枢） |
| P4 | 高优先级（必须有一条通道可用） |
| P5 | 中优先级（MCP 发现是工具来源） |
| P6 | 中优先级（上下文完善） |
| P7 | 中优先级（联调验证） |
| P8 | 完善与发布 |
