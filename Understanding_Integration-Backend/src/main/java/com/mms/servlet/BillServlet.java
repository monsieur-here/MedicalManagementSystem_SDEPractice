package com.mms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.mms.dao.BillDAO;
import com.mms.dao.BillDAOImpl;
import com.mms.model.Bill;
import com.mms.utils.GsonTimeUtility;

/**
 * Servlet implementation class BillServlet
 */
@WebServlet("/patient-bill")
public class BillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BillDAO billDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        // Initialize DAO - in a real app, inject DB connection or use DI framework
        this.billDAO = new BillDAOImpl(); // Using in-memory implementation for demonstration
        // Initialize Gson using the GsonTimeUtility to handle Timestamp and Date
        this.gson = GsonTimeUtility.getGson();
    }

    /**
     * Handles GET requests to retrieve bill information.
     * Supports:
     * - /bills?id={bill_id}           : Get specific bill by ID
     * - /bills?appointmentId={appointment_id}&page={page}&size={size} : Get bills for a specific appointment
     * - /bills?patientName={patient_name}&page={page}&size={size} : Get bills by patient name
     * - /bills?page={page}&size={size} : Get all bills (requires receptionist role)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String billIdParam = req.getParameter("bill_id");
        String appointmentIdParam = req.getParameter("appointment_id"); // New parameter
        String patientNameParam = req.getParameter("patient_name"); // New parameter
        String pageParam = req.getParameter("page");
        String sizeParam = req.getParameter("size");

        try {
            // Pagination defaults
            int page = 1;
            int size = 10;
            try {
                if (pageParam != null && !pageParam.isEmpty()) page = Math.max(1, Integer.parseInt(pageParam));
                if (sizeParam != null && !sizeParam.isEmpty()) size = Math.max(1, Integer.parseInt(sizeParam));
            } catch (NumberFormatException e) {
                System.err.println("Warning: Invalid page/size parameter. Using defaults. Error: " + e.getMessage());
            }

            // Priority 1: Get bill by ID
            if (billIdParam != null && !billIdParam.isEmpty()) {
                int billId;
                try {
                    billId = Integer.parseInt(billIdParam);
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(gson.toJson(Map.of("error", "Invalid Bill ID format. Must be a number.")));
                    return;
                }

                Bill bill = billDAO.getBillById(billId);
                if (bill != null) {
                    out.print(gson.toJson(bill));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(gson.toJson(Map.of("error", "Bill not found with ID: " + billId)));
                }
                return; // Exit after handling specific bill request
            }

            // Priority 2: Get bills by Appointment ID
            if (appointmentIdParam != null && !appointmentIdParam.isEmpty()) {
                int appointmentId;
                try {
                    appointmentId = Integer.parseInt(appointmentIdParam);
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(gson.toJson(Map.of("error", "Invalid Appointment ID format. Must be a number.")));
                    return;
                }
                List<Bill> bills = billDAO.getBillsByAppointmentId(appointmentId, page, size);
                if (bills != null && !bills.isEmpty()) {
                    out.print(gson.toJson(bills));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
                    out.print(gson.toJson(Map.of("message", "No bills found for appointment ID: " + appointmentId)));
                }
                return; // Exit after handling appointment-specific bills
            }

            // Priority 3: Get bills by Patient Name
            if (patientNameParam != null && !patientNameParam.isEmpty()) {
                List<Bill> bills = billDAO.getBillsByPatientName(patientNameParam, page, size);
                if (bills != null && !bills.isEmpty()) {
                    out.print(gson.toJson(bills));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
                    out.print(gson.toJson(Map.of("message", "No bills found for patient name: " + patientNameParam)));
                }
                return; // Exit after handling patient name specific bills
            }

            // Priority 4: Get all bills (requires Receptionist role)
            // Authorization Check for "Get All Bills"
            HttpSession session = req.getSession(false); // Do not create a new session
            String userDesignation = (session != null) ? (String) session.getAttribute("designation") : null;

            if (!"Receptionist".equalsIgnoreCase(userDesignation)) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
                out.print(gson.toJson(Map.of("error", "Access denied. Only Receptionists can view all bills.")));
                return;
            }

            List<Bill> allBills = billDAO.getAllBills(page, size);
            if (allBills != null && !allBills.isEmpty()) {
                out.print(gson.toJson(allBills));
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                out.print(gson.toJson(Map.of("message", "No bills available in the system.")));
            }

        } catch (SQLException e) {
            System.err.println("Database error in BillServlet (doGet): " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(Map.of("error", "Database error: " + e.getMessage())));
        } catch (Exception e) {
            System.err.println("Unexpected error in BillServlet (doGet): " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(Map.of("error", "An internal server error occurred: " + e.getMessage())));
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * Handles POST requests to create a new bill.
     * Requires Receptionist role.
     * Expected JSON body:
     * {
     * "appointment_id": 1,
     * "patient_name": "Alice Patient",
     * "Insurance_type": "Private",
     * "amount": 150.00,
     * "payment_method": "Credit Card",
     * "billing_date": "2025-06-29" // Must match yyyy-MM-dd format
     * // bill_id will be auto-generated by InMemoryBillDAO if not provided or 0
     * }
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String requestBody = "";
        Bill bill = null;

        try {
            // Authorization Check for "Create Bill"
            HttpSession session = req.getSession(false);
            String userDesignation = (session != null) ? (String) session.getAttribute("designation") : null;
            // Assuming staffId is stored as Integer in session for the receptionist who creates the bill
            Integer receptionistStaffId = (session != null) ? (Integer) session.getAttribute("staffId") : null;

            if (!"Receptionist".equalsIgnoreCase(userDesignation) || receptionistStaffId == null) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
                out.print(gson.toJson(Map.of("error", "Access denied. Only Receptionists can create bills.")));
                return;
            }

            // Read and parse JSON request body
            requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println("DEBUG: Incoming POST Request Body (Bill): " + requestBody);
            bill = gson.fromJson(requestBody, Bill.class); // Use local gson instance 

            // Basic validation for mandatory fields (adjust as per your needs)
            if (bill == null || bill.getAppointment_id() == 0 ||
                bill.getPatient_name() == null || bill.getPatient_name().isEmpty() ||
                bill.getInsurance_type() == null || bill.getInsurance_type().isEmpty() ||
                bill.getAmount() <= 0 || // Amount must be positive
                bill.getPayment_method() == null || bill.getPayment_method().isEmpty() ||
                bill.getBilling_date() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson(Map.of("error", "Missing or invalid required bill fields (appointment_id, patient_name, Insurance_type, amount, payment_method, billing_date). Amount must be positive.")));
                return;
            }

            // Set the receptionist ID from the session before adding to DAO
            // Ensure your Bill DTO has this field if you want to store it
            // As per your new Bill DTO, receptionistStaffId is not present.
            // If you still want to store it, you'd need to add it back to Bill.java
            // bill.setReceptionistStaffId(receptionistStaffId); // COMMENTED OUT as per new Bill DTO

            boolean added = billDAO.addBill(bill);
            if (added) {
                resp.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
                out.print(gson.toJson(Map.of("message", "Bill created successfully", "billId", bill.getBill_id())));
            } else {
                resp.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict (e.g., if billId was provided and conflicted)
                out.print(gson.toJson(Map.of("error", "Failed to create bill. Possible ID conflict.")));
            }

        } catch (SQLException e) {
            System.err.println("Database error in BillServlet (doPost): " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(Map.of("error", "Database error: " + e.getMessage())));
        } catch (Exception e) {
            System.err.println("Unexpected error in BillServlet (doPost): " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(Map.of("error", "An internal server error occurred: " + e.getMessage())));
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

}
