package com.mms.servlet;

import java.io.IOException;

import com.mms.utils.CORSFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.mms.utils.CORSFilter;

@WebServlet("/patient/logout")
public class PatientLogoutServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//		resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
//		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().write("{\"message\": \"Logout successful\"}");
	}
}
