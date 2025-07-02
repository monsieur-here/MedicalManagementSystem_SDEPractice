package com.mms.model;

import java.sql.Date;

public class Bill {
	private int bill_id;
	private int appointment_id;
	private String patient_name;
	private String Insurance_type;
	private double amount;
	private String payment_method;
	private Date billing_date;
	
	public Bill(int billId, int appointmentId, String patientName, String insuranceType, double amount, String paymentMethod, Date billingDate) {
		this.bill_id = billId;
		this.appointment_id = appointmentId;
		this.patient_name = patientName;
		this.Insurance_type = insuranceType;
		this.amount = amount;
		this.payment_method = paymentMethod;
		this.billing_date = billingDate;
	}
	
	public int getBill_id() {
		return bill_id;
	}
	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
	}
	public int getAppointment_id() {
		return appointment_id;
	}
	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getInsurance_type() {
		return Insurance_type;
	}
	public void setInsurance_type(String insurance_type) {
		Insurance_type = insurance_type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public Date getBilling_date() {
		return billing_date;
	}
	public void setBilling_date(Date billing_date) {
		this.billing_date = billing_date;
	}
	
	
}
