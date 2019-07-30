package com.snowflake.poc;

import java.security.PrivateKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    Connection conn = null;

	/*
	*
	* Connect to the database.  
	* Open an issue to make this more generic so it can be
	* used with other DBMS'
	*
	*/
    public DBConnection() {
        try {
            Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    PrivateKey encryptedPrivateKey;

    public void setEncryptedPrivateKey(PrivateKey encryptedPrivateKey) {
        this.encryptedPrivateKey = encryptedPrivateKey;
    }

    public void makeConnection(Properties props) {
        StringBuffer url = new StringBuffer("jdbc:snowflake://").append(props.getProperty("account"));
        Properties sqlProperties = new Properties();
        sqlProperties.put("user", props.getProperty("user"));
        sqlProperties.put("password", props.getProperty("password"));
        //sqlProperties.put("privateKey", encryptedPrivateKey);

        boolean isConnected = false;
        int retryCount = 0;

        while (!isConnected && (retryCount++ < 5)) {
            try {
                conn = DriverManager.getConnection(url.toString(), sqlProperties);
                isConnected = true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                try {
                    System.err.println("RetryCount: :" + retryCount);
                    Thread.sleep(1000*retryCount);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
        if (!isConnected) System.exit(-1);
        //System.err.println("Connected!");
    }

    public void disconnect() { 
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public boolean executeSQL(String sql) {
        boolean rc = false;

        try {
            conn.createStatement().execute(sql);
            rc = true;
        }
        catch (Exception e) {
            System.err.println(sql);
            e.printStackTrace();
            return rc;
        }
        return rc;
    }
}
