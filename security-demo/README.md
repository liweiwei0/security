# security-demo

## filter interceptor aspect(切片) controllerAdvice(自定义异常处理) 这四类调用过程

```text
请求过程
请求 --> filter -- interceptor --> controllerAdvice --> aspect --> 业务逻辑

响应过程
业务逻辑 --> aspect --> controllerAdvice --> interceptor --> filter --> 响应
```

![](001.PNG '调用管理图')

- filter 过滤器 接受请求响应对象 获取请求相关参数（无法获取请求的controller信息） 是javax的声明不知道spring声明的controller等mvc架构信息
- interceptor 拦截器 获取请求的controller及方法（无法获取参数）
- aspect 切片 获取请求controller的参数信息

> 注：
自定义异常在controllerAdvice中捕捉的 由controllerAdvice处理 此时异常在interceptor中的afterCompletion方法不会获取到

## springmvc 异步处理请求 模式分析

### spring异步处理机制
- 使用Callable或者DeferredResult当成Controller的返回值，能够处理异步返回 '单个结果' 的场景
- 使用ResponseBodyEmitter/SseEmitter或者StreamingResponseBody来流式处理 '多个返回值'
- 在Controller中使用响应式客户端调用服务并返回响应式的数据对象

## 使用wiremock模拟接口响应
> 启动服务作为mock数据的服务器
- 在demo目录下运行命令java -jar wiremock-standalone-2.19.0启动服务
- 之后在项目中通过wiremock客户端向服务器注册接口及相应结果
