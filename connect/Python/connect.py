#!/usr/bin/env python
import os
import snowflake.connector

#
# Setup the necessary variables
#
sf_user=""
sf_password=""
sf_account=""

#
# Check to make sure the proper env vars are set
# If they are set, get the values from them
# Otherwise, let the user know the vars must be 
# Properly set.
#
try:
    sf_user=os.environ['SNOWFLAKE_USER']
except KeyError as e:
    print("SNOWFLAKE_USER is not set")
    exit(0)

try:
    sf_password=os.environ['SNOWFLAKE_PASSWORD']
except KeyError as e:
    print("SNOWFLAKE_PASSWORD is not set")
    exit(0)

try:
    sf_account=os.environ['SNOWFLAKE_ACCOUNT']
except KeyError as e:
    print("SNOWFLAKE_ACCOUNT is not set")
    exit(0)

#
# Setup the connection context
#
ctx = snowflake.connector.connect(
    user=sf_user,
    password=sf_password,
    account=sf_account
    )


#
# Close the connection
# Uncomment this out if just testing the connection.  
# Leave commented out if this is to be imported into another module
# As a library
#
#ctx.close()
