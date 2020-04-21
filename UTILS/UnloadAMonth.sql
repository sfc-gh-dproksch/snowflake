--
-- stored proc to unload data by a given year and month
-- yes, I know the database.schema.tablename should be parameters....
--
create or replace procedure UnloadAMonth (THEMONTH STRING, THEYEAR STRING)
  returns string
  language javascript
  strict
as
$$
  var counter = 0;
  var pastYear = THEYEAR - 5;

  // list the partition values
  var partitions = snowflake.execute({ sqlText: "COPY INTO @~/trips/" + pastYear + "/" + THEMONTH + "/ " +
		"FROM ( " +
	"SELECT tripduration, dateadd('year', -5, starttime) AS starttime, dateadd('year', -5, stoptime) AS stoptime, " +
	"	start_station_id, end_station_id, bikeid, usertype, birth_year, gender, program_id " +
	"FROM CITIBIKE_BIG_V2_LOCAL.CLUSTERED.TRIPS " +
	"WHERE year(STARTTIME ) = " + THEYEAR + " AND month(STARTTIME ) = " + THEMONTH +
	") " +
	"FILE_format = ( TYPE = CSV ) overwrite = TRUE, max_file_size = 256000000;" });

 
  return 'hi';
$$;
