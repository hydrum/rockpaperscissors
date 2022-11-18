CREATE TABLE `game` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `player_name` VARCHAR(50) NOT NULL,
    `player_choice` VARCHAR(255) NOT NULL,
    `cpu_choice` VARCHAR(255) NOT NULL,
    `outcome` VARCHAR(255) NOT NULL,
    `created_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`)
);