#!/usr/bin/env bash
hostIp=`ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p' | sed -n '1p;1q'`
export DBIP=$hostIp
cd ./database-migration/
docker-compose up
docker-compose down
cd ../
