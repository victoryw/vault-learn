#!/bin/bash

cd devops/
docker-compose down -v

cd ../
rm -rf vault-volume/file
