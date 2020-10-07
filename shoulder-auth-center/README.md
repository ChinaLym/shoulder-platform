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



权限相关：
- [RBAC](https://zhuanlan.zhihu.com/p/98559681)
- [ACL, DAC, MAC, RBAC, ABAC模型的不同应用场景](https://zhuanlan.zhihu.com/p/70548562)
- [服务认证与鉴权](https://zhuanlan.zhihu.com/p/101595143)（适合权限数量较少，如OpenAPI）
- 服务认证：accessToken + refreshToken。前端每10分钟检查一下 refreshToken 过期时间，如果发现即将过期，提前申请 refreshToken

[gitee:六个高Star开源项目，让你更懂OAuth和单点登录](https://zhuanlan.zhihu.com/p/187131269)