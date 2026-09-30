package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hospital.util.DBConnection;

public class MedicalRecordDAO {

    public boolean addMedicalRecord(
            int patientId,
            int doctorId,
            int appointmentId,
            String diagnosis,
            String doctorNotes,
            String prescription) {

        boolean status = false;

        String sql =
                "INSERT INTO medical_records " +
                "(patient_id, doctor_id, appointment_id, diagnosis, doctor_notes, prescription) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setInt(3, appointmentId);
            ps.setString(4, diagnosis);
            ps.setString(5, doctorNotes);
            ps.setString(6, prescription);

            int result = ps.executeUpdate();

            if (result > 0) {
                status = true;
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error while adding medical record");
            e.printStackTrace();
        }

        return status;
    }
}