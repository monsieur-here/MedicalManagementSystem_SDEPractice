package com.mms.model;

import java.sql.Timestamp;

//import java.util.Date;

//	private int AppointmentId; 
//	private int userId;
//	private String fullName;
//	private String gender;
//	private String age;
//	private String appointmentDate;
//	private String email;
//	private String phone;
//	private String diseases;
//	private int doctorId;
//	private String address;
//	private String status;
	
	public class Appointment {
		
		private static final long serialVersionUID = 1L;
		
		private int Appointment_id;
	    private Timestamp Slot;
	    private Integer Patient_id;
	    private String Patient_name;
	    private String Visit_description;
	    private Integer doctor_id;
	    private String doctor_name;
	    private String Specialist;	// can be null for Receptionists
	    private String status;

	    public Appointment() {}

		public int getAppointmentId() {
			return Appointment_id;
		}

		public void setAppointmentId(int appointmentId) {
			this.Appointment_id = appointmentId;
		}

		public Timestamp getSlot() {
			return Slot;
		}

		public void setSlot(Timestamp slot) {
			this.Slot = slot;
		}

		public Integer getPatientId() {
			return Patient_id;
		}

		public void setPatientId(Integer patientId) {
			this.Patient_id = patientId;
		}

		public String getPatientName() {
			return Patient_name;
		}

		public void setPatientName(String patientName) {
			this.Patient_name = patientName;
		}

		public String getVisitDescription() {
			return Visit_description;
		}

		public void setVisitDescription(String visitDescription) {
			this.Visit_description = visitDescription;
		}

		public Integer getDoctorId() {
			return doctor_id;
		}

		public void setDoctorId(Integer doctorId) {
			this.doctor_id = doctorId;
		}

		public String getDoctorName() {
			return doctor_name;
		}

		public void setDoctorName(String doctorName) {
			this.doctor_name = doctorName;
		}

		public String getSpecialist() {
			return Specialist;
		}

		public void setSpecialist(String specialist) {
			this.Specialist = specialist;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	    // Getters and Setters
	    

	    // Constructors
	    
}
