CREATE TABLE IF NOT EXISTS products (
    id             BIGSERIAL PRIMARY KEY,
    account_number BIGINT         NOT NULL,
    balance        DECIMAL(10, 2) NOT NULL,
    product_type   VARCHAR(100)   NOT NULL,
    user_id        BIGINT REFERENCES users(id)
    );