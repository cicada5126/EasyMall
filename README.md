# EasyMall
Java project 学校高级实作课程模板项目：电商系统  EasyMall。

从课程学习角度出发，整理相关步骤和概念。



项目开发IDE信息：

Eclipse Java EE IDE for Web Developers.

Version: Neon.3 Release (4.6.3)

or:

IntelliJ IDEA Community Edition 2024.1.1



IDE配置：

java JDK : jdk1.8.0_101

maven仓库：ali_repo

版本maven3.6

------

各Part学习任务：

1. [SpringCloud介绍、项目基本配置](Part1.md)

2. [搭建商品系统微服务](Part2.md)

3. [商品添加-商品修改-搭建图片上传微服务](Part3.md)

4. [搭建用户系统（注册-登录) Redis概述、安装、Redis五种数据类型及其命令](Part4.md)

5. [部署多实例Redis-商品数据的缓存使用-分片连接池-登录功能整合Redis](Part5.md)

6. [Redis高可用 搭建Redis集群 JedisCluster操作集群](Part6.md)

7. [登录顶替-商品缓存-搭建高可用的配置中心-搭建购物车系统](Part7.md)

8. [MySQL主从复制 MySQL双机热备、mycat读写分离、单分片、多分片、全局表、ER分片表](Part8.md)

9. [Mycat的ER分片表-搭建订单系统微服务](Part9.md)

10. [消息队列的引入，Rabbit的搭建和配置，Springboot整合RabbitMQ ，秒杀功能的实现](Part10.md)

11. [nginx和host配置](nginx和host配置.md)

    ------

> 由于没有保存各Part学习任务的代码，无法进行对应的运行。
>
> 尝试学习完最主要的部分，梳理这套代码的主要功能和技术点。

项目运行指南，完成IDE的基本配置如java和maven后，以IDEA为例：

点击pom.xml文件添加为maven项目

![image-20240709165827471](../../AppData/Roaming/Typora/typora-user-images/image-20240709165827471.png)

执行maven生命周期的clean和install安装依赖查看是否正常。

![image-20240709165939019](../../AppData/Roaming/Typora/typora-user-images/image-20240709165939019.png)

确保项目的依赖选项如configserver、两个eurake；服务器数据库mycat、JedisCluster等已经成功开启后点击Stater进行启动项目

![image-20240709170153610](../../AppData/Roaming/Typora/typora-user-images/image-20240709170153610.png)
