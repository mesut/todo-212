CREATE Table IF NOT EXISTS User(id SERIAL,
    username VARCHAR(255) NOT NULL, 
    password_hash VARCHAR(255) NOT NULL,
    UNIQUE KEY (username)
);