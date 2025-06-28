package com.mms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mms.dao.PatientDAO;
import com.mms.dao.PatientDAOImpl;
import com.mms.model.Patient;
import com.mms.model.Registration_User;
import com.mms.utils.CORSFilter;

/**
 * Servlet implementation class PatientRegistrationServlet
 */
@WebServlet("/patient/register")
public class PatientRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//		response.setHeader("Access-Control-Allow-Headers", "Content-Type");

		try {

			 BufferedReader reader = request.getReader();
		        StringBuilder jsonBuilder = new StringBuilder();
		        String line;
		        while ((line = reader.readLine()) != null) {
		            jsonBuilder.append(line);
		        }

		        String jsonData = jsonBuilder.toString();

		        // Parse JSON (with Gson)
		        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();

		        String name = jsonObject.get("patient_name").getAsString();
		        System.out.println("Name: == "+name);
		        String gender = jsonObject.get("Gender").getAsString();
		        String dobStr = jsonObject.get("Date_of_Birth").getAsString();
		        String contactNumber = jsonObject.get("Contact_number").getAsString();
		        String address = jsonObject.get("Address").getAsString();
		        String email = jsonObject.get("email").getAsString();
		        String password = jsonObject.get("password").getAsString();
//		        int patientId = jsonObject.get("patient_id").getAsInt();
		        System.out.println("DOB"+dobStr);

		        Date dob = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dobStr).getTime());
		        System.out.println("dob "+dob.toString());
			// Get form values
//			String name = request.getParameter("Patient_Name");
//			String dobStr = request.getParameter("Date_Of_Birth"); // Format: yyyy-MM-dd
//			String gender = request.getParameter("Gender");
//			String contactNumber = request.getParameter("Contact_number");
//			String address = request.getParameter("address");
//			String email = request.getParameter("email");
//			String password = request.getParameter("password");
//			int patientId = request.getIntHeader("patient_id");

//			Date dob = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dobStr).getTime());

			// Create DTO
			Patient patient = new Registration_User();
			patient.setPatientName(name);
			patient.setDateOfBirth(dob);
			patient.setGender(gender);
			patient.setContactNumber(contactNumber);
			patient.setPatientEmail(email);
			patient.setPatientPassword(password);
//			patient.setPatientId(patientId);
			patient.setAddress(address);
			
			// Save to DB
			boolean success = false;
			try {
				success = PatientDAOImpl.addPatient(patient);
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
