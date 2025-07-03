package com.mms.servlet;

import com.google.gson.Gson;
import com.mms.dao.AppointmentDAO;
import com.mms.dao.AppointmentDAOImpl;
import com.mms.model.Appointment;
import com.mms.utils.GsonTimeUtility; // Import the correct utility
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

// 1. Define a specific URL pattern for this servlet.
@WebServlet("/doctor/appointments")
public class DoctorAppointmentServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;
    private Gson gson;

    // 2. Initialize DAO and Gson instances once when the servlet is created.
    // This is more efficient than creating them on every request.
    @Override
    public void init() throws ServletException {
        this.appointmentDAO = new AppointmentDAOImpl();
        // Correctly initialize Gson using the utility that handles SQL Time objects
        this.gson = GsonTimeUtility.getGson();
    }

    // 3. Handle the GET request for doctor appointments.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            // 4. Get the 'doctorId' from the request's query parameters (e.g., ?doctorId=D001).
            String doctorId = req.getParameter("doctorId");

            // 5. Validate that the doctorId was provided. If not, send a "Bad Request" error.
            if (doctorId == null || doctorId.trim().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Missing or empty doctorId parameter\"}");
                return; // Stop further execution
            }

            // 6. Call the DAO method to get the appointments for the specified doctor.
            List<Appointment> appointments = appointmentDAO.getAllAppointmentsForDoctor(doctorId);

            // 7. Convert the list of appointments to a JSON string.
            String jsonResponse = gson.toJson(appointments);

            // 8. Write the JSON response to the client.
            out.print(jsonResponse);

        } catch (SQLException e) {
            // 9. Handle database errors.
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(); // Log the full error to the server console for debugging.
            out.print("{\"error\": \"Database error occurred while fetching appointments.\"}");
        } catch (Exception e) {
            // 10. Handle any other unexpected errors.
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            out.print("{\"error\": \"An unexpected error occurred.\"}");
        }
    }
}
