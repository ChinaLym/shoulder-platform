# shoulder-platform-starter-rpc-server

包含如 `spring-boot-starter-web`、`shoulder-starter-web` 参数校验、参数转换、统一异常处理等

- 请求限流
    - `Sentinel`
- 熔断
    - 当必需依赖无法使用时，返回熔断值，（如数据库无法连接、依赖的其他服务无法正确响应）
- 降级
    - 返回默认值
    - 返回缓存值
    - 返回失败
- 缓存
    - `spring-cache`
    

## 限流 [Sentinel](https://sentinelguard.io/zh-cn/)


[sentinel](https://github.com/alibaba/Sentinel)
    
[sentinel-wiki 生产环境使用 sentinel](https://github.com/alibaba/Sentinel/wiki/%E5%9C%A8%E7%94%9F%E4%BA%A7%E7%8E%AF%E5%A2%83%E4%B8%AD%E4%BD%BF%E7%94%A8-Sentinel)

核心注解：
`@SentinelResource(value = "sayHello")`

[sentinel demo 指南](https://github.com/sentinel-group/sentinel-guides)

[sentinel 高级玩法与案例](https://github.com/sentinel-group/sentinel-awesome)

