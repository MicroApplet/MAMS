#!/bin/sh
pwd
ls -la
cd target
pwd
ls -la
cp ./target/UserApplication.jar /app/build/user/web/
cp ./*.sh /app/build/user/web/
cp ./Dockerfile /app/build/user/web/