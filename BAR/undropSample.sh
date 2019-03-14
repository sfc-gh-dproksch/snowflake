#!/bin/bash

snowsql -a <account> -u <user> --private-key-path <pathToPrivateKey> << EOF
--
-- Create a warehouse to use
--
create warehouse drop_test_wh with warehouse_size = 'MEDIUM' 
   warehouse_type = 'STANDARD' auto_suspend = 60 auto_resume=true 
   min_cluster_count = 1;

--
-- Create a Database with a schema some tables in it.
--
CREATE DATABASE drop_db_source;
USE DATABASE drop_db_source;

--
-- Create a Schema
--
CREATE SCHEMA drop_schema_source;
USE SCHEMA drop_schema_source;

--
-- Create 3 tables and populate them
--
CREATE TABLE nation LIKE snowflake_sample_data.tpch_sf10.nation;
CREATE TABLE region LIKE snowflake_sample_data.tpch_sf10.region;
CREATE TABLE customer LIKE snowflake_sample_data.tpch_sf10.customer;

insert into nation select * from snowflake_sample_data.tpch_sf10.nation;
insert into region select * from snowflake_sample_data.tpch_sf10.region;
insert into customer select * from snowflake_sample_data.tpch_sf10.customer;

select 'nation', count(*) from nation
UNION
select 'region', count(*) from region
UNION
select 'customer', count(*) from customer
;

--
-- Drop Database
--
drop database drop_db_source;
use database drop_db_source;
undrop database drop_db_source;
use database drop_db_source;

--
-- Drop Schema
--
drop schema drop_schema_source;
use schema drop_schema_source;
undrop schema drop_schema_source;
use schema drop_schema_source;


--
-- Drop Table
--
drop table customer;
select count(*) from customer;
undrop table customer;
select count(*) from customer;

--
-- Clean Up
--
drop database drop_db_source;
drop warehouse drop_test_wh;


EOF
