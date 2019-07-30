package com.snowflake.poc;

import java.security.PrivateKey;
import java.util.Properties;

public class UserTest {

    DBConnection dbc = null;    

    public UserTest(Properties props, PrivateKey encryptedPrivateKey) {
        dbc = new DBConnection();
        dbc.setEncryptedPrivateKey(encryptedPrivateKey);
        dbc.makeConnection(props);
    }
}