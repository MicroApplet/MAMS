#!/bin/sh

# applet/applet-web
# shellcheck disable=SC2164
cd /drone/src
mkdir -p /app/build/applet/web
# shellcheck disable=SC2164
cd ./applet/applet-web
sh start.sh


# channel/channel-wechat/channel-wechat-official/channel-wechat-official-web
# shellcheck disable=SC2164
cd /drone/src
mkdir -p /app/build/channel/wechat/official/web
# shellcheck disable=SC2164
cd ./channel/channel-wechat/channel-wechat-official/channel-wechat-official-web
sh start.sh


# user/user-web
# shellcheck disable=SC2164
cd /drone/src
mkdir -p /app/build/user/web
# shellcheck disable=SC2164
cd ./user/user-web
sh start.sh