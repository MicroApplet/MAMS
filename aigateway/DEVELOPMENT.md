# AI Gateway 开发/技术文档

> AIMAMS 后端服务，位于 `MAMS/aigateway/`

---

## 一、技术栈

| 层级 | 技术 |
|------|------|
| 语言 | Java 21 |
| 框架 | Spring Boot 4.0.0-M2 |
| 微服务治理 | Nacos（服务发现 + 配置中心） |
| ORM | MyBatis Flex |
| AI 编排 | 自研 AI Engine（可委托 Dify） |
| MCP 协议 | 遵循 MCP 开放标准 |
| SSE | 标准 `text/event-stream` |
| 消息/流式 | Spring WebFlux（SSE 输出）或 WebMVC 异步 |
| 构建 | Maven（MAMS 子模块） |

---

## 二、项目结构

```
MAMS/aigateway/
  ├── pom.xml
  ├── README.md
  ├── DEVELOPMENT.md          ← 本文档
  ├── TASKS.md                ← 任务清单
  │
  └── src/main/java/com/asialjim/microapplet/mams/aigateway/
      │
      ├── AiGatewayApplication.java           ← 启动类
      │
      ├── config/
      │   ├── AiGatewayConfig.java            ← 通用配置
      │   └── NacosRouterConfig.java          ← Nacos 路由配置监听
      │
      ├── controller/
      │   └── AiChatController.java           ← SSE 对话端点 /ai/chat
      │
      ├── session/
      │   ├── Session.java                    ← 会话模型（用户级+会话级）
      │   ├── IntentContext.java              ← 意图级上下文
      │   ├── SessionManager.java             ← 会话管理器
      │   └── CrossIntentBridge.java          ← 跨意图桥接
      │
      ├── intent/
      │   ├── IntentRecognizer.java           ← 识别器接口
      │   ├── IntentResult.java               ← 意图识别结果
      │   ├── engine/
      │   │   ├── IntentRecognitionEngine.java ← 意图识别引擎（调度器+判定器）
      │   │   ├── Scheduler.java              ← 调度器
      │   │   └── Judge.java                  ← 综合判定器
      │   └── recognizers/
      │       ├── RuleRecognizer.java         ← 规则引擎识别器
      │       └── LlmRecognizer.java          ← LLM 直连识别器
      │
      ├── router/
      │   ├── Router.java                     ← 路由层
      │   ├── RouterRule.java                 ← 路由规则
      │   └── Channel.java                    ← 执行通道枚举
      │
      ├── channel/
      │   ├── ChannelExecutor.java            ← 通道执行器接口
      │   ├── DirectMcpExecutor.java          ← 直调 MCP Tool
      │   ├── AiDirectExecutor.java           ← 直连 AI 引擎
      │   └── DifyDelegateExecutor.java       ← 委托 Dify
      │
      ├── tool/
      │   ├── McpDiscoveryService.java        ← MCP Server 自动发现
      │   └── McpInvoker.java                 ← MCP 工具调用器
      │
      └── sse/
          ├── SseEvent.java                   ← SSE 事件模型
          └── SseEmitterHelper.java           ← SSE 发射器工具
```

---

## 三、核心模块设计

### 3.1 SSE 对话入口（Controller）

```java
@RestController
@RequestMapping("/ai")
public class AiChatController {

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<SseEvent> chat(@RequestBody ChatRequest request,
                                @RequestHeader("X-User-Id") String userId) {
        // ① 创建/获取会话
        // ② 调用意图识别引擎
        // ③ 路由层判定执行通道
        // ④ 执行通道处理
        // ⑤ 返回 SSE 流
    }
}
```

### 3.2 意图识别引擎

```
用户消息 → IntentRecognitionEngine
               │
          Scheduler 调度
          ┌────┴────┐
          │         │
     RuleRecognizer  LlmRecognizer  ← 可扩展
          │         │
          └────┬────┘
               │
            Judge 综合判定
               │
           IntentResult
```

```java
public class IntentResult {
    private String intent;            // 意图标识
    private double confidence;        // 置信度
    private String defaultChannel;    // 默认执行通道
    private Map<String, Object> params;  // 提取的参数
    private String source;            // 来源识别器
}
```

### 3.3 路由层

```java
@Component
public class Router {
    private final Map<String, RouterRule> rules;  // 从 Nacos 热加载

    public Channel route(IntentResult intent) {
        RouterRule rule = rules.get(intent.getIntent());
        if (rule != null) {
            return rule.getChannel();       // 配置覆盖
        }
        return Channel.valueOf(intent.getDefaultChannel()); // 走默认
    }
}
```

Nacos 配置格式：

```yaml
# dataId: aimams-router.yaml, group: AIMAMS
router:
  rules:
    query_order:
      channel: direct_mcp
    send_message:
      channel: dify
    complex_business:
      channel: ai_direct
  fallback: intent_default
```

### 3.4 会话上下⽂管理

四级上下文模型：

```java
// 用户级 — 跨会话持有
public class UserContext {
    private String userId;
    private String role;
    private String platform;
}

// 会话级 — 一个会话窗口
public class Session {
    private String sessionId;
    private String userId;
    private String platform;
    private Map<String, IntentContext> intents;      // 意图上下文
    private List<CrossIntentBridge> bridges;         // 跨意图桥接
}

// 意图级 — 隔离
public class IntentContext {
    private String intentId;
    private String intentName;
    private Map<String, Object> output;              // 该意图的输出
}

// 跨意图桥接 — 显式引用
public class CrossIntentBridge {
    private String fromIntentId;
    private String toIntentId;
    private List<String> sharedKeys;                 // 共享哪些字段
}
```

### 3.5 执行通道

```java
public interface ChannelExecutor {
    Flux<SseEvent> execute(IntentResult intent, Session session);
}

// 直调 MCP Tool
@Component
public class DirectMcpExecutor implements ChannelExecutor {
    public Flux<SseEvent> execute(IntentResult intent, Session session) {
        // ① 发射 thinking 事件
        // ② 调用 MCP Tool
        // ③ 根据结果决定返回 text / data / ui
        // ④ 发射 done 事件
    }
}

// 直连 AI 引擎
@Component
public class AiDirectExecutor implements ChannelExecutor {
    public Flux<SseEvent> execute(IntentResult intent, Session session) {
        // ① 将意图 + 上下文组装成 LLM prompt
        // ② 调用 LLM（携带 MCP Tools 列表作为 function calling）
        // ③ 逐 token/事件流式返回
    }
}

// 委托 Dify
@Component
public class DifyDelegateExecutor implements ChannelExecutor {
    public Flux<SseEvent> execute(IntentResult intent, Session session) {
        // ① 提取意图级上下文作为参数
        // ② 调用 Dify API（传入参数，不传历史消息）
        // ③ 透传 Dify 的流式响应
    }
}
```

### 3.6 MCP Server 自动发现

```java
@Component
public class McpDiscoveryService {
    // 监听 Nacos 服务列表变化
    @NacosEventListener
    public void onServiceChange(List<ServiceInstance> instances) {
        // 筛选 metadata.mcp.enabled = true 的服务
        // 拼接 mcp.endpoint
        // 探测可用性
        // 拉取工具列表（MCP 协议标准）
    }
}
```

Nacos 服务注册时约定的元数据：

```yaml
# 各微服务在 Nacos 注册时的 metadata
metadata:
  mcp.enabled: true
  mcp.endpoint: /mcp
```

---

## 四、SSE 事件输出

所有执行通道的输出统一为 SSE 流，事件类型与前端约定一致：

| event | 谁发射 | 说明 |
|-------|--------|------|
| `thinking` | 各 Executor | 过程反馈，{"text": "..."} |
| `tool_call` | DirectMcpExecutor / AiDirectExecutor | 调用工具时 |
| `tool_result` | DirectMcpExecutor | 工具返回结果 |
| `text` | 各 Executor | 纯文本回复 |
| `data` | DirectMcpExecutor | 结构化数据返回 |
| `ui` | 各 Executor | 动态 UI 卡片 |
| `error` | 各 Executor / 全局异常处理 | 错误信息 |
| `require_input` | 各 Executor | 需要用户补充 |
| `done` | 各 Executor | 结束标识 |

---

## 五、与 MAMS 其他模块的关系

```
aigateway 依赖 MAMS 领域层：

  aigateway
      │
      ├── user-interface      ← 用户查询、认证
      ├── applet-interface    ← 应用查询
      ├── channel-base        ← 渠道类型
      └── commons-*           ← 基础设施
```

**重要原则**：
- aigateway **不直接操作数据库**，通过 MAMS 领域层接口访问
- aigateway **不包含业务 CRUD**，只做 AI 编排
- MAMS 原有模块无感知（aigateway 是旁路新增）

---

## 六、配置项

```yaml
# application.yml
aimams:
  router:
    nacos-data-id: aimams-router.yaml
    nacos-group: AIMAMS
  intent:
    timeout: 5000               # 意图识别超时（毫秒）
    default-judge: highest-confidence
  channel:
    dify:
      url: https://api.dify.ai/v1
      api-key: ${DIFY_API_KEY}
    ai-direct:
      model: deepseek-v3
      endpoint: https://api.xxx.com
  mcp:
    discovery-interval: 30000   # MCP 服务发现轮询间隔（毫秒）
```

---

## 七、开发指南

### 7.1 本地开发

```bash
# 编译
cd MAMS
mvn clean install -pl aigateway -am

# 运行
mvn spring-boot:run -pl aigateway

# 测试
mvn test -pl aigateway
```

### 7.2 SSE 接口测试

```bash
curl -X POST http://localhost:8080/ai/chat \
  -H "Content-Type: application/json" \
  -H "X-User-Id: test_user" \
  -d '{"message": "帮我查张三的订单"}' \
  --no-buffer
```

### 7.3 扩展点

| 扩展点 | 方式 |
|--------|------|
| 新增意图识别器 | 实现 `IntentRecognizer` 接口，`@Component` 自动注册 |
| 新增执行通道 | 实现 `ChannelExecutor` 接口，注册到 Router |
| 自定义路由规则 | 通过 Nacos 配置动态下发 |
| 自定义 UI Schema 类型 | 前端注册，后端只需在 `ui` 事件的 payload 中填对应的 `uiType` |
