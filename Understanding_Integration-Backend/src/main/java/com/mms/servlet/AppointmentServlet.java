package com.mms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.mms.dao.AppointmentDAO;
import com.mms.dao.AppointmentDAOImpl;
import com.mms.model.Appointment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.mms.utils.CORSFilter;

@WebServlet("/appointment")
public class AppointmentServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
    	
    	resp.setContentType("application/json");
        String idParam = req.getParameter("id");
        PrintWriter out = resp.getWriter();

        try {
            if (idParam != null) {
                // Get specific appointment
                int id = Integer.parseInt(idParam);
                Appointment appointment = appointmentDAO.getAppointmentById(id);
                out.print(gson.toJson(appointment));
            } else {
                // Get paginated list
                int page = Integer.parseInt(req.getParameter("page"));
                int size = Integer.parseInt(req.getParameter("size"));
                List<Appointment> appointments = appointmentDAO.getAllAppointments(page, size);
                out.print(gson.toJson(appointments));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Create a new appointment
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            Appointment appointment = gson.fromJson(req.getReader(), Appointment.class);

            // Validate doctor availability before creating
            boolean isAvailable = appointmentDAO.isDoctorAvailable(appointment.getDoctorId(), appointment.getSlot());
            if (!isAvailable) {
                resp.setStatus(409); // Conflict
                out.print("{\"error\":\"Doctor not available at that slot.\"}");
                return;
            }

            boolean success = appointmentDAO.addAppointment(appointment);
            out.print("{\"success\":" + success + "}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Update appointment
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            Appointment appointment = gson.fromJson(req.getReader(), Appointment.class);

            boolean isAvailable = appointmentDAO.isDoctorAvailable(appointment.getDoctorId(), appointment.getSlot());
            if (!isAvailable) {
                resp.setStatus(409);
                out.print("{\"error\":\"Doctor not available at that slot.\"}");
                return;
            }

            boolean success = appointmentDAO.updateAppointment(appointment);
            out.print("{\"success\":" + success + "}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Delete appointment
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean success = appointmentDAO.deleteAppointment(id);
            out.print("{\"success\":" + success + "}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
