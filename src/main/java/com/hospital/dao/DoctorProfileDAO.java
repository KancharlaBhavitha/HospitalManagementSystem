package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hospital.model.Doctor;
import com.hospital.util.DBConnection;

public class DoctorProfileDAO {

    public Doctor getDoctorById(int doctorId) {

        Doctor doctor = null;

        String sql =
            "SELECT * FROM doctors WHERE doctor_id = ?";

        try {

            Connection con =
                DBConnection.getConnection();

            PreparedStatement ps =
                con.prepareStatement(sql);

            ps.setInt(1, doctorId);

            ResultSet rs =
                ps.executeQuery();

            if (rs.next()) {

                doctor = new Doctor();

                doctor.setDoctorId(
                    rs.getInt("doctor_id")
                );

                doctor.setFullName(
                    rs.getString("full_name")
                );

                doctor.setSpecialization(
                    rs.getString("specialization")
                );

                doctor.setDisease(
                    rs.getString("disease")
                );

                doctor.setPhoneNumber(
                    rs.getString("phone_number")
                );

                doctor.setEmail(
                    rs.getString("email")
                );

                doctor.setExperience(
                    rs.getInt("experience")
                );

                doctor.setConsultationFee(
                    rs.getDouble("consultation_fee")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                "Error while getting doctor profile"
            );

            e.printStackTrace();
        }

        return doctor;
    }
}