-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 20, 2021 at 06:39 AM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cakeland`
--

-- --------------------------------------------------------

--
-- Table structure for table `cake`
--

CREATE TABLE `cake` (
  `CakeID` char(7) NOT NULL,
  `CakeName` varchar(50) NOT NULL,
  `CakePrice` int(11) NOT NULL,
  `CakeSize` varchar(20) NOT NULL,
  `CakeShape` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cake`
--

INSERT INTO `cake` (`CakeID`, `CakeName`, `CakePrice`, `CakeSize`, `CakeShape`) VALUES
('COF5686', 'Cheese Cake', 165000, '15 cm', 'Oval'),
('CON1360', 'Mocca Cake', 120000, '20 cm', 'Oval'),
('CON8164', 'Carrot Cake', 170000, '20 cm', 'Oval'),
('COV6627', 'Pandan Cake', 125000, '25 cm', 'Oval'),
('COV6658', 'Marble Cake', 150000, '25 cm', 'Oval'),
('CRH0661', 'Fruit Cake', 210000, '30 x 30 cm', 'Rectangle'),
('CRH1677', 'Rainbow Cake', 160000, '30 x 30 cm', 'Rectangle'),
('CRT2925', 'Lapis Cake', 220000, '10 x 10 cm', 'Rectangle'),
('CRW5203', 'Black Forest Cake', 160000, '20 x 20 cm', 'Rectangle'),
('CRW7418', 'Chiffon Cake', 140000, '20 x 20 cm', 'Rectangle');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `UserID` char(5) NOT NULL,
  `CakeID` char(7) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`UserID`, `CakeID`, `Quantity`) VALUES
('U5165', 'CON1360', 3),
('U5165', 'COV6658', 4);

-- --------------------------------------------------------

--
-- Table structure for table `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `TransactionID` char(5) NOT NULL,
  `CakeID` char(7) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactiondetail`
--

INSERT INTO `transactiondetail` (`TransactionID`, `CakeID`, `Quantity`) VALUES
('T8587', 'COF5686', 3),
('T8587', 'CON8164', 2),
('T8587', 'CRW5203', 4),
('T6728', 'CON1360', 3),
('T6728', 'CRW5203', 2);

-- --------------------------------------------------------

--
-- Table structure for table `transactionheader`
--

CREATE TABLE `transactionheader` (
  `TransactionID` char(5) NOT NULL,
  `UserID` char(5) NOT NULL,
  `TransactionDate` date NOT NULL,
  `PickUpDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactionheader`
--

INSERT INTO `transactionheader` (`TransactionID`, `UserID`, `TransactionDate`, `PickUpDate`) VALUES
('T6728', 'U7498', '2021-05-20', '2021-05-23'),
('T8587', 'U5165', '2021-05-19', '2021-05-22');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` char(5) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `UserEmail` varchar(50) NOT NULL,
  `UserPassword` varchar(50) NOT NULL,
  `UserGender` varchar(10) NOT NULL,
  `UserDOB` date NOT NULL,
  `UserPhoneNumber` varchar(20) NOT NULL,
  `UserAddress` varchar(100) NOT NULL,
  `UserRole` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `Username`, `UserEmail`, `UserPassword`, `UserGender`, `UserDOB`, `UserPhoneNumber`, `UserAddress`, `UserRole`) VALUES
('A0001', 'admin', 'admin@lala.com', 'admin123', 'Female', '2002-07-10', '08123456789', 'Lalala Street', 'Admin'),
('U5165', 'hehehe', 'hehe@lalala.com', 'hehe123', 'Male', '2001-10-24', '08192839829', 'Lilili Street', 'User'),
('U7498', 'haihai', 'hihihi@haha.com', 'hehee124', 'Male', '2002-06-16', '089103812931', 'Hehehe Hahaha Street', 'User');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cake`
--
ALTER TABLE `cake`
  ADD PRIMARY KEY (`CakeID`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD KEY `UserID` (`UserID`),
  ADD KEY `CakeID` (`CakeID`);

--
-- Indexes for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD KEY `CakeID` (`CakeID`),
  ADD KEY `TransactionID` (`TransactionID`);

--
-- Indexes for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`CakeID`) REFERENCES `cake` (`CakeID`),
  ADD CONSTRAINT `cart_ibfk_3` FOREIGN KEY (`CakeID`) REFERENCES `cake` (`CakeID`);

--
-- Constraints for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD CONSTRAINT `transactiondetail_ibfk_1` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionID`),
  ADD CONSTRAINT `transactiondetail_ibfk_2` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionID`),
  ADD CONSTRAINT `transactiondetail_ibfk_3` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionID`);

--
-- Constraints for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD CONSTRAINT `transactionheader_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
