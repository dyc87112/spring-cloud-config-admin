# spring-cloud-config-admin（简称：SCCA）

[![Build Status](https://travis-ci.org/dyc87112/spring-cloud-config-admin.svg?branch=master)](https://travis-ci.org/dyc87112/spring-cloud-config-admin)

在Spring Cloud的微服务架构方案中虽然提供了Spring Cloud Config来担任配置中心的角色，但是该项目的功能在配置的管理层面还是非常欠缺的。初期我们可以依赖选取的配置存储系统（比如：Gitlab、Github）给我们提供的配置管理界面来操作所有的配置信息，但是这样的管理还是非常粗粒度的，因此这个项目的目的就是解决这个问题，希望提供一套基于Spring Cloud Config配置中心的可视化管理系统。

**项目地址**

- Github: https://github.com/dyc87112/spring-cloud-config-admin
- Gitee：https://gitee.com/didispace/spring-cloud-config-admin

- 前端Github: https://github.com/stone-jin/spring-cloud-config-admin-web
- 前端Gitee: https://gitee.com/stone-jin/spring-cloud-config-admin-web

# 架构

本项目采用了前后端分离的架构，通过core模块抽象了前端需要的操作，再通过persistence和discovery模块隔离不同的配置仓库和不同的服务注册中心，从而达到前端程序不需要关心到底使用了什么存储配置以及使用了什么注册中心，这样用户可以根据自己的需要自由的组合不同的配置存储和服务治理机制，尽可能的匹配大部分Spring Cloud用户的需求。

![](https://github.com/dyc87112/spring-cloud-config-admin/raw/master/statics/images/scca-arch.png)

# 特性

## 1.0.0

- 灵活易用的配置管理界面
- 具备对Spring Cloud Config基本元素以及为方便管理增加的管理元素的维护功能，包括：多环境管理、环境特有参数的维护、加密参数维护、项目维护、配置的多版本等
- 便捷常用操作，包括：加密解密功能、批量替换环境参数、批量加密等
- 支持多种配置编辑模式：列表格式、YAML格式、PROPERTIES格式
- 适配目前已经在使用Spring Cloud Config的用户（支持DB和Git存储）
- 模块可分离部署，支持多种不同场景的用户使用
- 兼容所有Spring Cloud支持的服务发现机制

## 1.1.0

规划中。欢迎提交ISSUE提出您的想法，我们会综合考虑纳入该版本的开发清单中！

# 文档

关于本项目的详细使用方法以及各版本的迭代内容可查看下面的内容：

- [使用手册](https://dyc87112.github.io/spring-cloud-config-admin/)
- [版本说明](https://dyc87112.github.io/spring-cloud-config-admin/release_notes.html)

# 贡献

- [程序猿DD](https://github.com/dyc87112)
- [stone-jin](https://github.com/stone-jin)
