
DROP TABLE IF EXISTS `customers` CASCADE;
CREATE TABLE IF NOT EXISTS `customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) NOT NULL,
    `surname` VARCHAR(40) NOT NULL,
    PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `items` CASCADE;
CREATE TABLE IF NOT EXISTS `items` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `item_name` VARCHAR(40) NOT NULL,
    `price` DECIMAL(20, 2) NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE `orders` CASCADE;
CREATE TABLE IF NOT EXISTS `orders` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `customer_id` INT(11) NOT NULL,
    FOREIGN KEY (`customer_id`) REFERENCES `customers`(`id`),
    PRIMARY KEY (`id`)
);
DROP TABLE `order_items` CASCADE;
CREATE TABLE IF NOT EXISTS `order_items` (
`id` INT(11) NOT NULL auto_increment,
`order_id` INT(11) NOT NULL,
`item_id` INT(11) NOT NULL,
FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`),
FOREIGN KEY (`item_id`) REFERENCES `items`(`id`),
PRIMARY KEY (`id`)
);