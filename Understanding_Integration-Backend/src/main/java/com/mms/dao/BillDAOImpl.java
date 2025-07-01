package com.mms.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

//import java.util.Comparator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.mms.model.Bill;

public class BillDAOImpl implements BillDAO {

    private final Map<Integer, Bill> billStore = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0); // Auto-incrementing bill ID

    public BillDAOImpl() {
        try {
			addSampleData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void addSampleData() throws SQLException {
        // Sample data aligned with the new Bill DTO
        // bill_id, appointment_id, patient_name, Insurance_type, amount, payment_method, billing_date
        addBill(new Bill(idCounter.incrementAndGet(), 1, "Alice Patient", "Private", 150.00, "Credit Card", Date.valueOf(LocalDate.now().minusDays(5))));
        addBill(new Bill(idCounter.incrementAndGet(), 2, "Bob Patient", "Public", 75.50, "Cash", Date.valueOf(LocalDate.now().minusDays(2))));
        addBill(new Bill(idCounter.incrementAndGet(), 1, "Alice Patient", "Private", 300.00, "Insurance", Date.valueOf(LocalDate.now().minusDays(10))));
        addBill(new Bill(idCounter.incrementAndGet(), 3, "Charlie Patient", "Public", 25.00, "Debit Card", Date.valueOf(LocalDate.now())));
    }

    @Override
    public Bill getBillById(int billId) throws SQLException {
        return billStore.get(billId);
    }

    @Override
    public List<Bill> getBillsByAppointmentId(int appointmentId, int pageNo, int pageSize) throws SQLException {
        int offset = (pageNo - 1) * pageSize;
        return billStore.values().stream()
                .filter(bill -> bill.getAppointment_id() == appointmentId)
                .sorted((bill1, bill2) -> bill2.getBilling_date().compareTo(bill1.getBilling_date())) // Sort by most recent date first
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Bill> getBillsByPatientName(String patientName, int pageNo, int pageSize) throws SQLException {
        int offset = (pageNo - 1) * pageSize;
        return billStore.values().stream()
                .filter(bill -> bill.getPatient_name().equalsIgnoreCase(patientName))
                .sorted((bill1, bill2) -> bill2.getBilling_date().compareTo(bill1.getBilling_date())) // Sort by most recent date first
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Bill> getAllBills(int pageNo, int pageSize) throws SQLException {
        int offset = (pageNo - 1) * pageSize;
        return billStore.values().stream()
        		.sorted((bill1, bill2) -> bill2.getBilling_date().compareTo(bill1.getBilling_date())) // Sort by most recent date first
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addBill(Bill bill) throws SQLException {
        // If billId is 0 (meaning new bill, not provided by client), assign a new one
        if (bill.getBill_id() == 0) {
            bill.setBill_id(idCounter.incrementAndGet());
        } else if (billStore.containsKey(bill.getBill_id())) {
            // If billId is provided and already exists, it's a conflict
            return false;
        }
        billStore.put(bill.getBill_id(), bill);
        return true;
    }

    @Override
    public boolean updateBill(Bill bill) throws SQLException {
        if (billStore.containsKey(bill.getBill_id())) {
            billStore.put(bill.getBill_id(), bill); // Overwrite existing entry
            return true;
        }
        return false; // Bill not found for update
    }

    @Override
    public boolean deleteBill(int billId) throws SQLException {
        return billStore.remove(billId) != null;
    }

    @Override
    public int getTotalBills() throws SQLException {
        return billStore.size();
    }

    @Override
    public int getTotalBillsByAppointmentId(int appointmentId) throws SQLException {
        return (int) billStore.values().stream()
                .filter(bill -> bill.getAppointment_id() == appointmentId)
                .count();
    }

    @Override
    public int getTotalBillsByPatientName(String patientName) throws SQLException {
        return (int) billStore.values().stream()
                .filter(bill -> bill.getPatient_name().equalsIgnoreCase(patientName))
                .count();
    }
}
