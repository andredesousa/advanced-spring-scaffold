CREATE TABLE IF NOT EXISTS "users" (
	id serial PRIMARY KEY,
	username VARCHAR (20) UNIQUE NOT NULL,
	password VARCHAR (65) NOT NULL,
	email VARCHAR (255) UNIQUE NOT NULL
);

INSERT INTO "users" (id, username, password, email) 
    VALUES (1, 'admin', '$2b$10$jkzR/NI9PCgA3UXhx5T6WOqPJkzhTGAJY/5Z0txIfRt57ThjqfSOe', 'admin@admin');
