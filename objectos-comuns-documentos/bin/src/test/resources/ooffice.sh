#!/bin/bash
#
# Copyright 2012 Objectos, Fábrica de Software LTDA.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not
# use this file except in compliance with the License. You may obtain a copy of
# the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations under
# the License.
#
# author ricardo.murad@objectos.com.br (Ricardo Murad)
#
export OOFFICE_EXEC=/usr/lib64/openoffice/program/soffice.bin
/usr/lib64/openoffice/program/soffice.bin -headless -nofirststartwizard -accept="socket,host=localhost,port=8100;urp;StarOffice.Service" &
#PID=$(pidof -o %PPID $OOFFICE_EXEC)
#case "$1" in
#  start)
#    stat_busy "Starting Net LibreOffice Daemon"
#    [ -z "$PID" ] && /usr/lib64/openoffice/program/soffice.bin -headless -nofirststartwizard -accept="socket,host=localhost,port=8100;urp;StarOffice.Service" &> /dev/null
#    if [ $? -gt 0 ]; then
#      echo "starty"
#    else
#      add_daemon ooffice-net
#      stat_done
#    fi
#    ;;
#  stop)
#    stat_busy "Stopping Net LibreOffice Daemon"
#    [ ! -z "$PID" ] && kill $PID &> /dev/null
#    if [ $? -gt 0 ]; then
#      stat_fail
#    else
#      rm_daemon ooffice-net
#      stat_done
#    fi
#    ;;
#  restart)
#    $0 stop
#    sleep 1
#    $0 start
#    ;;
#  *)
#    echo "usage: $0 {start|stop|restart}"
#esac
#exit 0
#