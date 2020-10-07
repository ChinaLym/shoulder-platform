# shoulder-platform-starter-trace

统一链路追踪技术方案。

分布式链路追踪：[谷歌Dapper论文-中文](http://bigbully.github.io/Dapper-translation/)


选型指南，符合业界规范：OpenTracing；低入侵。


主流开源方案：

- [Twitter-Zipkin](https://zipkin.io/)
    - Twitter 提供。大规模应用，UI较弱
- [Skywalking](http://skywalking.apache.org/)
    - 国产优秀开源项目。高性能，shoulder 最初对接方案
- [美团点评-CAT](https://zipkin.io/)
    - 大众点评。UI强大，代码侵入，非 `OpenTracing`
- [Pinpoint](https://github.com/naver/pinpoint)
    - 韩国。记录数据详细、UI强大，非 `OpenTracing`


这里推荐 `Skywalking`，但其服务端会比其他两个消耗更多的资源（CPU、内存）。

由于这三者均可以做到代码无入侵，选型平滑切换。为了减少资源占用 Shoulder 默认选用了 `Zipkin`


注意事项：

- 涉及 websocket 时会产生一些问题
    - [github](https://github.com/spring-cloud/spring-cloud-sleuth/issues/276) 
    - [spring WebSocket 指南](https://spring.io/guides/gs/messaging-stomp-websocket/)
    - 最好指定下 `webjars-locator-core` 包的版本，不要使用`spring-boot-parent`管理的该版本，否则会出现 `http://localhost:8080/webjars/jquery/jquery.min.js` `404`问题。 

---
## 使用

```java

        @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
    

```

```properties
        spring.zipkin.baseUrl=http://xxx:9411/
        spring.zipkin.sender.type=web

        spring.rabbitmq.host=my.site
        spring.rabbitmq.username=zipkin
        spring.rabbitmq.password=zipkin
```


---

## 参考

https://www.jianshu.com/p/92a12de11f18