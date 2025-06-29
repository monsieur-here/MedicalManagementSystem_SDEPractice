package com.mms.model;

import java.util.Date;

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
		private int appointmentId;
	    private Date slot;
	    private Integer patientId;
	    private String patientName;
	    private String visitDescription;
	    private Integer doctorId;
	    private String doctorName;
	    private String specialist;	// can be null for Receptionists
	    private String status;

	    public Appointment() {}

		public int getAppointmentId() {
			return appointmentId;
		}

		public void setAppointmentId(int appointmentId) {
			this.appointmentId = appointmentId;
		}

		public Date getSlot() {
			return slot;
		}

		public void setSlot(Date slot) {
			this.slot = slot;
		}

		public Integer getPatientId() {
			return patientId;
		}

		public void setPatientId(Integer patientId) {
			this.patientId = patientId;
		}

		public String getPatientName() {
			return patientName;
		}

		public void setPatientName(String patientName) {
			this.patientName = patientName;
		}

		public String getVisitDescription() {
			return visitDescription;
		}

		public void setVisitDescription(String visitDescription) {
			this.visitDescription = visitDescription;
		}

		public Integer getDoctorId() {
			return doctorId;
		}

		public void setDoctorId(Integer doctorId) {
			this.doctorId = doctorId;
		}

		public String getDoctorName() {
			return doctorName;
		}

		public void setDoctorName(String doctorName) {
			this.doctorName = doctorName;
		}

		public String getSpecialist() {
			return specialist;
		}

		public void setSpecialist(String specialist) {
			this.specialist = specialist;
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
