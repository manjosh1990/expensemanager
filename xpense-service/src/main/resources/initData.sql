use 'xpense';

delete from `category`;
-- dumping category data
insert into `category` (`name`) values ('FOOD&RESTAURANT');
insert into `category` (`name`) values ('BILLS&UTILITIES');
insert into `category` (`name`) values ('MEDICAL_EXPENSES&INSURANCE');
insert into `category` (`name`) values ('FUEL&VEHICLE_MAINTENANCE');
insert into `category` (`name`) values ('ENTERTAINMENT');
insert into `category` (`name`) values ('SHOPPING');
insert into `category` (`name`) values ('Adhoc&Others');
insert into `category` (`name`) values ('HOUSING');
insert into `category` (`name`) values ('INVESTMENT');
insert into `category` (`name`) values ('LOANS');


-- dumping food&restaurant category data
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Groceries', '1');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Veggies/Fruits', '1');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('KitchenItems', '1');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('DiningOut', '1');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('MilkCurdBakery', '1');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('EggsFish&Meat', '1');

-- dumping bills category data
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Electricity', '2');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('GAS', '2');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Water', '2');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Phone', '2');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Cable&DTH', '2');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Internet', '2');


-- dumping  medical expenses category data
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Doctor/Dentist', '3');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Medicine/Drugs', '3');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('MedicalInsurance', '3');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Checkup/Tests', '3');


-- dumping  Fuel&VehicleMaintenance category data
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('CarMaintenance', '4');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('TwoWheelerMaintenance', '4');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('BusTaxTrainFare', '4');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Tolls', '4');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Fuel', '4');


-- dumping  entertainment category data
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Netflix', '5');

-- dumping  shopping category data
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Clothing', '6');

-- dumping adhoc category data
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Gifts', '7');

-- dumping housing category data
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Maids', '8');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('DryCleaning', '8');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Maintainance&Repairs', '8');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Rent', '8');

-- dumping investments category data
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('MutualFunds', '9');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Equity', '9');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Assets', '9');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('Gold', '9');

-- dumping Loans category data
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('HomeLoan', '10');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('PersonalLoan', '10');
INSERT INTO `xpense_category` (`sub_category`, `fk_category`) VALUES ('HousingLoan', '10');







