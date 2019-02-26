#!/bin/bash

snowsql -a account -u username -w warehouse -d database -p ./rsa_key.p8 << EOF
select count(*) from nation;
EOF
