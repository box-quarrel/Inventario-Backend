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



-- Insert sample data into Categories table
INSERT INTO Categories (name, code, description, created_by, last_update_by)
VALUES
    ('Electronics', 'ELEC001', 'Electronic items and gadgets', 'admin', 'admin'),
    ('Groceries', 'GROC001', 'Food items and daily essentials', 'manager', 'manager'),
    ('Furniture', 'FURN001', 'Home and office furniture', 'admin', 'admin'),
    ('Clothing', 'CLOT001', 'Apparel for men, women, and children', 'admin', 'admin'),
    ('Pharmacy', 'PHAR001', 'Medicines and health products', 'manager', 'manager');



INSERT INTO Customers (name, email, phone, address, created_by, last_update_by)
VALUES
    ('John Doe', 'johndoe@example.com', '123-456-7890', '123 Main St, Springfield', 'admin', 'admin'),
    ('Jane Smith', 'janesmith@example.com', '098-765-4321', '456 Oak St, Rivertown', 'admin', 'admin'),
    ('Paul Brown', 'paulbrown@example.com', '567-890-1234', '789 Pine St, Hilltop', 'admin', 'admin');


INSERT INTO Suppliers (name, contact_name, contact_phone, email, address, created_by, last_update_by)
VALUES
    ('Global Supplies', 'Alice Green', '111-222-3333', 'alice@globalsupplies.com', '101 Industrial Rd, Metropolis', 'admin', 'admin'),
    ('Tech Distributors', 'Bob White', '444-555-6666', 'bob@techdistributors.com', '202 Technology Park, Innoville', 'admin', 'admin'),
    ('Office Essentials', 'Charlie Black', '777-888-9999', 'charlie@officeessentials.com', '303 Business St, Commerce City', 'admin', 'admin');

INSERT INTO Unit_of_Measures (name, code, description, created_by, last_update_by)
VALUES
    ('Kilogram', 'KG', 'Measurement for weight', 'admin', 'admin'),
    ('Liter', 'L', 'Measurement for volume', 'admin', 'admin'),
    ('Piece', 'PC', 'Individual unit measurement', 'admin', 'admin');


INSERT INTO Products (name, code, description, category_code, primary_uom_code, quantity, current_cost, current_price, barcode, image_url, created_by, last_update_by)
VALUES
    ('Laptop', 'PROD001', 'High-performance laptop', 'ELEC001', 'PC', 50, 800.00, 1000.00, '1234567890', 'http://example.com/laptop.jpg', 'admin', 'admin'),
    ('Desk Chair', 'PROD002', 'Ergonomic desk chair', 'FURN001', 'PC', 100, 50.00, 80.00, '0987654321', 'http://example.com/chair.jpg', 'admin', 'admin'),
    ('Smartphone', 'PROD003', 'Latest smartphone model', 'ELEC001', 'PC', 200, 300.00, 500.00, '1122334455', 'http://example.com/phone.jpg', 'admin', 'admin');



INSERT INTO Users (username, password, enabled) VALUES ('admin', '$2y$10$dhDiEkz5cCevbzn9LA7ieuf1/iSj49Q6UMZtHnTP/lBU42fzEMAGy', true);
INSERT INTO Users (username, password, enabled) VALUES ('manager', '$2y$10$dVtwm.6dB6bJ.ZcGUzo7MuhoIkDxECtW2/TFYrAhXlwExdcngDKmm', true);
INSERT INTO Users (username, password, enabled) VALUES ('user', '$2y$10$AKqnKpiz4KlT1h5tPEkDHeAuBvksoy.3Vh6OBbM.yWR4GuBYU4pce', true);


INSERT INTO Authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO Authorities (username, authority) VALUES ('admin', 'ROLE_MANAGER');
INSERT INTO Authorities (username, authority) VALUES ('admin', 'ROLE_USER');

INSERT INTO Authorities (username, authority) VALUES ('manager', 'ROLE_MANAGER');
INSERT INTO Authorities (username, authority) VALUES ('manager', 'ROLE_USER');

INSERT INTO Authorities (username, authority) VALUES ('user', 'ROLE_USER');