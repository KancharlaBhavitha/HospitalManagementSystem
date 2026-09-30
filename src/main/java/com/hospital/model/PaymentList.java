package com.hospital.model;

public class PaymentList extends Payment {

    private String patientName;

    public PaymentList() {
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}