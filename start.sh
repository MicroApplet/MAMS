#!/bin/sh


# ////////////////////////////////////////////
# ==============
sh /drone/src/aigateway/service/docker.sh




# ////////////////////////////////////////////
# ==============
#sh /drone/src/app/admin/docker.sh

# ==============
sh /drone/src/app/do/docker.sh

# ==============
sh /drone/src/app/service/docker.sh




# ////////////////////////////////////////////
# ==============
sh /drone/src/gwadmin/service/docker.sh



# ////////////////////////////////////////////
# ==============
#sh /drone/src/user/admin/docker.sh

# ==============
#sh /drone/src/user/auth/docker.sh

# ==============
sh /drone/src/user/do/docker.sh

# ==============
sh /drone/src/user/service/docker.sh





# ////////////////////////////////////////////
# ==============
sh /drone/src/wx-applet/do/docker.sh

# ==============
sh /drone/src/wx-common/do/docker.sh

# ==============
#sh /drone/src/wx-mp/admin/docker.sh

# ==============
#sh /drone/src/wx-mp/open/docker.sh

# ==============
sh /drone/src/wx/service/docker.sh