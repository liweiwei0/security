# security-demo

## filter interceptor aspect(切片) controllerAdvice(自定义异常处理) 这四类调用过程

```text
请求过程
请求 --> filter -- interceptor --> controllerAdvice --> aspect --> 业务逻辑

响应过程
业务逻辑 --> aspect --> controllerAdvice --> interceptor --> filter --> 响应
```

![](./001.PNG '调用管理图')

- filter 过滤器 接受请求响应对象 获取请求相关参数（无法获取请求的controller信息） 是javax的声明不知道spring声明的controller等mvc架构信息
- interceptor 拦截器 获取请求的controller及方法（无法获取参数）
- aspect 切片 获取请求controller的参数信息

> 注：
自定义异常在controllerAdvice中捕捉的 由controllerAdvice处理 此时异常在interceptor中的afterCompletion方法不会获取到
