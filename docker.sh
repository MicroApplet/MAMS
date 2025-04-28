#!/bin/sh
pwd
ls -la
# applet
cd ./applet || exit
sh docker.sh
cd ../