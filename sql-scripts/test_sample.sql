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



INSERT INTO users (username, password, enabled) VALUES ('admin', '$2y$10$dhDiEkz5cCevbzn9LA7ieuf1/iSj49Q6UMZtHnTP/lBU42fzEMAGy', true);
INSERT INTO users (username, password, enabled) VALUES ('manager', '$2y$10$dVtwm.6dB6bJ.ZcGUzo7MuhoIkDxECtW2/TFYrAhXlwExdcngDKmm', true);
INSERT INTO users (username, password, enabled) VALUES ('user', '$2y$10$AKqnKpiz4KlT1h5tPEkDHeAuBvksoy.3Vh6OBbM.yWR4GuBYU4pce', true);


INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_MANAGER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');

INSERT INTO authorities (username, authority) VALUES ('manager', 'ROLE_MANAGER');
INSERT INTO authorities (username, authority) VALUES ('manager', 'ROLE_USER');

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');