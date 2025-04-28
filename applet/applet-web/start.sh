#!/bin/sh
pwd
ls -la
cp ./target/AppletApplication.jar /app/build/applet/web/
cp ./*.sh /app/build/applet/web/
cp ./Dockerfile /app/build/applet/web/