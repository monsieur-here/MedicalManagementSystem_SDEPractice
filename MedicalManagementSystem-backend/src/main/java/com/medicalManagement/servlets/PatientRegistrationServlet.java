package com.medicalManagement.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.medicalManagement.dao.PatientDAO;
import com.medicalManagement.model.Patient;

/**
 * Servlet implementation class PatientRegistrationServlet
 */
@WebServlet("/patient/register")
public class PatientRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
    	response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    	response.setHeader("Access-Control-Allow-Headers", "Content-Type");

		
		try {
            // Get form values
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String contactNumber = request.getParameter("contact_number");
            String gender = request.getParameter("gender");
            String dobStr = request.getParameter("dob");  // Format: yyyy-MM-dd

            java.sql.Date dob = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dobStr).getTime());

            // Create DTO
            Patient patient = new Patient();
            patient.setPatientName(name);
            patient.setPatientEmail(email);
            patient.setPatientPassword(password);
            patient.setContactNumber(contactNumber);
            patient.setGender(gender);
            patient.setDateOfBirth(dob);

            // Save to DB
            boolean success = false;
			try {
				success = PatientDAO.addPatient(patient);
			} catch (Exception e) {
				e.printStackTrace();
			}

            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("Patient registered successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Registration failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid input: " + e.getMessage());
        }
    }
}
