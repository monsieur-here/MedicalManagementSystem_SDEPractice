package com.mms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map; // For consistent error responses

import com.google.gson.Gson;
import com.mms.dao.AppointmentDAO;
import com.mms.dao.AppointmentDAOImpl;
import com.mms.model.Appointment;
import com.mms.utils.GsonTimeUtility; // Import the utility class
import com.mms.utils.CORSFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// jakarta.servlet.http.HttpSession; // Not directly used in the provided code snippet for logic

@WebServlet("/appointment")
public class AppointmentServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO; // Declare, but don't initialize here
    private Gson gson; // Declare, but don't initialize here

    @Override
    public void init() throws ServletException {
        // Initialize DAO (assuming AppointmentDAOImpl has a default constructor or appropriate init)
        this.appointmentDAO = new AppointmentDAOImpl(); // Or inject a connection if needed

        // Initialize Gson using the GsonTimeUtility to handle custom date/time formats
        this.gson = GsonTimeUtility.getGson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
    	
    	resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String idParam = req.getParameter("Appointment_id");
        // No need for a new Gson instance here as it's initialized in init()

        try {
            if (idParam != null && !idParam.isEmpty()) { // Added null and empty check for idParam
                // Get specific appointment
                int id;
                try {
                    id = Integer.parseInt(idParam);
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(gson.toJson(Map.of("error", "Invalid ID format. Must be a number.")));
                    return;
                }
                
                Appointment appointment = appointmentDAO.getAppointmentById(id);
                if (appointment != null) {
                    out.print(gson.toJson(appointment));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(gson.toJson(Map.of("error", "Appointment not found with ID: " + id)));
                }
            } else {
                // Get paginated list
                String pageParam = req.getParameter("page");
                String sizeParam = req.getParameter("size");

                int page = 1; // Default page
                int size = 10; // Default size

                if (pageParam != null && !pageParam.isEmpty()) {
                    try {
                        page = Math.max(1, Integer.parseInt(pageParam));
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Invalid page parameter '" + pageParam + "'. Using default page 1.");
                    }
                }
                if (sizeParam != null && !sizeParam.isEmpty()) {
                    try {
                        size = Math.max(1, Integer.parseInt(sizeParam));
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Invalid size parameter '" + sizeParam + "'. Using default size 10.");
                    }
                }

                List<Appointment> appointments = appointmentDAO.getAllAppointments(page, size);
                if (appointments != null && !appointments.isEmpty()) {
                    out.print(gson.toJson(appointments));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
                    out.print(gson.toJson(Map.of("message", "No appointments available.")));
                }
            }
        } catch (Exception e) { // Catch more specific exceptions if possible
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            out.print(gson.toJson(Map.of("error", "An internal server error occurred: " + e.getMessage())));
        } finally {
            if (out != null) {
                out.flush();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Create a new appointment
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            // Use the initialized Gson instance
            Appointment appointment = gson.fromJson(req.getReader(), Appointment.class);

            // Basic validation
            if (appointment == null || appointment.getDoctorId() == 0 || appointment.getPatientId() == 0 || appointment.getSlot() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson(Map.of("error", "Missing required appointment fields (doctorId, patientId, slot).")));
                return;
            }

            // Validate doctor availability before creating
            // Assuming isDoctorAvailable expects java.sql.Timestamp for slot
            boolean isAvailable = appointmentDAO.isDoctorAvailable(appointment.getDoctorId(), appointment.getSlot());
            if (!isAvailable) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict
                out.print(gson.toJson(Map.of("error", "Doctor not available at that slot.")));
                return;
            }

            boolean success = appointmentDAO.addAppointment(appointment);
            resp.setStatus(success ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_BAD_REQUEST); // 201 for success, 400 for failure
            out.print(gson.toJson(Map.of("success", success)));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            out.print(gson.toJson(Map.of("error", "An internal server error occurred: " + e.getMessage())));
        } finally {
            if (out != null) {
                out.flush();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Update appointment
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            // Use the initialized Gson instance
            Appointment appointment = gson.fromJson(req.getReader(), Appointment.class);

            // Basic validation for update
            if (appointment == null || appointment.getAppointmentId() == 0 || appointment.getDoctorId() == 0 || appointment.getPatientId() == 0 || appointment.getSlot() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson(Map.of("error", "Missing required appointment fields for update (id, doctorId, patientId, slot).")));
                return;
            }

            boolean isAvailable = appointmentDAO.isDoctorAvailable(appointment.getDoctorId(), appointment.getSlot());
            if (!isAvailable) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                out.print(gson.toJson(Map.of("error", "Doctor not available at that slot.")));
                return;
            }

            boolean success = appointmentDAO.updateAppointment(appointment);
            resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_NOT_FOUND); // 200 for success, 404 if not found
            out.print(gson.toJson(Map.of("success", success)));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            out.print(gson.toJson(Map.of("error", "An internal server error occurred: " + e.getMessage())));
        } finally {
            if (out != null) {
                out.flush();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Delete appointment
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson(Map.of("error", "Missing appointment ID for deletion.")));
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson(Map.of("error", "Invalid appointment ID format. Must be a number.")));
                return;
            }

            boolean success = appointmentDAO.deleteAppointment(id);
            resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_NOT_FOUND); // 200 for success, 404 if not found
            out.print(gson.toJson(Map.of("success", success)));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            out.print(gson.toJson(Map.of("error", "An internal server error occurred: " + e.getMessage())));
        } finally {
            if (out != null) {
                out.flush();
            }
        }
    }
}
