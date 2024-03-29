# Postgres Encoding Issues WRT SQL_ASCII Encoded Databases

## Problem Statement
An SQL_ASCII encoded database will accept characters with an ASCII code > 127.  Using psql, these characters are returned as "?".  Using a JDBC connection, an error is thrown.  ERROR: invalid byte sequence for encoding "UTF8": (hex code for character)

```
createdb -E SQL_ASCII sql_ascii_db

set search_path to test,public;

drop table if exists t;
drop schema if exists test cascade;

create schema test;

create table if not exists test.t(f1 integer, f2 char(100), f3 integer);

truncate table t;

insert into t values (1,'Regular String Text.',-1);
insert into t values (2,'Curly Brace: ' || chr(123) || '<--' ,-2);
insert into t values (3,'Copyright: ' || chr(169) || '<--' ,-3);
insert into t values (4,'Registered: ' || chr(174) || '<--' ,-4);

select * from t;

 f1 |                                                  f2                        
                          | f3 
----+----------------------------------------------------------------------------
--------------------------+----
  4 | Registered: ?<--                                                            
                         | -4
  3 | Copyright: ?<--                                                             
                         | -3
  2 | Curly Brace: {<--                                                          
                          | -2
  1 | Regular String Text.                                                       
                          | -1
(4 rows)

```
## Potential Workaround
A potential workaround is to convert the sql_ascii encoding to latin-1, then to UTF-8.  The syntax looks like:
```
convert_from(convert(convert_to(columnName,'sql_ascii'), 'latin-1', 'utf-8'), 'utf-8')  as columnName
```

Finding every offending column in the database can be an arduous task, given the number of tables and columns per table.  The Java application, Encoding.java, was developed as a sample way to quickly generate a VIEW for every table and to convert all columns that are CHAR, VARCHAR, and TEXT from SQL_ASCII to UTF-8.

The following modifications will are necessary:
-  The connection string will need to reflect the database(s) being run against
-  The SQL for *distinctSchemaTable* will need a different *table_catalog* and *table_name* predicate values.  Perhaps even a *table_schema* predicate will need to be added. 
-  The SQL for *sqlStmt* will need to have the predicate values for *table_catalog*changed.

Obviously, any other modifications, such as the name of the generated VIEW, is at the user's discretion.

*Note:*  This workaround was developed|tested with:
1.  Postgres 9.6 docker pull postgres:9.6
1.  postgresql-42.2.6.jar JDBC Driver
1.  OpenJDK 8u222b10 - Linux x86


h/t to *Dan Dowdy* for the nice convert string.
