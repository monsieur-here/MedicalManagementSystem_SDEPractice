package com.medicalManagement.servlets;

import com.medicalManagement.dao.StaffDAO;
import com.medicalManagement.dao.StaffDAOImpl;
import com.medicalManagement.model.StaffSupport;
import com.medicalManagement.utils.DBConnection;
import com.medicalManagement.utils.JsonUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.List;

@WebServlet("/staffSupport")
public class StaffSupportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private StaffDAO staffDAO;

    @Override
    public void init() {
        try {
			staffDAO = new StaffDAOImpl(DBConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
    	resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    	resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

    	
    	String idParam = req.getParameter("id");
        String designation = req.getParameter("designation");
        String pageParam = req.getParameter("page");
        String sizeParam = req.getParameter("size");

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        
//        String role = (String) req.getSession().getAttribute("role");
//        if (!"Receptionist".equals(role)) {
//            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }


        try {
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                StaffSupport staff = staffDAO.getStaffById(id);
                if (staff != null) {
                    out.print(JsonUtil.toJson(staff));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Staff not found\"}");
                }
                return;
            }

            int page = 1;
            int size = 10;

            try {
                page = Math.max(1, Integer.parseInt(req.getParameter("page")));
                size = Math.max(1, Integer.parseInt(req.getParameter("size")));
            } catch (NumberFormatException e) {
                System.out.println("The values are negative");
            }
            int offset = (page - 1) * size;

            List<StaffSupport> staffList = staffDAO.getStaffByDesignation(designation, offset, size);
            out.print(JsonUtil.toJson(staffList));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
    	resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    	resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

    	
    	StaffSupport staff = JsonUtil.fromJson(req.getReader(), StaffSupport.class);
        boolean created = false;
		try {
			created = staffDAO.createStaff(staff);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        if (created) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\": \"Staff created successfully\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Failed to create staff\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
    	resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
    	resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    	resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    	
    	StaffSupport staff = JsonUtil.fromJson(req.getReader(), StaffSupport.class);
        boolean updated = false;
		try {
			updated = staffDAO.updateStaff(staff);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        if (updated) {
            out.print("{\"message\": \"Staff updated successfully\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Failed to update staff\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
    	resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    	resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    	
    	String idParam = req.getParameter("id");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Missing staff ID\"}");
            return;
        }

        int id = Integer.parseInt(idParam);
        boolean deleted = false;
		try {
			deleted = staffDAO.deleteStaff(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (deleted) {
            out.print("{\"message\": \"Staff deleted successfully\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Staff not found or failed to delete\"}");
        }
    }
}
