#!/usr/bin/env bash
docker-compose up -d

sleep 5
until docker exec  data-db psql postgres postgres -c "\\x" 2>/dev/null 1>/dev/null; do
    echo 'Waiting for database start ...'
    sleep 2
done
