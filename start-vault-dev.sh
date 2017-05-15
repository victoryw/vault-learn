#! /bin/bash
cd devops/
docker-compose up -d
export VAULT_ADDR=http://127.0.0.1:8201
export VAULT_TOKEN=devroot
vault write secret/my-application password=H@rdT0Gu3ss
