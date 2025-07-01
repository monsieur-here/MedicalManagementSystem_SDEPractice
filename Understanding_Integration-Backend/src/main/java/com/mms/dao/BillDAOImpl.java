package com.mms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

//import java.util.Comparator;

import java.util.List;
import java.util.Map;

import com.mms.model.Bill;
import com.mms.utils.DBConnection;

public class BillDAOImpl implements BillDAO {

//    private final Map<Integer, Bill> billStore = new HashMap<>();
//    private final AtomicInteger idCounter = new AtomicInteger(0); // Auto-incrementing bill ID
//    

//    private void addSampleData() throws SQLException {
//        // Sample data aligned with the new Bill DTO
//        // bill_id, appointment_id, patient_name, Insurance_type, amount, payment_method, billing_date
//        addBill(new Bill(idCounter.incrementAndGet(), 1, "Alice Patient", "Private", 150.00, "Credit Card", Date.valueOf(LocalDate.now().minusDays(5))));
//        addBill(new Bill(idCounter.incrementAndGet(), 2, "Bob Patient", "Public", 75.50, "Cash", Date.valueOf(LocalDate.now().minusDays(2))));
//        addBill(new Bill(idCounter.incrementAndGet(), 1, "Alice Patient", "Private", 300.00, "Insurance", Date.valueOf(LocalDate.now().minusDays(10))));
//        addBill(new Bill(idCounter.incrementAndGet(), 3, "Charlie Patient", "Public", 25.00, "Debit Card", Date.valueOf(LocalDate.now())));
//    }
	
	 private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
	        Bill bill = new Bill();
	        bill.setBill_id(rs.getInt("bill_id"));
	        bill.setAppointment_id(rs.getInt("appointment_id"));
	        bill.setPatient_name(rs.getString("patient_name"));
	        bill.setInsurance_type(rs.getString("Insurance_type"));
	        bill.setAmount(rs.getDouble("amount"));
	        bill.setPayment_method(rs.getString("payment_method"));
	        bill.setBilling_date(rs.getDate("billing_date"));
	        return bill;
	    }

    @Override
    public Bill getBillById(int billId) throws SQLException {
    	 String sql = "SELECT bill_id, appointment_id, patient_name, Insurance_type, amount, payment_method, billing_date FROM bill WHERE bill_id = ?";
    	    try (Connection conn = DBConnection.getConnection(); // Assuming you get connection here
    	         PreparedStatement stmt = conn.prepareStatement(sql)) {
    	        stmt.setInt(1, billId);
    	        try (ResultSet rs = stmt.executeQuery()) {
    	            if (rs.next()) {
//    	                System.out.println("DAO DEBUG: ResultSet has data for billId: " + billId); // ADD THIS
    	                // ... (your existing code to map ResultSet to Bill object)
    	                Bill bill = new Bill();
    	                bill.setBill_id(rs.getInt("bill_id"));
    	                bill.setAppointment_id(rs.getInt("appointment_id"));
    	                bill.setPatient_name(rs.getString("patient_name"));
    	                bill.setInsurance_type(rs.getString("Insurance_type")); // Ensure column name matches
    	                bill.setAmount(rs.getDouble("amount"));
    	                bill.setPayment_method(rs.getString("payment_method"));
    	                bill.setBilling_date(rs.getDate("billing_date"));
    	                return bill;
    	            } else {
    	                return null;
    	            }
    	        }
    	    }
    }

    @Override
    public List<Bill> getBillsByAppointmentId(int appointmentId, int pageNo, int pageSize) throws SQLException {
    	List<Bill> bills = new ArrayList<>();
        String sql = "SELECT bill_id, appointment_id, patient_name, Insurance_type, amount, payment_method, billing_date FROM Bill WHERE appointment_id = ? ORDER BY billing_date DESC LIMIT ? OFFSET ?";
        int offset = (pageNo - 1) * pageSize;
        System.out.println("DAO DEBUG: Executing SQL: " + sql + " for appointmentId: " + appointmentId + ", offset: " + offset + ", limit: " + pageSize);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appointmentId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        }
        System.out.println("DAO DEBUG: Found " + bills.size() + " bills for appointmentId: " + appointmentId);
        return bills;
    }

    @Override
    public List<Bill> getBillsByPatientName(String patientName, int pageNo, int pageSize) throws SQLException {
    	List<Bill> bills = new ArrayList<>();
        String sql = "SELECT bill_id, appointment_id, patient_name, Insurance_type, amount, payment_method, billing_date FROM Bill WHERE patient_name LIKE ? ORDER BY billing_date DESC LIMIT ? OFFSET ?";
        int offset = (pageNo - 1) * pageSize;
        System.out.println("DAO DEBUG: Executing SQL: " + sql + " for patientName: " + patientName + ", offset: " + offset + ", limit: " + pageSize);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + patientName + "%"); // Use LIKE for partial name matching
            stmt.setInt(2, pageSize);
            stmt.setInt(3, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        }
        System.out.println("DAO DEBUG: Found " + bills.size() + " bills for patientName: " + patientName);
        return bills;
    }

    @Override
    public List<Bill> getAllBills(int pageNo, int pageSize) throws SQLException {
    	List<Bill> bills = new ArrayList<>();
        String sql = "SELECT bill_id, appointment_id, patient_name, Insurance_type, amount, payment_method, billing_date FROM Bill ORDER BY billing_date DESC LIMIT ? OFFSET ?";
        int offset = (pageNo - 1) * pageSize;
        System.out.println("DAO DEBUG: Executing SQL: " + sql + " for all bills, offset: " + offset + ", limit: " + pageSize);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        }
        System.out.println("DAO DEBUG: Found " + bills.size() + " total bills.");
        return bills;
    }

    @Override
    public boolean addBill(Bill bill) throws SQLException {
    	String sql = "INSERT INTO Bill (appointment_id, patient_name, Insurance_type, amount, payment_method, billing_date) VALUES (?, ?, ?, ?, ?, ?)";
        System.out.println("DAO DEBUG: Adding bill: " + bill.toString());
        try (Connection conn = DBConnection.getConnection();
             // Use Statement.RETURN_GENERATED_KEYS to get the auto-generated bill_id
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bill.getAppointment_id());
            stmt.setString(2, bill.getPatient_name());
            stmt.setString(3, bill.getInsurance_type());
            stmt.setDouble(4, bill.getAmount());
            stmt.setString(5, bill.getPayment_method());
            stmt.setDate(6, bill.getBilling_date());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        bill.setBill_id(generatedKeys.getInt(1)); // Set the generated ID back to the Bill object
                        System.out.println("DAO DEBUG: Bill added successfully with generated ID: " + bill.getBill_id());
                    }
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean updateBill(Bill bill) throws SQLException {
    	String sql = "UPDATE Bill SET appointment_id = ?, patient_name = ?, Insurance_type = ?, amount = ?, payment_method = ?, billing_date = ? WHERE bill_id = ?";
        System.out.println("DAO DEBUG: Updating bill with ID: " + bill.getBill_id() + ", new data: " + bill.toString());
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bill.getAppointment_id());
            stmt.setString(2, bill.getPatient_name());
            stmt.setString(3, bill.getInsurance_type());
            stmt.setDouble(4, bill.getAmount());
            stmt.setString(5, bill.getPayment_method());
            stmt.setDate(6, bill.getBilling_date());
            stmt.setInt(7, bill.getBill_id());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("DAO DEBUG: Rows affected by update: " + rowsAffected);
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean deleteBill(int billId) throws SQLException {
    	String sql = "DELETE FROM Bill WHERE bill_id = ?";
        System.out.println("DAO DEBUG: Deleting bill with ID: " + billId);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("DAO DEBUG: Rows affected by delete: " + rowsAffected);
            return rowsAffected > 0;
        }
    }

    @Override
    public int getTotalBills() throws SQLException {
    	String sql = "SELECT COUNT(*) FROM Bill";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    @Override
    public int getTotalBillsByAppointmentId(int appointmentId) throws SQLException {
    	String sql = "SELECT COUNT(*) FROM Bill WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appointmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return 0;
        }
    }

    @Override
    public int getTotalBillsByPatientName(String patientName) throws SQLException {
    	String sql = "SELECT COUNT(*) FROM Bill WHERE patient_name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + patientName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return 0;
        }
    }
}
