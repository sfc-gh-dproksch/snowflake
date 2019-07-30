package com.snowflake.poc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * DBHammer - Attempt to pound your DBMS into the 
 * ground with concurrency.  
 *
 */
public class DBHammer {

    private Properties props;
    private PrivateKey encryptedPrivateKey;
    private ArrayList<String> sqlList;

    private Properties getProps() {
        return props;
    }

    private PrivateKey getEncryptedPrivateKey() {
        return encryptedPrivateKey;
    }

    private ArrayList<String> getSqlList() {
        return sqlList;
    }

    public static void main(String[] args) {
        System.out.println("Begin Hammer!");
        DBHammer dbh = new DBHammer();
        dbh.prep(args);
        dbh.hammerIt();
        System.out.println("End Hammer!");
    }

	/*
	* Prepare the necessary artifacts for executeion.
	* 1.  Load the properties
	* 2.  Read in the file which contains all of the SQL job stream
	*     This file contains SQL which is delimited by the ";" statement
	*     terminator.  The SQL **MUST NOT** contain any comments, i.e.,
	*     --This is an SQL comment
	* 3.  If a public/private key exchange is being used for authentication,
	*     it is loaded and processed here.
	*
	*/
    private void prep(String[] args) {
        String propertiesFile = args[0];

        try {
            InputStream input = new FileInputStream(propertiesFile);
            props = new Properties();
            props.load(input);

            // Read in the SQL file, and convert it to an ArrayList
            String[] content = new String(Files.readAllBytes(Paths.get(props.getProperty("sqlFile")))).split(";");
            sqlList = new ArrayList<String>(content.length);
            for (int i = 0; i < content.length; i++) {
                if (content[i].trim().length() > 0)
                    sqlList.add(content[i].trim().replaceAll("\n", " "));
            }

            // Convert the private key to a byte array
            if ( props.getProperty("encKey").equalsIgnoreCase("YES")) {
                File f = new File(props.getProperty("keyLocation"));
                FileInputStream fis = new FileInputStream(f);
                DataInputStream dis = new DataInputStream(fis);
                byte[] keyBytes = new byte[(int) f.length()];
                dis.readFully(keyBytes);
                dis.close();

                String encrypted = new String(keyBytes);
                String passphrase = props.getProperty("passphrase");
                encrypted = encrypted.replace("-----BEGIN ENCRYPTED PRIVATE KEY-----", "");
                encrypted = encrypted.replace("-----END ENCRYPTED PRIVATE KEY-----", "");
                EncryptedPrivateKeyInfo pkInfo = new EncryptedPrivateKeyInfo(Base64.getMimeDecoder().decode(encrypted));
                PBEKeySpec keySpec = new PBEKeySpec(passphrase.toCharArray());
                SecretKeyFactory pbeKeyFactory = SecretKeyFactory.getInstance(pkInfo.getAlgName());
                PKCS8EncodedKeySpec encodedKeySpec = pkInfo.getKeySpec(pbeKeyFactory.generateSecret(keySpec));
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                encryptedPrivateKey = keyFactory.generatePrivate(encodedKeySpec);
            }   
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	/*
	*
	* hammerIt - start your engines
	*
	*/
    private void hammerIt() {
        int numUsers = new Integer(props.getProperty("numUsers"));
        List<UserThread> users = new ArrayList<UserThread>();
        CountDownLatch startSignal = new CountDownLatch(numUsers);
        CountDownLatch stopSignal = new CountDownLatch(numUsers);

        for (int i = 0; i < numUsers; i++) {
            users.add(new UserThread(startSignal, stopSignal));
        }

        int i = 0;
        long jobStartTime = System.currentTimeMillis();
        long jobEndTime = 0;
        long totalJobTime = 0;
        for (UserThread ut : users) {
            ut.setThreadName("User - " + i++);
            ut.setProps(props);
            ut.setEncryptedPrivateKey(encryptedPrivateKey);
            ut.setSqlList(sqlList);
            ut.start();
        }

        try {
            stopSignal.await();
            //jobEndTime = System.currentTimeMillis();
            //totalJobTime = jobEndTime-jobStartTime;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            totalJobTime=-1;
        }
        System.out.println("Done Waiting...");
        /*
		* Awesome metrics, just need a better way to collect and 
		* report it so the I/O required in the println statements
		* doesn't factor into the results.
		* Open an Issue for this
        * System.out.println("Total Job Time(ms): " + totalJobTime);
        * System.out.println("Total Job Time(s): " + totalJobTime/1000.00);
        * System.out.println("Total Job Time(m): " + totalJobTime/1000.00/60);
        */
        
    }
}


