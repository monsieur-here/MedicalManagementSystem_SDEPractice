package com.mms.servlet;

import jakarta.servlet.ServletConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.mms.dao.UserDAO;
import com.mms.model.Patient;
import com.mms.model.Registration_User;
import com.mms.model.User;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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

	/**
	 * @see Servlet#init(ServletConfig)
	 */
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    StringBuilder jsonBuffer = new StringBuilder();
	    String line;
	    try (BufferedReader reader = request.getReader()) {
	        while ((line = reader.readLine()) != null) {
	            jsonBuffer.append(line);
	        }
	    }

	    String requestBody = jsonBuffer.toString();
	    System.out.println("Received JSON: " + requestBody);

	    // Parse using org.json
	    JSONObject jsonObject = new JSONObject(requestBody);

	    User newUser = new User();
	    newUser.setFirstName(jsonObject.getString("first_name"));
	    newUser.setLastName(jsonObject.getString("last_name"));
	    newUser.setEmail(jsonObject.getString("email"));
	    newUser.setPassword(jsonObject.getString("password"));
	    newUser.setRole(jsonObject.getString("role"));

        try {
			if(UserDAO.addUser(newUser))
			response.setContentType("application/json");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        response.getWriter().write("{\"status\":\"received\"}");
//		MyData data = new Gson().fromJson(requestBody, MyData.class);
//		doGet(request, response);
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
