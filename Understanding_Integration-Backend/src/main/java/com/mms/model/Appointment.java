package com.mms.model;

import java.time.LocalDateTime;
import java.sql.Date;

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
		private Integer id;
		private Integer slot;
		private Integer patientId;
		private Integer doctorId;
		private String notes;
	    private String status;
		private Date appointmentDate;

//		private int appointmentId;
//	    private Date slot;
//	    private Integer patientId;
//	    private String patientName;
//	    private String visitDescription;
//	    private Integer doctorId;
//	    private String doctorName;
//	    private String specialist;	// can be null for Receptionists
//	    private String status;

	    public Appointment() {}

		public Appointment(Integer id, Integer slot, Integer patientId, Integer doctorId, String notes, String status, Date appointmentDate) {
			this.id = id;
			this.slot = slot;
			this.patientId = patientId;
			this.doctorId = doctorId;
			this.notes = notes;
			this.status = status;
			this.appointmentDate = appointmentDate;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getSlot() {
			return slot;
		}

		public void setSlot(Integer slot) {
			this.slot = slot;
		}

		public Integer getPatientId() {
			return patientId;
		}

		public void setPatientId(Integer patientId) {
			this.patientId = patientId;
		}

		public Integer getDoctorId() {
			return doctorId;
		}

		public void setDoctorId(Integer doctorId) {
			this.doctorId = doctorId;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Date getAppointmentDate() {
			return appointmentDate;
		}

		public void setAppointmentDate(Date appointmentDate) {
			this.appointmentDate = appointmentDate;
		}
	}
