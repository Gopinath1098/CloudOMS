-- Create product table
CREATE TABLE IF NOT EXISTS product_entity (
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
CREATE TABLE IF NOT EXISTS order_entity (
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