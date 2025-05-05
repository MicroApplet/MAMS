#指定基础镜像
FROM openjdk:8u121-jre-alpine
EXPOSE 80
COPY ./UserApplication.jar /
# 赋予执行权限
RUN chmod 755 -R /UserApplication.jar
# 设置
ENTRYPOINT ["java","-Xms256m -Xmx1g -jar","/UserApplication.jar"]
MAINTAINER AsialJim