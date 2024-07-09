# switchhost

首先需要使用switchhost配置本地内网ip到域名的跳转

简要代码可以如下：由于easymall有公开网站，为避免错误跳转，使用mall2替代easymall为主页的网站名。

`127.0.0.1 www.mall2.com
127.0.0.1 image.easymall.com`

在`switchhost`文件中，代码配置的这一部分：

```
127.0.0.1 image.easymall.com
```

意味着将本地回环地址`127.0.0.1`（也被称为localhost）映射到域名`image.easymall.com`。这通常用于本地开发环境，允许开发者在开发过程中使用`image.easymall.com`这个域名来访问本地服务器，而不是远程服务器。

具体来说：

- `127.0.0.1`：是一个特殊的IP地址，用于指向本地计算机。当使用这个IP地址时，数据包不会通过网络接口发送出去，而是直接在本机内部处理。
- `image.easymall.com`：是一个域名，通常指向一个远程服务器。在这个配置中，它被映射到本地回环地址，意味着任何指向`image.easymall.com`的请求都会被解析到本地计算机。

这种配置通常用于以下情况：

1. **本地开发**：开发者可以在开发过程中使用与生产环境相同的域名，但所有请求都会指向本地开发服务器。
2. **测试**：在测试环境中，可以模拟远程服务器的行为，同时确保所有请求都在本地处理，便于调试和测试。
3. **避免DNS污染**：在某些情况下，可能需要避免域名被DNS污染或劫持，通过本地hosts文件配置可以确保域名解析的准确性。

# nginx

```
server {
		listen       80;
		server_name  image.easymall.com;
		location / {
		       root D:\\java\\easymall_image;
		}
	}



server {
		listen 80;
		server_name www.mall2.com;
		location /{
			root easymall-static;
			index index.html;
		}	
		location /products {
			proxy_pass http://127.0.0.1:9005/zuul-product/product/manage;
			add_header 'Access-Control-Allow-Credentials' 'true';
			add_header 'Access-Control-Allow-Origin' '*'; 
		}
		location /user {
   		proxy_pass http://127.0.0.1:9005/zuul-user/user/manage;
   		add_header 'Access-Control-Allow-Credentials' 'true';
   		add_header 'Access-Control-Allow-Origin' '*'; 
  		}
  		location /cart {
  		 proxy_pass http://127.0.0.1:9005/zuul-cart/cart/manage;
   		add_header 'Access-Control-Allow-Credentials' 'true';
   		add_header 'Access-Control-Allow-Origin' '*'; 
  	}
  		location /order {
   		proxy_pass http://127.0.0.1:9005/zuul-order/order/manage;
   		add_header 'Access-Control-Allow-Credentials' 'true';
   		add_header 'Access-Control-Allow-Origin' '*'; 
  	}	
		location /uploadImg {
   		proxy_pass http://127.0.0.1:9005/zuul-pic/pic/upload;
   		add_header 'Access-Control-Allow-Credentials' 'true';
   		add_header 'Access-Control-Allow-Origin' '*'; 
 	 }	
		location /seckills {
		proxy_pass http://127.0.0.1:9005/zuul-seckill/seckill/manage;
		add_header 'Access-Control-Allow-Credentials' 'true';
		add_header 'Access-Control-Allow-Origin' '*'; 
	}
	}

```

> \1) 运行过程分析
>
> 起始访问地址，通过js:
>
> http://www.easymall.com/products/pageManage?page=1&rows=5
>
> http://www.easymall.com/products/pageManage;
>
> ***\*http://127.0.0.1:9005/zuul-product/product/manage/pageManage?page=1&rows=5\****
>
> |nginx监听 location匹配
>
> 将www.easymall.com/products去掉
>
> 剩下/pageManage;拼接proxy_pass的路径
>
> ***\*|\****nginx拼接转发后发送的地址
>
> http://localhost:9005/zuul-product/product/manage/pageManage
>
> ***\*|\****zuul网关接收到地址
>
> ***\*|\****满足路由匹配/zuul-product/**
>
> ***\*|\****路径过滤http://localhost:9005/zuul-prod/
>
> ***\*|\****拼接底层restTemplate发起向服务的请求
>
> http://productservice/product/manage/pageManage
>
> ***\*|\****经过ribbon抓取的服务list进行正确服务节点的拼接
>
> http://localhost:10001/product/manage/pageManage

测试请开启

\1. 启动nginx

\2. 启动两个Eureka注册中心

\3. 启动zuul

\4. 启动商品微服务

确保nginx域名与host相匹配

考虑使用http连接而非https