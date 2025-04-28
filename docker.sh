#!/bin/sh
pwd
ls -la
cat applet
# applet
cd ./applet || exit
sh docker.sh
cd ../