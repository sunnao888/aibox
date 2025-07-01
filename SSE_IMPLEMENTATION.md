# JManus SSE 流式接口实现文档

## 概述

本文档描述了将 ManusController 中的 jManus 接口从普通 HTTP 接口改为 Server-Sent Events (SSE) 流式接口的实现方案。

## 实现的功能

1. **SSE 流式响应**: 将原来的同步响应改为异步流式响应
2. **实时推送**: 智能体执行过程中的新增结果会实时推送给客户端
3. **生命周期管理**: 正确处理 SSE 连接的打开、数据传输、关闭和异常处理
4. **增量推送**: 只推送 `state.getResult()` 中新增的元素，避免重复推送

## 修改的文件

### 1. ManusController.java
- 将 `jManus` 方法的返回类型从 `CommonResult<List<ResultMessage>>` 改为 `SseEmitter`
- 添加 `produces = MediaType.TEXT_EVENT_STREAM_VALUE` 指定响应类型
- 添加相应的日志记录

### 2. ManusService.java
- 添加新的接口方法 `jManusStream(ManusReqVO reqVO)` 返回 `SseEmitter`

### 3. ManusServiceImpl.java
- 实现 `jManusStream` 方法
- 使用 `CompletableFuture.runAsync` 异步执行智能体任务
- 设置 SSE 超时时间为 10 分钟
- 添加连接生命周期回调处理

### 4. BaseAgent.java
- 添加新方法 `runWithSseEmitter(String userMessage, SseEmitter sseEmitter)`
- 实现增量结果推送逻辑
- 添加错误处理和异常推送

## SSE 事件类型

实现中定义了以下 SSE 事件类型：

1. **start**: 任务开始执行
2. **result**: 智能体执行过程中的结果数据
3. **complete**: 任务执行完成
4. **error**: 执行过程中的错误信息

## 数据格式

### 请求格式
```json
{
    "userMessage": "用户输入的消息内容"
}
```

### SSE 响应格式
```
event: start
data: {"message":"开始处理您的请求..."}

event: result
data: {"type":"AGENT","step":1,"result":"智能体的响应内容"}

event: complete
data: {"message":"任务执行完成"}
```

## 使用方式

### 前端 JavaScript 示例
```javascript
fetch('/admin-api/biz/manus/jManus', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'text/event-stream'
    },
    body: JSON.stringify({
        userMessage: '您的消息内容'
    })
}).then(response => {
    const reader = response.body.getReader();
    const decoder = new TextDecoder();
    
    function readStream() {
        reader.read().then(({ done, value }) => {
            if (done) return;
            
            const chunk = decoder.decode(value);
            // 处理 SSE 数据
            console.log(chunk);
            
            readStream();
        });
    }
    
    readStream();
});
```

### 使用 EventSource (如果支持 GET 请求)
```javascript
const eventSource = new EventSource('/admin-api/biz/manus/jManus');

eventSource.addEventListener('start', function(event) {
    console.log('任务开始:', event.data);
});

eventSource.addEventListener('result', function(event) {
    const result = JSON.parse(event.data);
    console.log('新结果:', result);
});

eventSource.addEventListener('complete', function(event) {
    console.log('任务完成:', event.data);
    eventSource.close();
});

eventSource.addEventListener('error', function(event) {
    console.error('发生错误:', event.data);
});
```

## 测试

项目根目录下提供了 `sse-test.html` 文件，可以用来测试 SSE 接口的功能。

1. 启动应用服务器
2. 在浏览器中打开 `sse-test.html`
3. 输入服务器地址和用户消息
4. 点击"开始连接"按钮测试 SSE 功能

## 技术特点

1. **异步处理**: 使用 `CompletableFuture` 异步执行智能体任务，避免阻塞主线程
2. **增量推送**: 通过记录上次推送的结果数量，只推送新增的结果
3. **错误处理**: 完善的异常处理机制，确保错误信息也能通过 SSE 推送
4. **连接管理**: 正确处理 SSE 连接的各种状态和生命周期事件
5. **超时控制**: 设置合理的超时时间，防止连接长时间占用资源

## 注意事项

1. **浏览器兼容性**: SSE 在现代浏览器中都有良好支持
2. **连接数限制**: 浏览器对同一域名的 SSE 连接数有限制（通常为 6 个）
3. **网络代理**: 某些代理服务器可能会缓冲 SSE 响应，影响实时性
4. **错误重连**: 客户端应该实现适当的重连机制
5. **资源清理**: 确保在页面卸载时正确关闭 SSE 连接

## 后续优化建议

1. **认证授权**: 添加 SSE 连接的身份验证机制
2. **连接池管理**: 实现 SSE 连接的池化管理
3. **消息持久化**: 考虑将消息持久化，支持断线重连后的消息恢复
4. **性能监控**: 添加 SSE 连接的性能监控和指标收集
5. **负载均衡**: 在集群环境下考虑 SSE 连接的负载均衡策略
