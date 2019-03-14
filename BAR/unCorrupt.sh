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
CREATE DATABASE uncorrupt;
USE DATABASE uncorrupt;

--
-- Create a Schema
--
CREATE SCHEMA demo;
USE SCHEMA demo;

--
-- Create 3 tables and populate them
--
CREATE TABLE nation LIKE snowflake_sample_data.tpch_sf10.nation;
CREATE TABLE region LIKE snowflake_sample_data.tpch_sf10.region;
CREATE TABLE customer LIKE snowflake_sample_data.tpch_sf10.customer;

--
-- Populate the tables
--
insert into nation select * from snowflake_sample_data.tpch_sf10.nation;
insert into region select * from snowflake_sample_data.tpch_sf10.region;
insert into customer select * from snowflake_sample_data.tpch_sf10.customer;

--
-- How many rows are there?
--
select 'nation', count(*) from nation
UNION
select 'region', count(*) from region
UNION
select 'customer', count(*) from customer
;
--
-- Setup some timestamp information.  This is only so the script can
-- run unattended.  If you want to run step by step, the user can
-- manually put the timestamp value in or change to use BEFORE STATEMENT
-- with the proper statement id
--
alter session set timezone='America/Detroit';
alter session set timestamp_output_format = 'YYYY-MM-DD HH24:MI:SS.FF3';
set ts=(select  current_timestamp(4));
--
-- NOTE:  If running in a worksheet or a non-*nix environment, 
-- the \$ will need to be replaced with a plain $
--
select \$ts;

--
-- Mayhem makes an appearance!
--
UPDATE customer
SET c_acctbal = (c_acctbal * -1) + 22,
    c_mktsegment = 'SOFTWARE'
;

--
-- Let's see the damage Mayhem did
--
select c_custkey, c_acctbal, c_mktsegment from customer where c_custkey < 22;

-- 
-- Let's fix the damage
--
-- NOTE:  If running in a worksheet or a non-*nix environment, 
-- the \$ will need to be replaced with a plain $
--
create or replace table customer_repair clone customer 
    before (timestamp => \$ts);

select c_custkey, c_acctbal, c_mktsegment from customer_repair where c_custkey < 22;

alter table customer_repair swap with customer;

select c_custkey, c_acctbal, c_mktsegment from customer where c_custkey < 22;

--
-- Clean Up
--
drop database uncorrupt;
drop warehouse drop_test_wh;


EOF
