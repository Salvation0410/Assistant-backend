# Assistant

基于 Spring Boot 与 LangChain4j 构建的编程学习 / 求职面试 AI 助手示例项目。项目接入阿里云 DashScope 通义千问模型，演示了基础对话、AiService、会话记忆、结构化输出、RAG 检索增强以及面试题网页抓取工具的集成方式。

## 功能特性

- 基础 AI 对话：通过 `ChatModel` 调用通义千问模型生成回答。
- AiService 封装：使用 LangChain4j 的 `AiServices` 将接口代理为 AI 服务。
- 系统提示词：从 `system-prompt.txt` 加载编程学习和求职面试助手角色设定。
- 会话记忆：使用 `MessageWindowChatMemory` 保留最近 10 条消息上下文。
- RAG 检索增强：加载 `src/main/resources/doc` 下的知识文档，按段落切分并进行向量检索。
- 结构化输出：通过 Java `record` 接收模型生成的结构化学习建议。
- 工具调用示例：通过 Jsoup 抓取面试鸭网站的相关面试题。

## 技术栈

- Java 21
- Spring Boot 3.5.14
- Maven
- LangChain4j 1.1.0 / 1.1.0-beta7
- DashScope 通义千问
- Lombok
- Jsoup

## 项目结构

```text
.
├── pom.xml
├── src
│   ├── main
│   │   ├── java/com/song/assitant
│   │   │   ├── AssitantApplication.java
│   │   │   ├── ai
│   │   │   │   ├── AiCodeHelper.java
│   │   │   │   └── AiCodeHelperService.java
│   │   │   ├── config
│   │   │   │   ├── AiCodeHelperFactory.java
│   │   │   │   └── AiCodeHelperServiceFactory.java
│   │   │   ├── rag
│   │   │   │   └── RagConfig.java
│   │   │   └── tools
│   │   │       └── InterviewQuestionTool.java
│   │   └── resources
│   │       ├── application.yml
│   │       ├── application-local.yml
│   │       ├── system-prompt.txt
│   │       └── doc
│   └── test
│       └── java/com/song/assitant
```

## 环境要求

请先确认本机已安装：

- JDK 21+
- Maven 3.8+，或直接使用项目自带的 Maven Wrapper
- 可用的 DashScope API Key

## 配置说明

模型配置位于 `src/main/resources/application.yml`：

```yaml
spring:
  application:
    name: Assistant

langchain4j:
  community:
    dashscope:
      chat-model:
        model-name: qwen-max
        api-key: 你的 DashScope API Key
      embedding-model:
        model-name: text-embedding-v4
        api-key: 你的 DashScope API Key
```

建议不要将真实 API Key 提交到 Git 仓库。可以将本地私密配置放到 `application-local.yml`，并在启动时启用 `local` profile：

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```

## 启动项目

Windows：

```powershell
.\mvnw.cmd spring-boot:run
```

macOS / Linux：

```bash
./mvnw spring-boot:run
```

默认会启动 Spring Boot 应用。当前项目主要通过测试类演示 LangChain4j 能力，暂未提供 Controller 接口。

## 运行测试

Windows：

```powershell
.\mvnw.cmd test
```

macOS / Linux：

```bash
./mvnw test
```

测试类说明：

- `AiCodeHelperTest`：基础对话、多模态消息、会话记忆、RAG 调用示例。
- `AiCodeHelperServiceTest`：结构化输出示例。
- `AiCodeHelperServiceFactoryTest`：AiService 工厂创建和调用示例。

注意：部分测试会真实调用 DashScope 模型接口，需要有效 API Key 和网络环境。

## 核心模块说明

### `AiCodeHelper`

直接注入 `ChatModel`，手动构造 `SystemMessage` 与 `UserMessage` 完成基础对话。适合理解 LangChain4j 最基础的模型调用流程。

### `AiCodeHelperService`

使用 LangChain4j AiService 声明式接口：

- `chat`：普通对话。
- `chatForReport`：生成结构化报告。
- `chatWithRag`：返回包含检索结果等信息的 `Result<String>`。

### `AiCodeHelperFactory`

通过 `AiServices.builder` 创建 `AiCodeHelperService` Bean，并配置：

- 通义千问聊天模型
- 最近 10 条消息的窗口记忆
- RAG 内容检索器

### `RagConfig`

加载 `src/main/resources/doc` 目录下的文档，使用段落切分器处理文本，再写入向量存储并构建 `ContentRetriever`。检索配置如下：

- `maxResults = 5`
- `minScore = 0.75`
- 每个文本片段最大 1000 字符
- 相邻片段最大重叠 200 字符

### `InterviewQuestionTool`

使用 Jsoup 访问面试鸭搜索页，根据关键词提取相关面试题标题。该类展示了 LangChain4j Tool 的定义方式。

## 知识库文档

RAG 默认读取以下目录：

```text
src/main/resources/doc
```

当前包含：

- Java 编程学习路线
- 程序员常见面试题
- 鱼皮的项目学习建议
- 鱼皮的求职指南

如需扩展知识库，可将新的 Markdown 文档放入该目录。应用启动时会重新加载并向量化这些文档。

## 常见问题

### 1. 启动或测试时 API Key 报错怎么办？

检查 `application.yml` 或本地 profile 配置中是否填写了有效的 DashScope API Key，并确认账号已开通对应模型权限。

### 2. RAG 没有检索到内容怎么办？

可以检查：

- `src/main/resources/doc` 目录是否存在文档。
- 文档内容是否与用户问题相关。
- `minScore` 是否过高。当前阈值为 `0.75`，可根据实际效果调低。

### 3. 终端中文显示乱码怎么办？

请确认源文件编码、IDE 编码和终端编码一致，推荐统一使用 UTF-8。

## 后续可扩展方向

- 增加 REST Controller，对外提供聊天接口。
- 接入前端页面，实现完整 AI 助手体验。
- 将向量数据持久化到 Milvus、PGVector、Elasticsearch 等外部存储。
- 为工具调用接入更多求职网站或题库来源。
- 增加异常处理、限流、日志追踪和配置隔离。
