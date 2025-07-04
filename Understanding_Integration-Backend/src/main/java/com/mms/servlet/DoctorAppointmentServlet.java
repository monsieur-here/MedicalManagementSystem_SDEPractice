package com.mms.servlet;

import com.google.gson.Gson;
import com.mms.dao.AppointmentDAO;
import com.mms.dao.AppointmentDAOImpl;
import com.mms.dao.UserDAO;
import com.mms.model.Appointment;
import com.mms.model.AppointmentDetails;
import com.mms.model.DoctorApiResponse;
import com.mms.model.User;
import com.mms.utils.DBConnection;
import com.mms.utils.GsonTimeUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap; // 2. Import HashMap
import java.util.List;
import java.util.Map; // 3. Import Map

@WebServlet("/doctor/appointments")
public class DoctorAppointmentServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;
    private Gson gson;
    private UserDAO userDAO;


    @Override
    public void init() throws ServletException {
        this.appointmentDAO = new AppointmentDAOImpl();
        this.gson = GsonTimeUtility.getGson();
        try {
            userDAO = new UserDAO(DBConnection.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        DoctorApiResponse apiResponse; // Use the new class

        try {
            String doctorId = req.getParameter("doctorId");

            if (doctorId == null || doctorId.trim().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                apiResponse = new DoctorApiResponse("Missing or empty doctorId parameter", 400, null);
                out.print(gson.toJson(apiResponse));
                return;
            }

            List<Appointment> appointments = appointmentDAO.getAllAppointmentsForDoctor(doctorId);
            List<AppointmentDetails> detailedAppointments = new ArrayList<>();

            for (Appointment appointment : appointments) {
                int patientId = appointment.getPatientId();
//                int doctorId = appointment.getDoctorId();

                User patient = userDAO.getUserById(patientId);
                User doctor = userDAO.getDoctorById(Integer.parseInt(doctorId));

                AppointmentDetails details = new AppointmentDetails(appointment, patient, doctor);
                detailedAppointments.add(details);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("appointments", detailedAppointments);

            apiResponse = new DoctorApiResponse("Appointments fetched successfully", 200, data);

            // Set the HTTP status to OK.
            resp.setStatus(HttpServletResponse.SC_OK); // 200

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            e.printStackTrace();
            apiResponse = new DoctorApiResponse("Database error occurred.", 500, null);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            e.printStackTrace();
            apiResponse = new DoctorApiResponse("An unexpected error occurred.", 500, null);
        }

        out.print(gson.toJson(apiResponse));
    }
}
