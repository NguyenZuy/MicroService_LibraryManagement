#!/bin/bash
set -e
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE book;
    CREATE DATABASE loan_slip;
    CREATE DATABASE return_slip;
    CREATE DATABASE supplier;
    CREATE DATABASE order_slip;
    CREATE DATABASE import_slip;
EOSQL
#init-multiple-databases.sh