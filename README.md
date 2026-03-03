# Pharma Trial Drug Tracking System

A complete Java console-based application designed to manage the core operations of a Pharmaceutical Clinical Trial. It allows administrators to track roles, users, clinical sites, and trial phases seamlessly using a relational database.

## Features

- **Role Management:**
  - Create, view, update, and delete user roles within the system.

- **Clinical Site Management:**
  - Manage various clinical sites where trials take place.
  - Track site details such as location, maximum capacity for doctors, and patients.

- **User Management:**
  - Register and manage users (Doctors, Researchers, Admins).
  - Associate users with specific roles, clinical sites, and hospitals.

- **Clinical Trial Management:**
  - Create and track clinical trials from start to finish.
  - Monitor phases, trial status (Ongoing, Completed), and associate trials with clinical sites.

## Project Structure

The project follows a standard MVC / DAO design pattern:

- **`src/connection/`**: Contains `DatabaseConnection.java` which manages the JDBC connection to the database.
- **`src/dao/`**: Data Access Object classes used for executing CRUD operations on the database (e.g., `RoleDAO`, `UserAccountDAO`, `ClinicalSiteDAO`, `ClinicalTrialDAO`).
- **`src/model/`**: POJO (Plain Old Java Object) entity classes representing the database tables (`Role`, `UserAccount`, `ClinicalSite`, `ClinicalTrial`).
- **`src/menu/`**: Contains `PharmaMenu.java`, the main execution point of the application featuring an interactive Command Line Interface (CLI).
- **`src/setup/`**: Contains `CreateTables.java` which handles the initial database schema setup and table creation.

## Prerequisites

- Java Development Kit (JDK) 17+
- A compatible relational database (e.g., MySQL, PostgreSQL)
- JDBC Driver installed and present in the classpath

## How to Run

1. Configure your database credentials in `src/connection/DatabaseConnection.java`.
2. Run `src/setup/CreateTables.java` as a Java Application to generate the required tables.
3. Run `src/menu/PharmaMenu.java` to start the interactive application console.
