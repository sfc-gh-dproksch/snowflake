library("RJDBC", "DBI")

#
# Set the necessary connection variables
#
# The account is everything to the left of .snowflakecomputing.com
# On the connection URL
#
sf_account <- "XXXXXX" 
sf_user <- "XXXXXXX"
sf_password <- "XXXXXXXXX"
sf_URL = paste('jdbc:snowflake://', sf_account, '.snowflakecomputing.com' ,sep="")

#
# If the Snowflake JDBC Driver .jar file is in the CLASSPATH,
# it is not necessary to have the parameter classPATH specified.
# IMHO, this makes the call cleaner
#
# A version with and without are left below
#
jdbcDriver <- JDBC(driverClass="com.snowflake.client.jdbc.SnowflakeDriver")
#jdbcDriver <- JDBC(driverClass="com.snowflake.client.jdbc.SnowflakeDriver", 
#                classPath="/tmp/path>/snowflake-jdbc-3.6.9.jar")

#
# Connect to Snowflake
#
jdbcConnection <- dbConnect(jdbcDriver, sf_URL, sf_user,sf_password)

#
# What time is it?
#
result <- dbGetQuery(jdbcConnection, "select current_timestamp() as now")
 print(result)

