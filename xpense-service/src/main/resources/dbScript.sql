DROP DATABASE IF EXISTS `xpense`;
CREATE DATABASE IF NOT EXISTS `xpense`;
use `xpense`;

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(256) NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `xpense_category`;
CREATE TABLE IF NOT EXISTS `xpense_category`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `sub_category` varchar(256) NOT NULL,
    `fk_category` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_category_key` FOREIGN KEY (`fk_category`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(256) NOT NULL,
    `type` ENUM('BANK','CREDIT_CARD','WALLET','PAYLATER','CASH'),
    `opening_balance`  DECIMAL(10,2) NOT NULL,
    `current_balance` DECIMAL(10,2) DEFAULT 0,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `xpense_transactions`;
CREATE TABLE IF NOT EXISTS `xpense_transactions`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `name` varchar(256) NOT NULL,
    `type` ENUM('BANK','CREDIT_CARD','WALLET','PAYLATER') NOT NULL,
    `decription` varchar(1024) NOT NULL,
    `fk_xpense_category` int(11) NOT NULL,
    `withdrawal_amount` DECIMAL(10,2),
    `deposit_amount` DECIMAL(10,2),
    `current_balance` DECIMAL(10,2) DEFAULT 0,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_xpense_category_key` FOREIGN KEY (`fk_xpense_category`) REFERENCES `xpense_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);


