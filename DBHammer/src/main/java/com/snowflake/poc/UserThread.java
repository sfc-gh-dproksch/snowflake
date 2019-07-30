package com.snowflake.poc;

import java.security.PrivateKey;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class UserThread extends Thread {

    private Properties props;
    private PrivateKey encryptedPrivateKey;
    private List<String> sqlList;
    private String threadName;
    private final CountDownLatch startSignal;
    private final CountDownLatch stopSignal;

    public void setProps(Properties props) {
        this.props = props;
    }

    public void setEncryptedPrivateKey(PrivateKey encryptedPrivateKey) {
        this.encryptedPrivateKey = encryptedPrivateKey;
    }

    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }

    /**
     * @param threadName the threadName to set
     */
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public UserThread(CountDownLatch startSignal, CountDownLatch stopSignal) {
        //this.threadName = threadName;
        this.startSignal = startSignal;
        this.stopSignal = stopSignal;
    }

    public void run() {

        // Connect
        DBConnection db = new DBConnection();

        /*
         * JIC we need to debug properties again for(Enumeration<Object> e =
         * props.keys(); e.hasMoreElements();) System.out.println(e.nextElement());
         */
        db.setEncryptedPrivateKey(encryptedPrivateKey);
        db.makeConnection(props);

        /*
         * 1. SET WAREHOUSE to execute on 
	 * 2. SET DEFAULT DATABASE 
	 * 3. SET DEFAULT SCHEMA
         * 4. TURN OFF RESULT SET CACHE 
	 * 5. SET QUERY TAG to Thread-name
         */
        db.executeSQL("USE ROLE ACCOUNTADMIN");
        db.executeSQL("USE WAREHOUSE " + props.getProperty("warehouse").trim());
        db.executeSQL("USE DATABASE " + props.getProperty("database").trim());
        db.executeSQL("USE SCHEMA " + props.getProperty("schema").trim());
        db.executeSQL("ALTER SESSION SET USE_CACHED_RESULT = FALSE");
        db.executeSQL("ALTER SESSION SET QUERY_TAG = 'JAVA DEMO - " + threadName.trim() + "'");
        // Countdown
        startSignal.countDown();

	/*
	*
	*  If we are waiting for all connections to be made, wait on the
	*  countdown latch.  If not, let's run some queries
	*
	*/
        try {
            if (props.getProperty("useLatch").equalsIgnoreCase("YES")) {
                startSignal.await();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Run SQL
        int iterations = new Integer(props.getProperty("iterations"));
        for(int iteration=0; iteration<iterations;iteration++) {
            //System.out.println("Begin of Iteration");
            for(String sql: sqlList) {
                //System.out.println("-->" + sql + "<---");
                db.executeSQL(sql);
            }
            //System.out.println("End of Iteration");
        }
        // Disconnect
        db.disconnect();

        stopSignal.countDown();
    }

}
