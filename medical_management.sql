use doctor_management;
desc doctor;

insert into doctor values (2, "Ross", "Cardiology", "15098640981", "Yes", 30003);
update staff_support set staff_id = 30016 where staff_id = 300016;
DELETE FROM admin WHERE Admin_id = 156;
update staff_support set availability = 'no' where staff_id=30016;

INSERT INTO staff_support (availability) VALUES ('1965-01-01');
select * from staff_support;

INSERT INTO patient (
    patient_id, name, date_of_birth, contact_number, address,
    staff_id, doctor_id,
     patient_history
) VALUES
(20003,'John Doe', '1990-05-14', '9876543210', '123 Elm Street, NY', 30001, 01, 'No prior issues'),
(20004,'Alice Smith', '1985-03-22', '9123456780', '456 Oak Avenue, CA', 30002, 02, 'Had chickenpox in 2020'),
(20005,'Bob Johnson', '1978-11-10', '9988776655', '789 Maple Drive, TX', 30001, 01,  'History of hypertension'),
(20006,'Emily Davis', '2000-08-30', '9001122334', '101 Pine Road, FL', 30004, 03,  'Allergic to penicillin' ),
(20007,'Chris Evans', '1992-07-18', '8887766554', '202 Cedar Blvd, WA', 30003, 04, 'Irritable Bowel Syndrome');

INSERT INTO Admin (Admin_id, Username, Password, Email) VALUES
( 10004, 'Carol_004', 'Carol$789', 'carol04@gmail.com'),
(10005, 'David_005', 'David*321', 'david05@gmail.com'),
(10006, 'Eve_006', 'Eve@654', 'eve06@gmail.com'),
(10007, 'Frank_007', 'Frank!987', 'frank07@gmail.com'),
(10008, 'Grace_008', 'Grace#111', 'grace08@gmail.com'),
(10009, 'Hank_009', 'Hank$222', 'hank09@gmail.com'),
(10010, 'Ivy_010', 'Ivy*333', 'ivy10@gmail.com');

INSERT INTO Doctor (doctor_id, Name, Specialization, Contact_Number, Availability, Staff_id) VALUES

(3, 'Dr. Meghan', 'Pediatrics', '9876543210', 'yes', 30002),
(4, 'Dr. Harry', 'Cardiology', '9988776655', 'yes', 30004),
(5, 'Dr. Frank', 'Neurology', '9090909090', 'no', 30005),
(6, 'Dr. Anjali', 'Dermatology', '9871122334', 'yes', 30006),
(7, 'Dr. Karan', 'ENT', '9321456789', 'yes', 30007),
(8, 'Dr. Jennifer', 'Psychiatry', '9445566778', 'no', 30008),
(9, 'Dr. Carol', 'Radiology', '9556677889', 'yes', 30009),
(10, 'Dr. Susan', 'Oncology', '9667788990', 'yes', 30010);


INSERT INTO Appointment (Appointment_id, Date_of_visit, Visit_description, Patient_id) VALUES
(1, '2025-01-12', 'Routine annual physical examination and general wellness check.', 20001),
(2, '2025-02-05', 'Complaints of persistent migraines; advised MRI scan.', 20002),
(3, '2025-03-17', 'Consultation for mild fever and fatigue. Advised rest and fluids.', 20003),
(4, '2025-04-03', 'Follow-up on chickenpox history; skin sensitivity observed.', 20004),
(5, '2025-04-27', 'Blood pressure monitoring and hypertension medication adjustment.', 20005),
(6, '2025-05-09', 'Reported skin rash; allergy suspected. Prescribed antihistamines.', 20006),
(7, '2025-05-20', 'Gastrointestinal discomfort; discussed IBS management.', 20007);

INSERT INTO Bill (bill_id, patient_id, Name, Insurance, amount, payment_method, billing_date, doctor_id, visit_description) VALUES
(1, 20001, 'Claire', 'Yes', 120.50, 'Credit Card', '2025-01-12', 1, 'Routine annual physical examination and general wellness check.'),
(2, 20002, 'Helena_Rossi', 'No', 230.00, 'Cash', '2025-02-05', 3, 'Complaints of persistent migraines; advised MRI scan.'),
(3, 20003, 'John Doe', 'Yes', 89.99, 'Debit Card', '2025-03-17', 1, 'Consultation for mild fever and fatigue. Advised rest and fluids.'),
(4, 20004, 'Alice Smith', 'Yes', 150.75, 'Insurance', '2025-04-03', 2, 'Follow-up on chickenpox history; skin sensitivity observed.'),
(5, 20005, 'Bob Johnson', 'Yes', 175.20, 'Credit Card', '2025-04-27', 1, 'Blood pressure monitoring and hypertension medication adjustment.'),
(6, 20006, 'Emily Davis', 'No', 98.00, 'Cash', '2025-05-09', 4, 'Reported skin rash; allergy suspected. Prescribed antihistamines.'),
(7, 20007, 'Chris Evans', 'Yes', 210.00, 'Insurance', '2025-05-20', 3, 'Gastrointestinal discomfort; discussed IBS management.');


INSERT INTO Diagnosis (patient_id, diagnosis_id, dosage, date_of_diagnosis) VALUES
(20001, 1, '500mg Paracetamol', '2025-01-12'),
(20002, 2, '250mg Sumatriptan', '2025-02-05'),
(20003, 3, '10ml Cough Syrup', '2025-03-17'),
(20004, 4, 'Antihistamine 5mg', '2025-04-03'),
(20005, 5, '5mg Amlodipine', '2025-04-27'),
(20006, 6, 'Hydrocortisone Cream', '2025-05-09'),
(20007, 7, 'Loperamide 2mg', '2025-05-20');

INSERT INTO Medication (medication_id, medication_name, dosage, frequency) VALUES
(1, 'Paracetamol', '500mg', 'Twice a day'),
(2, 'Sumatriptan', '250mg', 'Once as needed'),
(3, 'Cough Syrup', '10ml', 'Thrice a day'),
(4, 'Cetirizine', '5mg', 'Once daily'),
(5, 'Amlodipine', '5mg', 'Once daily'),
(6, 'Hydrocortisone Cream', 'Apply thin layer', 'Twice daily'),
(7, 'Loperamide', '2mg', 'After each loose stool');

INSERT INTO registration (Id, Name, Contact_number, Date_of_Birth, Address) VALUES
(1, 'Claire', '15699876541', '1983-03-09', 'Heidelberg'),
(2, 'Helena_Rossi', '15598321756', '1980-01-19', 'Mannheim'),
(3, 'John Doe', '9876543210', '1990-05-14', '123 Elm Street, NY'),
(4, 'Alice Smith', '9123456780', '1985-03-22', '456 Oak Avenue, CA'),
(5, 'Bob Johnson', '9988776655', '1978-11-10', '789 Maple Drive, TX'),
(6, 'Emily Davis', '9001122334', '2000-08-30', '101 Pine Road, FL'),
(7, 'Chris Evans', '8887766554', '1992-07-18', '202 Cedar Blvd, WA');


INSERT INTO Room (room_id, room_type, room_number, patient_id, patient_name, Staff_id) VALUES
(101, 'General', 'G-101', 20001, 'Claire', 30001),
(102, 'Private', 'P-102', 20002, 'Helena_Rossi', 30003),
(103, 'ICU', 'ICU-03', 20003, 'John Doe', 30001),
(104, 'Semi-Private', 'SP-104', 20004, 'Alice Smith', 30002),
(105, 'General', 'G-105', 20005, 'Bob Johnson', 30001),
(106, 'Private', 'P-106', 20006, 'Emily Davis', 30004),
(107, 'ICU', 'ICU-07', 20007, 'Chris Evans', 30003);


INSERT INTO Staff_support (Staff_id, designation, Name, contact_number) VALUES
(30007, 'Radiologist', 'Dr. Sophie Braun', '15577889900'),
(30008, 'Physiotherapist', 'Markus Fischer', '15522334455'),
(30009, 'Billing Clerk', 'Nina Hartmann', '15566772288'),
(30010, 'Janitor', 'Peter Lange', '15599887766'),
(30011, 'Security', 'Thomas Roth', '15588443322'),
(30012, 'Dietician', 'Laura König', '15599001122'),
(30013, 'IT Support', 'Daniel Becker', '15577665544'),
(30014, 'Emergency Nurse', 'Katrin Müller', '15566558899'),
(30015, 'Lab Technician', 'Lena Weiss', '15511223344'),
(300016, 'Pharmacist', 'David Schmitt', '15566778899');


INSERT INTO Admin (Admin_id, name, designation, Username, Password, Email) VALUES
(1, 'Maria Klein', 'Nurse', 'mklein_admin', 'Nurse@123', 'maria.klein@medcare.com'),
(2, 'Tom Berger', 'Receptionist', 'tberger_admin', 'Reception!45', 'tom.berger@medcare.com'),
(3, 'Dr. Siddharth', 'General Physician', 'siddharth_gp', 'Siddharth@55', 'dr.siddharth@medcare.com'),
(4, 'Dr. Sophie Braun', 'Radiologist', 'sophie_radiology', 'SophieR@22', 'sophie.braun@medcare.com'),
(5, 'Nina Hartmann', 'Billing Clerk', 'nhartmann_admin', 'Billing@789', 'nina.hartmann@medcare.com'),
(6, 'Peter Lange', 'Janitor', 'plange_admin', 'CleanItUp@99', 'peter.lange@medcare.com'),
(7, 'David Schmitt', 'Pharmacist', 'dschmitt_admin', 'Pharma#88', 'david.schmitt@medcare.com'),
(8, 'Lena Weiss', 'Lab Technician', 'lweiss_admin', 'Lab123!', 'lena.weiss@medcare.com');

INSERT INTO registration (
  Patient_name, Gender, Contact_number, Date_of_birth, Address, Email, Password
) VALUES
('Claire Meyer', 'Female', '15699876541', '1983-03-09', 'Heidelberg', 'claire.meyer@medcare.com', 'Claire@123'),
('Helena Rossi', 'Female', '15598321756', '1980-01-19', 'Mannheim', 'helena.rossi@medcare.com', 'Helena#80'),
('John Doe', 'Male', '9876543210', '1990-05-14', '123 Elm Street, NY', 'john.doe@medcare.com', 'JohnD!90'),
('Alice Smith', 'Female', '9123456780', '1985-03-22', '456 Oak Ave, CA', 'alice.smith@medcare.com', 'Alice@1985'),
('Bob Johnson', 'Male', '9988776655', '1978-11-10', '789 Maple Dr, TX', 'bob.johnson@medcare.com', 'BobJ@78'),
('Emily Davis', 'Female', '9001122334', '2000-08-30', '101 Pine Rd, FL', 'emily.davis@medcare.com', 'EmilyD#00'),
('Chris Evans', 'Male', '8887766554', '1992-07-18', '202 Cedar Blvd, WA', 'chris.evans@medcare.com', 'ChrisE@92');
