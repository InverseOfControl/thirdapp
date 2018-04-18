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
function usage(){
   echo "Usage: $0 -f filepath"
   echo "filepath is the server config file."
}

function start(){
   if [ $# != 2 ]; then
      echo "Input error. Please input $0 -f filepath"
      exit 1
   fi
   
   tmp=`jps -ml| grep "com.zendaimoney.thirdpp.transfer"|grep $config_filepath | wc -l`  
   if [ $tmp -eq 1 ]; then
      if [ ! -f "$log_filepath" ]; then
         touch $log_filepath
      fi
      echo "tpp transfer already started..."
      exit 1
   fi 
   
    JAVA_MEM_OPTS=""
	BITS=`java -version 2>&1 | grep -i 64-bit`
	if [ -n "$BITS" ]; then
	    JAVA_MEM_OPTS=" -server -Xmx2g -Xms2g -Xmn512m -XX:PermSize=64m -XX:MaxPermSize=128m -XX:MaxTenuringThreshold=0 -Xss256k -XX:MaxDirectMemorySize=128m -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
	else
	    JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
	fi

   echo "tpp transfer is  starting..."

   LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
   echo $LIB_JARS >"$log_filepath" &
   java $JAVA_MEM_OPTS -classpath $CONF_DIR:$CLASSES_DIR:$LIB_JARS com.zendaimoney.thirdpp.transfer.ServerStartup $1 $config_filepath > "$log_filepath" &
   appName=`grep "appName" $config_filepath | sed -e 's/.*=//'`
   echo "$!" > $BIN_DIR/"$appName".pid
   echo "tpp transfer start sucessful..." 
   echo "$appName pid is `cat $BIN_DIR/$appName.pid`"
   
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
