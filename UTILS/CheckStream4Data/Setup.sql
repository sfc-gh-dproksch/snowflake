create database util_dev;
create schema if not exists util;

create or replace table t (f1 integer);
create or replace stream t_s on table t;
insert into t values (1);
select * from t_s;
select SYSTEM$STREAM_HAS_DATA('t_s');
