# spring-cloud-config-admin（简称：SCCA）

[![Build Status](https://travis-ci.org/dyc87112/spring-cloud-config-admin.svg?branch=master)](https://travis-ci.org/dyc87112/spring-cloud-config-admin)

在Spring Cloud的微服务架构方案中虽然提供了Spring Cloud Config来担任配置中心的角色，但是该项目的功能在配置的管理层面还是非常欠缺的。初期我们可以依赖选取的配置存储系统（比如：Gitlab、Github）给我们提供的配置管理界面来操作所有的配置信息，但是这样的管理还是非常粗粒度的，因此这个项目的目的就是解决这个问题，希望提供一套基于Spring Cloud Config配置中心的可视化管理系统。

**项目地址**

- Github: https://github.com/dyc87112/spring-cloud-config-admin
- Gitee：https://gitee.com/didispace/spring-cloud-config-admin

- 前端Github: https://github.com/stone-jin/spring-cloud-config-admin-web
- 前端Gitee: https://gitee.com/stone-jin/spring-cloud-config-admin-web

# 使用手册

查看详细点击：[《使用手册》](https://dyc87112.github.io/spring-cloud-config-admin/)

# 路线图

**[Release Notes](https://dyc87112.github.io/spring-cloud-config-admin/release_notes.html)**

- v1.0.0：实现基础版配置管理功能：实现环境管理、环境参数管理、项目管理、配置管理、对配置信息的加密解密、环境配置等基础功能。对于服务治理上支持eureka和consul、配置存储上支持git和db。
- v1.1.0：待细化：实现用户管理、操作审计、反向操作工具（从git导入项目、配置等）、批量操作工具（创建环境同步管理项目等便捷操作）

# 贡献者

- [程序猿DD](https://github.com/dyc87112)
- [stone-jin](https://github.com/stone-jin)
