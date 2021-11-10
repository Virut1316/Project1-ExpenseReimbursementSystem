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

* Login page:
Input username and password to enter the system, the home page that loads depends if the user is a manager or an employee
![image](https://user-images.githubusercontent.com/54875369/141060186-9482586a-0701-4e1f-8304-09fcced54035.png)

* Employee home page:
It display all of the pending or resolved reimbursement requests that the user owns, user can change the displayed reimbursements by clicking the pending or resolved buttons
![image](https://user-images.githubusercontent.com/54875369/141060486-09ba0ceb-aa83-4f3e-b56e-1bab12ce4847.png)
![image](https://user-images.githubusercontent.com/54875369/141060537-d56fc723-17bd-4a64-849b-bc606df2d552.png)



## License

This project uses the following license: [<license_name>](<link>).
