#!/bin/bash
export MB=mindmap_backend.jar

export MB_port=8899

case "$1" in

start)
        echo "--------MB 开始启动--------------"
        nohup java -jar $MB >/dev/null 2>&1 &
        MB_pid=`lsof -i:$MB_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$MB_pid" ]
            do
              MB_pid=`lsof -i:$MB_port|grep "LISTEN"|awk '{print $2}'`
            done
        echo "MB pid is $MB_pid" 
        echo "--------MB 启动成功--------------"

        echo "===startAll success==="
        ;;

 stop)
        P_ID=`ps -ef | grep -w $MB | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===MB process not exists or stop success"
        else
            kill -9 $P_ID
            echo "MB killed success"
        fi
        
        echo "===stopAll success==="
        ;;

restart)
        $0 stop
        sleep 2
        $0 start
        echo "===restartAll success==="
        ;;
esac
exit 0

