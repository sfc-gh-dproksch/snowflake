create or replace procedure cols2string(db string, sc string, tb string) 
returns string
language javascript
as 
$$
    //
    // Setup the pre + post part of the results
    //
    var select_part = 'create or replace table ' + DB + '.' + SC + '.' + TB + '( \n'
    var col_part = '';
    var from_part = ' );';
    var projection_sql = "select column_name || '   ' || " + 
    ' case   ' +
    "    when data_type = 'NUMBER' then 'string' " +
    "    when data_type = 'TEXT' then 'string' " +
    "    else data_type  " +
    ' end as data_type ' +
    'from util_dev.information_schema.columns where table_catalog = ? and  table_schema = ? ' +
    ' and  table_name = ? order by ordinal_position';
    var stmt = snowflake.createStatement({
        sqlText: projection_sql
        ,binds: [DB.trim(), SC.trim(), TB.trim()]
    });
    
    var rs = stmt.execute();
    var first_row = true;
    var cname = '';
    while (rs.next()) {
        cname = rs.getColumnValue(1);
        if (first_row) {
            col_part = col_part + cname;
            first_row = false;
        }
        else {
            col_part = col_part + ',' + cname;
        }
        col_part += '\n';
    }
    return select_part + col_part + from_part;
$$
;
