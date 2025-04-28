#!/bin/sh
# applet
# shellcheck disable=SC2164
cd /app/build/applet/web
sh docker.sh
rm -rf /app/build/applet/web

# channel/wx-official
# shellcheck disable=SC2164
cd /app/build/channel/wechat/official/web
sh docker.sh
rm -rf /app/build/channel/wechat/official/web

# user/user-web
# shellcheck disable=SC2164
cd /app/build/user/web
sh docker.sh
rm -rf /app/build/user/web