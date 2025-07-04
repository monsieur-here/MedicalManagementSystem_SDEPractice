package com.mms.model;

public class AppointmentDetails {
    private Appointment appointment;
    private User patient;
    private User doctor;

    public AppointmentDetails(Appointment appointment, User patient, User doctor) {
        this.appointment = appointment;
        this.patient = patient;
        this.doctor = doctor;
    }

    // Getters
    public Appointment getAppointment() {
        return appointment;
    }

    public User getPatient() {
        return patient;
    }

    public User getDoctor() {
        return doctor;
    }
}

