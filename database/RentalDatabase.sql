-- MySQL Workbench Forward Engineering

-- SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
-- SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
-- SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema VehicleRentalSystem
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema VehicleRentalSystem
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `VehicleRentalSystem` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `VehicleRentalSystem` ;

-- -----------------------------------------------------
-- Table `VehicleRentalSystem`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VehicleRentalSystem`.`User` (
  `username` VARCHAR(15) NOT NULL,
  `hash` VARCHAR(64) NOT NULL,
  `salt` VARCHAR(32) NOT NULL,
  `admin` TINYINT NOT NULL DEFAULT '0',
  `insurance` DOUBLE NULL DEFAULT '0',
  PRIMARY KEY (`username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `VehicleRentalSystem`.`Vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VehicleRentalSystem`.`Vehicle` (
  `vehicle_id` INT NOT NULL AUTO_INCREMENT,
  `fuel_type` VARCHAR(45) NOT NULL,
  `transmission_type` VARCHAR(45) NOT NULL,
  `rent` DOUBLE NULL DEFAULT '0',
  `special_details` JSON NOT NULL,
  `count` INT NOT NULL DEFAULT '0',
  `type` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `VehicleRentalSystem`.`RentalHistory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VehicleRentalSystem`.`RentalHistory` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `vehicle_id` INT NOT NULL,
  `rental_date` DATETIME NOT NULL,
  `return_date` DATETIME NULL DEFAULT NULL,
  `regn_number` VARCHAR(45) NOT NULL,
  `rented` TINYINT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `regn_number_UNIQUE` (`regn_number` ASC) VISIBLE,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  INDEX `vehicle_id` (`vehicle_id` ASC) VISIBLE,
  CONSTRAINT `rentalhistory_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `VehicleRentalSystem`.`User` (`username`),
  CONSTRAINT `rentalhistory_ibfk_2`
    FOREIGN KEY (`vehicle_id`)
    REFERENCES `VehicleRentalSystem`.`Vehicle` (`vehicle_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `VehicleRentalSystem`;

DELIMITER $$
USE `VehicleRentalSystem`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `VehicleRentalSystem`.`RentalHistory_AFTER_DELETE`
AFTER DELETE ON `VehicleRentalSystem`.`RentalHistory`
FOR EACH ROW
BEGIN
    UPDATE Vehicle 
    SET count = count + 1 
    WHERE vehicle_id = OLD.vehicle_id;
END$$


DELIMITER ;

-- SET SQL_MODE=@OLD_SQL_MODE;
-- SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
-- SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
