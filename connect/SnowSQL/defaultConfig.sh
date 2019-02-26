#!/bin/bash

snowsql << EOF
select * from nation limit 2;
EOF

