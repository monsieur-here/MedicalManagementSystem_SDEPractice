package com.mms.dao;

import com.mms.model.Prescription;

import java.sql.SQLException;
import java.util.List;

public interface PrescriptionDAO {
    boolean addPrescription(Prescription prescription);
    List<Prescription> getPrescriptions(int pageNo, int pageSize) throws SQLException;
}
