#!/bin/bash

#
# Uses command line switches to configure the connection
# Uses standard $SNOWSQL_* env vars for configuration
# --accountname SNOWSQL_ACCOUNT
# --username SNOWSQL_USER
SNOWSQL_WAREHOUSE=WH

snowsql << EOF
select * from nation limit 2;
EOF

