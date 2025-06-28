package com.mms.model;

import java.sql.Connection;
import java.sql.Date;

public class Patient{
	private int patientId;
	private String patientName;
	private String gender;
	private Date dateOfBirth;
	private String contactNumber;
	private String address;
	private String insuranceType;
	private int doctorId;
	private String patientHistory;
	private String patientEmail;
	private String patientPassword;

	public Patient() {
	}

	public Patient(int patientId, String patientName, String gender, Date dateOfBirth, String contactNumber,
			String address, String insuranceType, int doctorId, String patientHistory) {
		this.patientId = patientId;
		this.patientName = patientName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.contactNumber = contactNumber;
		this.address = address;
		this.insuranceType = insuranceType;
		this.doctorId = doctorId;
		this.patientHistory = patientHistory;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getPatientHistory() {
		return patientHistory;
	}

	public void setPatientHistory(String patientHistory) {
		this.patientHistory = patientHistory;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public String getPatientPassword() {
		return patientPassword;
	}

	public void setPatientPassword(String patientPassword) {
		this.patientPassword = patientPassword;
	}

	// getters and setters

}