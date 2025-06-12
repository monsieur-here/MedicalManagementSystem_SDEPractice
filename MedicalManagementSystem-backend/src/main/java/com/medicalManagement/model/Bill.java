package com.medicalManagement.model;

import java.sql.Date;

public class Bill {
	private int billId;
	private int patientId;
	private String patientName;
	private String insuranceType;
	private double Amount;
	private String paymentMethod;
	private Date billingDate;
	private int doctorId;
	private int visitDescription;
	
	public int getBillId() {
		return billId;
	}
	public int getPatientId() {
		return patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public String getInsuranceType() {
		return insuranceType;
	}
	public double getAmount() {
		return Amount;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public Date getBillingDate() {
		return billingDate;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public int getVisitDescription() {
		return visitDescription;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public void setVisitDescription(int visitDescription) {
		this.visitDescription = visitDescription;
	}
	
}
