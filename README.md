## 恋爱补给站 AI智能体

## 项目简介

基于 Spring Boot 3 与 Spring AI，结合 RAG、Tool Calling 及 MCP 协议，打造企业级 AI 情感陪伴助手——“恋爱补给站 AI智能体”。该系统能够为用户提供专业的情感指导与陪伴服务，具备多轮情景对话、记忆持久化和 RAG 知识库检索等核心能力。借助 ReAct 推理框架，智能体可自主规划任务、调用外部工具（如网页搜索、资源获取与 PDF 生成），独立完成诸如定制完整约会方案并输出文档等复杂情感任务。

## 技术栈

- Java21 + Springboot3
- SpringAI
- RAG知识库
- PGvector 向量数据库
- Tool Calling 工具调用
- MCP模型上下文协议
- ReAct Agent 智能体构建
- Serverless 计算服务
- SSE异步推送

## 核心模块

- **情感对话引擎**：基于 Spring AI 的多轮情景对话能力，支持记忆持久化。
- **RAG 知识库**：结合 PGvector 向量数据库，实现高效的知识检索。
- **工具调用模块**：支持 Tool Calling，可调用外部工具（如网页搜索、PDF 生成）。
- **ReAct 智能体**：基于 ReAct 框架的任务规划与执行能力。
- **Serverless 计算服务**：动态扩展计算资源，支持高并发场景。
- **SSE 异步推送**：实时推送任务状态和结果。



