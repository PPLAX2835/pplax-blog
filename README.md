# PPLAX-Blog

个人开发的一个博客项目，因为看见别人的博客项目，就觉得很厉害，然后自己也想整一个（当前部署不了，租的服务器太拉...）

目前做到了Spring Gateway + Oauth2的统一登录认证、权限及菜单动态分配、mysql与缓存相关的优化、统一异常处理、消息队列对评论等相关数据的处理、restful风格的api等

## 页面图片

后台

​![image](assets/image-20240503204942-z6txsse.png)​

前台

​![image](assets/image-20240503205008-7plb5q8.png)​

就展示这点吧

## 用到的技术

### 后端

​`Spring Cloud Alibaba`​ `Spring Boot`​  `My batis-plus`​ `Spring Security Oauth2`​ `Spring Cloud Gateway`​ `druid`​ 等等吧

主要参考了

[蘑菇博客: 蘑菇博客(MoguBlog)，一个基于微服务架构的前后端分离博客系统。Web端使用Vue + Element , 移动端使用uniapp和ColorUI。后端使用Spring cloud + Spring boot + mybatis-plus进行开发，使用 Jwt + Spring Security做登录验证和权限校验，使用ElasticSearch和Solr作为全文检索服务，使用Github Actions完成博客的持续集成，使用ELK收集博客日志，文件支持上传七牛云和Minio，支持Docker Compose脚本一键部署。 (gitee.com)](https://gitee.com/moxi159753/mogu_blog_v2)

[RuoYi-Cloud: 🎉 基于Spring Boot、Spring Cloud &amp; Alibaba的分布式微服务架构权限管理系统，同时提供了 Vue3 的版本 (gitee.com)](https://gitee.com/y_project/RuoYi-Cloud)

[GitHub - xcyeye/aurora-blog: 🔥Aurora博客是一个基于Spring Cloud Alibaba的多人微服务博客项目，前台和后台界面非常漂亮，特征：邮箱链接验证、账户锁定等邮件功能。前端技术：TypeScript + Vue3 + Pinia + NaiveUi，后端技术：Spring Cloud Alibaba + RabbitMq + Seata + Oauth2。](https://github.com/xcyeye/aurora-blog)

等大佬的项目

### 前端

​`vue2`​ `element-ui`​

前台使用了[拾壹博客: 一款vue+springboot前后端分离的博客系统，博客后台管理系统使用了vue+elmentui开发，后端使用Sa-Token进行权限管理,支持动态菜单权限，动态定时任务，文件支持本地和七牛云上传，使用ElasticSearch作为全文检索服务，支持QQ、微信公众号扫码、码云、GitHub登录。实现即时通讯聊天室功能 (gitee.com)](https://gitee.com/quequnlong/shiyi-blog)的前台项目（非常感谢）

‍

## 如何跑起来

### 环境依赖

​`MySQL 8`​ `rabbitmq 3.9.1`​ `nacos-server 2.2.0`​ `minio`​ `nodejs 12.22.12`​ `Java 8`​ 大概是这些

#### docker-compose

```bash
# MySQL 8 可参考： https://hub.docker.com/_/mysqlersion: '3'
services:
  mysql:
    image: registry.cn-hangzhou.aliyuncs.com/zhengqing/mysql:8.0  # 原镜像`mysql:8.0`
    container_name: mysql8                                    # 容器名为'mysql8'
    restart: unless-stopped                                               # 指定容器退出后的重启策略为始终重启，但是不考虑在Docker守护进程启动时就已经停止了的容器
    volumes:                                                      # 数据卷挂载路径设置,将本机目录映射到容器目录
      - "./mysql/my.cnf:/etc/mysql/my.cnf"
      - "./mysql/data:/var/lib/mysql"
#      - "./mysql/conf.d:/etc/mysql/conf.d"
      - "./mysql/mysql-files:/var/lib/mysql-files"
    environment:                        # 设置环境变量,相当于docker run命令中的-e
      TZ: Asia/Shanghai
      LANG: en_US.UTF-8
      MYSQL_ROOT_PASSWORD: root         # 设置root用户密码
      MYSQL_DATABASE: demo              # 初始化的数据库名称
    privileged: true
    user: root
    ports:                              # 映射端口
      - "3306:3306"

```

```bash
# docker-compose参考：https://github.com/nacos-group/nacos-docker/blob/master/example/standalone-mysql-5.7.yaml
# Nacos文档：https://nacos.io/zh-cn/index.html
version: '3'

# 网桥 -> 方便相互通讯
networks:
  nacos:
    driver: bridge

services:
  nacos:
    image: registry.cn-hangzhou.aliyuncs.com/zhengqing/nacos-server:2.2.0    # 原镜像`nacos/nacos-server:2.2.0`
    container_name: nacos_server                                 # 容器名为'nacos_server'
    restart: unless-stopped                                              # 指定容器退出后的重启策略为始终重启，但是不考虑在Docker守护进程启动时就已经停止了的容器
    volumes:                                                     # 数据卷挂载路径设置,将本机目录映射到容器目录
      - "./nacos/logs:/home/nacos/logs"
    environment:                        # 设置环境变量,相当于docker run命令中的-e
      - PREFER_HOST_MODE=hostname                 # 如果支持主机名可以使用hostname,否则使用ip，默认也是ip
      - MODE=standalone                           # 单机模式启动
      - SPRING_DATASOURCE_PLATFORM=mysql          # 数据源平台 仅支持mysql或不保存empty
      # TODO 修改mysql连接信息
      - MYSQL_SERVICE_HOST=192.168.32.1          # 注：这里不能为`127.0.0.1`或`localhost`方式！！！
      - MYSQL_SERVICE_DB_NAME=nacos_config        # 这里需要执行nacos_config.sql
      - MYSQL_SERVICE_PORT=3306
      - MYSQL_SERVICE_USER=root
      - MYSQL_SERVICE_PASSWORD=root
      # TODO 修改JVM调优参数
      - JVM_XMS=128m   #-Xms default :2g
      - JVM_XMX=128m   #-Xmx default :2g
      - JVM_XMN=64m    #-Xmn default :1g
      - JVM_MS=32m     #-XX:MetaspaceSize default :128m
      - JVM_MMS=32m    #-XX:MaxMetaspaceSize default :320m
    ports:
      - "8848:8848"
    networks:
      - nacos
    mem_limit: 1000m   # 最大使用内存

```

```bash
# 环境变量可参考： https://www.rabbitmq.com/configure.html
#               https://github.com/rabbitmq/rabbitmq-server/blob/master/deps/rabbit/docs/rabbitmq.conf.example
version: '3'
services:
  rabbitmq:
    image: registry.cn-hangzhou.aliyuncs.com/zhengqing/rabbitmq:3.9.1-management        # 镜像`rabbitmq:3.9.1-management` 【 注：该版本包含了web控制页面 】
    container_name: rabbitmq            # 容器名为'rabbitmq'
    hostname: my-rabbit
    restart: unless-stopped                                       # 指定容器退出后的重启策略为始终重启，但是不考虑在Docker守护进程启动时就已经停止了的容器
    environment:                        # 设置环境变量,相当于docker run命令中的-e
      TZ: Asia/Shanghai
      LANG: en_US.UTF-8
    volumes:                            # 数据卷挂载路径设置,将本机目录映射到容器目录
      - "./rabbitmq/config/rabbitmq.conf:/etc/rabbitmq/rabbitmq.conf"
      - "./rabbitmq/config/10-default-guest-user.conf:/etc/rabbitmq/conf.d/10-default-guest-user.conf"
      - "./rabbitmq/data:/var/lib/rabbitmq"
      - "./rabbitmq/plugins/rabbitmq_delayed_message_exchange-3.9.0.ez:/opt/rabbitmq/plugins/rabbitmq_delayed_message_exchange-3.9.0.ez"
#      - "./rabbitmq/log:/var/log/rabbitmq"
    ports:                              # 映射端口
      - "5672:5672"
      - "15672:15672"

```

```bash
# 可参考 https://docs.min.io/docs/minio-docker-quickstart-guide.html
version: '3'
services:
  minio:
    image: minio/minio:latest                                    # 原镜像`minio/minio:latest`
    container_name: minio                                        # 容器名为'minio'
    restart: unless-stopped                                              # 指定容器退出后的重启策略为始终重启，但是不考虑在Docker守护进程启动时就已经停止了的容器
    volumes:                                                     # 数据卷挂载路径设置,将本机目录映射到容器目录
      - "./minio/data:/data"
      - "./minio/minio:/minio"
      - "./minio/config:/root/.minio"
    environment:                                      # 设置环境变量,相当于docker run命令中的-e
      TZ: Asia/Shanghai
      LANG: en_US.UTF-8
      MINIO_PROMETHEUS_AUTH_TYPE: "public"
      MINIO_ACCESS_KEY: "root"                        # 登录账号
      MINIO_SECRET_KEY: "password"                    # 登录密码
    command: server /data  --console-address ":9001"
    logging:
      driver: "json-file"
      options:
        max-size: "100m"
    ports:                              # 映射端口
      - "9002:9000" # 文件上传&预览端口
      - "9001:9001" # 控制台访问端口

```

#### 用到的sql脚本

分别创建对应文件名的数据库，然后分别执行就好

[nacos_config.sql](assets/nacos_config-20240503210201-w0t84y5.sql)

[pplax_blog.sql](assets/pplax_blog-20240503210201-9ehnzum.sql)
