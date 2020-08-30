-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Aug 26, 2020 at 04:15 PM
-- Server version: 5.7.25
-- PHP Version: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `user`
--

-- --------------------------------------------------------

--
-- Table structure for table `rock_paper_scissors_user`
--

CREATE TABLE `rock_paper_scissors_user` (
  `username` varchar(100) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;