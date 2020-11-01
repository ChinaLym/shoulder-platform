# shoulder-user-center

基于 Spring Security 的 Oauth2 认证授权中心，可作为单点登录服务器。


## 工程目录结构：

![目录结构](img/projectAndModule.png)

---

## 扩展
 
### 单点登录 SSO

`SSO` 是一种思想，或者说是一种抽象的解决方案，可以通过它实现：用户可以无感知地在不同系统间访问受保护资源，而不必因访问不同系统而登录不同的账号。

![sso](img/sso.png)

(图片来源于网络，侵删)

由于 `SSO` 并没有强制要求使用者如何实现认证，因此，可以通过不同的手段来达到该墓地，比如可以通过 Oauth2、JWT、Session 等。

### Oauth2


