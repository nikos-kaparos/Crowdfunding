CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    role VARCHAR(255),
    enabled BOOLEAN
    );

INSERT INTO users (id, username, password, email, role, enabled) VALUES (0, 'nikos', '1234', 'nikos@exmpale.com','admin', TRUE);


