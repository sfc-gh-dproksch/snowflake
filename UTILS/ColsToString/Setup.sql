create database util_dev;
create schema if not exists util;

create table dev_table (f1 integer,
                       f2 decimal(5,2),
                       f3 varchar(10),
                       f4 string,
                       f5 date,
                       f6 timestamp,
                       f7 variant);
