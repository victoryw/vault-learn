#!/usr/bin/env bash
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
