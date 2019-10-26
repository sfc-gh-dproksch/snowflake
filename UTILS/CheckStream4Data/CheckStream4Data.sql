--
-- Encapsulate the logic required to check of a stream contains data
-- Author:  Proksch
--
create or replace procedure check_stream_for_data(db string, sc string, pipename string) 
returns boolean
language javascript
as 
$$
    var fqn = DB.trim() + '.' + SC.trim() + '.' + PIPENAME.trim();
    var sql_stmt = 'select SYSTEM$STREAM_HAS_DATA(?)'
    var stmt = snowflake.createStatement({
        sqlText: sql_stmt
        ,binds: [fqn]
    });
    rs = stmt.execute();
    rs.next();
    return rs.getColumnValue(1);
$$
;

create or replace function check_stream_for_data(pipename string) 
returns boolean
as 
$$
    select SYSTEM$STREAM_HAS_DATA(PIPENAME)
$$
;
