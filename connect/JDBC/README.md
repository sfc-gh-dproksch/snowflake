# Connecting to Snowflake Using JDBC

Snowflake provides extensive [documentation](https://docs.snowflake.net/manuals/user-guide/jdbc.html) on connecting via JDBC.

There are several pre-reqs that must be met:
-  64 bit O/S 
-  Grab the JDBC driver from [Maven](https://repo1.maven.org/maven2/net/snowflake/snowflake-jdbc/)
-  Dive deeper by visiting the [JDBC Git Repo](https://github.com/snowflakedb/snowflake-jdbc)

The JDBC Driver class is net.snowflake.client.jdbc.SnowflakeDriver.  

The Connection string is jdbc:snowflake://<account>.snowflakecomputing.com where <account> encompasses the account name + region + cloud provider.  For example:
-  AWS US West:  jdbc\:snowflake://myaccount.snowflakecomputing.com
-  AWS US East:  jdbc:snowflake://myaccount.us-east-1.snowflakecomputing.com
-  Azure US East: jdbc:snowflake://myaccount.east-us-2.azure.snowflakecomputing.com 
