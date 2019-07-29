/*
Sample Java Application to provide a VIEW over a table that has the SQL_ASCII
character encoding.  These tables can throw errors if ASCII character > 127
are stored in CHAR, VARCHAR and TEXT columns.

For Example:
set search_path to test,public;

drop table if exists t;
drop schema if exists test cascade;

create schema test;

create table if not exists test.t(f1 integer, f2 char(100), f3 integer);

truncate table t;

insert into t values (1,'Regular String Text.',-1);
insert into t values (2,'Curly Brace: ' || chr(123) || '<--' ,-2);
insert into t values (3,'Copyright: ' || chr(169) || '<--' ,-3);
insert into t values (4,'Registered: ' || chr(174) || '<--' ,-4);

select * from t;

Only the row where f1 = 1 will return correctly.


*/
import java.sql.*;
import java.util.Properties;

public class Encoding {
	// Set up the pre & post strings around the column to be run
	// through the conversion process
	private static final String CONVERT_PRE = "convert_from(convert(CONVERT_TO(";
	private static final String CONVERT_POST = ",'sql_ascii'), 'latin-1', 'utf-8'), 'utf-8')  as ";


	/*
	Load the postgres JDBC driver class
	*/
	public void Encoding() {
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println("Begin Encoding Demo.");
		Encoding e = new Encoding();
		e.buildViews();
		System.out.println("End Encoding Demo.");
	}

	/*
	*/
	private void buildViews() {
		Connection db = null;
		Properties props = new Properties();
		try {
			// Connect to Database
			db = DriverManager.getConnection("jdbc:postgresql:sql_ascii_db"
							, props);
			// Create ResultSet of all tables in a database / schema
			// For "real" use, modify the predicate(s) to restrict to 
			// Your specific q
			String distinctSchemaTable = "select " +
				"distinct table_schema, table_name " +
				"from information_schema.columns " +
				"where table_catalog = 'sql_ascii_db' and " +
				"table_name in ('t','t1','t2','t3')";

			//
			// sql_ascii_db is the test database I used
			// This will need to change based upon
			// actual your actual environment
			//
			String sqlStmt = "select " + 
				"table_schema, table_name, column_name, " + 
				"ordinal_position, data_type " +
				"from information_schema.columns " +
				"where table_catalog = 'sql_ascii_db' and " +
				"table_schema = ? and table_name = ? " +
				" order by ordinal_position";
			PreparedStatement p = db.prepareStatement(sqlStmt);
			ResultSet rs = null;
			ResultSet distSTrs = db.createStatement()
					.executeQuery(distinctSchemaTable);
			String tableSchema;
			String tableName;
			while (distSTrs.next()) {
				tableSchema = distSTrs.getString(1);
				tableName = distSTrs.getString(2);
				p.setString(1,tableSchema);
				p.setString(2,tableName);
				rs = p.executeQuery();
				StringBuffer viewSQL = new StringBuffer();
				viewSQL.append("CREATE OR REPLACE VIEW ")
						.append(tableSchema.trim().toUpperCase())
						.append(".")
						.append(tableName.trim().toUpperCase())
						.append("_VIEW as (")
						.append(" select ");

				String comma = "";
				while (rs.next()) {
					String columnName = rs.getString(3);
					String dataType = rs.getString(5);
					viewSQL.append(makeProjection(columnName,dataType,comma));
					if (comma.equals("")) comma = ",";
				}
				viewSQL.append(" FROM ")
					.append(tableSchema.trim().toUpperCase())
					.append(".")
					.append(tableName.trim().toUpperCase())
					.append(")");
					System.out.println(viewSQL.toString());
			}
			db.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String makeProjection(String columnName, String dataType, String comma) {
			StringBuffer sb = new StringBuffer();
			switch (dataType) {
				case "character":
				case "character varying":
				case "text":
					sb.append(comma).append(CONVERT_PRE)
						.append(columnName)
						.append(CONVERT_POST).append(columnName);
					break;
				default:
					sb.append(comma).append(columnName);
					break;
			}
			return sb.toString();
	}
}
