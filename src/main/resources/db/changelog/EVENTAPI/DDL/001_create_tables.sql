CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Creating the users table
CREATE TABLE events (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    date TIMESTAMP NOT NULL,
    location VARCHAR(255) NOT NULL,
    description VARCHAR(500)
);
