# Connecting to Snowflake using SnowSQL
1. Install SnowSQL.  Instructions to install SnowSQL for are located [here](https://docs.snowflake.net/manuals/user-guide/snowsql-install-config.html).

2. Configure SnowSQL
	* Configure the SnowSQL config file (optional).  It is important to **note** here that **the password is stored in a clear text file**.  It is the **responsibility of the user** to ensure that **proper protections** are taken to **prevent unauthorized access** to the file and its contents.

*Sample Configuration File*
[connections]
 # *WARNING* *WARNING* *WARNING* *WARNING* *WARNING* *WARNING*
 #
 # The Snowflake user password is stored in plain text in this file.
 # Pay special attention to the management of this file.
 # Thank you.
 #
 # *WARNING* *WARNING* *WARNING* *WARNING* *WARNING* *WARNING*

 #If a connection doesn't specify a value, it will default to these
 #
 accountname = myAccountName
 # region is the name between the accountname and "snowflakecomputing.com"
 #region = 
 username = userName
 password = passWord
 #dbname = defaultdbname
 #schemaname = defaultschema
 #warehousename = defaultwarehouse
 #rolename = defaultrolename
 #proxy_host = defaultproxyhost
 #proxy_port = defaultproxyport

With this config file properly filled out, it is then possible to connect using: snowsql 

	* Configure the SnowSQL command line (optional).  This method uses the command line switches to configure SnowSQL.  This option maybe optimal when using SnowSQL in a non-development mode.  For example, the necessary configuration values can be stored in kubernetes secrets or another configuration solution such as etcd.

3. 

