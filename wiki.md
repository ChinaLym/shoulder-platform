# shoulder-platform wiki

# 目录 & 能力介绍
创建工程
- 通过 `maven` 的 `archetype` 快速创建工程骨架
    - 基于 `shoulder-framework`（面向spring-boot）
    - 基于 `shoulder-platform`（面向 spring-cloud）
版本管理pom：`shoulder-dependencies`
父级pom：`shoulder-parent`
web 增强 shoulder-web（spring-boot-starter-web增强）
    - 统一业务异常拦截
    - 统一返回值包装
    - xss、csrf（考虑抽出来？）
    - 参数校验
    - 请求日志美化（仅开发态）
核心包：shoulder-core
    - 异常定义
    - 日志增强
    - 通用工具
    - 应用信息
    - 定义了通用的返回值，参数

日志相关(运行日志，操作日志，追踪日志)
    运行日志：shoulder-core 里
    操作日志：shoulder-business-log
    追踪日志：未封装，采用 zipkin


安全与认证(基于 `spring security`)
    单点登录：shoulder-cas
    支持跨域的认证：JWT
缓存：未封装，采用 spring-cache
集群会话共享：未封装，采用 spring-session

----

cloud

