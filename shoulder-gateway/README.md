# shoulder-platform-gateway

- 对外网关
    - api-gateway 开放 api 网关
        > Oauth2 授权码模式，access-token 认证凭证
    - web-gateway web网关
        > 非前后分离 用户名密码登录、Session 作为认证凭证
        > 前后分离 Oauth2-授权码/密码模式认证、accessToken 作为认证凭证
    - app-gateway app网关
        > Oauth2-授权码/密码模式认证，access-token 认证凭证 
    - h5-gateway 小程序网关
        > Oauth2 简化模式，适合纯前端应用，且通信为 https
    - 注：这些网关用于隔离内部细节，虽然是系统对外服务的门面，但一般也不会直接暴露于外部网络，而是位于 nginx 等代理服务器之后，系统服务之前。

- 对内网关
    > Oauth2 客户端模式，access-token 认证凭证
    - biz-gateway 业务网关
    - storage-gateway 对象存储，统一存储
    - third-api-gateway 调用第三方接口过该网关，按照业务重要程度选择性单独部署

- 后台应用
    - 

           
                              i.        
                            .`   i.      
                          .`       `i      
                     ,.·`            i      
                .··`                  i     
           .··``           Spring     1     
         ·`                 ./        i     
        :                .//          i     
       (              ,.//           i      
       :            .##`           ,:      
        `·       .##i`           .:       
           `:.###:,           ,·`        
     .#.  :###::`   `--....-`         
     `·`  ```    
