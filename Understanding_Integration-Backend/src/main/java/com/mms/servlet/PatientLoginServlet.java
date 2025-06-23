package com.mms.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mms.utils.DBConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mms.dao.PatientDAO;
import com.mms.dao.PatientDAOImpl;
import com.mms.model.Patient;
import com.mms.utils.CORSFilter;
//import com.mms.model.Registration_User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;
import com.mms.utils.CORSFilter;

@WebServlet("/patient/login")
public class PatientLoginServlet extends HttpServlet {
	private PatientDAO patientDAO;

	@Override
	public void init() {
		try {
			patientDAO = new PatientDAOImpl(DBConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//		resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
//		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//		resp.setHeader("Access-Control-Allow-Headers", "Content-Type"); 

		try {
			BufferedReader reader = req.getReader();
			Gson gson = new Gson();
			Map<String, String> loginData = gson.fromJson(reader, new TypeToken<Map<String, String>>() {
			}.getType());

			String email = loginData.get("patient_email");
			String password = loginData.get("password");

			if (email == null || password == null) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email and password are required.");
				return;
			}

			Patient patient = patientDAO.getPatientByEmail(email);
			if (patient != null) { // Perform the null check here
		        System.out.println(patient.getPatientPassword()); // Now it's safe to call
		        if (patient.getPatientPassword().equals(password)) {
		            HttpSession session = req.getSession(true);
		            session.setAttribute("patientId", patient.getPatientId());
		            resp.setStatus(HttpServletResponse.SC_OK);
		            writeJsonResponse(resp, Map.of("message", "Login successful", "patientId", patient.getPatientId()));
		        } else {
		            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials.");
		        }
		    } else {
		        // Handle the case where patient is null (e.g., no patient found)
		        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials."); // Or a more specific message
		    }
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
 	}

	private void writeJsonResponse(HttpServletResponse resp, Object obj) throws IOException {
		resp.setContentType("application/json");
		resp.getWriter().write(new Gson().toJson(obj));
	}
}
