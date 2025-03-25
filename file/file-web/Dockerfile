#指定基础镜像(一般指定程序所依赖的环境)
#本例子是构建SpringBoot所以我指定的jdk环境变量
FROM openjdk:8

#将./hello-world.jar添加到容器的/opt目录中
COPY ./*.jar /opt

#指定dockerfile的命令在哪个目录下执行
WORKDIR /opt

#指定容器和外界交互的端口
EXPOSE 10002

#容器运行后执行的命令(该命令在WORKDIR指定的工作目录下执行)
#注意：多个CMD指令只有最后一个会生效
CMD nohup java -jar *.jar /root/logs/output.log 2>&1 &

#在构建镜像时执行的命令
#我这边用它来打印java的环境变量
RUN java -version

#添加该镜像的元数据
LABEL version="latest" description="文件服务" by="Asial Jim"