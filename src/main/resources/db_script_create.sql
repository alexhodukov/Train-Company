DROP SCHEMA IF EXISTS `train_company`;

CREATE SCHEMA IF NOT EXISTS `train_company`
CHARACTER SET `utf8`;

USE `train_company`;

CREATE TABLE IF NOT EXISTS `train_company`.`roles` 
(
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` ENUM ('USER', 'ADMIN', 'SUPER_ADMIN') NOT NULL,
  PRIMARY KEY (`role_id`)
)
	CHARSET = utf8
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

CREATE TABLE IF NOT EXISTS `train_company`.`staff` 
(
  `contract_id` INT NOT NULL AUTO_INCREMENT,
  `staff_first_name` VARCHAR(64) NOT NULL,
  `staff_second_name` VARCHAR(64) NOT NULL,
  `staff_password` VARCHAR(64) NOT NULL,
  `staff_email` VARCHAR(128) NOT NULL UNIQUE,
  `role_id` INT NOT NULL,
  `staff_age` INT NOT NULL,
  `staff_registration_date` DATE NOT NULL,
  PRIMARY KEY (`contract_id`),
  CONSTRAINT `fk_staff_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `train_company`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
	CHARSET = utf8
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

CREATE TABLE IF NOT EXISTS `train_company`.`distributors` 
(
  `contract_id` INT NOT NULL AUTO_INCREMENT,
  `dist_first_name` VARCHAR(64) NOT NULL,
  `dist_second_name` VARCHAR(64) NOT NULL,
  `dist_password` VARCHAR(64) NOT NULL,
  `dist_email` VARCHAR(128) NOT NULL UNIQUE,
  `role_id` INT NOT NULL,
  `dist_age` INT NOT NULL,
  `dist_registration_date` DATE NOT NULL,
  `qualification` ENUM ('CLIENT','MANAGER','DIRECTOR','GOLD_DIRECTOR') NOT NULL,
  `leader_contract_id` INT NOT NULL,
  `dist_money` DOUBLE(6,2) DEFAULT 0.00,
  `left_node_number` INT NOT NULL,
  `right_node_number` INT NOT NULL,
  PRIMARY KEY (`contract_id`),
  CONSTRAINT `fk_dist_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `train_company`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
	CHARSET = utf8
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

CREATE TABLE IF NOT EXISTS `train_company`.`statuses` 
(
  `status_id` INT NOT NULL AUTO_INCREMENT,
  `status_name` ENUM ('ACTIVE', 'INACTIVE') NOT NULL,
  PRIMARY KEY (`status_id`)
)
	CHARSET = utf8
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

CREATE TABLE IF NOT EXISTS `train_company`.`categories` 
(
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_title` VARCHAR(64) NOT NULL,
  `category_description` VARCHAR(2048) NULL,
  PRIMARY KEY (`category_id`)
)
	ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8
	COLLATE = utf8_unicode_ci
;

CREATE TABLE IF NOT EXISTS `train_company`.`products` 
(
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `product_title` VARCHAR(64) NOT NULL,
  `product_description` VARCHAR(2048) NULL,
  `product_price` DOUBLE(6,2) NOT NULL,
  `product_quantity` INT NOT NULL,
  `product_bonus` INT NOT NULL,
  `category_id` INT NULL,
  `status_id` INT NOT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `fk_category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `train_company`.`categories` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_status_id`
    FOREIGN KEY (`status_id`)
    REFERENCES `train_company`.`statuses` (`status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
	CHARSET = utf8
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

CREATE TABLE IF NOT EXISTS `train_company`.`orders` 
(
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `order_date` DATE NOT NULL,
  `total_price` DOUBLE(6,2) NOT NULL,
  `total_bonus` INT NOT NULL,
  `contract_id` INT NOT NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `fk_contract_id`
    FOREIGN KEY (`contract_id`)
    REFERENCES `train_company`.`distributors` (`contract_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION
)
	CHARSET = utf8
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;
	
CREATE TABLE IF NOT EXISTS `train_company`.`order_details` 
(
  `details_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity_ordered` INT NOT NULL,
  `sum_price` DOUBLE(6,2) NOT NULL,
  `sum_bonus` INT NOT NULL,
  PRIMARY KEY (`details_id`),
  CONSTRAINT `fk_order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `train_company`.`orders` (`order_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `train_company`.`products` (`product_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION
)
	CHARSET = utf8
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

CREATE TABLE IF NOT EXISTS `train_company`.`reports` 
(
  `report_id` INT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `bonus` DOUBLE(6,2) NOT NULL,
  `personal_points` INT NOT NULL,
  `personal_group_points` INT NOT NULL,
  `group_points` INT NOT NULL,
  `contract_id` INT NOT NULL,
  PRIMARY KEY (`report_id`),
  CONSTRAINT `fk_report_contract_id`
    FOREIGN KEY (`contract_id`)
    REFERENCES `train_company`.`distributors` (`contract_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION
)
	CHARSET = utf8
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;
