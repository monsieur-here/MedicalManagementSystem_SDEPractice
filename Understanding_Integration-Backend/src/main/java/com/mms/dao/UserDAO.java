package com.mms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
