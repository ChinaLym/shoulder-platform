# shoulder-platform-gateway

- 对外网关
    - api-gateway 开放 api 网关
    - web-gateway web网关
    - app-gateway app网关
    - 注：这些网关用于隔离内部细节，虽然是系统对外服务的门面，但一般也不会直接暴露于外部网络，而是位于 nginx 等代理服务器之后，系统服务之前。

- 对内网关
    - biz-gateway 业务网关
    - storage-gateway 对象存储，统一存储
    - third-api-gateway 调用第三方接口过该网关，按照业务重要程度选择性单独部署


           
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
