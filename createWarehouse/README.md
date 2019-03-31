# Create a Snowflake Warehouse

Virtual Warehouses are where the computing magic happens.  Warehouses come in the following sizes:

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
CREATE WAREHOUSE ff WITH WAREHOUSE_SIZE = 'X4LARGE' WAREHOUSE_TYPE = 'STANDARD' AUTO_SUSPEND = 600 AUTO_RESUME = TRUE MIN_CLUSTER_COUNT = 1 MAX_CLUSTER_COUNT = 2 SCALING_POLICY = 'STANDARD';
```
