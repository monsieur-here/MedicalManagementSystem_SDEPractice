-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: medical_management
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `Admin_id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`Admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10011 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'Siddharth Dyavanapalli','Siddharth.d@medcare.com','Siddharth@123'),(2,'Dakshitha Ancha','Dakshitha.a@medcare.com','Dakshitha!45'),(3,'Joshal Pinto','Joshal.p@medcare.com','Joshal@55'),(4,'Nandan Muruli','Nandan.m@medcare.com','Nandan@22'),(5,'Kiran Raghupathi','Kiran.r@medcare.com','Kiran@789');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `Appointment_id` int NOT NULL AUTO_INCREMENT,
  `Slot` datetime NOT NULL,
  `Patient_id` int DEFAULT NULL,
  `Patient_name` varchar(45) DEFAULT NULL,
  `Visit_description` varchar(400) NOT NULL,
  `doctor_id` int NOT NULL,
  `doctor_name` varchar(45) NOT NULL,
  `Specialist` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`Appointment_id`),
  UNIQUE KEY `Appointment_id_UNIQUE` (`Appointment_id`),
  KEY `patient_id_idx` (`Patient_id`),
  KEY `doctor_id_idx` (`doctor_id`),
  CONSTRAINT `doctor_id` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `patient_id` FOREIGN KEY (`Patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,'2025-04-10 09:00:00',101,'Anna Müller','Initial consultation for persistent cough and fatigue. Patient reported symptoms for two weeks.',8,'Dr. David Lehmann','General','Attended'),(2,'2025-04-15 14:30:00',105,'Sophie Becker','Follow-up for rash on arm. Rash has slightly improved with prescribed cream.',10,'Dr. Peter Wagner','Dermatology','Attended'),(3,'2025-04-22 11:00:00',103,'Lena Weber','Therapy session for anxiety. Discussed coping mechanisms and stress reduction techniques.',1,'Dr. Lena Schneider','Therapist','Attended'),(4,'2025-05-02 10:00:00',108,'David Wagner','Pre-operative consultation for knee surgery. Reviewed surgical procedure and recovery plan.',2,'Dr. Max Richter','Surgeon','Attended'),(5,'2025-05-08 16:00:00',102,'Max Schmidt','Radiology scan for suspected fracture. Patient reported severe pain after a fall.',4,'Dr. Thomas Weber','Radiology','Attended'),(6,'2025-05-15 09:30:00',106,'Thomas Klein','Routine pediatric check-up. Child is healthy and vaccinations are up to date.',6,'Dr. Lukas Braun','Pediatrics','Attended'),(7,'2025-05-20 13:00:00',109,'Marie Hoffmann','Gynecological examination. Discussed family planning options.',7,'Dr. Julia Nowak','Gynecologist','Attended'),(8,'2025-05-28 10:30:00',104,'Lukas Fischer','Physiotherapy session for back pain. Performed exercises and stretches.',5,'Dr. Anna König','Physiotherapist','Attended'),(9,'2025-06-05 11:30:00',107,'Julia Schulz','General check-up. Patient expressed concerns about high blood pressure readings.',9,'Dr. Marie Schulz','General','Missed'),(10,'2025-06-10 15:00:00',110,'Peter Neumann','Emergency appointment for sudden severe headache. Patient reported blurred vision.',8,'Dr. David Lehmann','General','Attended');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `bill_id` int NOT NULL AUTO_INCREMENT,
  `appointment_id` int NOT NULL,
  `patient_name` varchar(45) NOT NULL,
  `Insurance_type` varchar(15) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_method` varchar(45) NOT NULL,
  `billing_date` date NOT NULL,
  PRIMARY KEY (`bill_id`),
  UNIQUE KEY `bill_id_UNIQUE` (`bill_id`),
  KEY `appointment_id_idx` (`appointment_id`),
  CONSTRAINT `appointment_id` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`Appointment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,1,'Anna Müller','Public',85.50,'Credit Card','2025-04-10'),(2,2,'Sophie Becker','Private',120.00,'Cash','2025-04-15'),(3,3,'Lena Weber','Public',95.75,'Credit Card','2025-04-22'),(4,4,'David Wagner','Private',550.25,'Cash','2025-05-02'),(5,5,'Max Schmidt','No Insurance',310.00,'Credit Card','2025-05-08'),(6,6,'Thomas Klein','Public',70.00,'Cash','2025-05-15'),(7,7,'Marie Hoffmann','Private',180.50,'Credit Card','2025-05-20'),(8,8,'Lukas Fischer','Public',115.00,'Cash','2025-05-28'),(9,9,'Julia Schulz','No Insurance',90.00,'Credit Card','2025-06-05'),(10,10,'Peter Neumann','Private',250.75,'Cash','2025-06-10');
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combined_medical_record`
--

DROP TABLE IF EXISTS `combined_medical_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combined_medical_record` (
  `medical_record_id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `patient_name` varchar(60) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `appointment_slot` datetime NOT NULL,
  `Doctor_name` varchar(45) NOT NULL,
  `Specialization` varchar(45) NOT NULL,
  `prescription_id` int NOT NULL,
  `Bill_amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`medical_record_id`),
  KEY `fkn_patient_id_idx` (`patient_id`),
  KEY `fkn_prescription_id_idx` (`prescription_id`),
  CONSTRAINT `fkn_patient_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `fkn_prescription_id` FOREIGN KEY (`prescription_id`) REFERENCES `prescription` (`prescription_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combined_medical_record`
--

LOCK TABLES `combined_medical_record` WRITE;
/*!40000 ALTER TABLE `combined_medical_record` DISABLE KEYS */;
INSERT INTO `combined_medical_record` VALUES (1,101,'Anna Müller','anna.mueller@gmail.com','2025-04-10 09:00:00','Dr. David Lehmann','General',201,85.50),(2,105,'Sophie Becker','sophie.becker@gmail.com','2025-04-15 14:30:00','Dr. Peter Wagner','Dermatology',202,120.00),(3,103,'Lena Weber','lena.weber@gmail.com','2025-04-22 11:00:00','Dr. Lena Schneider','Therapist',203,95.75),(4,108,'David Wagner','david.wagner@gmail.com','2025-05-02 10:00:00','Dr. Max Richter','Surgeon',204,550.25),(5,102,'Max Schmidt','max.schmidt@gmail.com','2025-05-08 16:00:00','Dr. Thomas Weber','Radiology',205,310.00),(6,106,'Thomas Klein','thomas.klein@gmail.com','2025-05-15 09:30:00','Dr. Lukas Braun','Pediatrics',206,70.00),(7,109,'Marie Hoffmann','marie.hoffmann@gmail.com','2025-05-20 13:00:00','Dr. Julia Nowak','Gynecologist',207,180.50),(8,104,'Lukas Fischer','lukas.fischer@gmail.com','2025-05-28 10:30:00','Dr. Anna König','Physiotherapist',208,115.00),(9,107,'Julia Schulz','julia.schulz@gmail.com','2025-06-05 11:30:00','Dr. Marie Schulz','General',209,90.00),(10,110,'Peter Neumann','peter.neumann@gmail.com','2025-06-10 15:00:00','Dr. David Lehmann','General',210,250.75);
/*!40000 ALTER TABLE `combined_medical_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `doctor_id` int NOT NULL AUTO_INCREMENT,
  `doctor_name` varchar(45) NOT NULL,
  `Gender` varchar(45) NOT NULL,
  `Specialization` varchar(45) NOT NULL,
  `Contact_Number` varchar(15) NOT NULL,
  `Availability` varchar(45) NOT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'Dr. Lena Schneider','Female','Therapist','+4917611223344','Monday-Friday 9:00-17:00'),(2,'Dr. Marc Lehmann','Male','Surgeon','+4915155667788','Tuesday-Saturday 8:00-16:00'),(3,'Dr. Sarah Koch','Female','Surgeon','+4916099887766','Monday-Friday 10:00-18:00'),(4,'Dr. Paul Richter','Male','Radiology','+4917033221100','Monday-Thursday 8:30-16:30'),(5,'Dr. Clara Wolf','Female','Physiotherapist','+4917744556677','Wednesday-Sunday 9:00-17:00'),(6,'Dr. Felix Bergmann','Male','Pediatrics','+4915222334455','Monday-Friday 9:00-17:00'),(7,'Dr. Eva Fuchs','Female','Gynecologist','+4917688776655','Tuesday-Saturday 8:00-16:00'),(8,'Dr. Jonas Gross','Male','General','+4915111223344','Monday-Friday 10:00-18:00'),(9,'Dr. Emilia Hoffmann','Female','General','+4916055443322','Monday-Thursday 8:30-16:30'),(10,'Dr. Leon Keller','Male','Dermatology','+4917099887766','Wednesday-Sunday 9:00-17:00'),(11,'Dr. Hannah Bauer','Female','Therapist','+4917877665544','Monday-Friday 9:00-17:00'),(12,'Dr. David Vogel','Male','Surgeon','+4915711223344','Tuesday-Saturday 8:00-16:00'),(13,'Dr. Laura Schneider','Female','Pediatrics','+4917622334455','Monday-Friday 10:00-18:00'),(14,'Dr. Moritz Jung','Male','Radiology','+4915133445566','Monday-Thursday 8:30-16:30'),(15,'Dr. Sophia Schwarz','Female','Physiotherapist','+4916300112233','Wednesday-Sunday 9:00-17:00');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `patient_name` varchar(45) NOT NULL,
  `Gender` varchar(45) NOT NULL,
  `Date_of_Birth` date NOT NULL,
  `Contact_Number` varchar(15) NOT NULL,
  `Address` varchar(45) NOT NULL,
  `Insurance_type` varchar(30) NOT NULL,
  `doctor_id` int NOT NULL,
  `patient_history` varchar(400) NOT NULL,
  `patient_email` varchar(100) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`patient_id`),
  KEY `fk_doctor_id_idx` (`doctor_id`),
  CONSTRAINT `fk_doctor_id` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20008 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (101,'Anna Müller','Female','1985-03-15','+4917612345678','Hauptstr. 12, 69115 Heidelberg','Public',8,'Initial consultation for persistent cough and fatigue. Patient reported symptoms for two weeks.','anna.mueller@gmail.com','securepass1'),(102,'Max Schmidt','Male','1978-11-22','+4915198765432','Berliner Ring 45, 76137 Karlsruhe','No Insurance',4,'Radiology scan for suspected fracture. Patient reported severe pain after a fall.','max.schmidt@gmail.com','mypassword2'),(103,'Lena Weber','Female','1992-07-01','+4916023456789','Goethestr. 7, 70173 Stuttgart','Public',1,'Therapy session for anxiety. Discussed coping mechanisms and stress reduction techniques.','lena.weber@gmail.com','lenapass3'),(104,'Lukas Fischer','Male','1965-09-10','+4917087654321','Friedrichstr. 88, 80331 München','Public',5,'Physiotherapy session for back pain. Performed exercises and stretches.','lukas.fischer@gmail.com','lukaspw4'),(105,'Sophie Becker','Female','2000-01-25','+4917734567890','Schillerstr. 3, 20095 Hamburg','Private',10,'Follow-up for rash on arm. Rash has slightly improved with prescribed cream.','sophie.becker@gmail.com','sophiepass5'),(106,'Thomas Klein','Male','1980-04-18','+4915245678901','Kaiserstr. 1, 52062 Aachen','Public',6,'Routine pediatric check-up. Child is healthy and vaccinations are up to date.','thomas.klein@gmail.com','thomaskl6'),(107,'Julia Schulz','Female','1995-02-28','+4917890123456','Dorfstr. 22, 10115 Berlin','No Insurance',9,'General check-up. Patient expressed concerns about high blood pressure readings.','julia.schulz@gmail.com','juliapw7'),(108,'David Wagner','Male','1972-08-05','+4917356789012','Rosenweg 9, 04103 Leipzig','Private',2,'Pre-operative consultation for knee surgery. Reviewed surgical procedure and recovery plan.','david.wagner@gmail.com','davidw8'),(109,'Marie Hoffmann','Female','1988-12-12','+4916301234567','Am Markt 15, 40213 Düsseldorf','Private',7,'Gynecological examination. Discussed family planning options.','marie.hoffmann@gmail.com','marieh9'),(110,'Peter Neumann','Male','1960-06-30','+4915789012345','Bachgasse 5, 90403 Nürnberg','Private',8,'Emergency appointment for sudden severe headache. Patient reported blurred vision.','peter.neumann@gmail.com','peterne10');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescription` (
  `prescription_id` int NOT NULL,
  `appointment_id` int NOT NULL,
  `prescription_name` varchar(200) NOT NULL,
  `medication` varchar(100) NOT NULL,
  `dosage` varchar(45) NOT NULL,
  `frequency` varchar(45) NOT NULL,
  `date_issued` date NOT NULL,
  `criticality` varchar(45) NOT NULL,
  PRIMARY KEY (`prescription_id`),
  KEY `fk_appointment_id_idx` (`appointment_id`),
  CONSTRAINT `fk_appointment_id` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`Appointment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES (201,1,'For persistent cough and fatigue related to a common cold.','Ibuprofen','200mg','Twice daily','2025-04-10','Low'),(202,2,'Topical treatment for skin rash on the arm.','Hydrocortisone cream','1%','Apply thinly twice daily','2025-04-15','Medium'),(203,3,'To help manage anxiety symptoms.','Sertraline','50mg','Once daily in the morning','2025-04-22','High'),(204,4,'Post-operative pain management for knee surgery.','Tramadol','50mg','Every 6 hours as needed','2025-05-02','High'),(205,5,'To alleviate pain and swelling from suspected fracture.','Naproxen','250mg','Twice daily with food','2025-05-08','Medium'),(206,6,'Routine vitamin supplement for child development.','Multivitamin drops','5ml','Once daily','2025-05-15','Low'),(207,7,'Oral contraceptive for family planning.','Ethinyl estradiol/Levonorgestrel','0.03mg/0.15mg','Once daily','2025-05-20','Medium'),(208,8,'Muscle relaxant for acute back pain.','Cyclobenzaprine','10mg','Three times daily','2025-05-28','Medium'),(209,9,'To help control elevated blood pressure.','Lisinopril','10mg','Once daily','2025-06-05','High'),(210,10,'For severe headache and blurred vision.','Sumatriptan','100mg','Once at onset of headache','2025-06-10','High');
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration` (
  `Patient_name` varchar(60) NOT NULL,
  `Gender` varchar(45) NOT NULL,
  `Contact_number` varchar(15) NOT NULL,
  `Date_of_birth` date NOT NULL,
  `Address` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `patient_id` int NOT NULL,
  PRIMARY KEY (`Contact_number`),
  KEY `fk1_patient_id_idx` (`patient_id`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk1_patient_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO `registration` VALUES ('Max Schmidt','Male','+4915198765432','1978-11-22','Berliner Ring 45, 76137 Karlsruhe','max.schmidt@gmail.com','mypassword2',102),('Thomas Klein','Male','+4915245678901','1980-04-18','Kaiserstr. 1, 52062 Aachen','thomas.klein@gmail.com','thomaskl6',106),('Peter Neumann','Male','+4915789012345','1960-06-30','Bachgasse 5, 90403 Nürnberg','peter.neumann@gmail.com','peterne10',110),('Lena Weber','Female','+4916023456789','1992-07-01','Goethestr. 7, 70173 Stuttgart','lena.weber@gmail.com','lenapass3',103),('Marie Hoffmann','Female','+4916301234567','1988-12-12','Am Markt 15, 40213 Düsseldorf','marie.hoffmann@gmail.com','marieh9',109),('Lukas Fischer','Male','+4917087654321','1965-09-10','Friedrichstr. 88, 80331 München','lukas.fischer@gmail.com','lukaspw4',104),('David Wagner','Male','+4917356789012','1972-08-05','Rosenweg 9, 04103 Leipzig','david.wagner@gmail.com','davidw8',108),('Anna Müller','Female','+4917612345678','1985-03-15','Hauptstr. 12, 69115 Heidelberg','anna.mueller@gmail.com','securepass1',101),('Sophie Becker','Female','+4917734567890','2000-01-25','Schillerstr. 3, 20095 Hamburg','sophie.becker@gmail.com','sophiepass5',105),('Julia Schulz','Female','+4917890123456','1995-02-28','Dorfstr. 22, 10115 Berlin','julia.schulz@gmail.com','juliapw7',107);
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` int NOT NULL AUTO_INCREMENT,
  `room_availability` varchar(45) NOT NULL,
  `room_type` varchar(45) NOT NULL,
  `room_number` varchar(45) NOT NULL,
  `patient_id` int DEFAULT NULL,
  `patient_name` varchar(65) DEFAULT NULL,
  `Check_in` datetime DEFAULT NULL,
  `Check_out` datetime DEFAULT NULL,
  PRIMARY KEY (`room_id`),
  KEY `fk2_patient_id_idx` (`patient_id`),
  CONSTRAINT `fk2_patient_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'Occupied','Private','P-301',102,'Max Schmidt','2025-05-08 17:00:00','2025-05-12 10:00:00'),(2,'Occupied','General','G-102',110,'Peter Neumann','2025-06-10 16:00:00','2025-06-13 11:00:00'),(3,'Available','Semi-Private','SP-201',NULL,NULL,NULL,NULL),(4,'Occupied','General','G-101',108,'David Wagner','2025-05-01 08:00:00','2025-05-05 14:00:00'),(5,'Available','Private','P-302',NULL,NULL,NULL,NULL),(6,'Occupied','General','G-103',104,'Lukas Fischer','2025-05-27 09:00:00','2025-05-30 12:00:00'),(7,'Available','Semi-Private','SP-202',NULL,NULL,NULL,NULL),(8,'Occupied','Private','P-303',107,'Julia Schulz','2025-06-04 10:00:00','2025-06-08 10:00:00'),(9,'Available','General','G-104',NULL,NULL,NULL,NULL),(10,'Occupied','General','G-105',101,'Anna Müller','2025-04-09 13:00:00','2025-04-12 11:00:00');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff_support`
--

DROP TABLE IF EXISTS `staff_support`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff_support` (
  `Staff_id` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(45) NOT NULL,
  `Staff_name` varchar(45) NOT NULL,
  `Gender` varchar(45) NOT NULL,
  `contact_number` varchar(15) NOT NULL,
  `staff_availability` varchar(45) NOT NULL,
  `shift_start` time NOT NULL DEFAULT '09:00:00',
  `shift_end` time NOT NULL DEFAULT '17:00:00',
  `staff_email` varchar(100) NOT NULL,
  `password` varchar(45) NOT NULL,
  `specialist` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Staff_id`),
  UNIQUE KEY `Staff_id_UNIQUE` (`Staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30037 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_support`
--

LOCK TABLES `staff_support` WRITE;
/*!40000 ALTER TABLE `staff_support` DISABLE KEYS */;
INSERT INTO `staff_support` VALUES (1,'Nurse','Lisa Schmidt','Female','+4917211223344','Monday-Friday','08:00:00','16:00:00','lisa.schmidt@clinic.com','nursepass1',NULL),(2,'Receptionist','Markus Müller','Male','+4917322334455','Monday-Friday','09:00:00','17:00:00','markus.muller@clinic.com','receptionpass2',NULL),(3,'Emergency Nurse','Sabine Bauer','Female','+4917433445566','Tuesday-Saturday','10:00:00','18:00:00','sabine.bauer@clinic.com','emergencypass3','Emergency Care'),(4,'Care Taker','Julian Koch','Male','+4917544556677','Wednesday-Sunday','07:00:00','15:00:00','julian.koch@clinic.com','caretakerpass4',NULL),(5,'Nurse','Franziska Weber','Female','+4917655667788','Monday, Wednesday, Friday','12:00:00','20:00:00','franziska.weber@clinic.com','nursepass5',NULL),(6,'Lab Technician','Christoph Richter','Male','+4917766778899','Monday-Friday','09:00:00','17:00:00','christoph.richter@clinic.com','labtechpass6','Medical Diagnostics'),(7,'Nurse','Laura Becker','Female','+4917877889900','Tuesday, Thursday, Saturday','08:00:00','16:00:00','laura.becker@clinic.com','nursepass7',NULL),(8,'Care Taker','Sebastian Schulz','Male','+4917988990011','Monday-Friday','14:00:00','22:00:00','sebastian.schulz@clinic.com','caretakerpass8',NULL),(9,'Nurse','Daniela Hoffmann','Female','+4915199001122','Wednesday-Sunday','07:00:00','15:00:00','daniela.hoffmann@clinic.com','nursepass9',NULL),(10,'Care Taker','Florian Wagner','Male','+4915200112233','Monday-Friday','22:00:00','06:00:00','florian.wagner@clinic.com','caretakerpass10',NULL);
/*!40000 ALTER TABLE `staff_support` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-11 20:08:50
