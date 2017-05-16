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
docker exec  data-db psql -v ON_ERROR_STOP=1 --username 'vaultuser' -d 'vaultdb' -c "CREATE TABLE TestTable (testId varchar(17) PRIMARY KEY);"
docker exec  data-db psql -v ON_ERROR_STOP=1 --username 'vaultuser' -d 'vaultdb' -c "INSERT INTO public.TestTable(testId) values ('1')"
export VAULT_ADDR=http://127.0.0.1:8201
export VAULT_TOKEN=devroot
vault write secret/my-application password=H@rdT0Gu3ss

vault mount postgresql
vault write postgresql/config/connection \
    connection_url="postgres://postgres:r0ys1ngh4m@db:5432/vaultdb?sslmode=disable"

#dynamic Secret backend
vault write postgresql/config/lease lease=1h lease_max=24h
vault write postgresql/roles/operator \
    sql="CREATE ROLE \"{{name}}\" WITH LOGIN PASSWORD '{{password}}' VALID UNTIL '{{expiration}}';
    GRANT ALL PRIVILEGES ON DATABASE vaultdb TO \"{{name}}\";"
#dynamic Secret backend



cd ../
