package com.mms.servlet;

import com.mms.model.GeneralResponse;
import jakarta.servlet.ServletConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import com.google.gson.Gson;
import com.mms.dao.UserDAO;
import com.mms.model.Patient;
import com.mms.model.Registration_User;
import com.mms.model.User;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class PatientSignupServlet
 */
@WebServlet("/patient/signup")
public class PatientSignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PatientSignupServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Read JSON from request body
		StringBuilder jsonBuffer = new StringBuilder();
		String line;
		try (BufferedReader reader = request.getReader()) {
			while ((line = reader.readLine()) != null) {
				jsonBuffer.append(line);
			}
		}

		String requestBody = jsonBuffer.toString();
		System.out.println("Received JSON: " + requestBody);

		// Prepare response
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		GeneralResponse generalResponse = new GeneralResponse();

		try {
			// Parse JSON
			JSONObject jsonObject = new JSONObject(requestBody);

			// Create User object
			User newUser = new User();
			newUser.setFirstName(jsonObject.getString("first_name"));
			newUser.setLastName(jsonObject.getString("last_name"));
			newUser.setEmail(jsonObject.getString("email"));
			newUser.setPassword(jsonObject.getString("password"));
			newUser.setRole(jsonObject.getString("role"));

			// Add user to DB
			if (UserDAO.saveUser(newUser) != null) {
				generalResponse.setCode(200);
				generalResponse.setMsg("User Added");
				HashMap<String, Object> data = new HashMap<>();
				data.put("user", newUser);
				generalResponse.setData(data);
			} else {
				generalResponse.setCode(400);
				generalResponse.setMsg("User not added");
			}

		} catch (SQLException | JSONException e) {
			e.printStackTrace();
			generalResponse.setCode(500);
			generalResponse.setMsg("Internal server error: " + e.getMessage());
		}

		// Write response as JSON
		PrintWriter out = response.getWriter();
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("code", generalResponse.getCode());
		jsonResponse.put("msg", generalResponse.getMsg());
		if (generalResponse.getData() != null) {
			jsonResponse.put("data", generalResponse.getData());
		}
		out.print(jsonResponse.toString());
		out.flush();
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}