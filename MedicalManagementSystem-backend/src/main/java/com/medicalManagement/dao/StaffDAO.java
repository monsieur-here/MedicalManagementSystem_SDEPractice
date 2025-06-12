package com.medicalManagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.medicalManagement.model.StaffSupport;

public interface StaffDAO {
	List<StaffSupport> getStaff(int pageNo, int pageSize) throws SQLException;
    StaffSupport getStaffById(int staffId) throws SQLException;
    boolean createStaff(StaffSupport staff) throws SQLException;
    boolean updateStaff(StaffSupport staff) throws SQLException;
    boolean deleteStaff(int staffId) throws SQLException;
    int getTotalStaff() throws SQLException;

    // Optionally: filter by designation
    List<StaffSupport> getStaffByDesignation(String designation, int pageNo, int pageSize) throws SQLException;
//	List<StaffSupport> getAllStaff(String designation, int offset, int size);
    
    public List<StaffSupport> getAllDoctors() throws SQLException;
    public List<StaffSupport> getAllReceptionists() throws SQLException; 
    public List<StaffSupport> getAllStaff() throws SQLException;
    public static StaffSupport getStaffByEmail(String email) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
