#!/usr/bin/env python
import os
import snowflakeconnection as sf

def executeCursor(cursor):
    query="select N_NATIONKEY, N_NAME from SNOWFLAKE_SAMPLE_DATA.TPCH_SF1.NATION"

    try:
        cursor.execute(query)
        for (N_NATIONKEY, N_NAME) in cursor:
            print('{0} - {1}'.format(N_NATIONKEY, N_NAME))
    finally:
        cursor.close()


def setWarehouse(ctx,wh):
    ctx.execute("use warehouse {0}".format(wh))

if __name__ == "__main__":
    print("Begin Query Sample")
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
    ctx = sf.getConnection( sf_user,sf_password,sf_account)

    #
    # Create a cursor object from the context
    #
    cursor = ctx.cursor()

    #
    # Set the warehouse to use.  This is not necessary if a use
    # has a default warehouse set.
    #
    setWarehouse(cursor, "wh")   

    #
    # Execute the query
    #
    executeCursor(cursor)

    #
    # Close the context
    #
    ctx.close


    print("End Query Sample")

