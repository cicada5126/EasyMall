# Part7:登录顶替-商品缓存-搭建高可用的配置中心-搭建购物车系统]

## 搭建高可用的配置中心

config-server工程

Spring cloud config是springcloud为共享配置的使用提供的一个组件,可以实现从远程库读取配置文件,交给连接访问他的所有客户端使用,根据读取文件的结构和规则,客户端可以选择使用哪些配置文件.

![image-20240609230527163](figs/Part7/config_center.png)