package com.medicalManagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.medicalManagement.model.StaffSupport;
import com.medicalManagement.utils.DBConnection;

public class StaffDAOImpl implements StaffDAO {
	
	private Connection conn;

    public StaffDAOImpl(Connection conn) {
        this.conn = conn;
    }

	 @Override
	    public List<StaffSupport> getStaff(int pageNo, int pageSize) throws SQLException {
	        List<StaffSupport> list = new ArrayList<>();
	        String sql = "SELECT * FROM staff_support ORDER BY staff_id LIMIT ?, ?";
	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setInt(1, (pageNo - 1) * pageSize);
	            ps.setInt(2, pageSize);

	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                StaffSupport staff = mapRowToStaff(rs);
	                list.add(staff);
	            }
	        }
	        return list;
	    }

	    @Override
	    public StaffSupport getStaffById(int staffId) throws SQLException {
	        String sql = "SELECT * FROM staff_support WHERE staff_id = ?";
	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setInt(1, staffId);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return mapRowToStaff(rs);
	            }
	        }
	        return null;
	    }

	    public boolean createStaff(StaffSupport staff) {
	        String sql = "INSERT INTO staff_support (name, designation, contact_number, specialization) VALUES (?, ?, ?, ?)";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, staff.getName());
	            stmt.setString(2, staff.getDesignation());
	            stmt.setString(3, staff.getContactNumber());
	            stmt.setString(4, staff.getSpecialist());

	            return stmt.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    @Override
	    public boolean updateStaff(StaffSupport staff) throws SQLException {
	        String sql = "UPDATE staff_support SET name = ?, gender = ?, contact_number = ?, designation = ?, specialist = ? WHERE staff_id = ?";
	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setString(1, staff.getName());
	            ps.setString(2, staff.getDesignation());
	            ps.setString(3, staff.getGender());
	            ps.setString(4, staff.getContactNumber());
	            ps.setString(5, staff.getSpecialist());
	            ps.setInt(6, staff.getStaffId());
	            return ps.executeUpdate() > 0;
	        }
	    }

	    @Override
	    public boolean deleteStaff(int staffId) throws SQLException {
	        String sql = "DELETE FROM staff_support WHERE staff_id = ?";
	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setInt(1, staffId);
	            return ps.executeUpdate() > 0;
	        }
	    }

	    @Override
	    public int getTotalStaff() throws SQLException {
	        String sql = "SELECT COUNT(*) FROM staff_support";
	        try (Connection con = DBConnection.getConnection();
	             Statement stmt = con.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {
	            if (rs.next()) return rs.getInt(1);
	        }
	        return 0;
	    }

	    @Override
	    public List<StaffSupport> getStaffByDesignation(String designation, int pageNo, int pageSize) throws SQLException {
	        List<StaffSupport> list = new ArrayList<>();
	        String sql = "SELECT * FROM staff_support WHERE designation = ? ORDER BY staff_id LIMIT ?, ?";
	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setString(1, designation);
	            ps.setInt(2, pageNo);		// err-1 logged and detected
	            ps.setInt(3, pageSize);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                list.add(mapRowToStaff(rs));
	            }
	        }
	        return list;
	    }

	    private StaffSupport mapRowToStaff(ResultSet rs) throws SQLException {
	        return new StaffSupport(
	                rs.getInt("staff_id"),
	                rs.getString("name"),
	                rs.getString("gender"),
	                rs.getString("contact_number"),
	                rs.getString("designation"),
	                rs.getString("specialist")
	        );
	    }
	    
	    @Override
	    public List<StaffSupport> getAllDoctors() throws SQLException {
	        List<StaffSupport> doctors = new ArrayList<>();
	        String sql = "SELECT * FROM staff_support WHERE designation = 'Doctor'";
	        try (PreparedStatement stmt = conn.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                StaffSupport doctor = extractStaff(rs);
	                doctors.add(doctor);
	            }
	        }
	        return doctors;
	    }
	    
	    @Override
	    public List<StaffSupport> getAllReceptionists() throws SQLException {
	        List<StaffSupport> receptionists = new ArrayList<>();
	        String sql = "SELECT * FROM staff_support WHERE designation = 'Receptionist'";
	        try (PreparedStatement stmt = conn.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                StaffSupport receptionist = extractStaff(rs);
	                receptionists.add(receptionist);
	            }
	        }
	        return receptionists;
	    }
	    
	    
	    private StaffSupport extractStaff(ResultSet rs) throws SQLException {
	        StaffSupport staff = new StaffSupport();
	        staff.setStaffId(rs.getInt("staff_id"));
	        staff.setName(rs.getString("name"));
	        staff.setDesignation(rs.getString("designation"));
	        staff.setSpecialist(rs.getString("specialization")); // nullable for receptionists
	        staff.setShiftStart(rs.getTime("shift_start"));
	        staff.setShiftEnd(rs.getTime("shift_end"));
	        return staff;
	    }

	    @Override
	    public List<StaffSupport> getAllStaff() throws SQLException {
	        String sql = "SELECT * FROM staff_support";
	        List<StaffSupport> staffList = new ArrayList<>();
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	                StaffSupport staff = new StaffSupport();
	                staff.setStaffId(rs.getInt("staff_id"));
	                staff.setName(rs.getString("staff_name"));
	                staff.setDesignation(rs.getString("designation")); // Doctor/Receptionist
	                staff.setSpecialist(rs.getString("specialist")); // Only for doctors
	                staff.setContactNumber(rs.getString("contact_number"));
	                staff.setShiftStart(rs.getTime("shift_start"));
	                staff.setShiftEnd(rs.getTime("shift_end"));
	                staffList.add(staff);
	            }
	        }
	        return staffList;
	    }
	    
	    public StaffSupport getStaffByEmail(String email) throws SQLException {
	        String sql = "SELECT * FROM staff_support WHERE staff_email = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, email);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                StaffSupport staff = new StaffSupport();
	                staff.setStaffId(rs.getInt("id"));
	                staff.setName(rs.getString("name"));
	                staff.setContactNumber(rs.getString("contact_number"));
	                staff.setGender(rs.getString("gender"));
	                staff.setDesignation(rs.getString("designation"));
	                staff.setSpecialist(rs.getString("specialist"));
	                staff.setShiftStart(rs.getTime("shift_start"));
	                staff.setShiftEnd(rs.getTime("shift_end"));
	                staff.setEmail(rs.getString("email"));
	                staff.setPassword(rs.getString("password"));
	                return staff;
	            }
	        }
	        return null;
	    }

	    
//		@Override
//		public List<StaffSupport> getAllStaff(String designation, int offset, int size) {
//			// TODO Auto-generated method stub
//			return null;
//		}
}
