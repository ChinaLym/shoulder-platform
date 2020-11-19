# shoulder-platform-common

Shoulder 平台各个工程的`基础设施层`统一实现（为了简化使用者调试、该工程的模块需要发布至`maven`仓库）

## 工程模块图如下

![工程结构.png](../img/archetype/projectAndModule.png)


* 本模块提供了基础中间件的选型与集成，目的：统一技术栈选型。
* 平台中不同系统必然存在重复的工作（中间件对接、依赖管理与维护、系统级规范与约定）在这里统一实现
* 平台项目中不应引入本模块（`shoulder-platform-common`）以外的`jar`，以保证整个平台的安全，且即使只有一个人也容易维护。
* 本工程内各个模块相当于 `shoulder-framework` 的常用 `starter`，类似 `spring-cloud-starter-xxx`
* 为了便于单人也能快速维护、未专门创新新仓库维护这些，且未发布至`maven`中央仓库。

----

## 统一更换 shoulder-framework 版本
```xml
<shoulder.version>0.5-SNAPSHOT</shoulder.version><!-- shoulder-version -->
<version>0.5-SNAPSHOT</version><!-- shoulder-version -->
```

## 统一更换 shoulder-platform-common 版本
```xml
<version>1.0-SNAPSHOT</version><!-- shoulder-platform-version -->
<shoulder-platform.version>1.0-SNAPSHOT</shoulder-platform.version><!-- shoulder-platform-version -->
```