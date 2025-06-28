package com.mms.servlet;

import com.google.gson.Gson;

import com.mms.dao.PatientDAO;
import com.mms.dao.PatientDAOImpl;
import com.mms.model.Patient;
import com.mms.utils.DBConnection;
import com.mms.utils.CORSFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/patient")
public class PatientServlet extends HttpServlet {
	private PatientDAO patientDAO;

	@Override
	public void init() throws ServletException {
		try {
			patientDAO = new PatientDAOImpl(DBConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//		resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
//		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		Gson gson = new Gson();

		String idParam = req.getParameter("id");
		String pageParam = req.getParameter("page");
		String sizeParam = req.getParameter("size");

		try {
			if (idParam != null) {
				int id = Integer.parseInt(idParam);
				Patient p = patientDAO.getPatientById(id);
				out.print(gson.toJson(p));
			} else {
				int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
				int size = (sizeParam != null) ? Integer.parseInt(sizeParam) : 10;
				List<Patient> patients = patientDAO.getPatients(page, size);
				out.print(gson.toJson(patients));
			}
		} catch (Exception e) {
			resp.setStatus(500);
			out.print("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			Patient p = new Gson().fromJson(req.getReader(), Patient.class);
			boolean success = PatientDAO.addPatient(p);
			resp.setStatus(success ? 201 : 400);
		} catch (Exception e) {
			resp.setStatus(500);
			resp.getWriter().print("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			Patient p = new Gson().fromJson(req.getReader(), Patient.class);
			boolean success = patientDAO.updatePatient(p);
			resp.setStatus(success ? 200 : 400);
		} catch (Exception e) {
			resp.setStatus(500);
			resp.getWriter().print("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			boolean success = patientDAO.deletePatient(id);
			resp.setStatus(success ? 200 : 400);
		} catch (Exception e) {
			resp.setStatus(500);
			resp.getWriter().print("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
