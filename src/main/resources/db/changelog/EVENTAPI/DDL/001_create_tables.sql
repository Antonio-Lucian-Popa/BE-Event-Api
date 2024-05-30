CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create events table
CREATE TABLE events (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    location VARCHAR(255),
    date TIMESTAMP
);

-- Create invitations table
CREATE TABLE invitations (
    id UUID PRIMARY KEY,
    event_id UUID REFERENCES events(id),
    email VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL
);
