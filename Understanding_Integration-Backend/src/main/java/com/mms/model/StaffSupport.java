package com.mms.model;

import java.sql.Time;

public class StaffSupport {
	private int Staff_id;
	private String Staff_name;
	private String designation; // Doctor, Receptionist, Nurse...
	private String Gender;
	private String contact_number;
	private String specialist; // can be null for Receptionists
	private String staff_availability;
	private Time shift_start;
	private Time shift_end;
	private String staff_email;
	private String password;

	public StaffSupport() {
	}

//	int staffId
	public StaffSupport(String designation, String name, String gender, String contactNumber,
                String staffAvailability, Time shiftStart, Time shiftEnd, String email,
                String password, String specialist) {
//					this.Staff_id = staffId;
					this.designation = designation;
					this.Staff_name = name;
					this.Gender = gender;
					this.contact_number = contactNumber;
					this.staff_availability = staffAvailability;
					this.shift_start = shiftStart;
					this.shift_end = shiftEnd;
					this.staff_email = email;
					this.password = password;
					this.specialist = specialist;
	}

	public int getStaffId() {
		return Staff_id;
	}

	public void setStaffId(int staffId) {
		this.Staff_id = staffId;
	}

	public String getName() {
		return Staff_name;
	}

	public void setName(String name) {
		this.Staff_name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		this.Gender = gender;
	}

	public String getContactNumber() {
		return contact_number;
	}

	public void setContactNumber(String contactNumber) {
		this.contact_number = contactNumber;
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
		return shift_start;
	}

	public void setShiftStart(Time shiftStart) {
		this.shift_start = shiftStart;
	}

	public Time getShiftEnd() {
		return shift_end;
	}

	public void setShiftEnd(Time shiftEnd) {
		this.shift_end = shiftEnd;
	}

	public String getEmail() {
		return staff_email;
	}

	public void setEmail(String email) {
		this.staff_email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
