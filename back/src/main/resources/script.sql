CREATE TABLE USERS (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `email` varchar(255),
    `username` varchar(255),
    `password` varchar(255),
    `created_at` timestamp,
    `updated_at` timestamp
);

CREATE TABLE `ARTICLES` (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `titre` varchar(255),
    `contenu` varchar(255),
    `owner_id` integer NOT NULL,
    `created_at` timestamp,
    `updated_at` timestamp
);

ALTER TABLE `ARTICLES` ADD FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`);
