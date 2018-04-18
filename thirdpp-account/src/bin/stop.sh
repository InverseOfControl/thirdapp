#!/bin/sh
#set -x


function usage(){
   echo "Usage: $0 appName.pid"
   echo "pid is the appName."
}

function stop(){
   if [ $# != 1 ]; then
      echo "Input error. Please input $0 appName.pid"
      exit 1
   fi

   if [ ! -f "$1" ]; then
      echo "pid file $1 does not exist"
      exit 1
   fi
   kill -9 `cat $1`
   rm -rf $1
   echo "Process closed."
}

stop $@

exit $?