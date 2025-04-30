#!/bin/sh
# 定义应用组名
group_name='asialjim'
# 定义应用名称 ,这里的name是获取你仓库的名称，也可以自己写
app_name='mams-chl-wx-official'
docker_name='aj-mams-chl-wx-official'
# 定义应用版本
app_version='latest'
# 定义应用环境
profile_active='prod'

echo '发布微信公众号开始...'
docker stop ${docker_name}

docker rm ${docker_name}

docker rmi ${group_name}/${app_name}:${app_version}

docker build -t ${group_name}/${app_name}:${app_version} .

docker run --name ${docker_name} \
--network api \
--cpus="2" --memory="1024m" \
--env-file /root/.env/mams.env \
-e 'spring.profiles.active'=${profile_active} \
-e TZ="Asia/Shanghai" \
-v /etc/localtime:/etc/localtime \
-v /home/asialjim/.app/docker/github/MicroBank/${app_name}/logs:/var/logs \
-d ${group_name}/${app_name}:${app_version}


echo '发布微信公众号结束...'
