#!/bin/sh
pwd

# applet
mkdir /app/build/applet
# shellcheck disable=SC2164
cd ./applet
sh start.sh
cd ../

