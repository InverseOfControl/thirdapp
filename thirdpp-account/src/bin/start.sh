#!/bin/sh 
#set -x
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf
CLASSES_DIR=$DEPLOY_DIR/classes
LIB_DIR=$DEPLOY_DIR/lib


config_filepath=$2
tmp=
log_filepath=$BIN_DIR/run.log
jar_path=

echo "config_filepath:" $config_filepath
echo "log_filepath: " $log_filepath
function usage(){
   echo "Usage: $0 -f filepath"
   echo "filepath is the server config file."
}

function start(){
   echo "parameter size:"$# 
   if [ $# != 2 ]; then
      echo "Input error. Please input $0 -f filepath"
      exit 1
   fi
   
   tmp=`jps -ml | grep "com.zendaimoney.thirdpp.account"|grep $config_filepath | wc -l`
   echo "tep=" $tmp
     
   if [ $tmp -eq 1 ]; then
      if [ ! -f "$log_filepath" ]; then
         touch $log_filepath
      fi
      echo "tpp account already started..."
      exit 1
   fi 
   
   echo "tpp account is starting..."

   LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
   echo $LIB_JARS >"$log_filepath" &
   java -classpath $CONF_DIR:$CLASSES_DIR:$LIB_JARS com.zendaimoney.thirdpp.account.ServerStartup $1 $config_filepath > "$log_filepath" &
   appName=`grep "appName" $config_filepath | sed -e 's/.*=//'`
   sleep 10
   tmp=`jps -ml | grep "com.zendaimoney.thirdpp.account"|grep $config_filepath | wc -l`
   if [ $tmp -eq 1 ]; then
      echo "appName="$appName
      echo "$!" > $BIN_DIR/"$appName".pid
      echo "tpp account start sucessful..." 
      echo "$appName pid is `cat $BIN_DIR/$appName.pid`"
   fi 
}

case $1 in 
   -f) 
      start $@ 
   ;;
   *) 
      usage 
    ;; 
esac 

exit $? 
