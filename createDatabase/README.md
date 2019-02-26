# Create a Snowflake Database

A database is where Snowflake physically stores data.  This storage is decoupled from any compute engines that are needed to process the data.

A database can be protected by continuous data protection.  This is a combination of the durability provided by a cloud providers Object Storage and Time Travel.  Time travel allows the user to access data as it was at any point in time up to 90 days in the past.  The user also has the option of making the database transient.  A transient object is not eligible to participate in Time Travel.

A few sample commands:

1.  Create a regular database.  The retention period is either 1 day for STANDARD accounts or 90 days for ENTERPRISE accounts.
```
CREATE OR REPLACE DATABASE exampledb;
```

2.  Create a transient database 
```
CREATE OR REPLACE TRANSIENT DATABASE exampledb;
```

3.  Clone a database (for use with DEV/TEST and/or BAR):
```
CREATE OR REPLACE DATABASE exampledb
clone sourcedb at ( TIMESTAMP => myTimestamp );
```



