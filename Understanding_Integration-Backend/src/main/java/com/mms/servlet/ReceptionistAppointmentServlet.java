package com.mms.servlet;

import com.google.gson.Gson;
import com.mms.dao.AppointmentDAO;
import com.mms.dao.AppointmentDAOImpl;
import com.mms.model.Appointment;
import com.mms.model.ReceptionistApiResponse;
import com.mms.utils.GsonTimeUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;



@WebServlet("/receptionist/appointments")
public class ReceptionistAppointmentServlet extends HttpServlet
{
    private AppointmentDAO appointmentDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.appointmentDAO = new AppointmentDAOImpl();
        this.gson = GsonTimeUtility.getGson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        ReceptionistApiResponse apiResponse;
        try {
            List<Appointment> appointments = appointmentDAO.getAllAppointmentsForReceptionist();
            Map<String, Object> data = new HashMap<>();
            data.put("appointments", appointments);

            apiResponse = new ReceptionistApiResponse("Appointments fetched successfully for receptionist", 200, data);

            resp.setStatus(HttpServletResponse.SC_OK); // 200

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            e.printStackTrace();
            apiResponse = new ReceptionistApiResponse("Database error: " + e.getMessage(), 500, null);
        }
        out.print(gson.toJson(apiResponse));
    }

    // This is the new doPut method to handle appointment status updates.
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        ReceptionistApiResponse apiResponse;

        try {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = req.getReader().readLine()) != null) {
                sb.append(line);
            }
            Map<String, Object> requestData = gson.fromJson(sb.toString(), Map.class);

            Object idObj = requestData.get("appointmentId");
            String status = (String) requestData.get("status");
            String appointmentDateStr = (String) requestData.get("appointment_date");

            if (idObj == null || status == null || status.trim().isEmpty() || appointmentDateStr == null || appointmentDateStr.trim().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                apiResponse = new ReceptionistApiResponse("Missing 'appointmentId', 'status', or 'appointment_date' in request body.", 400, null);
                out.print(gson.toJson(apiResponse));
                return;
            }

            int appointmentId = ((Double) idObj).intValue();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = dateFormat.parse(appointmentDateStr);
            Timestamp appointmentDate = new Timestamp(parsedDate.getTime());

            boolean success = appointmentDAO.updateAppointment(appointmentId, status, appointmentDate);

            if (success) {
                Appointment updatedAppointment = appointmentDAO.getAppointmentById(appointmentId);

                List<Appointment> appointmentList = new ArrayList<>();
                if (updatedAppointment != null) {
                    appointmentList.add(updatedAppointment);
                }

                Map<String, Object> data = new HashMap<>();
                data.put("appointments", appointmentList);

                resp.setStatus(HttpServletResponse.SC_OK);
                apiResponse = new ReceptionistApiResponse("Appointment updated successfully.", 200, data);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                apiResponse = new ReceptionistApiResponse("Appointment not found or could not be updated.", 404, null);
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            apiResponse = new ReceptionistApiResponse("An unexpected error occurred: " + e.getMessage(), 500, null);
        }

        out.print(gson.toJson(apiResponse));
    }


}