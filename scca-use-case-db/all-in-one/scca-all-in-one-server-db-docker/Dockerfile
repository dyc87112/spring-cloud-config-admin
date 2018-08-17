FROM registry.cn-hangzhou.aliyuncs.com/micro-java/openjdk:8-jre-alpine

MAINTAINER 545544032@qq.com

ENV TZ="Asia/Shanghai" JVM_PARAMS=" " SPRING_PARAMS=" "

ADD target/*.jar /server.jar

EXPOSE 8080

CMD java $JVM_PARAMS -Djava.security.egd=file:/dev/./urandom -jar /server.jar $SPRING_PARAMS