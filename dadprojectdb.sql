-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 16, 2024 at 11:20 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mockdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `course_id` varchar(10) NOT NULL,
  `course_name` varchar(100) NOT NULL,
  `lecturer_id` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`course_id`, `course_name`, `lecturer_id`) VALUES
('C01', 'Mathematics', 'L01'),
('C02', 'Physics', 'L01'),
('C03', 'Chemistry', 'L02'),
('C04', 'Biology', 'L03'),
('C05', 'Computer Science', 'L04'),
('C06', 'History', 'L05'),
('C07', 'Geography', 'L06'),
('C08', 'English Literature', 'L07'),
('C09', 'Philosophy', 'L08'),
('C10', 'Sociology', 'L09');

-- --------------------------------------------------------

--
-- Table structure for table `lecturers`
--

CREATE TABLE `lecturers` (
  `lecturer_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lecturers`
--

INSERT INTO `lecturers` (`lecturer_id`, `name`, `email`, `password`) VALUES
('L01', 'Dr. John Doe', 'john.doe@gmail.com', 'john123'),
('L02', 'Prof. Jane Roe', 'jane.roe@gmail.com', 'jane245'),
('L03', 'Dr. Emily Clark', 'emily.clark@gmail.com', 'emily356'),
('L04', 'Prof. Michael Scott', 'michael.scott@gmail.com', 'micheal89'),
('L05', 'Dr. Sarah Lee', 'sarah.lee@gmail.com', 'sarah69'),
('L06', 'Prof. Andrew Kim', 'andrew.kim@gmail.com', 'andrew787'),
('L07', 'Dr. Laura King', 'laura.king@gmail.com', 'laura800'),
('L08', 'Prof. Daniel Wright', 'daniel.wright@gmail.com', 'daniel567'),
('L09', 'Dr. Linda Hall', 'linda.hall@gmail.com', 'linda007'),
('L10', 'Prof. Kevin Lewis', 'kevin.lewis@gmail.com', 'kevin420');

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE `results` (
  `result_id` int(11) NOT NULL,
  `student_id` varchar(10) DEFAULT NULL,
  `course_id` varchar(10) DEFAULT NULL,
  `score` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `results`
--

INSERT INTO `results` (`result_id`, `student_id`, `course_id`, `score`) VALUES
(1, 'S01', 'C01', 85.00),
(2, 'S01', 'C02', 90.00),
(3, 'S02', 'C01', 78.00),
(4, 'S02', 'C03', 88.50),
(5, 'S03', 'C02', 92.00),
(6, 'S03', 'C03', 85.50),
(7, 'S04', 'C01', 80.00),
(8, 'S04', 'C02', 70.00),
(9, 'S05', 'C03', 95.00),
(10, 'S05', 'C04', 89.00),
(11, 'S06', 'C05', 75.00),
(12, 'S06', 'C06', 82.00),
(13, 'S07', 'C07', 88.00),
(14, 'S07', 'C08', 91.00),
(15, 'S08', 'C09', 87.50),
(16, 'S08', 'C10', 84.00),
(17, 'S09', 'C01', 90.00),
(18, 'S09', 'C02', 86.50),
(19, 'S10', 'C03', 92.00),
(20, 'S10', 'C04', 78.00);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_id`, `name`, `email`, `password`) VALUES
('S01', 'Alice Johnson', 'alice@gmail.com', 'pass123'),
('S02', 'Bob Smith', 'bob@gmail.com', 'bob123'),
('S03', 'Carol White', 'carol@gmail.com', 'carol234'),
('S04', 'David Brown', 'david@gmail.com', 'david10'),
('S05', 'Eve Davis', 'eve@gmail.com', 'eve45'),
('S06', 'Frank Miller', 'frank@gmail.com', 'frank00'),
('S07', 'Grace Wilson', 'grace@gmail.com', 'grace2323'),
('S08', 'Hank Moore', 'hank@gmail.com', 'hank90'),
('S09', 'Ivy Taylor', 'ivy@gmail.com', 'ivy500'),
('S10', 'Jack Anderson', 'jack@gmail.com', 'jack3434');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`course_id`),
  ADD KEY `lecturer_id` (`lecturer_id`);

--
-- Indexes for table `lecturers`
--
ALTER TABLE `lecturers`
  ADD PRIMARY KEY (`lecturer_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `results`
--
ALTER TABLE `results`
  ADD PRIMARY KEY (`result_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `course_id` (`course_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
  MODIFY `result_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturers` (`lecturer_id`);

--
-- Constraints for table `results`
--
ALTER TABLE `results`
  ADD CONSTRAINT `results_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `results_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
