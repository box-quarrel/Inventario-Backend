-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables if they exist
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS unit_of_measures;
DROP TABLE IF EXISTS suppliers;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS sale_lines;
DROP TABLE IF EXISTS po_header;
DROP TABLE IF EXISTS po_lines;
DROP TABLE IF EXISTS product_returns;
DROP TABLE IF EXISTS price_history;
DROP TABLE IF EXISTS cost_history;
DROP TABLE IF EXISTS product_supplier;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;

-- Enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Modify categories Table
CREATE TABLE categories (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(50) NOT NULL,
                            code VARCHAR(50) NOT NULL UNIQUE,  -- Ensure code is UNIQUE
                            description VARCHAR(255),
                            creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                            created_by VARCHAR(255),
                            last_update_by VARCHAR(255),
                            last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Modify unit_of_measures Table
CREATE TABLE unit_of_measures (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  name VARCHAR(50) NOT NULL,
                                  code VARCHAR(50) NOT NULL UNIQUE,  -- Ensure code is UNIQUE
                                  description VARCHAR(255),
                                  creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  created_by VARCHAR(255),
                                  last_update_by VARCHAR(255),
                                  last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create products Table
CREATE TABLE products (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(50) NOT NULL,
                          code VARCHAR(50) NOT NULL UNIQUE,
                          description VARCHAR(255),
                          category_code VARCHAR(50),
                          primary_uom_code VARCHAR(50),
                          quantity INT NOT NULL,
                          current_cost DOUBLE,
                          current_price DOUBLE,
                          barcode VARCHAR(255) UNIQUE,
                          image_url VARCHAR(255),
                          creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                          created_by VARCHAR(255),
                          last_update_by VARCHAR(255),
                          last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (category_code) REFERENCES categories(code),
                          FOREIGN KEY (primary_uom_code) REFERENCES unit_of_measures(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create price_history Table
CREATE TABLE price_history (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               product_id BIGINT NOT NULL,
                               old_price DOUBLE NOT NULL,
                               new_price DOUBLE NOT NULL,
                               creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                               created_by VARCHAR(255),
                               last_update_by VARCHAR(255),
                               last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create cost_history Table
CREATE TABLE cost_history (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              product_id BIGINT NOT NULL,
                              old_cost DOUBLE NOT NULL,
                              new_cost DOUBLE NOT NULL,
                              creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                              created_by VARCHAR(255),
                              last_update_by VARCHAR(255),
                              last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create suppliers Table
CREATE TABLE suppliers (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(50) NOT NULL,
                           contact_name VARCHAR(255),
                           contact_phone VARCHAR(50),
                           email VARCHAR(255),
                           address VARCHAR(255),
                           creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                           created_by VARCHAR(255),
                           last_update_by VARCHAR(255),
                           last_update_date DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create product_supplier Table
CREATE TABLE product_supplier (
                                  product_id BIGINT,
                                  supplier_id BIGINT,
                                  PRIMARY KEY (product_id, supplier_id),
                                  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                  FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create customers Table
CREATE TABLE customers (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(50) NOT NULL,
                           email VARCHAR(255),
                           phone VARCHAR(50),
                           address TEXT,
                           creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                           created_by VARCHAR(255),
                           last_update_by VARCHAR(255),
                           last_update_date DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create sales Table
CREATE TABLE sales (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       sales_number VARCHAR(50) UNIQUE NOT NULL,
                       net_amount DOUBLE NOT NULL,
                       total_amount DOUBLE NOT NULL,
                       discount_value DOUBLE,
                       discount_type VARCHAR(50),
                       customer_id BIGINT,
                       creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                       created_by VARCHAR(255),
                       last_update_by VARCHAR(255),
                       last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (customer_id) REFERENCES customers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Continue the remaining tables with the same lowercase naming convention...

-- Create sale_lines table
CREATE TABLE sale_lines (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            quantity INT NOT NULL,
                            sale_id BIGINT,
                            product_id BIGINT,
                            creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                            created_by VARCHAR(255),
                            last_update_by VARCHAR(255),
                            last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (sale_id) REFERENCES sales(id),
                            FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create po_header table
CREATE TABLE po_header (
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
                           FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create po_lines table
CREATE TABLE po_lines (
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
                          FOREIGN KEY (po_header_id) REFERENCES po_header(id),
                          FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create product_returns table
CREATE TABLE product_returns (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 sale_id BIGINT,
                                 product_id BIGINT,
                                 quantity_returned INT NOT NULL,
                                 reason TEXT,
                                 creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 created_by VARCHAR(255),
                                 last_update_by VARCHAR(255),
                                 last_update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 FOREIGN KEY (sale_id) REFERENCES sales(id),
                                 FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create users table
CREATE TABLE users (
                       username VARCHAR(50) PRIMARY KEY,
                       password VARCHAR(255) NOT NULL,
                       enabled BOOLEAN NOT NULL,
                       creation_date DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create authorities table
CREATE TABLE authorities (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             username VARCHAR(50) NOT NULL,
                             authority VARCHAR(50) NOT NULL,
                             creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                             created_by VARCHAR(255),
                             CONSTRAINT unique_username_authority UNIQUE (username, authority),
                             FOREIGN KEY (username) REFERENCES users(username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



-- Insert sample data into Categories table
INSERT INTO categories (name, code, description, created_by, last_update_by)
VALUES
    ('Electronics', 'ELEC001', 'Electronic items and gadgets', 'admin', 'admin'),
    ('Groceries', 'GROC001', 'Food items and daily essentials', 'manager', 'manager'),
    ('Furniture', 'FURN001', 'Home and office furniture', 'admin', 'admin'),
    ('Clothing', 'CLOT001', 'Apparel for men, women, and children', 'admin', 'admin'),
    ('Pharmacy', 'PHAR001', 'Medicines and health products', 'manager', 'manager');



INSERT INTO customers (name, email, phone, address, created_by, last_update_by)
VALUES
    ('John Doe', 'johndoe@example.com', '123-456-7890', '123 Main St, Springfield', 'admin', 'admin'),
    ('Jane Smith', 'janesmith@example.com', '098-765-4321', '456 Oak St, Rivertown', 'admin', 'admin'),
    ('Paul Brown', 'paulbrown@example.com', '567-890-1234', '789 Pine St, Hilltop', 'admin', 'admin');


INSERT INTO suppliers (name, contact_name, contact_phone, email, address, created_by, last_update_by)
VALUES
    ('Global Supplies', 'Alice Green', '111-222-3333', 'alice@globalsupplies.com', '101 Industrial Rd, Metropolis', 'admin', 'admin'),
    ('Tech Distributors', 'Bob White', '444-555-6666', 'bob@techdistributors.com', '202 Technology Park, Innoville', 'admin', 'admin'),
    ('Office Essentials', 'Charlie Black', '777-888-9999', 'charlie@officeessentials.com', '303 Business St, Commerce City', 'admin', 'admin');

INSERT INTO unit_of_measures (name, code, description, created_by, last_update_by)
VALUES
    ('Kilogram', 'KG', 'Measurement for weight', 'admin', 'admin'),
    ('Liter', 'L', 'Measurement for volume', 'admin', 'admin'),
    ('Piece', 'PC', 'Individual unit measurement', 'admin', 'admin');


INSERT INTO products (name, code, description, category_code, primary_uom_code, quantity, current_cost, current_price, barcode, image_url, created_by, last_update_by)
VALUES
    ('Laptop', 'PROD001', 'High-performance laptop', 'ELEC001', 'PC', 50, 800.00, 1000.00, '1234567890', 'http://example.com/laptop.jpg', 'admin', 'admin'),
    ('Desk Chair', 'PROD002', 'Ergonomic desk chair', 'FURN001', 'PC', 100, 50.00, 80.00, '0987654321', 'http://example.com/chair.jpg', 'admin', 'admin'),
    ('Smartphone', 'PROD003', 'Latest smartphone model', 'ELEC001', 'PC', 200, 300.00, 500.00, '1122334455', 'http://example.com/phone.jpg', 'admin', 'admin');



INSERT INTO users (username, password, enabled) VALUES ('admin', '$2y$10$dhDiEkz5cCevbzn9LA7ieuf1/iSj49Q6UMZtHnTP/lBU42fzEMAGy', true);
INSERT INTO users (username, password, enabled) VALUES ('manager', '$2y$10$dVtwm.6dB6bJ.ZcGUzo7MuhoIkDxECtW2/TFYrAhXlwExdcngDKmm', true);
INSERT INTO users (username, password, enabled) VALUES ('user', '$2y$10$AKqnKpiz4KlT1h5tPEkDHeAuBvksoy.3Vh6OBbM.yWR4GuBYU4pce', true);


INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_MANAGER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');

INSERT INTO authorities (username, authority) VALUES ('manager', 'ROLE_MANAGER');
INSERT INTO authorities (username, authority) VALUES ('manager', 'ROLE_USER');

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');