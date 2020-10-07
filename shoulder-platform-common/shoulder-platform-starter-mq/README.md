# shoulder-platform-starter-mq

包含如 `RabbitMQ`、`spring cloud stream`等

MQ 技术选型：
- ~~ActiveMQ~~
    - Java开发、符合 Jms、且功能全面，对于小型项目足够
    - 但由于使用的项目越来越少，从长远角度不看好
    - 若原使用该技术，推荐替换为 `RabbitMQ` 或 `RocketMQ`
- RabbitMQ
    - 使用广泛，功能全面，默认选型
- KafKa、RocketMQ
    - 适用于堆积场景，大数据量场景，如追踪日志、监控日志、操作日志等数据流
    - 其中 KafKa 由于使用广泛且诞生较早，第三方支持更全面，更推荐
    - RocketMQ 使用 Java 编写，更适合维护团队偏 Java 的系统，
- Plusar
    - 高性能，性能随节点数线性扩展
    - 功能强大支持租户
    - 较新，文档和经验较少，准备选型
    