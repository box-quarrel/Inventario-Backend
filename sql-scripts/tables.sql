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
DROP TABLE IF EXISTS PRICE_HISTORY;
DROP TABLE IF EXISTS COST_HISTORY;
DROP TABLE IF EXISTS Product_Supplier;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Authorities;

-- Enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Modify Categories Table
CREATE TABLE Categories (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            code VARCHAR(50) NOT NULL UNIQUE,  -- Ensure code is UNIQUE
                            description TEXT,
                            creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                            created_by VARCHAR(255),
                            last_update_by VARCHAR(255),
                            last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Modify Unit_of_Measures Table
CREATE TABLE Unit_of_Measures (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  name VARCHAR(255) NOT NULL,
                                  code VARCHAR(50) NOT NULL UNIQUE,  -- Ensure code is UNIQUE
                                  description TEXT,
                                  creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  created_by VARCHAR(255),
                                  last_update_by VARCHAR(255),
                                  last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Products Table
CREATE TABLE Products (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          code VARCHAR(50) NOT NULL UNIQUE,
                          description TEXT,
                          category_code VARCHAR(50),
                          primary_uom_code VARCHAR(50),
                          quantity INT,
                          current_cost DOUBLE, -- not used in pharmacy for example
                          current_price DOUBLE, -- should not be used in reporting. Should use the actual selling unit price in sales_lines table
                          barcode VARCHAR(50) UNIQUE,
                          image_url VARCHAR(255),
                          creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                          created_by VARCHAR(255),
                          last_update_by VARCHAR(255),
                          last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (category_code) REFERENCES Categories(code),
                          FOREIGN KEY (primary_uom_code) REFERENCES Unit_of_Measures(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create PRICE_HISTORY Table
CREATE TABLE PRICE_HISTORY (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               product_id BIGINT NOT NULL,
                               old_price DOUBLE NOT NULL,
                               new_price DOUBLE NOT NULL,
                               creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                               created_by VARCHAR(255),
                               last_update_by VARCHAR(255),
                               last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create COST_HISTORY Table
CREATE TABLE COST_HISTORY (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              product_id BIGINT NOT NULL,
                              old_cost DOUBLE NOT NULL,
                              new_cost DOUBLE NOT NULL,
                              creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                              created_by VARCHAR(255),
                              last_update_by VARCHAR(255),
                              last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Create Suppliers Table
CREATE TABLE Suppliers (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           contact_name VARCHAR(255),
                           contact_phone VARCHAR(50),
                           email VARCHAR(255),
                           address TEXT,
                           creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                           created_by VARCHAR(255),
                           last_update_by VARCHAR(255),
                           last_update_date DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Product_Supplier Table
CREATE TABLE Product_Supplier (
                                  product_id BIGINT,
                                  supplier_id BIGINT,
                                  PRIMARY KEY (product_id, supplier_id),
                                  FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE,
                                  FOREIGN KEY (supplier_id) REFERENCES Suppliers(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;;


-- Create Customers Table
CREATE TABLE Customers (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255),
                           phone VARCHAR(50),
                           address TEXT,
                           creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                           created_by VARCHAR(255),
                           last_update_by VARCHAR(255),
                           last_update_date DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Sales Table
CREATE TABLE Sales (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       sales_number varchar(50) unique NOT NULL,
                       net_amount DOUBLE NOT NULL,  -- based on the discount type, this can be calculated by two means: 1- total_amount - discount_value , 2- total_amount - (total_amount*discount_value)
                       total_amount DOUBLE NOT NULL,
                       discount_value DOUBLE,   -- either an actual amount value or a percentage value
                       discount_type VARCHAR(50),
                       customer_id BIGINT,
                       creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                       created_by VARCHAR(255),
                       last_update_by VARCHAR(255),
                       last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (customer_id) REFERENCES Customers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Sale_Lines Table
CREATE TABLE Sale_Lines (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            quantity INT NOT NULL,
                            sale_id BIGINT,
                            product_id BIGINT,
                            creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                            created_by VARCHAR(255),
                            last_update_by VARCHAR(255),
                            last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (sale_id) REFERENCES Sales(id),
                            FOREIGN KEY (product_id) REFERENCES Products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create PO_HEADER Table
CREATE TABLE PO_HEADER (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           po_number VARCHAR(50) UNIQUE NOT NULL,
                           po_status VARCHAR(50),
                           po_discount DOUBLE,
                           total_amount DOUBLE,
                           notes TEXT,
                           supplier_id BIGINT,
                           creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                           created_by VARCHAR(255),
                           last_update_by VARCHAR(255),
                           last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create PO_LINES Table
CREATE TABLE  PO_LINES (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           requested_quantity INT NOT NULL,
                           received_quantity INT NOT NULL,
                           unit_price DOUBLE NOT NULL,
                           po_header_id BIGINT,
                           product_id BIGINT,
                           creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                           created_by VARCHAR(255),
                           last_update_by VARCHAR(255),
                           last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           FOREIGN KEY (po_header_id) REFERENCES PO_HEADER(id),
                           FOREIGN KEY (product_id) REFERENCES Products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Returns Table
CREATE TABLE  PRODUCT_RETURNS (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  sale_id BIGINT,
                                  product_id BIGINT,
                                  quantity_returned INT NOT NULL,
                                  reason TEXT,
                                  creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  created_by VARCHAR(255),
                                  last_update_by VARCHAR(255),
                                  last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  FOREIGN KEY (sale_id) REFERENCES Sales(id),
                                  FOREIGN KEY (product_id) REFERENCES Products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Users Table
CREATE TABLE  Users (
                        username VARCHAR(50) PRIMARY KEY,
                        password VARCHAR(255) NOT NULL,
                        enabled BOOLEAN NOT NULL,
                        creation_date DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Authorities Table
CREATE TABLE  Authorities (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              username VARCHAR(50),
                              authority VARCHAR(50),
                              creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                              created_by VARCHAR(255),
                              CONSTRAINT unique_username_authority UNIQUE (username, authority),
                              FOREIGN KEY (username) REFERENCES Users(username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
