#!/bin/sh

# applet
mkdir /app/build/applet/web
# shellcheck disable=SC2164
cd ./applet/applet-web
sh start.sh
rm -rf /app/build/applet/web

