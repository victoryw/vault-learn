#!/usr/bin/env bash
echo 'begin to deploy test db and user'
docker exec  data-db psql -v ON_ERROR_STOP=1 --username 'postgres' -c "CREATE USER vaultuser WITH PASSWORD 'r0ys1ngh4m';"
docker exec  data-db psql -v ON_ERROR_STOP=1 --username 'postgres' -c "CREATE DATABASE vaultdb OWNER vaultuser;"
docker exec  data-db psql -v ON_ERROR_STOP=1 --username 'postgres' -c "GRANT ALL PRIVILEGES ON DATABASE vaultdb to vaultuser;"

