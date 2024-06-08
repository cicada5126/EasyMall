# Part3

商品添加-商品修改-搭建图片上传微服务

商品添加-商品修改Part2最后已经完成

图片上传搭建过程：

#### 1.搭建一个quikstart

搭建easymall-microservice-pic

2编辑pom文件

1)继承parent

2)依赖common-resources

#### 3.编辑application\.properties

> server.port=10002
>
> server.contextPath=/
>
> spring.application.name=picservice
>
> eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
>
> pic.pathDirPrefix=d://java//easymall_image/
>
> pic.urlPrefix=http://image.easymall.com/

 

#### **1.** 创建启动类(eureka客户端)

@EnableEurekaClient 

### 6.2.2根据接口文件完成图片上传功能

#### 1. 接口文件

![img](figs/Part3/upload_js.jpg) 

#### 2. 控制器层 

#### 3. Service层

1)判断后缀合法		

2判断是不是木马

3)创建以upload开始的路径

4)创建nginx访问的静态目录,pathDir, 通过配置文件中的变量pathDirPrefix 创建(D:\java\easymall_image***\*\upload\2\2\c\a\b\0\e\b\*\***)

5)创建urlPrefix （http://www.easymalll.com/upload/2/2/c/a/b/0/e/b/)		

6)拼接图片名称,将图片重命名 uuid表示图片存储访问的名称

7上传文件到磁盘路径

8)返回urlPrefix+图片名称的路径

![img](figs/Part3/upload_service.jpg) 

### 6.2.3 配置zuul网关路由 配置Nginx.conf的转发

#### 1.修改nginx配置文件

修改localhost /uploadImg指向zuul网关路径

![img](figs/Part3/upload_nginx.jpg) 

#### 2.定义zuul路由

zuul.routes.zuul-pic.path=/zuul-pic/**

zuul.routes.zuul-pic.serviceId=picservice

 

### 6.2.4 与页面整合测试

1.启动Eureka

2.启动zuul网关

3.启动商品系统

4.启动文件上传系统

5启动Nginx