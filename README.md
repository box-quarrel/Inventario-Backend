# Inventario Backend ![CI/CD Pipeline](https://github.com/box-quarrel/Inventario-Backend/actions/workflows/pipeline.yml/badge.svg)

**Inventario Backend** is a RESTful API designed to serve as the backend for a Point of Sale (POS) system. It manages products, sales, inventory, and user authentication, providing endpoints for various POS operations.

## Overview

- **📦 Product Management**: Create, update, delete, and retrieve products.}
- **🛒 Sales Processing**: Create, view, update, and delete sales.
- **📊 Inventory Management**: Track, adjust, and log inventory; receive low stock alerts. 
- **🔐 User Authentication**: Sign up, login, and role-based access control. 
- **🧾 Purchase Management**: Create, view, update purchases; manage suppliers. 
- **👥 Customer Management**: Create, update, delete, and view customers. 
- **🚚 Supplier Management**: Create, update, delete, and view suppliers. 
- **📈 Reporting and Analytics**: Generate sales and inventory reports; analyze product performance. 
- **📈 Security and Data Integrity**: JWT authentication, role-based access, and data validation. 
Here are some badges you can use for your README:


## Technologies Used

- ![CI/CD Pipeline](https://github.com/box-quarrel/Inventario-Backend/actions/workflows/pipeline.yml/badge.svg)
- ![Java](https://img.shields.io/badge/Java-22-blue.svg)
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen.svg)
- ![Hibernate](https://img.shields.io/badge/Hibernate-5.5.7-yellow.svg)
- ![MySQL](https://img.shields.io/badge/MySQL-8.4-orange.svg)
- ![Maven](https://img.shields.io/badge/Maven-3.9-purple.svg)
- ![Lombok](https://img.shields.io/badge/Lombok-1.18.20-red.svg)
- ![MapStruct](https://img.shields.io/badge/MapStruct-1.4.2-lightgrey.svg)
- ![OpenAPI](https://img.shields.io/badge/OpenAPI-3.1.3-blue.svg)
- ![Swagger](https://img.shields.io/badge/Swagger-3.0.0-brightgreen.svg)
- ![JUnit](https://img.shields.io/badge/JUnit-5.7.0-red.svg)
- ![Mockito](https://img.shields.io/badge/Mockito-3.6.28-yellow.svg)
- ![Docker](https://img.shields.io/badge/Docker-20.10.7-blue.svg)
- ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI/CD-brightgreen.svg)
- ![License](https://img.shields.io/badge/License-MIT-green.svg)

## Getting Started

### Prerequisites

- **Java 22** or higher installed.
- **MySQL 8.4** database setup.
- **Maven** installed.

### Installation
I would like to make the installation based on creating the .env file
and adding the defined variables
Also using the init_sql.sql file to create the database and the tables
lets begin
1. **Clone the repository**:
   ```bash
   git clone https://github.com/box-quarrel/Inventario-Backend.git

    cd Inventario-Backend
    ```
2. **Create the database and tables**:
   ```bash
   mysql -u your_db_username -p < src/main/resources/init_sql.sql
   ```
   
3. **Create a `.env` file**:
   ```bash
   touch .env
   ```
   
4. **Add the following environment variables to the `.env` file**:
   ```properties
    SPRING_PROFILES_ACTIVE=dev
    DB_HOST=localhost
    DB_NAME=inventario_directory (you can change the name but change also in the init_sql.sql file)
    DB_USERNAME=your_db_username
    DB_PASSWORD=your_db_password
    SECRETE_KEY=your_secrete_key
    ```
5. **If you want to run on docker-compose you can add the following variables also to the `.env` file. OPTIONAL STEP** :
   ```properties
    MYSQL_ROOT_PASSWORD=root
    MYSQL_DATABASE=inventario_directory (you can change the name but change also in the init_sql.sql file)
    MYSQL_USER=your_db_username
    MYSQL_PASSWORD=your_db_password
    ```
   ```
6. **Build and run the application**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
    
### API Documentation
The API endpoints are documented using Swagger.
To use the Swagger UI, follow these steps:
1. **once the application is running, navigate to the following URL**:
   ```
   http://localhost:8080/inventario/api/swagger-ui/index.html
   ```

2. **Go to Authentication Endpoint and use the following credentials to login**:
    ```json
    {
      "username": "admin",
      "password": "admin"
    }
    ```
    OR
    ```json
    {
      "username": "manager",
      "password": "manager"
    }
    ```
    OR
    ```json
    {
      "username": "user",
      "password": "user"
    }
    ```
3. **Click on the `Authorize` button and paste the token in the following format**:
    ```
    Bearer <token here>
    ```

### License
This project is licensed under the MIT License.

### Contact
For questions or support, please contact me on the following contact details:
[![LinkedIn](https://img.shields.io/badge/-LinkedIn-blue?style=flat&logo=Linkedin&logoColor=white)]([https://www.linkedin.com/in/abdulhamid-mazroua/])
[![Email](https://img.shields.io/badge/-Email-c14438?style=flat&logo=Gmail&logoColor=white)](mailto:[abdulhamidmazroua@gmail.com])
