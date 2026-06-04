#!/bin/sh

# ////////////////////////////////////////////
# ==============
cp ./aigateway/aigateway-web/target/app.jar /drone/src/aigateway/web
cp ./aigateway/aigateway-web/docker.sh /drone/src/aigateway/web
cp ./aigateway/aigateway-web/Dockerfile /drone/src/aigateway/web




# ////////////////////////////////////////////
# ==============
cp ./app/app-admin-web/target/app.jar /drone/src/app/admin
cp ./app/app-admin-web/docker.sh /drone/src/app/admin
cp ./app/app-admin-web/Dockerfile /drone/src/app/admin

# ==============
cp ./app/app-do-web/target/app.jar /drone/src/app/do
cp ./app/app-do-web/docker.sh /drone/src/app/do
cp ./app/app-do-web/Dockerfile /drone/src/app/do

# ==============
cp ./app/app-service-web/target/app.jar /drone/src/app/service
cp ./app/app-service-web/docker.sh /drone/src/app/service
cp ./app/app-service-web/Dockerfile /drone/src/app/service





# ////////////////////////////////////////////
# ==============
cp ./user/user-admin-web/target/app.jar /drone/src/user/admin
cp ./user/user-admin-web/docker.sh /drone/src/user/admin
cp ./user/user-admin-web/Dockerfile /drone/src/user/admin

# ==============
cp ./user/user-auth-web/target/app.jar /drone/src/user/auth
cp ./user/user-auth-web/docker.sh /drone/src/user/auth
cp ./user/user-auth-web/Dockerfile /drone/src/user/auth

# ==============
cp ./user/user-do-web/target/app.jar /drone/src/user/do
cp ./user/user-do-web/docker.sh /drone/src/user/do
cp ./user/user-do-web/Dockerfile /drone/src/user/do

# ==============
cp ./user/user-service-web/target/app.jar /drone/src/user/service
cp ./user/user-service-web/docker.sh /drone/src/user/service
cp ./user/user-service-web/Dockerfile /drone/src/user/service





# ////////////////////////////////////////////
# ==============
cp ./wx/wx-applet-do-web/target/app.jar /drone/src/wx-applet/do
cp ./wx/wx-applet-do-web/docker.sh /drone/src/wx-applet/do
cp ./wx/wx-applet-do-web/Dockerfile /drone/src/wx-applet/do

# ==============
cp ./wx/wx-common-do-web/target/app.jar /drone/src/wx-common/do
cp ./wx/wx-common-do-web/docker.sh /drone/src/wx-common/do
cp ./wx/wx-common-do-web/Dockerfile /drone/src/wx-common/do

# ==============
cp ./wx/wx-mp-admin-web/target/app.jar /drone/src/wx-mp/admin
cp ./wx/wx-mp-admin-web/docker.sh /drone/src/wx-mp/admin
cp ./wx/wx-mp-admin-web/Dockerfile /drone/src/wx-mp/admin

# ==============
cp ./wx/wx-mp-open-web/target/app.jar /drone/src/wx-mp/open
cp ./wx/wx-mp-open-web/docker.sh /drone/src/wx-mp/open
cp ./wx/wx-mp-open-web/Dockerfile /drone/src/wx-mp/open
