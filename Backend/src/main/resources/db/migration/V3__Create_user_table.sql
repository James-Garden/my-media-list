CREATE TABLE users
(
    id UUID PRIMARY KEY,
    creation_timestamp TIMESTAMP WITH TIME ZONE,
    update_timestamp TIMESTAMP WITH TIME ZONE,
    display_name TEXT,
    email TEXT,
    password TEXT
);
