
-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables if they exist
DROP TABLE IF EXISTS Categories;
DROP TABLE IF EXISTS Unit_of_Measures;
DROP TABLE IF EXISTS Suppliers;
DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Sales;
DROP TABLE IF EXISTS Sale_Lines;
DROP TABLE IF EXISTS PO_HEADER;
DROP TABLE IF EXISTS PO_LINES;
DROP TABLE IF EXISTS PRODUCT_RETURNS;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Authorities;

-- Enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Create Categories Table
CREATE TABLE Categories (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            code VARCHAR(50) UNIQUE NOT NULL,
                            description TEXT,
                            creation_date DATETIME,
                            created_by VARCHAR(255),
                            last_update_by VARCHAR(255),
                            last_update_date DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Unit_of_Measures Table
CREATE TABLE Unit_of_Measures (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  name VARCHAR(255) NOT NULL,
                                  code VARCHAR(50) UNIQUE NOT NULL,
                                  description TEXT,
                                  creation_date DATETIME,
                                  created_by VARCHAR(255),
                                  last_update_by VARCHAR(255),
                                  last_update_date DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Products Table
CREATE TABLE Products (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          code VARCHAR(50) NOT NULL,
                          barcode VARCHAR(50) UNIQUE,
                          description TEXT,
                          primary_uom_code VARCHAR(50),
                          current_cost DECIMAL(10,2), -- not used in pharmacy for example
                          current_price DECIMAL(10,2), -- should not be used in reporting. Should use the actual selling unit price in sales_lines table
                          quantity INT,
                          image VARCHAR(255),
                          category_code VARCHAR(50),
                          creation_date DATETIME,
                          created_by VARCHAR(255),
                          last_update_by VARCHAR(255),
                          last_update_date DATETIME,
                          FOREIGN KEY (category_code) REFERENCES Categories(code),
                          FOREIGN KEY (primary_uom_code) REFERENCES Unit_of_Measures(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Suppliers Table
CREATE TABLE Suppliers (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           contact_name VARCHAR(255),
                           contact_phone VARCHAR(50),
                           email VARCHAR(255),
                           address TEXT,
                           creation_date DATETIME,
                           created_by VARCHAR(255),
                           last_update_by VARCHAR(255),
                           last_update_date DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Product_Supplier Table
CREATE TABLE Product_Supplier (
                                  product_id INT,
                                  supplier_id INT,
                                  PRIMARY KEY (product_id, supplier_id),
                                  FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE,
                                  FOREIGN KEY (supplier_id) REFERENCES Suppliers(id) ON DELETE CASCADE
);


-- Create Customers Table
CREATE TABLE Customers (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255),
                           phone VARCHAR(50),
                           address TEXT,
                           creation_date DATETIME,
                           created_by VARCHAR(255),
                           last_update_by VARCHAR(255),
                           last_update_date DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Sales Table
CREATE TABLE Sales (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       sales_number varchar(50) unique NOT NULL,
                       total_amount DECIMAL(10,2) NOT NULL,
                       discount DECIMAL(10,2),
                       customer_id INT,
                       creation_date DATETIME,
                       created_by VARCHAR(255),
                       last_update_by VARCHAR(255),
                       last_update_date DATETIME,
                       FOREIGN KEY (customer_id) REFERENCES Customers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Sale_Lines Table
CREATE TABLE Sale_Lines (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            quantity INT NOT NULL,
                            unit_price DECIMAL(10,2) NOT NULL,
                            sale_id INT,
                            product_id INT,
                            creation_date DATETIME,
                            created_by VARCHAR(255),
                            last_update_by VARCHAR(255),
                            last_update_date DATETIME,
                            FOREIGN KEY (sale_id) REFERENCES Sales(id),
                            FOREIGN KEY (product_id) REFERENCES Products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create PO_HEADER Table
CREATE TABLE PO_HEADER (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           po_number VARCHAR(50) UNIQUE NOT NULL,
                           po_status VARCHAR(50),
                           po_discount DECIMAL(10,2),
                           total_amount DECIMAL(10,2),
                           notes TEXT,
                           supplier_id INT,
                           creation_date DATETIME,
                           created_by VARCHAR(255),
                           last_update_by VARCHAR(255),
                           last_update_date DATETIME,
                           FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create PO_LINES Table
CREATE TABLE  PO_LINES (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           quantity INT NOT NULL,
                           unit_price DECIMAL(10,2) NOT NULL,
                           po_header_id INT,
                           product_id INT,
                           creation_date DATETIME,
                           created_by VARCHAR(255),
                           last_update_by VARCHAR(255),
                           last_update_date DATETIME,
                           FOREIGN KEY (po_header_id) REFERENCES PO_HEADER(id),
                           FOREIGN KEY (product_id) REFERENCES Products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Returns Table
CREATE TABLE  PRODUCT_RETURNS (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  customer_id INT,
                                  product_id INT,
                                  quantity_returned INT NOT NULL,
                                  reason TEXT,
                                  creation_date DATETIME,
                                  created_by VARCHAR(255),
                                  last_update_by VARCHAR(255),
                                  last_update_date DATETIME,
                                  FOREIGN KEY (customer_id) REFERENCES Customers(id),
                                  FOREIGN KEY (product_id) REFERENCES Products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Users Table
CREATE TABLE  Users (
                        username VARCHAR(50) PRIMARY KEY,
                        password VARCHAR(255) NOT NULL,
                        enabled BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Authorities Table
CREATE TABLE  Authorities (
                              username VARCHAR(50),
                              authority VARCHAR(50),
                              PRIMARY KEY (username, authority),
                              FOREIGN KEY (username) REFERENCES Users(username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
