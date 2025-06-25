package com.mms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mms.model.Patient;
import com.mms.utils.DBConnection;

public class PatientDAOImpl implements PatientDAO {

	private Connection conn;

	public PatientDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Patient> getPatients(int pageNo, int pageSize) throws SQLException {
		List<Patient> list = new ArrayList<>();
		String sql = "SELECT * FROM patient ORDER BY patient_id LIMIT ?, ?";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, (pageNo - 1) * pageSize);
			ps.setInt(2, pageSize);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Patient patient = new Patient();
				patient.setPatientId(rs.getInt("patient_id"));
				patient.setPatientName(rs.getString("patient_name"));
				patient.setGender(rs.getString("Gender"));
				patient.setDateOfBirth(rs.getDate("Date_of_Birth"));
				patient.setContactNumber(rs.getString("Contact_Number"));
				patient.setAddress(rs.getString("Address"));
				patient.setInsuranceType(rs.getString("Insurance_type"));
				patient.setDoctorId(rs.getInt("doctor_id"));
				patient.setPatientHistory(rs.getString("patient_history"));
				list.add(patient);
			}
		}
		return list;
	}

	@Override
	public Patient getPatientById(int patientId) throws SQLException {
		Patient patient = null;
		String sql = "SELECT * FROM patient WHERE patient_id = ?";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, patientId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				patient = new Patient();
				patient.setPatientId(rs.getInt("patient_id"));
				patient.setPatientName(rs.getString("patient_name"));
				patient.setGender(rs.getString("Gender"));
				patient.setDateOfBirth(rs.getDate("Date_of_Birth"));
				patient.setContactNumber(rs.getString("Contact_Number"));
				patient.setAddress(rs.getString("Address"));
				patient.setInsuranceType(rs.getString("Insurance_type"));
				patient.setDoctorId(rs.getInt("doctor_id"));
				patient.setPatientHistory(rs.getString("patient_history"));
			}
		}
		return patient;
	}

	public static boolean addPatient(Patient patient) throws SQLException {
		String sql = "INSERT INTO patient(patient_name, Gender, Date_of_Birth, Contact_Number, Address, Insurance_type, doctor_id, patient_history,patient_email,password) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, patient.getPatientName());
			ps.setString(2, patient.getGender());
			ps.setDate(3, new java.sql.Date(patient.getDateOfBirth().getTime()));
			ps.setString(4, patient.getContactNumber());
			ps.setString(5, patient.getAddress());
			ps.setString(6, "PUBLIC");
			ps.setInt(7, 4);
			ps.setString(8, "Patient history");
			ps.setString(9, patient.getPatientEmail());
			ps.setString(10, patient.getPatientPassword());
//			System.out.println(ps.executeUpdate() > 0);

			return ps.executeUpdate() > 0;
			
		}
	}

	@Override
	public boolean updatePatient(Patient patient) throws SQLException {
		String sql = "UPDATE patient SET patient_name = ?, Gender = ?, Date_of_Birth = ?, Contact_Number = ?, Address = ?, Insurance_type = ?, doctor_id = ?, patient_history = ? WHERE patient_id = ?";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, patient.getPatientName());
			ps.setString(2, patient.getGender());
			ps.setDate(3, new java.sql.Date(patient.getDateOfBirth().getTime()));
			ps.setString(4, patient.getContactNumber());
			ps.setString(5, patient.getAddress());
			ps.setString(6, patient.getInsuranceType());
			ps.setInt(7, 6);
			ps.setString(8, "Patient History");
			ps.setInt(9, patient.getPatientId());

			return ps.executeUpdate() > 0;
		}
	}

	@Override
	public boolean deletePatient(int patientId) throws SQLException {
		String sql = "DELETE FROM patient WHERE patient_id = ?";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, patientId);
			return ps.executeUpdate() > 0;
		}
	}

	@Override
	public int getTotalPatients() throws SQLException {
		String sql = "SELECT COUNT(*) FROM patient";
		try (Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	@Override
	public Patient getPatientByEmail(String email) throws SQLException {
		String sql = "SELECT * FROM patient WHERE patient_email = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Patient patient = new Patient();
				patient.setPatientId(rs.getInt("patient_id"));
				patient.setPatientName(rs.getString("patient_name"));
				patient.setGender(rs.getString("Gender"));
				patient.setDateOfBirth(rs.getDate("Date_Of_Birth"));
				patient.setContactNumber(rs.getString("Contact_Number"));
				patient.setAddress(rs.getString("Address"));
				patient.setInsuranceType("PUBLIC");
				patient.setDoctorId(1);
				patient.setPatientHistory("Diagnosed/Not Diagnosed previously");
				patient.setPatientEmail(rs.getString("patient_email"));
				patient.setPatientPassword(rs.getString("password"));
				
				return patient;
			}
		}
		return null;
	}

}
