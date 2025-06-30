package com.mms.dao;

import com.mms.model.Prescription;
import com.mms.utils.DBConnection;

import java.sql.*;

public class PrescriptionDAOImpl implements PrescriptionDAO{

    @Override
    public boolean addPrescription(Prescription prescription) {
        String sql = "INSERT INTO prescription (appointment_id, prescription_name, medication, dosage, frequency, criticality) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, prescription.getAppointmentId());
            ps.setString(2, prescription.getPrescriptionName());
            ps.setString(3, prescription.getMedication());
            ps.setString(4, prescription.getDosage());
            ps.setString(5, prescription.getFrequency());
            ps.setString(6, prescription.getCriticality());


            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting prescription: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
