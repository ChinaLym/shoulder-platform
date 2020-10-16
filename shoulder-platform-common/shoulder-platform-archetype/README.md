# shoulder-platform-archetype

基于 `shoulder-platform` 的多模块的 `maven` 骨架工程，适合基于 spring cloud 的微服务模式的微服务工程。

## 诞生背景

项目中大都是分模块开发，一个模块一个工程，通过一个外层工程管理所有模块，充分利用maven的`聚合`、`继承`的特性来减少项目管理的成本。

> 聚合是为了管理多模块build，继承是方便依赖管理

目标是选择 `shoulder-platform-archetype` 便自动创建一个父模块，以及多个子模块，父模块既是聚合也可提供依赖继承。


## 保姆级使用介绍

下面以 IDEA 为例，介绍如何通过 `shoulder-platform-archetype` 快速创建一个引入了`shoulder`的 spring boot web 工程

### 添加 shoulder 的 archetype

第一次使用时需要添加，以后都不用这一步咯，`version` 不同，创建的也可能不同哦

```
groupId     cn.itlym.platform
artifactId  shoulder-platform-archetype
version     1.0-SNAPSHOT
```

![添加 shoulder 的 archetype](../doc/img/archetype/idea/add.png)


### 基于 shoulder 提供的模板创建 maven 工程

#### 选择shoulder
![选择shoulder](../doc/img/archetype/idea/1.png)

#### 输入 gourpId、artifactId
![输入 gourpId、artifactId](../doc/img/archetype/idea/2.png)

#### 输入覆盖模板的值
![输入覆盖模板的值](../doc/img/archetype/idea/3.png)
确保 `rootArtifactId` 和 `appId` 相同

#### 完成创建
![完成创建](../doc/img/archetype/idea/4.png)

#### 等待创建完毕
![等待创建完毕](../doc/img/archetype/idea/5.png)
- 如果不设置缓存，这一步因访问maven官网，可能会比较慢，解决方式参考 [IDEA 创建maven工程 create from archetype 很慢](https://blog.csdn.net/qq_35425070/article/details/108958087)
- 创建完毕后，我们需要 reimport maven 依赖，这里我们直接点击自动更新

#### 启动运行
![启动运行](../doc/img/archetype/idea/6.png)

可以访问 DemoController [http://localhost:8080/demo/test](http://localhost:8080/demo/test) 查看一下


---

## 模板属性表
|属性 key | 说明 | 默认值 |
|----|----|----|
| appId | 应用/服务标识 | 使用 `${rootArtifactId}` 值 |
| package | 包路径 | `${groupId}.${appId}` |
| contextPath | 上下文路径 | `${appId}` |
| StartClassName | 启动类名 | ShoulderApplication |
| author | 作者名 | shoulder |


---

## DDD 介绍

* https://zhuanlan.zhihu.com/p/77311830
* https://www.cnblogs.com/daoqidelv/p/7499244.html