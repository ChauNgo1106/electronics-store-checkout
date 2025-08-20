-- 🖥️ Electronic Products
INSERT INTO product (id, name, category, price, stock, available)
VALUES
        (1, 'Samsung Galaxy S22', 'Smartphones', 899.99, 20, true),
        (2, 'Apple MacBook Air M2', 'Laptops', 1199.00, 15, true),
        (3, 'Sony WH-1000XM5 Headphones', 'Audio', 349.99, 30, true),
        (4, 'LG OLED TV 55"', 'Televisions', 1299.00, 10, true),
        (5, 'Logitech MX Master 3 Mouse', 'Accessories', 99.99, 50, true);

-- 🎁 Deals with Expiration
INSERT INTO deal (id, product_id, type, expiration)
VALUES
        (1, 1, 'BUY_ONE_GET_50_PERCENT_OFF_SECOND', '2025-08-25T23:59:00'),
        (2, 3, 'BUY_TWO_GET_ONE_FREE', '2025-08-22T18:00:00'),
        (3, 5, '10_PERCENT_OFF', '2025-09-01T00:00:00');
