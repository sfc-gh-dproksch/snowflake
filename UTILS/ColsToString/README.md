* Cols 2 String

Aids in debugging of the ingestion data when the width of VARCHAR, NUMBER(n,m), or other data quality issues arise.

This stored procedure will take a DB, SCHEMA, and TABLE as an argument and generate the new CREATE or REPLACE TABLE statement with the TEXT and NUMBER data type fields converted to STRING.

