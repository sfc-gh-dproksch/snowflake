# DBHammer - Stress Test A Database

## Problem Statement
Needed a way to simulate a lot (1K+) users executing a pre-defined SQL job stream.  DBHammer is a multi-threaded Java application which consumes a pre-defined SQL job stream using n number of threads to simulate multiple users.  All of the users are connecting to a specific warehouse and placing that warehouse under test.

## Components
-  hammer.properties:  Properties file which drives the execution of DBHammer
-  DBHammer:  App that places a warehouse under test with a given SQL job stream
-  Snowflake JDBC Driver

## Build
The build process for DBHammer is:
```
mvn clean package -Dmaven.test.skip=true
```

## Run
In the target directory, maven will build a jar file.  In this example, the jar file is called DBHammer-1.0-SNAPSHOT.jar.

Additionally, the Snowflake JDBC JAR needs to be downloaded from search.maven.com.  

To execute DBHammer, add both the Snowflake JDBC JAR and the DBHammer JAR to the CLASSPATH.  
```
export CLASSPATH=$CLASSPATH:./target/DBHammer-1.0-SNAPSHOT.jar:./snowflake-jdbc-3.8.7.jar

java com.snowflake.poc.DBHammer hammer.operties
``` 

