CREATE USER usename PASSWORD = '************' 
       DISPLAY_NAME = 'Last, First' FIRST_NAME = 'First' 
       LAST_NAME = 'Last' EMAIL = 'first.last@my-company.com' 
       DEFAULT_ROLE = "PUBLIC" MUST_CHANGE_PASSWORD = TRUE;
GRANT ROLE "PUBLIC" TO USER username;
GRANT ROLE "ACCOUNTADMIN" TO USER username;
