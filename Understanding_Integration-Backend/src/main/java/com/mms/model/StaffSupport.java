package com.mms.model;

import java.sql.Time;

public class StaffSupport {
	private int staffId;
	private String name;
	private String designation; // Doctor, Receptionist, Nurse...
	private String gender;
	private String contactNumber;
	private String specialist; // can be null for Receptionists
	private String staff_availability;
	private Time shiftStart;
	private Time shiftEnd;
	private String email;
	private String password;

	public StaffSupport() {
	}

	public StaffSupport(int staffId, String designation, String name, String gender, String contactNumber,
                String staffAvailability, Time shiftStart, Time shiftEnd, String email,
                String password, String specialist) {
					this.staffId = staffId;
					this.designation = designation;
					this.name = name;
					this.gender = gender;
					this.contactNumber = contactNumber;
					this.staff_availability = staffAvailability;
					this.shiftStart = shiftStart;
					this.shiftEnd = shiftEnd;
					this.email = email;
					this.password = password;
					this.specialist = specialist;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getStaff_availability() {
		return staff_availability;
	}

	public void setStaff_availability(String staff_availability) {
		this.staff_availability = staff_availability;
	}

	public String getSpecialist() {
		return specialist;
	}

	public void setSpecialist(String specialist) {
		this.specialist = specialist;
	}

	public Time getShiftStart() {
		return shiftStart;
	}

	public void setShiftStart(Time shiftStart) {
		this.shiftStart = shiftStart;
	}

	public Time getShiftEnd() {
		return shiftEnd;
	}

	public void setShiftEnd(Time shiftEnd) {
		this.shiftEnd = shiftEnd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
