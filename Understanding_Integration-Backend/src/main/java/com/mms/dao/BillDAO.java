package com.mms.dao;

import java.sql.SQLException;
import java.util.List;

import com.mms.model.Bill;

public interface BillDAO {

	Bill getBillById(int billId) throws SQLException;

    /**
     * Retrieves bills by appointment ID, with pagination.
     * This is useful for finding all bills related to a specific appointment.
     * @param appointmentId The ID of the appointment.
     * @param pageNo The page number (1-based).
     * @param pageSize The number of records per page.
     * @return A list of Bill objects for the appointment.
     * @throws SQLException If a database access error occurs.
     */
    List<Bill> getBillsByAppointmentId(int appointmentId, int pageNo, int pageSize) throws SQLException;

    /**
     * Retrieves bills by patient name, with pagination.
     * Note: Searching by name might yield multiple results; consider patient ID for unique results.
     * @param patientName The name of the patient.
     * @param pageNo The page number (1-based).
     * @param pageSize The number of records per page.
     * @return A list of Bill objects for the patient.
     * @throws SQLException If a database access error occurs.
     */
    List<Bill> getBillsByPatientName(String patientName, int pageNo, int pageSize) throws SQLException;

    /**
     * Retrieves all bills in the system, with pagination.
     * @param pageNo The page number (1-based).
     * @param pageSize The number of records per page.
     * @return A list of all Bill objects.
     * @throws SQLException If a database access error occurs.
     */
    List<Bill> getAllBills(int pageNo, int pageSize) throws SQLException;

    /**
     * Adds a new bill to the system.
     * @param bill The Bill object to add.
     * @return true if the bill was added successfully, false otherwise (e.g., if ID conflict).
     * @throws SQLException If a database access error occurs.
     */
    boolean addBill(Bill bill) throws SQLException;

    /**
     * Updates an existing bill's details.
     * @param bill The Bill object with updated information.
     * @return true if the bill was updated successfully, false otherwise (e.g., bill not found).
     * @throws SQLException If a database access error occurs.
     */
    boolean updateBill(Bill bill) throws SQLException;

    /**
     * Deletes a bill by its ID.
     * @param billId The ID of the bill to delete.
     * @return true if the bill was deleted successfully, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    boolean deleteBill(int billId) throws SQLException;

    /**
     * Gets the total count of all bills.
     * @return The total number of bills.
     * @throws SQLException If a database access error occurs.
     */
    int getTotalBills() throws SQLException;

    /**
     * Gets the total count of bills for a specific appointment.
     * @param appointmentId The ID of the appointment.
     * @return The total number of bills for the given appointment.
     * @throws SQLException If a database access error occurs.
     */
    int getTotalBillsByAppointmentId(int appointmentId) throws SQLException;

    /**
     * Gets the total count of bills for a specific patient name.
     * @param patientName The name of the patient.
     * @return The total number of bills for the given patient name.
     * @throws SQLException If a database access error occurs.
     */
    int getTotalBillsByPatientName(String patientName) throws SQLException;
	
}
