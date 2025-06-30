package com.mms.dao;

import com.mms.model.Patient;
import com.mms.model.Prescription;
import com.mms.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Prescription> getPrescriptions(int pageNo, int pageSize) throws SQLException {
        List<Prescription> list = new ArrayList<>();
        String sql = "SELECT * FROM prescription ORDER BY prescription_id LIMIT ?, ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, (pageNo - 1) * pageSize);
            ps.setInt(2, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionId(rs.getInt("prescription_id"));
                prescription.setAppointmentId(rs.getInt("appointment_id"));
                prescription.setPrescriptionName(rs.getString("prescription_name"));
                prescription.setMedication(rs.getString("medication"));
                prescription.setDosage(rs.getString("dosage"));
                prescription.setFrequency(rs.getString("frequency"));
                prescription.setDateIssued(rs.getString("date_issued"));
                prescription.setCriticality(rs.getString("criticality"));
                list.add(prescription);
            }
        }
        return list;
    }
}
