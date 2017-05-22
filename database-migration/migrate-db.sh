#!/usr/bin/env bash
#hostIp=`ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p' | sed -n '1p;1q'`
export DBIP=10.245.4.2

export VAULT_TOKEN=devroot

docker-compose up
docker-compose down -v
cd ../
