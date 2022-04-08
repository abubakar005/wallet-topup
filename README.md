# wallet-topUp

This project is for wallet topUp where a GL to SVA transaction will be performed.

# Important Instructions:
1. Customers will get register in the application along with their valid wallet accounts once the application will run for the first time and those customers will then be used later in the topUp API.
2. These will be registered by scripts that are available in the project resources as a db changelog (Liquibase migration tool is used for this).
3. Created Two GLs (as a customers along with wallet) in the application for transaction (one for topUp account and other for fee collection).
4. Open API (Swagger) is also implemented for this project, API documentation can be accessed using the link IP:8080/swagger-ui/index.html
5. Health check API is also available to check API availability (via actuator) at the path IP:8080/actuator/health
6. Postgres Db used for this project as per the project description, I have used PostgresDB deployed on Heroku.
7. There is a single Rest API which will perform topUp transaction.
8. I have also created pipeline for auto deployment using the Github Actions and this project is deployed on the Heroku using the automation. It can be accessed at the link given below. It will be auto deployed if any change pushes to the repo in the main branch
9. I am saving all the transactions requests in the transaction_requests table for history with error code and error message
10. I have attached a postman collection as well in the project directory (/postman) for this topUp API

#Assumptions
charge_id is valid and unique otherwise it will throw an error
Currency supported here is only USD, other than this it will throw an error
This transaction can not be rolled back as it has been already done from the external system with deductions, we can process failed transactions manually or can implement a scheduler (just mentioning approach here)

# Technologies used for this Project
1. Spring Boot with Java 8 (Rest API)
2. Actuator (Health Check)
3. Liquibase (DB Migration Tool)
4. Open API - Swagger (API Documentation)
5. Github (Code Management)
6. Github Actions (Auto deploy, Pipeline)
7. Heroku (For database and project deployment)
    Deployment Link (Swagger): https://ab-top-up.herokuapp.com/swagger-ui/index.html 

#For Local Deployment

Pre requisites: Install JDK 8, Apache Maven, PostgreSQL 
Update PostgreSQL connection configuration in the application.properties file