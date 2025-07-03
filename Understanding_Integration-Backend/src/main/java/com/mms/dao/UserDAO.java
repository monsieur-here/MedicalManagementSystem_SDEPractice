package com.mms.dao;

import java.sql.*;

import com.mms.model.Patient;
import com.mms.model.User;
import com.mms.utils.DBConnection;

public class UserDAO {
	
	private Connection conn;

	public UserDAO(Connection conn) {
		this.conn = conn;
	}
	
	public static boolean addUser(User newUser) throws SQLException {
		String sql = "INSERT INTO users(email, password, role, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, newUser.getEmail());
			ps.setString(2, newUser.getPassword());
			ps.setString(3, newUser.getRole());
			ps.setString(4, newUser.getFirstName());
			ps.setString(5, newUser.getLastName());
//			ps.setString(6, "PUBLIC");
//			ps.setInt(7, 4);
//			ps.setString(8, "Patient history");
//			ps.setString(9, patient.getPatientEmail());
//			ps.setString(10, patient.getPatientPassword());
//			System.out.println(ps.executeUpdate() > 0);
			
//			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//	            if (generatedKeys.next()) {
//	                newUser.setId(generatedKeys.getInt(1)); // Set ID to the user object
//	            }
//	        }
			
			

			return ps.executeUpdate()>0;
			
		}
	}

	public static User saveUser(User newUser) throws SQLException {
		String sql = "INSERT INTO users(email, password, role, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, newUser.getEmail());
			ps.setString(2, newUser.getPassword());
			ps.setString(3, newUser.getRole());
			ps.setString(4, newUser.getFirstName());
			ps.setString(5, newUser.getLastName());
//			ps.setString(6, "PUBLIC");
//			ps.setInt(7, 4);
//			ps.setString(8, "Patient history");
//			ps.setString(9, patient.getPatientEmail());
//			ps.setString(10, patient.getPatientPassword());
			System.out.println(ps.executeUpdate() > 0);


			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                newUser.setId(generatedKeys.getInt(1)); // Set ID to the user object
	            }
	        }



			return newUser;

		}
	}

	public User getUserByEmail(String email) throws SQLException {
		String sql = "SELECT * FROM users WHERE email = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				User user=new User();
				user.setEmail(rs.getString("email"));
				user.setId(rs.getInt("id"));
				user.setRole(rs.getString("role"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setPassword(rs.getString("password"));
//				Patient patient = new Patient();
//				patient.setPatientId(rs.getInt("patient_id"));
//				patient.setPatientName(rs.getString("patient_name"));
//				patient.setGender(rs.getString("Gender"));
//				patient.setDateOfBirth(rs.getDate("Date_Of_Birth"));
//				patient.setContactNumber(rs.getString("Contact_Number"));
//				patient.setAddress(rs.getString("Address"));
//				patient.setInsuranceType("PUBLIC");
//				patient.setDoctorId(1);
//				patient.setPatientHistory("Diagnosed/Not Diagnosed previously");
//				patient.setPatientEmail(rs.getString("patient_email"));
//				patient.setPatientPassword(rs.getString("password"));

				return user;
			}
		}
		return null;
	}

}
