#! /bin/bash
cd ./pre-env/
sh infrastructure-initialize.sh
sh database-initialize.sh
source vault-initialize.sh
cd ../

#migrate db
cd ./database-migration
sh migrate-db.sh
#migrate db

