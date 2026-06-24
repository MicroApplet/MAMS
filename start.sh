#!/bin/sh


#
# Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

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