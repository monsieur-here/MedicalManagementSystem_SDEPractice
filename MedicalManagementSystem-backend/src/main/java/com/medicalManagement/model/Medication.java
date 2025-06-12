package com.medicalManagement.model;

import java.sql.Date;

public class Medication {
	private int medicationId;
	private String medicationName;
	private String dosage;
	private String frequency;
	private Date dateIssued;
	
	public Medication(int mddicationId, String medicationName, String dosage, String frequency, Date dateIssued) {
		// TODO Auto-generated constructor stub
		this.medicationId = medicationId;
		this.medicationName = medicationName;
		this.frequency = frequency;
		this.dateIssued = dateIssued;
	}
	
	public int getMedicationId() {
		return medicationId;
	}
	public void setDiagnosisId(int medicationId) {
		this.medicationId = medicationId;
	}
	public String getMedicationName() {
		return medicationName;
	}
	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public Date getDateIssued() {
		return dateIssued;
	}
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}
	
	
}
