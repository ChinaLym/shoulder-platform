# shoulder-platform

USER-CENTER

用户中心，提供 注册、登录、注销、RBAC 权限管理、认证、授权

支持单点登录，Oauth2 给第三方授权，通过第三方 OIDC认证，通过第三方 Oauth2 登录

提供管理租户、appKey 等 API

分为以下模块

- Account
    - 提供账户能力与管理，包含 注册、登录、注销、RBAC 权限管理、认证、授权
- Authentication
    - 认证
- Authority
    - 授权
- Audit
    - 日志审计


## 领域模型
（个体/组织-组织信息）自然人0..1 - n **用户**1 - n业务载体（如资产:卡、画像、外部站点信息、业务权限等）

用户 1 - n 角色 1 - n 各种角色信息

用户 1 - 1..n 操作员 1..n - 1 通行证 1 - 1..n 通行证别名（供外部或扩展认证机制）

操作员 - 操作权限

通行证 1 - 1 同行密码
 


## 权限相关：

依赖 RBAC 模型，业务规则由编码实现。


#### Why NOT ABAC
ABAC 与 RBAC 相比，可以动态管理操作所需属性、环境因素，业务场景举例

`最近 15 天未出境` 且 `戴口罩` 的 `保安` 可以通过 `指纹`+`工牌` 在 `工作时间` `进入` `所属工作区` 的 `监控室`

- `保安` 是 角色
- `指纹`、`工牌` 是 认证令牌
- `视力好` 是 属性，可以动态调整，如 `user.lastExitTime - now() > 15 && user.withMask` 
- `工作时间` `所属工作区` 是环境因素，且可调整，如 `isWorkTime(now()) == true && resource.address == user.workplace`
- `进入` 是 动作
- `监控室` 是 资源

通过ABAC实现动态的权限配置过于复杂。需要制定自己的解析语言，且每次判断都要解析规则，对程序的性能也造成严重的影响，即使大规模使用缓存，命中的概率也是非常的小，毕竟太多很多因素都是动态的，需要每次都对规则进行解析并计算。


- [RBAC](https://zhuanlan.zhihu.com/p/98559681)
- [ACL, DAC, MAC, RBAC, ABAC模型的不同应用场景](https://zhuanlan.zhihu.com/p/70548562)
- [服务认证与鉴权](https://zhuanlan.zhihu.com/p/101595143)（适合权限数量较少，如OpenAPI）
- 服务认证：accessToken + refreshToken。前端每10分钟检查一下 refreshToken 过期时间，如果发现即将过期，提前申请 refreshToken

[gitee:六个高Star开源项目，让你更懂OAuth和单点登录](https://zhuanlan.zhihu.com/p/187131269)