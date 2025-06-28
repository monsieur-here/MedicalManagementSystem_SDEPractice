package com.mms.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mms.dao.StaffDAO;
import com.mms.dao.StaffDAOImpl; // Assuming this implementation exists
import com.mms.model.StaffSupport; // Your DTO, previously Staff_Support
import com.mms.utils.DBConnection; // For database connection
import com.mms.utils.GsonTimeUtility;
import com.mms.utils.JsonUtil; // Your JSON utility class
//import com.mms.utils.JsonUtil.TimeTypeAdapter; 
import com.mms.utils.CORSFilter; // This is a filter, typically configured separately

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Map; // For error messages

@WebServlet("/staffSupport")
public class StaffSupportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffDAO staffDAO;

    @Override
    public void init() {
        try {
            // Initialize the DAO with a database connection
            staffDAO = new StaffDAOImpl(DBConnection.getConnection());
        } catch (SQLException e) {
            // Log the error if database connection fails at startup
            System.err.println("Error initializing StaffSupportServlet: Could not get database connection.");
            e.printStackTrace();
            // In a production environment, you might throw an UnavailableException
            // throw new UnavailableException("Database connection not available.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	// CORS headers commented out, assuming a CORSFilter is handling this or it's for internal use.
        // resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        // resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

    	resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		Gson gson = new Gson();
        
    	String idParam = req.getParameter("staff_id");
        String designation = req.getParameter("designation");
        String pageParam = req.getParameter("page");
        String sizeParam = req.getParameter("size");
        

        // Authentication/Authorization check (commented out as per original, but good practice)
        // String role = (String) req.getSession().getAttribute("role");
        // if (!"Receptionist".equals(role)) {
        // resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        // return;
        // }


        try {
            if ( idParam != null ) {
                int id = Integer.parseInt(idParam);
                 

                StaffSupport staff = staffDAO.getStaffById(id);
                if (staff != null) {
                    System.out.println("DEBUG: Staff found by ID: " + staff.getStaffId() + ", Name: " + staff.getName());
                    out.print(JsonUtil.toJson(staff));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(JsonUtil.toJson(Map.of("error", "Staff not found with ID: " + id)));
                }
//                out.flush();
//                return;
            }

            int page = 1;
            int size = 10;

            if (pageParam != null) {
                try {
                    page = Math.max(1, Integer.parseInt(pageParam));
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Invalid page parameter '" + pageParam + "'. Using default page 1. Error: " + e.getMessage());
                }
            }

            if (sizeParam != null && !sizeParam.isEmpty()) {
                try {
                    size = Math.max(1, Integer.parseInt(sizeParam));
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Invalid size parameter '" + sizeParam + "'. Using default size 10. Error: " + e.getMessage());
                }
            }

            int offset = (page - 1) * size;

            System.out.println("DEBUG: GET request for staff list. Designation: '" + designation + "', Page: " + page + ", Size: " + size + ", Offset: " + offset);

            List<StaffSupport> staffList = staffDAO.getStaffByDesignation(designation, offset, size);

            if (staffList != null && !staffList.isEmpty()) {
                System.out.println("DEBUG: Staff list retrieved successfully. Number of staff: " + staffList.size());
                out.print(JsonUtil.toJson(staffList));
            } else {
                System.out.println("DEBUG: No staff found matching criteria. Designation: '" + designation + "', Page: " + page + ", Size: " + size);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content for empty list
                out.print(JsonUtil.toJson(Map.of("message", "No staff found matching criteria.")));
            }

        } catch (SQLException e) {
            System.err.println("Database error in StaffSupportServlet (doGet): " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(JsonUtil.toJson(Map.of("error", "Database error: " + e.getMessage())));
        } catch (Exception e) {
            System.err.println("Unexpected error in StaffSupportServlet (doGet): " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(JsonUtil.toJson(Map.of("error", "An internal server error occurred: " + e.getMessage())));
        } finally {
            if (out != null) {
                out.flush();
                out.close(); // Close the writer to release resources
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORS headers commented out, a CORSFilter is handling this.
        // resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        // resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
//    	Gson gson = GsonTimeUtility.getGson();
    	BufferedReader br = req.getReader();
//    	StaffSupport staffTime = gson.fromJson(br, StaffSupport.class);
    	StaffSupport staff = JsonUtil.fromJson(br, StaffSupport.class);
    	
    	
        try {
//            System.out.println("Staff ID " + staff.getStaffId());
            System.out.println("Staff Name " + staff.getName());
            System.out.println("Staff Designation " + staff.getDesignation());
            System.out.println("Staff Email " + staff.getEmail());
            System.out.println("Staff Password " + staff.getPassword());
            

            boolean success = staffDAO.createStaff(staff);
            
          // Basic validation for critical fields (extend as needed)
          // Note: Assuming StaffSupport fields are correctly mapped by JsonUtil.fromJson
          // And that you have getters for new fields like getGender(), getContactNumber() etc.
            if (staff == null || staff.getStaffId() == 0 ||
              staff.getName() == null || staff.getName().isEmpty() ||
              staff.getEmail() == null || staff.getEmail().isEmpty() ||
              staff.getPassword() == null || staff.getPassword().isEmpty() ||
              staff.getDesignation() == null || staff.getDesignation().isEmpty()) { // Add other mandatory fields here
              resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
              JsonUtil.writeJsonResponse(resp, Map.of("error", "Missing required staff fields (staffId, name, email, password, designation, etc.)."));
              return;
          }
          
          resp.setStatus(success ? 201 : 400);
            
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.writeJsonResponse(resp, Map.of("error", "Invalid JSON format or missing data in request body."));
            System.err.println("Error parsing JSON for POST: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        boolean created = false;
        try {
            created = staffDAO.createStaff(staff); // Assumes createStaff takes StaffSupport
        } catch (SQLException e) {
            System.err.println("Database error creating staff: " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.writeJsonResponse(resp, Map.of("error", "Database error: " + e.getMessage()));
            return;
        }

        resp.setContentType("application/json"); // Ensure response is JSON
        PrintWriter out = resp.getWriter(); // Get writer after setting content type

        if (created) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.print(JsonUtil.toJson(Map.of("message", "Staff created successfully", "staffId", staff.getStaffId())));
        } else {
            resp.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict if staff already exists (e.g., by ID/email)
            out.print(JsonUtil.toJson(Map.of("error", "Failed to create staff. Staff might already exist or invalid data.")));
        }
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORS headers commented out, assuming a CORSFilter is handling this.
        // resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        // resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        StaffSupport staff = null;
        try {
            staff = JsonUtil.fromJson(req.getReader(), StaffSupport.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.writeJsonResponse(resp, Map.of("error", "Invalid JSON format or missing data in request body."));
            System.err.println("Error parsing JSON for PUT: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if (staff == null || staff.getStaffId() == 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.writeJsonResponse(resp, Map.of("error", "Staff ID is required for update."));
            return;
        }

        boolean updated = false;
        try {
            updated = staffDAO.updateStaff(staff); // Assumes updateStaff takes StaffSupport
        } catch (SQLException e) {
            System.err.println("Database error updating staff: " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.writeJsonResponse(resp, Map.of("error", "Database error: " + e.getMessage()));
            return;
        }

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        if (updated) {
            out.print(JsonUtil.toJson(Map.of("message", "Staff updated successfully", "staffId", staff.getStaffId())));
        } else {
            // Could be SC_NOT_FOUND if ID doesn't exist, or BAD_REQUEST if other validation fails in DAO
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // More likely for update failure
            out.print(JsonUtil.toJson(Map.of("error", "Failed to update staff. Staff not found or invalid data.")));
        }
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORS headers commented out, assuming a CORSFilter is handling this.
        // resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        // resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        String idParam = req.getParameter("staff_id");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (idParam == null || idParam.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(JsonUtil.toJson(Map.of("error", "Missing staff ID for deletion.")));
            out.flush();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(JsonUtil.toJson(Map.of("error", "Invalid staff ID format. Must be a number.")));
            out.flush();
            return;
        }

        boolean deleted = false;
        try {
            deleted = staffDAO.deleteStaff(id); // Assumes deleteStaff takes an int
        } catch (SQLException e) {
            System.err.println("Database error deleting staff: " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(JsonUtil.toJson(Map.of("error", "Database error: " + e.getMessage())));
            out.flush();
            return;
        }

        if (deleted) {
            out.print(JsonUtil.toJson(Map.of("message", "Staff deleted successfully", "staffId", idParam)));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print(JsonUtil.toJson(Map.of("error", "Staff not found or failed to delete with ID: " + id)));
        }
        out.flush();
    }
}
