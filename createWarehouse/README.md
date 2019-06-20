# Create a Snowflake Warehouse

Virtual Warehouses are where the computing magic happens.  Warehouses are also billed **by the second** after the first minute.  Warehouses are also almost instantly scalable **UP** and **DOWN** with an ALTER WAREHOUSE command.

Warehouses come in the following sizes:

* XSMALL   - Extra Small (1 node)
* SMALL    - Small (2 nodes)
* MEDIUM   - Medium (4 nodes)
* LARGE    - Large (8 nodes)
* XLARGE   - Extra Large (16 nodes)
* XXLARGE  - 2X Large (32 nodes)
* XXXLARGE - 3X Large (64 nodes)
* X4LARGE  - 4X Large (128 nodes)

The syntax for creating a warehouse is:
```
CREATE WAREHOUSE <warehouse name> 
WITH WAREHOUSE_SIZE = '<size>' WAREHOUSE_TYPE = 'STANDARD' 
AUTO_SUSPEND = <suspend time> AUTO_RESUME = <TRUE|FALSE> 
MIN_CLUSTER_COUNT = <min> MAX_CLUSTER_COUNT = <max> 
SCALING_POLICY = 'STANDARD';
```

Breaking down the syntax:
* <warehouse name> - This is the name of the warehouse.  It must be unique within your instance.
* <size> - This is the initial size of your warehouse.  The sizes are listed above.  The number of nodes is also the number of credits per hour that each sized cluster will consume.
* <suspend time> - this is the amount of idle time that will elapse before the warehouse is suspended.  This allows the warehouse to go into suspend mode and billing is halted.
* AUTO_RESUME - Select TRUE if you want the warehouse to automatically come back online when a user connects and issues an SQL command that requires compute resources to process.  Select FALSE if you want to have to start the warehouse manually.
* \<min\>, \<max\> - These two parameters govern the use of the [Multi-Cluster Warehouse feature](https://docs.snowflake.net/manuals/user-guide/warehouses-multicluster.html).  <min> is the minimum number of clusters which will be started to service users requests.  <max> is the maximum number of clusters which can be used to service user requests in time of heavy load.
 
