## Cathay Bank PreTest Requirements
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

## Test Data and Result
1. Save api - http://localhost:8080/coin/save
   ```json
   // Request
   {
       "code":"test",
       "description":"test description",
       "rate":"123,234.123",
       "rate_float":123234.123,
       "symbol":"test symbol"
   
   } 
   // Response
   {
       "code": "test",
       "symbol": "test symbol",
       "rate": "123,234.123",
       "description": "test description",
       "rate_float": 123234.123
   }
   // Coin already existed
   {
       "code": null,
       "symbol": null,
       "rate": null,
       "description": "Not successful",
       "rate_float": null
   }
   ```
2. Update api - http://localhost:8080/coin/update
   ```json
   // Request
   {
       "code":"test",
       "symbol":"test symbo11111"
   }
   // Response
   {
       "code": "test",
       "symbol": "test symbo11111",
       "rate": "123,234.123",
       "description": "test description",
       "rate_float": 123234.123
   }
   // Code doesn't exist
   {
       "code": null,
       "symbol": null,
       "rate": null,
       "description": "Not successful",
       "rate_float": null
   }
   ```
3. Get api - http://localhost:8080/coin/get?code=test
   ```json
   // Response
   {
       "code": "test",
       "symbol": "test symbo11111",
       "rate": "123,234.123",
       "description": "test description",
       "rate_float": 123234.123
   }
   // Code doesn't exist
   {
       "code": null,
       "symbol": null,
       "rate": null,
       "description": "Not successful",
       "rate_float": null
   }
   ```
4. Delete api - http://localhost:8080/coin/delete?code=test
   ```json
   // Response - Delete successfully
   true
   // Response - Fail
   false
   ```
   
5. Get all response from api - http://localhost:8080/coin/api/all \
   Response will be the same as https://api.coindesk.com/v1/bpi/currentprice.json
   
6. Get Simplified response from api - http://localhost:8080/coin/api
   ```json
   // Response
   {
       "updatedTime": "Apr 20, 2022 15:04:00 UTC",
       "coins": {
           "EUR": {
               "code": "EUR",
               "name": "歐元",
               "rate": "38,261.2858"
           },
           "GBP": {
               "code": "GBP",
               "name": "英鎊",
               "rate": "31,810.3305"
           },
           "USD": {
               "code": "USD",
               "name": "美元",
               "rate": "41,359.1704"
           }
       }
   }
   ```