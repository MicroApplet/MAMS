# AI Gateway 部署文档

## 环境要求

| 依赖 | 版本 | 说明 |
|------|------|------|
| Java | ≥ 21 | 推荐 JDK 21+ |
| Maven | ≥ 3.9 | 构建工具 |
| Redis | ≥ 6.x | 会话持久化（可选） |
| Nacos | ≥ 2.x | 服务发现 + 配置中心（可选） |

## 快速启动

### 1. 构建

```bash
cd MAMS/aigateway
mvn clean package -DskipTests -pl aigateway-web -am
```

### 2. 运行

```bash
java -jar aigateway-web/target/app.jar --server.port=8088
```

### 3. 验证

```bash
# 健康检查
curl http://localhost:8088/health

# AI 对话
curl -s -N -X POST http://localhost:8088/ai/chat \
  -H "Content-Type: application/json" \
  -H "X-User-Id: test" \
  -d '{"message":"你好"}'
```

## Docker 部署

```bash
# 构建镜像
docker build -t ai-gateway:latest -f aigateway-web/Dockerfile .

# 运行
docker run -d --name ai-gateway \
  -p 8088:8088 \
  -e REDIS_HOST=redis \
  -e REDIS_PORT=6379 \
  ai-gateway:latest
```

## 配置说明

| 环境变量 | 说明 | 默认值 |
|----------|------|--------|
| `SERVER_PORT` | 服务端口 | 8088 |
| `REDIS_HOST` | Redis 地址 | localhost |
| `REDIS_PORT` | Redis 端口 | 6379 |
| `REDIS_PASSWORD` | Redis 密码 | 空 |
| `LLM_API_KEY` | LLM API Key | 空 |
| `DIFY_API_KEY` | Dify API Key | 空 |
| `NACOS_ADDR` | Nacos 地址 | 空 |

## 目录结构

```
MAMS/aigateway/
  ├── aigateway-web/target/app.jar    ← 可执行 JAR
  ├── aigateway-web/Dockerfile        ← Docker 镜像
  ├── aigateway-web/docker.sh         ← Docker 构建脚本
  ├── API.md                          ← API 文档
  └── logs/audit/session.log          ← 审计日志
```
