DROP TABLE IF EXISTS `cars`;

CREATE TABLE `cars` (
  `id` VARCHAR(255) NOT NULL,
  `mileage` INT NOT NULL,
  `mpg` INT NOT NULL,
  `cost` INT NOT NULL,
  `salesPrice` INT DEFAULT NULL,
  `sold` INT DEFAULT NULL,
  `priceSold` INT DEFAULT NULL,
  `profit` INT DEFAULT NULL
);
