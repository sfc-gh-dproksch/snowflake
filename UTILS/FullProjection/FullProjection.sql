create or replace procedure full_projection(db string, sc string, tb string) 
returns string
language javascript
as 
$$
    //
    // Setup the pre + post part of the results
    //
    var select_part = 'select ';
    var col_part = '';
    var from_part = ' from ' + DB + '.' + SC + '.' + TB;
    var projection_sql = 'select column_name from util_dev.information_schema.columns where table_catalog = ? and  table_schema = ? and  table_name = ? order by ordinal_position';
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
    }
    return select_part + col_part + from_part;
$$
;
