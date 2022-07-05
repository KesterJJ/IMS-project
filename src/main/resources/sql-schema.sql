DROP DATABASE ims;

CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims` ;

CREATE TABLE IF NOT EXISTS `ims`.`customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) NOT NULL,
    `surname` VARCHAR(40) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`items` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `item_name` VARCHAR(40) NOT NULL,
    `price` DECIMAL(20, 2) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`orders` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `customer_id` INT(11) NOT NULL,
    FOREIGN KEY (`customer_id`) REFERENCES `ims`.`customers`(`id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`order_items` (
`id` INT(11) NOT NULL auto_increment,
`order_id` INT(11) NOT NULL,
`item_id` INT(11) NOT NULL,
FOREIGN KEY (`order_id`) REFERENCES `ims`.`orders`(`id`),
FOREIGN KEY (`item_id`) REFERENCES `ims`.`items`(`id`),
PRIMARY KEY (`id`)
);