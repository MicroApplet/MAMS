# AI Gateway API 文档

## 概览

AI Gateway 提供基于 SSE（Server-Sent Events）的 AI 对话 API，支持意图识别、路由分发和动态 UI 输出。

**基础 URL**: `http://localhost:8088`

---

## 1. SSE 对话端点

### `POST /ai/chat`

流式 AI 对话接口。请求为普通 HTTP POST，响应为 text/event-stream。

#### 请求头

| 头部 | 必填 | 说明 |
|------|------|------|
| `Content-Type` | 是 | `application/json` |
| `X-User-Id` | 是 | 用户编号 |
| `X-User-Role` | 否 | 用户角色位图 |
| `X-Platform` | 否 | 来源平台（wechat/h5/admin） |

#### 请求体

```json
{
  "sessionId": "可选，不传则自动生成",
  "message": "用户消息文本"
}
```

#### 响应格式（SSE）

```
event: thinking
data: {"text":"正在理解您的请求…"}

event: tool_call
data: {"tool":"query_order","params":{"raw":"帮我查订单"}}

event: tool_result
data: {"tool":"query_order","result":{"status":"ok"}}

event: text
data: {"text":"已完成查询"}

event: done
data: [DONE]
```

#### 事件类型

| event | 说明 | data 结构 |
|-------|------|-----------|
| `thinking` | AI 思考中 | `{"text": "..."}` |
| `tool_call` | 正在调用工具 | `{"tool": "...", "params": {...}}` |
| `tool_result` | 工具返回结果 | `{"tool": "...", "result": ...}` |
| `text` | 纯文本回复 | `{"text": "..."}` |
| `data` | 结构化数据 | `{"schema": ..., "data": [...], "summary": "..."}` |
| `ui` | 动态 UI 卡片 | `{"uiType": "card|list|table|form|confirm", "title": "...", "fields": [...], "actions": [...]}` |
| `error` | 错误信息 | `{"code": "...", "message": "..."}` |
| `require_input` | 需要用户补充 | `{"question": "...", "hint": "..."}` |
| `done` | 会话结束 | `[DONE]` |

#### 示例

```bash
curl -s -N -X POST http://localhost:8088/ai/chat \
  -H "Content-Type: application/json" \
  -H "X-User-Id: user123" \
  -d '{"message":"帮我查最近的订单"}'
```

---

## 2. 健康检查

### `GET /health`

```json
{
  "service": "AI Gateway",
  "version": "1.0.0",
  "status": "running",
  "timestamp": "2026-06-08T10:00:00Z"
}
```

---

## 3. 路由配置（Nacos）

### 配置方式

通过 Nacos 配置中心下发路由规则，dataId: `aimams-router.yaml`, group: `AIMAMS`。

### 配置格式

```yaml
router:
  rules:
    query_order:
      channel: DIRECT_MCP      # 直调 MCP Tool
    send_message:
      channel: DIFY            # 委托 Dify
    complex_business:
      channel: AI_DIRECT       # 直连 AI 引擎
  fallback: intent_default     # 未匹配时走意图默认通道
```

### 通道说明

| 通道 | 说明 | 适用场景 |
|------|------|---------|
| `DIRECT_MCP` | 直调 MCP Server | 简单 CRUD、参数明确的查询 |
| `AI_DIRECT` | 直连大模型 | 复杂推理、多步编排 |
| `DIFY` | 委托 Dify 平台 | 需要工作流编排的复杂业务 |

### 路由优先级

1. Nacos 配置的规则（最高优先级）
2. 意图识别结果自带的 defaultChannel
3. 全部不可用时的默认通道 `AI_DIRECT`

---

## 4. MCP Server 接入

### 注册方式

微服务在 Nacos 注册时添加以下 metadata，AI Gateway 会自动发现：

```yaml
# Nacos 服务注册 metadata
metadata:
  mcp.enabled: true
  mcp.endpoint: /mcp
```

### MCP 协议

遵循标准 MCP（Model Context Protocol）协议规范，工具定义和调用均通过 MCP 协议完成。

---

## 5. 配置项

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| `server.port` | 服务端口 | 8088 |
| `spring.redis.host` | Redis 地址 | localhost |
| `spring.redis.port` | Redis 端口 | 6379 |
| `aimams.intent.llm-endpoint` | LLM 意图识别端点 | 不配置则跳过 LLM 识别 |
| `aimams.intent.llm-model` | LLM 模型名 | deepseek-chat |
| `aimams.channel.dify.url` | Dify API 地址 | - |
| `aimams.channel.dify.api-key` | Dify API Key | - |

---

## 6. 审计日志

会话操作审计日志输出到 `logs/audit/session.log`，按天滚动保留 30 天。
