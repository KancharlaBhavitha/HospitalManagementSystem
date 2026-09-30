package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hospital.model.Prescription;
import com.hospital.util.DBConnection;

public class PrescriptionDAO {

    // Save Prescription
    public boolean savePrescription(Prescription prescription) {

        boolean status = false;

        String sql =
                "INSERT INTO prescriptions " +
                "(appointment_id, patient_id, doctor_id, " +
                "medicine_name, dosage, duration, instructions) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    prescription.getAppointmentId()
            );

            ps.setInt(
                    2,
                    prescription.getPatientId()
            );

            ps.setInt(
                    3,
                    prescription.getDoctorId()
            );

            ps.setString(
                    4,
                    prescription.getMedicineName()
            );

            ps.setString(
                    5,
                    prescription.getDosage()
            );

            ps.setString(
                    6,
                    prescription.getDuration()
            );

            ps.setString(
                    7,
                    prescription.getInstructions()
            );

            int rows = ps.executeUpdate();

            if (rows > 0) {
                status = true;
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }


    // Get Prescription by Appointment ID
    public Prescription getPrescriptionByAppointmentId(
            int appointmentId) {

        Prescription prescription = null;

        String sql =
                "SELECT prescription_id, appointment_id, " +
                "patient_id, doctor_id, medicine_name, " +
                "dosage, duration, instructions, " +
                "prescription_date " +
                "FROM prescriptions " +
                "WHERE appointment_id = ? " +
                "ORDER BY prescription_id DESC " +
                "LIMIT 1";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, appointmentId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                prescription =
                        new Prescription();

                prescription.setPrescriptionId(
                        rs.getInt("prescription_id")
                );

                prescription.setAppointmentId(
                        rs.getInt("appointment_id")
                );

                prescription.setPatientId(
                        rs.getInt("patient_id")
                );

                prescription.setDoctorId(
                        rs.getInt("doctor_id")
                );

                prescription.setMedicineName(
                        rs.getString("medicine_name")
                );

                prescription.setDosage(
                        rs.getString("dosage")
                );

                prescription.setDuration(
                        rs.getString("duration")
                );

                prescription.setInstructions(
                        rs.getString("instructions")
                );

                prescription.setPrescriptionDate(
                        rs.getString("prescription_date")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return prescription;
    }
}