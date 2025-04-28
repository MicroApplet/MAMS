#!/bin/sh

# applet/applet-web
mkdir /app/build/applet/web
# shellcheck disable=SC2164
cd ./applet/applet-web
sh start.sh
# shellcheck disable=SC2164
cd ~
rm -rf /app/build/applet/web

# channel/channel-wechat/channel-wechat-official/channel-wechat-official-web
mkdir /app/build/channel/wechat/official/web
# shellcheck disable=SC2164
cd ./channel/channel-wechat/channel-wechat-official/channel-wechat-official-web
sh start.sh
# shellcheck disable=SC2164
cd ~
rm -rf /app/build/channel/wechat/official/web

