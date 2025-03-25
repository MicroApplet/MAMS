#!/bin/sh
# 定义应用组名
group_name='mams'
# 定义应用名称
app_name='user-auth'
# 定义应用版本
app_version='latest'
docker stop ${app_name}
docker rm ${app_name}
docker rmi ${group_name}/${app_name}:${app_version}

pwd
ls -la
chmod +r mams.env
# 打包编译docker镜像
docker build -t ${group_name}/${app_name}:${app_version} .
#docker run -p 10001:10001 --name ${app_name} \
#--env-file mams.env \
#-e TZ="Asia/Shanghai" \
#-v /etc/localtime:/etc/localtime \
#-d ${group_name}/${app_name}:${app_version}