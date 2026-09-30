package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hospital.util.DBConnection;

public class DoctorMedicalRecordsDAO {

    public List<Object[]> getMedicalRecordsByDoctorId(int doctorId) {

        List<Object[]> records = new ArrayList<>();

        String sql =
                "SELECT " +
                "p.patient_id, " +
                "p.full_name, " +
                "p.email, " +
                "p.phone_number, " +
                "p.gender, " +
                "p.date_of_birth, " +
                "p.address, " +
                "p.blood_group, " +
                "p.medical_history, " +

                "a.appointment_id, " +
                "a.reason, " +
                "a.appointment_date, " +
                "a.status, " +

                "m.diagnosis, " +
                "m.doctor_notes, " +
                "m.prescription, " +

                "pr.medicine_name, " +
                "pr.dosage, " +
                "pr.duration, " +
                "pr.instructions " +

                "FROM appointments a " +

                "INNER JOIN patients p " +
                "ON a.patient_id = p.patient_id " +

                "LEFT JOIN medical_records m " +
                "ON a.appointment_id = m.appointment_id " +

                "LEFT JOIN prescriptions pr " +
                "ON a.appointment_id = pr.appointment_id " +

                "WHERE a.doctor_id = ? " +

                "ORDER BY a.appointment_id DESC";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Object[] record = new Object[20];

                    // Patient information
                    record[0] = rs.getInt("patient_id");
                    record[1] = rs.getString("full_name");
                    record[2] = rs.getString("email");
                    record[3] = rs.getString("phone_number");
                    record[4] = rs.getString("gender");
                    record[5] = rs.getString("date_of_birth");
                    record[6] = rs.getString("address");
                    record[7] = rs.getString("blood_group");
                    record[8] = rs.getString("medical_history");

                    // Appointment information
                    record[9] = rs.getInt("appointment_id");
                    record[10] = rs.getString("reason");
                    record[11] = rs.getString("appointment_date");
                    record[12] = rs.getString("status");

                    // Medical record
                    record[13] = rs.getString("diagnosis");
                    record[14] = rs.getString("doctor_notes");
                    record[15] = rs.getString("prescription");

                    // Prescription
                    record[16] = rs.getString("medicine_name");
                    record[17] = rs.getString("dosage");
                    record[18] = rs.getString("duration");
                    record[19] = rs.getString("instructions");

                    records.add(record);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error while getting medical records for doctor ID: "
                    + doctorId
            );

            e.printStackTrace();
        }

        return records;
    }
}