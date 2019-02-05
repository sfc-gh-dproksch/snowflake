# Connecting to Snowflake using SnowSQL
1. Install SnowSQL.  Instructions to install SnowSQL for are located [here](https://docs.snowflake.net/manuals/user-guide/snowsql-install-config.html).
2. Configure SnowSQL
* Configure the SnowSQL config file (optional).  It is important to **note** here that **the password is stored in a clear text file**.  It is the **responsibility of the user** to ensure that **proper protections** are taken to **prevent unauthorized access** to the file and its contents.
*Sample Configuration File*
* Configure the SnowSQL command line (optional).  This method uses the command line switches to configure SnowSQL.  This option maybe optimal when using SnowSQL in a non-development mode.  For example, the necessary configuration values can be stored in kubernetes secrets or another configuration solution such as etcd.
3. 

