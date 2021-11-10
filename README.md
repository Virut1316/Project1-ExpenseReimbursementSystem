# Expense Reimbursement System

## Project Description

The Expense Reimbursement System is a web application where employees can make requests to ask for reimbursement of expenses incurred while on company time. Employees can check the status of their pending and resolved requests. The system also handles the Finance manager's side, allowing them to check, approve and deny any request made in the system.

## Technologies Used

* Java 8
* Apache Maven - 3.8.2
* Log4j - 1.2.17
* JUnit - 4.13.2
* Mockito - 3.2.4
* Postgresql JDBC - 9.1
* Jackson - 2.9.9

## Features

Features ready
* User can log in and are redirected to the corresponding homepage
* User's page displays account information and allows updating it
* Reimbursement requests can be created by the employee
* Employee's reimbursements are displayed in home page, filtered by status
* Manager's home page has all reimbursements in the system, displayed by status
* Reimbursements can be filtered by user
* Manager can approve and Deny reimbursements

To-do list:
* Password encryption
* Allow uploading files while creating a reimbursement request
* Send email to the created employee with temporary credentials

## Getting Started
   
git clone https://github.com/Virut1316/Project1-ExpenseReimbursementSystem.git

1. Import **Project1-ExpenseReimbursementSystem/MavenRepository/** as a Maven project to STS
2. Configure src/main/resources/jdbc.properties with database connection
3. Update Maven project
5. Run **Project1-ExpenseReimbursementSystem/Querys/** queries on Postgresql database instance
6. Run on Tomcat server (Current version only runs on a local server)
![image](https://user-images.githubusercontent.com/54875369/141059565-757c1e98-9912-4717-b7ab-dcfb15f72663.png)

## Usage

*Login page
Input username and password to enter the system, the home page that loads depends if the user is a manager or an employee
![image](https://user-images.githubusercontent.com/54875369/141059915-9e8b85ca-55e5-489b-9a4c-e9759eca8c51.png)



## License

This project uses the following license: [<license_name>](<link>).
