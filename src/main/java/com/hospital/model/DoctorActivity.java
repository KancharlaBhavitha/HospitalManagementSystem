package com.hospital.model;

public class DoctorActivity {

    private int doctorId;
    private String doctorName;
    private int patientsChecked;
    private int completedAppointments;

    public DoctorActivity() {
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getPatientsChecked() {
        return patientsChecked;
    }

    public void setPatientsChecked(int patientsChecked) {
        this.patientsChecked = patientsChecked;
    }

    public int getCompletedAppointments() {
        return completedAppointments;
    }

    public void setCompletedAppointments(int completedAppointments) {
        this.completedAppointments = completedAppointments;
    }
}