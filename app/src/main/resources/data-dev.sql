-- Drop old tables if they exist
DROP TABLE IF EXISTS order_entity;
DROP TABLE IF EXISTS product_entity;

-- Create product table
CREATE TABLE product_entity (
    product_id VARCHAR(255) PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_desc TEXT,
    product_price NUMERIC(10,2) NOT NULL,
    product_stock INT NOT NULL CHECK (product_stock >= 0),
    category VARCHAR(100),
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create order table
CREATE TABLE order_entity (
    order_id VARCHAR(255) PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    total_price NUMERIC(10,2) NOT NULL,
    order_status VARCHAR(50) NOT NULL DEFAULT 'NEW',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_product FOREIGN KEY (product_id)
        REFERENCES product_entity(product_id)
);

-- Insert sample products
INSERT INTO product_entity (product_id, product_name, product_desc, product_price, product_stock, category, image_url)
VALUES
('PRD-01', 'Laptop', 'High performance laptop', 75000.00, 10, 'Electronics', 'http://example.com/laptop.jpg'),
('PRD-02', 'Smartphone', 'Latest Android smartphone', 25000.00, 25, 'Electronics', 'http://example.com/smartphone.jpg'),
('PRD-03', 'Headphones', 'Noise cancelling headphones', 5000.00, 50, 'Accessories', 'http://example.com/headphones.jpg');

-- Insert sample orders
INSERT INTO order_entity (order_id, product_id, quantity, total_price, order_status)
VALUES
('ORD-01', 'PRD-01', 2, 150000.00, 'NEW'),
('ORD-02', 'PRD-02', 1, 25000.00, 'NEW'),
('ORD-03', 'PRD-03', 3, 15000.00, 'NEW');
