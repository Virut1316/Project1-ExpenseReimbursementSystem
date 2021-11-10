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

* New request page: User can fill out the form to create a new pending request
![image](https://user-images.githubusercontent.com/54875369/141060851-51da0225-972e-4aa1-afc1-7ebe6c72904d.png)

* User page: User can see and change the information of the account as well as the password
![image](https://user-images.githubusercontent.com/54875369/141060965-96191b63-fc0a-4306-82aa-741e951bd9b1.png)
![image](https://user-images.githubusercontent.com/54875369/141060999-098819e6-2661-416c-9a9f-504da5ece709.png)

* Manager home page: Displays all of the reimbursement requests of all users, displayed by pending or resolved
![image](https://user-images.githubusercontent.com/54875369/141061330-bb079f94-987c-4faf-934b-5bfcefe25210.png)
![image](https://user-images.githubusercontent.com/54875369/141061368-cb77616d-498d-4160-a250-eb0cde1a99ad.png)

* Manager employee list: Displays all users that are employees and allows to see the reimbursements of that specific employee
![image](https://user-images.githubusercontent.com/54875369/141061537-ed36096b-df91-411c-b2fe-3d3b2512de37.png)
> A page similar to employee home page is displayed when seeing reimbursements of an especific employee
![image](https://user-images.githubusercontent.com/54875369/141061697-aae75b90-068c-45fe-9a17-bc2eda763558.png)

* Manager approve and deny page: By clicking the magnifying glass on a pending reimbursement this page is loaded and allows the manager to approve or deny that specific reimbursement
![image](https://user-images.githubusercontent.com/54875369/141062117-ac590f9c-b42d-4331-949a-abacdf4596b0.png)


## License

This project uses the following license: [<ACADEMIC PUBLIC LICENSE>](<https://omnetpp.org/intro/license>).
