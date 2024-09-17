CREATE TABLE USERS (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `email` varchar(255),
    `username` varchar(255),
    `password` varchar(255),
    `created_at` timestamp,
    `updated_at` timestamp
);