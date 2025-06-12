package com.medicalManagement.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.medicalManagement.utils.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {

    private Gson gson = new Gson();

    protected static class LoginRequest {
        public String email;
        public String password;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
    	resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    	resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

    	
    	BufferedReader reader = req.getReader();
        LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT admin_id, name, email FROM admin WHERE email = ? AND password = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, loginRequest.email);
                ps.setString(2, loginRequest.password); // Plain text, improve with hashing!

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    // Login success: create session
                    HttpSession session = req.getSession(true);
                    session.setAttribute("adminId", rs.getInt("admin_id"));
                    session.setAttribute("adminName", rs.getString("name"));
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"success\":true, \"name\":\"" + rs.getString("name") + "\"}");
                } else {
                    resp.setStatus(401);
                    resp.getWriter().write("{\"success\":false, \"message\":\"Invalid credentials\"}");
                }
            }
        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
