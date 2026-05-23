-- Insert sample products
INSERT INTO product_entity (product_id, product_name, product_desc, product_price, product_stock, category, image_url)
VALUES ('PRD-01', 'Laptop', 'High performance laptop', 75000.00, 10, 'Electronics', 'http://example.com/laptop.jpg')
ON CONFLICT (product_id) DO NOTHING;

INSERT INTO product_entity (product_id, product_name, product_desc, product_price, product_stock, category, image_url)
VALUES ('PRD-02', 'Smartphone', 'Latest Android smartphone', 25000.00, 25, 'Electronics', 'http://example.com/smartphone.jpg')
ON CONFLICT (product_id) DO NOTHING;

INSERT INTO product_entity (product_id, product_name, product_desc, product_price, product_stock, category, image_url)
VALUES ('PRD-03', 'Headphones', 'Noise cancelling headphones', 5000.00, 50, 'Accessories', 'http://example.com/headphones.jpg')
ON CONFLICT (product_id) DO NOTHING;

-- Insert sample orders
INSERT INTO order_entity (order_id, product_id, quantity, total_price, order_status)
VALUES ('ORD-01', 'PRD-01', 2, 150000.00, 'NEW')
ON CONFLICT (order_id) DO NOTHING;

INSERT INTO order_entity (order_id, product_id, quantity, total_price, order_status)
VALUES ('ORD-02', 'PRD-02', 1, 25000.00, 'NEW')
ON CONFLICT (order_id) DO NOTHING;

INSERT INTO order_entity (order_id, product_id, quantity, total_price, order_status)
VALUES ('ORD-03', 'PRD-03', 3, 15000.00, 'NEW')
ON CONFLICT (order_id) DO NOTHING;
