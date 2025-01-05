# Inventario Backend

**Inventario Backend** is a RESTful API designed to serve as the backend for a Point of Sale (POS) system. It manages products, sales, inventory, and user authentication, providing endpoints for various POS operations.

## Features

### Product Management
- **Create Product**: Add new products to the inventory.
- **Update Product**: Modify existing product details.
- **Delete Product**: Remove a product from the inventory.
- **Retrieve Product**: Get detailed information about a specific product.

### Sales Processing
- **Create Sale**: Process a sale and generate a sale record.
- **View Sales**: View historical sales records for analysis.
- **Update Sale**: Modify existing sale information if needed.
- **Delete Sale**: Remove a sale record.

### Inventory Management
- **Track Inventory**: Keep track of product stock levels.
- **Adjust Inventory**: Manually adjust stock levels for restocks, damages, etc.
- **Low Stock Alerts**: Receive notifications when stock levels fall below a threshold.
- **Inventory History**: Keep a log of inventory changes for audit purposes.

### User Authentication
- **Sign Up**: Register new users with roles such as admin, manager, or cashier.
- **Login**: Secure login with JWT-based authentication.
- **Role-based Access Control**: Different access levels based on user roles.

### Purchase Management
- **Create Purchase**: Record new purchase orders to restock inventory.
- **View Purchases**: Track historical purchases and their statuses.
- **Update Purchase**: Modify existing purchase orders if necessary.
- **Supplier Management**: Add, update, or delete supplier information linked to purchases.

### Customer Management
- **Create Customer**: Register new customers to track their purchase history.
- **Update Customer**: Modify existing customer information.
- **Delete Customer**: Remove a customer from the system.
- **View Customer**: Get detailed information about a customer, including their transactions.

### Supplier Management
- **Create Supplier**: Register new suppliers for inventory purchases.
- **Update Supplier**: Modify supplier contact details or products supplied.
- **Delete Supplier**: Remove a supplier from the system.
- **View Supplier**: Get detailed information about a supplier and their transactions.

### Reporting and Analytics
- **Sales Reports**: Generate reports of sales based on dates, products, or other filters.
- **Inventory Reports**: View reports on current inventory levels, product movement, etc.
- **Product Performance**: Analyze which products are performing well and which are not.

### Security and Data Integrity
- **JWT Authentication**: Secure RESTful API using JSON Web Tokens (JWT).
- **Role-based Access**: Limit access to endpoints based on user roles to ensure security.
- **Data Validation**: Ensure that input data is validated and sanitized before processing.


## Technologies Used

- **Java**: Primary programming language.
- **Spring Boot**: Framework for building the application.
- **Spring Security**: For authentication and authorization.
- **Hibernate**: ORM for database interactions.
- **MySQL**: Relational database management system.
- **Maven**: Build automation tool.

## Getting Started

### Prerequisites

- **Java 11** or higher installed.
- **MySQL** database setup.
- **Maven** installed.

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/box-quarrel/Inventario-Backend.git

2. **Navigate to the project directory**:
   ```bash
   cd Inventario-Backend
   
3. **Configure the database**:
   - Create a MySQL database named inventario
   - Update the database configuration in src/main/resources/application.properties:
     ```bash
       spring.datasource.url=jdbc:mysql://localhost:3306/inventario
       spring.datasource.username=your_username
       spring.datasource.password=your_password

4. **Configure your security key**:
   - Update the security configuration in src/main/resources/application.properties:
     ```bash
     jwt.secret=your_secret_key
     jwt.expiration=your_chosen_token_expiration_duration_in_milliseconds  

5. **Build the project**:
   ```bash
   mvn clean install

6. **Run the application**:
   ```bash
   mvn spring-boot:run

### API Documentation
The API endpoints are documented using Swagger.
Once the application is running, access the Swagger UI at:
http://localhost:8080/inventario/api/swagger-ui/index.html#/


### License
This project is licensed under the MIT License.

### Contact
For questions or support, please contact: abdulhamidmazroua@gmail.com
