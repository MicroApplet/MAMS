#!/bin/sh

# applet/applet-web
mkdir -p /app/build/applet/web
# shellcheck disable=SC2164
cd ./applet/applet-web
sh start.sh
# shellcheck disable=SC2164
cd ~
rm -rf /app/build/applet/web

# channel/channel-wechat/channel-wechat-official/channel-wechat-official-web
mkdir -p /app/build/channel/wechat/official/web
# shellcheck disable=SC2164
cd ./channel/channel-wechat/channel-wechat-official/channel-wechat-official-web
sh start.sh
# shellcheck disable=SC2164
cd ~
rm -rf /app/build/channel/wechat/official/web


# user/user-web
mkdir -p /app/build/user/web
# shellcheck disable=SC2164
cd ./user/user-web
sh start.sh
# shellcheck disable=SC2164
cd ~
rm -rf /app/build/user/web
