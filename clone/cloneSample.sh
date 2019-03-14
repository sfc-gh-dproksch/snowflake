#!/bin/bash

snowsql -a <account> -u <user> --private-key-path <pathToPrivateKey> << EOF

--
-- Create a warehouse to use
--
create warehouse clone_test_wh with warehouse_size = 'MEDIUM' 
   warehouse_type = 'STANDARD' auto_suspend = 60 auto_resume=true 
   min_cluster_count = 1;

--
-- Create a Database with a schema some tables in it.
--
CREATE DATABASE clone_db_source;
USE DATABASE clone_db_source;

--
-- Create a Schema
--
CREATE SCHEMA clone_schema_source;
USE SCHEMA clone_schema_source;

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
-- Start Cloning
--

--
-- Start with TABLES
--
create table nation_clone clone nation;
create table region_clone clone region;
create table customer_clone clone customer;

--
-- All row counts should be equal
--
select 'nation', 'orig', count(*) from nation
UNION
select 'region', 'orig', count(*) from region
UNION
select 'customer', 'orig', count(*) from customer
UNION
select 'nation', 'clone', count(*) from nation_clone
UNION
select 'region', 'clone', count(*) from region_clone
UNION
select 'customer', 'clone', count(*) from customer_clone
order by 1,2;
;

--
-- Clean Up Tables
--
drop table nation_clone;
drop table region_clone;
drop table customer_clone;


--
-- Clone the schema
--
CREATE SCHEMA clone_schema_target CLONE clone_schema_source;
USE SCHEMA clone_schema_target;
show tables;

--
-- All row counts should be equal
--
select 'nation', 'orig', count(*) from clone_schema_source.nation
UNION
select 'region', 'orig', count(*) from clone_schema_source.region
UNION
select 'customer', 'orig', count(*) from clone_schema_source.customer
UNION
select 'nation', 'clone', count(*) from nation
UNION
select 'region', 'clone', count(*) from region
UNION
select 'customer', 'clone', count(*) from customer
order by 1,2;
;

--
-- Clean Up
--
drop table nation;
drop table region;
drop table customer;

--
--
-- Go back to the source schema
USE SCHEMA clone_schema_source;
DROP SCHEMA clone_schema_target;

--
-- Clone the database
--
CREATE OR REPLACE DATABASE clone_db_target CLONE clone_db_source;
USE clone_db_target;

show schemas;
use schema clone_schema_source;
show tables;

--
-- Clean Up
--
drop database clone_db_source;
drop warehouse clone_test_wh;


EOF
