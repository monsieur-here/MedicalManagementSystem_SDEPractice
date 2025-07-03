package com.mms.servlet;

import com.google.gson.Gson;
import com.mms.dao.AppointmentDAO;
import com.mms.dao.AppointmentDAOImpl;
import com.mms.model.Appointment;
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

@WebServlet("/receptionist/appointments")
public class ReceptionistAppointmentServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.appointmentDAO = new AppointmentDAOImpl();
        this.gson = GsonTimeUtility.getGson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            List<Appointment> appointments = appointmentDAO.getAllAppointmentsForReceptionist();
            String jsonResponse = gson.toJson(appointments);
            out.print(jsonResponse);
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            out.print("{\"error\": \"Database error: " + e.getMessage() + "\"}");
        }
    }
}