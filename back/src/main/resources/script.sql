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
    `auteur` varchar(255),
    `created_at` timestamp,
    `updated_at` timestamp
);

CREATE TABLE `THEMES` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL
);

CREATE TABLE `SUBSCRIPTIONS` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` integer NOT NULL,
    `theme_id` BIGINT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`theme_id`) REFERENCES `THEMES` (`id`) ON DELETE CASCADE
);

CREATE TABLE `COMMENTS` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `article_id` INT NOT NULL,
    `comment` VARCHAR(255),
    `created_at` timestamp,
    FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`article_id`) REFERENCES `ARTICLES` (`id`) ON DELETE CASCADE
);

ALTER TABLE `ARTICLES` ADD FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `ARTICLES` ADD theme_id BIGINT;
ALTER TABLE `ARTICLES` ADD CONSTRAINT fk_theme FOREIGN KEY (theme_id) REFERENCES THEMES(id);
