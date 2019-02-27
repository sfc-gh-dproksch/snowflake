# Loading Delimited Files

Snowflake uses the COPY command to load data into the Snowflake service.  COPY can read data from either an internal Snowflake stage or an external stage that resides in the cloud providers Object Storage.

## COPY from an internal stage

1.  Create an internal stage
2.  PUT the file into the internal stage
3.  CREATE the target table
4.  COPY the file into the target table

## COPY from an external stage


