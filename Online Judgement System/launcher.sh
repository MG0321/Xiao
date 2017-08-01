#!/bin/bash

#fuser杀掉3000端口上的进程
fuser -k 3000/tcp
fuser -k 5000/tcp
#启动redis端口6379
redis-server &

#进入oj-server 以及令server.js运行在后台（&的作用）
cd ./oj-server
npm install
nodemon server.js &

#进入oj-client
cd ../oj-client
npm install
ng build --watch &
cd ../executor
pip install -r requirement.txt
python executor_server.py &

echo "=================================================="
read -p "PRESS [ENTER] TO TERMINATE PROCESSES." PRESSKEY

fuser -k 3000/tcp
fuser -k 5000/tcp
service redis_6379 stop
