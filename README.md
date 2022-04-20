## Cathy Bank PreTest Requirements
1. Design basic CRUD to handle Coin Table
2. Connect to CathayBank API to get Coin data
3. Create Table for locale name of currency 
4. Create Unit tests for testing all functionality

## Clarifications 
1. I didn't use the table for locale name of currency? I only created simple SQL in data.sql(not related to main function)
   > Instead of creating locale table, I used Java Currency to handle to avoid storing in database.
2. For unit test part, I didn't use unit test to test All functions
   > The main reason is that unit test is focus on unit only, it could not consider\
   > external factor (like other injection bean). However, I create postman collection to test\
   > my functionality.

## Testing steps
1. git clone this project
2. `cd <this project root path>`
3. Run `mvn install` 
4. Import postman collection https://www.getpostman.com/collections/dbc4da669fcd087c28d1
5. `cd target`
6. Execute `java -jar coindesk-0.0.1-SNAPSHOT.jar`
7. Test whole functionalities in Postman

## Test Cases
1. run `mvn test`
2. check the test result


