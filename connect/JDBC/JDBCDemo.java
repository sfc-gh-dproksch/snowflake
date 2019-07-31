import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/*
*
* Demo of how to connect to Snowflake via JDBC
*
*/
public class JDBCDemo {
	public JDBCDemo() {
	    try {
            Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}

	public static void main(String[] args) {
		System.out.println("Begin JDBC Connection Demo");
		JDBCDemo j = new JDBCDemo();
		j.connect();
		System.out.println("End JDBC Connection Demo");
	}

    private void connect() {
        String url = "jdbc:snowflake://<account>.snowflakecomputing.com";
        String user = "myUserName";
        String password = "myPassword";
        Properties props = new Properties();
        props.put("user",user);
        props.put("password",password);
        try {
            Connection conn = DriverManager.getConnection(url,props);
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
