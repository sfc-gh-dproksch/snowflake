#!/bin/bash

#
# Make sure SNOWSQL_PRIVATE_KEY is set in the environment.
#
PRIV_PATH=<PATHTO>/rsa_key.p8
snowsql -a account -u user -w warehouse --private-key-path ${PRIV_PATH} << EOF

create or replace database sampl;

EOF
