#!/bin/sh
pwd
ls -la
cp ./target/WeChatOfficialChlApplication.jar /app/build/user/web/
cp ./*.sh /app/build/user/web/
cp ./Dockerfile /app/build/user/web/