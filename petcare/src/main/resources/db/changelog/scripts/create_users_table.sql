CREATE TABLE IF NOT EXISTS users (
    id BINARY(16) PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    name VARCHAR(32),
    surname VARCHAR(32),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(10)
);