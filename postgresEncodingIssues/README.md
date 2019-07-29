# Postgres Encoding Issues WRT SQL_ASCII Encoded Databases

## Problem Statement
An SQL_ASCII encoded database will accept characters with an ascii code > 127.  Using psql, these characters are returned as "?".  Using a JDBC connection, an error is thrown.  ERROR: invalid byte sequence for encoding "UTF8": (hex code for character)

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
