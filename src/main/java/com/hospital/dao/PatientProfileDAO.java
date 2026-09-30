package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hospital.model.PatientProfile;
import com.hospital.util.DBConnection;

public class PatientProfileDAO {

    public PatientProfile getPatientProfile(int patientId) {

        PatientProfile patient = null;

        String sql =
                "SELECT * FROM patients "
                + "WHERE patient_id = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, patientId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                patient = new PatientProfile();

                patient.setPatientId(
                        rs.getInt("patient_id")
                );

                patient.setFullName(
                        rs.getString("full_name")
                );

                patient.setEmail(
                        rs.getString("email")
                );

                patient.setPhoneNumber(
                        rs.getString("phone_number")
                );

                patient.setGender(
                        rs.getString("gender")
                );

                patient.setDateOfBirth(
                        rs.getString("date_of_birth")
                );

                patient.setAddress(
                        rs.getString("address")
                );

                patient.setAge(
                        rs.getInt("age")
                );

                patient.setBloodGroup(
                        rs.getString("blood_group")
                );

                patient.setEmergencyNumber(
                        rs.getString("emergency_number")
                );

                patient.setMedicalHistory(
                        rs.getString("medical_history")
                );

                patient.setDocumentPath(
                        rs.getString("document_path")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "Error while getting patient profile"
            );

            e.printStackTrace();
        }

        return patient;
    }
}