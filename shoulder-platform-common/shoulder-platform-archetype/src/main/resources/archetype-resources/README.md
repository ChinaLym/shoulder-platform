# ${rootArtifactId}

工程目录结构：

![目录结构](img/projectAndModule.png)

---

# 常见报错

## 启动后报 dataId invalid 

报错内容如下：
```bash
com.alibaba.nacos.api.exception.NacosException: dataId invalid
	at com.alibaba.nacos.client.config.utils.ParamUtils.checkKeyParam(ParamUtils.java:90)
	at com.alibaba.nacos.client.config.NacosConfigService.getConfigInner(NacosConfigService.java:130)
	at com.alibaba.nacos.client.config.NacosConfigService.getConfig(NacosConfigService.java:97)
	at com.alibaba.cloud.nacos.client.NacosPropertySourceBuilder.loadNacosData(NacosPropertySourceBuilder.java:85)
	at com.alibaba.cloud.nacos.client.NacosPropertySourceBuilder.build(NacosPropertySourceBuilder.java:74)
	at com.alibaba.cloud.nacos.client.NacosPropertySourceLocator.loadNacosPropertySource(NacosPropertySourceLocator.java:204)
	at com.alibaba.cloud.nacos.client.NacosPropertySourceLocator.loadNacosDataIfPresent(NacosPropertySourceLocator.java:191)
	at com.alibaba.cloud.nacos.client.NacosPropertySourceLocator.loadApplicationConfiguration(NacosPropertySourceLocator.java:150)
	at com.alibaba.cloud.nacos.client.NacosPropertySourceLocator.locate(NacosPropertySourceLocator.java:103)
	at org.springframework.cloud.bootstrap.config.PropertySourceLocator.locateCollection(PropertySourceLocator.java:52)
	at org.springframework.cloud.bootstrap.config.PropertySourceLocator.locateCollection(PropertySourceLocator.java:47)
	at org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration.initialize(PropertySourceBootstrapConfiguration.java:98)
	at org.springframework.boot.SpringApplication.applyInitializers(SpringApplication.java:626)
	at org.springframework.boot.SpringApplication.prepareContext(SpringApplication.java:370)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:314)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1237)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1226)
```

在 `ParamUtils.checkKeyParam(ParamUtils.java:90)` 打断点 debug 可看是加载了 nacos 哪个配置。

默认是 appId-profile（默认 profile 是 `dev`）。

查看nacos中是否有对应的配置文件，如果没有则在配置中心添加或 bootstrap.yml 删除声明需要加载的配置项即可。

