package com.medicalManagement.dao; 

import java.sql.SQLException;
import java.util.List;

import com.medicalManagement.model.Patient;

public interface PatientDAO {
    List<Patient> getPatients(int pageNo, int pageSize) throws SQLException;
    Patient getPatientById(int patientId) throws SQLException;
    static boolean addPatient(Patient patient) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
    boolean updatePatient(Patient patient) throws SQLException;
    boolean deletePatient(int patientId) throws SQLException;
    int getTotalPatients() throws SQLException;
    
    Patient getPatientByEmail(String email) throws SQLException;

}
