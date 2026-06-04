# AI Gateway

AIMAMS 后端服务，MAMS 的子模块。

## 职责

作为 MAMS 的一个子模块，提供 AIMAMS 的 AI 交互能力：

- **AI 对话入口** — SSE `/ai/*` 端点
- **意图识别引擎** — 调度器 + 识别器 + 综合判定器
- **路由层** — 基于 Nacos 配置的动态通道路由
- **工具层** — 对接微服务治理体系下的 MCP Server
- **会话上下文管理** — 四级上下文（用户/会话/意图/跨意图桥接）

## 架构定位

```
入口网关 (Gateway)
    │
    ├── /api/*     → MAMS 传统业务服务
    ├── /ai/*      → AI Gateway (aigateway)
    │                   │
    │                   ├→ 意图识别 → 路由 → 执行通道
    │                   └→ 上下文管理
    └── /api/direct/* → 业务服务（AI 生成 UI 的按钮直连）
```

## 依赖

- MAMS 领域层（用户/应用/组织）
- Commons 基础设施（Nacos / 安全 / Web）
- 外部 MCP Server（通过 Nacos 自动发现）

## 设计文档

顶层设计概念见 [AIMAMS/DESIGN.md](../AIMAMS/DESIGN.md)。
