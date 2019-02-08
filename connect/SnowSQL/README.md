# Connecting to Snowflake using SnowSQL
1. Install SnowSQL.  Instructions to install SnowSQL for are located [here](https://docs.snowflake.net/manuals/user-guide/snowsql-install-config.html).

2. Configure SnowSQL
	* Configure the SnowSQL config file (optional).  It is important to **note** here that **the password is stored in a clear text file**.  It is the **responsibility of the user** to ensure that **proper protections** are taken to **prevent unauthorized access** to the file and its contents.

	*Sample Configuration File*
```
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
accountname = ChangeMeToYourAccount
# 
# This is the part between your accountname and snowflakecomputing.com
# in your URL.  If your URL is accountname.snowflakecomputing.com, 
# leave this blank, or commented out
#
#region = 
username = ChangeMeToYourUserName
password = ChangeMeToYourPassword
#dbname = defaultdbname
#schemaname = defaultschema
#warehousename = defaultwarehouse
#rolename = defaultrolename
#proxy_host = defaultproxyhost
#proxy_port = defaultproxyport
```

It is also possible to have named connections.  Named connections are enclosed by [] and are named connections.name.  For example:
```
[connections.example]
#Can be used in SnowSql as #connect example

accountname = accountname
username = username
password = password1234
```

Further customization of this configuration file will be discussed later.


With this config file properly filled out, it is then possible to connect using:
```
snowsql 
```

or connect to a specific named connection using
```
snowsql -c example
```

* Configure the SnowSQL command line (optional).  This method uses the command line switches to configure SnowSQL.  This option maybe optimal when using SnowSQL in a non-development mode.  For example, the necessary configuration values can be stored in kubernetes secrets or another configuration solution such as etcd.

3. 

