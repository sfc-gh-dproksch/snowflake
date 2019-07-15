install.packages("RODBC")
install.packages("RODBCDBI")
//install.packages("dplyr")
 
library(RODBC)
library(RODBCDBI)
library(dplyr)

con <- odbcConnect(dsn = "demo90")
f <- sqlQuery(con, "select n_regionkey, n_nationkey, n_name from snowflake_sample_data.tpch_sf1.nation")
f
close(con)

