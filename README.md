# OverTimeSystem

## Overview

OverTimeSystem is a web-based overtime management application developed using **Spring Boot**.
The system helps organizations manage employee overtime activities efficiently by providing features for project assignment, overtime logging, and report generation.

Employees can record their overtime work on assigned projects, while administrators can manage projects and employees. The system also allows exporting overtime records into **Excel files** for reporting and approval purposes.

---

## Features

* User registration and login with secure authentication
* Password encryption using Spring Security
* Role-based access control (Administrator and Employee)
* Project creation and employee assignment by administrators
* Employee overtime logging for assigned projects
* Export overtime reports to Excel
* Web interface built using Thymeleaf templates

---

## System Roles

### Administrator

* Create and manage projects
* Assign employees to projects
* View employee overtime records
* Manage users

### Employee

* Log overtime hours for assigned projects
* View their overtime history
* Export overtime reports to Excel

---

## Technologies Used

Backend:

* Java 17
* Spring Boot 3
* Spring Security
* Spring Data JPA

Frontend:

* Thymeleaf
* HTML
* CSS

Database:

* PostgreSQL

Libraries:

* Apache POI (Excel export)
* Lombok

Build Tool:

* Maven

## Excel Export

The system generates structured overtime approval sheets using **Apache POI**.
The exported Excel file includes:

* Employee information
* Department
* Project description
* Date and time of overtime
* Total working hours
* Approval sections for supervisor and HR

## Future Improvements

* REST API integration
* Email notifications for approvals
* Dashboard and analytics
* Overtime approval workflow
* Role-based authorization enhancements
* Docker deployment support

---

## Author

Apurba Rajyashree Thapa
