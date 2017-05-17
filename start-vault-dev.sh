#! /bin/bash
cd devops/
docker-compose up -d

sleep 5
until docker exec  data-db psql postgres postgres -c "\\x" 2>/dev/null 1>/dev/null; do
    echo 'Waiting for database start ...'
    sleep 2
done

echo 'begin to deploy test db and user'
docker exec  data-db psql -v ON_ERROR_STOP=1 --username 'postgres' -c "CREATE USER vaultuser WITH PASSWORD 'r0ys1ngh4m';"
docker exec  data-db psql -v ON_ERROR_STOP=1 --username 'postgres' -c "CREATE DATABASE vaultdb OWNER vaultuser;"
docker exec  data-db psql -v ON_ERROR_STOP=1 --username 'postgres' -c "GRANT ALL PRIVILEGES ON DATABASE vaultdb to vaultuser;"

export VAULT_ADDR=http://127.0.0.1:8201
export VAULT_TOKEN=devroot
vault write secret/my-application password=H@rdT0Gu3ss

vault mount postgresql
vault write postgresql/config/connection \
    connection_url="postgres://postgres:r0ys1ngh4m@db:5432/vaultdb?sslmode=disable"

#dynamic Secret backend for api
vault write postgresql/config/lease lease=1h lease_max=24h
vault write postgresql/roles/operator \
    sql="CREATE ROLE \"{{name}}\" WITH LOGIN PASSWORD '{{password}}' VALID UNTIL '{{expiration}}';
    GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO \"{{name}}\";"
#dynamic Secret backend for api

#static Secret backend for migration
vault write secret/migrator \
    username="vaultuser" \
    password="r0ys1ngh4m"
#static Secret backend for migration

cd ../

#migrate db
sh migrate-db.sh
#migrate db

