package com.medicalManagement.model;

import java.time.LocalDateTime;

public class Room {
	
	private int room_id;
	private String room_availability;
	private String room_type;
	private String room_number;
	private int patientId;
	private String patientName;
	private LocalDateTime CheckIn;
	private LocalDateTime CheckOut;
	
	public Room(int room_id, String room_availability, String room_type, String room_number) {
		// TODO Auto-generated constructor stub
		this.room_id = room_id;
		this.room_availability = room_availability;
		this.room_type = room_type;
		this.room_number = room_number;
	}
	public Room() {}
	
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public String getRoom_availability() {
		return room_availability;
	}
	public void setRoom_availability(String room_availability) {
		this.room_availability = room_availability;
	}
	public String getRoom_type() {
		return room_type;
	}
	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}
	public String getRoom_number() {
		return room_number;
	}
	public void setRoom_number(String room_number) {
		this.room_number = room_number;
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
	public LocalDateTime getCheckIn() {
		return CheckIn;
	}
	public void setCheckIn(LocalDateTime checkIn) {
		this.CheckIn = checkIn;
	}
	public LocalDateTime getCheckOut() {
		return CheckOut;
	}
	public void setCheckOut(LocalDateTime checkOut) {
		this.CheckOut = checkOut;
	}
	
	
	
}
