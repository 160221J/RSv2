-- ==============================
-- CREATE TABLE: products
-- ==============================
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    barcode VARCHAR(100) UNIQUE,
    category VARCHAR(100),
    cost_price NUMERIC(10,2) NOT NULL,
    selling_price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    expiry_date DATE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);


-- ==============================
-- CREATE TABLE: stock_movements
-- ==============================
CREATE TABLE stock_movements (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    type VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    cost_price_at_time NUMERIC(10,2),
    selling_price_at_time NUMERIC(10,2),
    note TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);


-- ==============================
-- CREATE TABLE: sales
-- ==============================
CREATE TABLE sales (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    quantity INT NOT NULL,
    sale_price NUMERIC(10,2) NOT NULL,
    profit NUMERIC(10,2) NOT NULL,
    sold_at TIMESTAMP DEFAULT NOW()
);


-- ==============================
-- CREATE TABLE: expenses
-- ==============================
CREATE TABLE expenses (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    date DATE NOT NULL DEFAULT CURRENT_DATE
);


-- ==============================
-- CREATE TABLE: withdrawals
-- ==============================
CREATE TABLE withdrawals (
    id SERIAL PRIMARY KEY,
    person VARCHAR(100),
    amount NUMERIC(10,2) NOT NULL,
    date DATE NOT NULL DEFAULT CURRENT_DATE
);
