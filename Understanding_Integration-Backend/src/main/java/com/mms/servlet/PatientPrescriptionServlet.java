package com.mms.servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mms.dao.PatientDAOImpl;
import com.mms.dao.PrescriptionDAO;
import com.mms.dao.PrescriptionDAOImpl;
import com.mms.model.Patient;
import com.mms.model.Prescription;
import com.mms.model.Registration_User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

@WebServlet("/patient/prescription")
public class PatientPrescriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            Integer appointmentId = jsonObject.get("appointment_id").getAsInt();
            String prescriptionName = jsonObject.get("prescription_name").getAsString();
            String medication = jsonObject.get("medication").getAsString();
            String dosage  = jsonObject.get("dosage").getAsString();
            String frequency = jsonObject.get("frequency").getAsString();
            String criticality = jsonObject.get("criticality").getAsString();

            // Create DTO
            Prescription prescription = new Prescription();
            prescription.setAppointmentId(appointmentId);
            prescription.setPrescriptionName(prescriptionName);
            prescription.setMedication(medication);
            prescription.setDosage(dosage);
            prescription.setFrequency(frequency);
            prescription.setCriticality(criticality);

            // Save to DB
            boolean success = false;
            try {
                PrescriptionDAOImpl prescriptionDAOImpl = new PrescriptionDAOImpl();
                success = prescriptionDAOImpl.addPrescription(prescription);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("Prescription addded successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Prescription not added.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
}