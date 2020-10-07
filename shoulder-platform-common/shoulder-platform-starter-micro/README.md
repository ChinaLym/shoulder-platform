# shoulder-platform

管理了 shoulder-platform 微服务开发中常用的jar（包含了shoulder-platform-common中几个常用的）

包含以下：
- 统一服务发现 & 注册
- 统一配置中心
- 统一链路追踪
- 统一性能监控、指标监控
- 统一服务调用
- 统一服务提供
- 统一消息通知

使用：

```xml
    <!-- WEB/db/mq/discovery/config/monitor/trace -->
    <dependency>
        <groupId>cn.itlym.platform</groupId>
        <artifactId>shoulder-platform-starter-micro</artifactId>
    </dependency>
```