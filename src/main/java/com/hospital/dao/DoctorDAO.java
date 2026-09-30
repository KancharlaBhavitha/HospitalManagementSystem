package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hospital.model.Doctor;
import com.hospital.util.DBConnection;

public class DoctorDAO {

    // =====================================================
    // 1. REGISTER DOCTOR
    // =====================================================

    public boolean registerDoctor(Doctor doctor) {

        boolean status = false;

        String sql =
            "INSERT INTO doctors " +
            "(full_name, specialization, disease, phone_number, " +
            "email, experience, consultation_fee) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection con =
                DBConnection.getConnection();

            PreparedStatement ps =
                con.prepareStatement(sql);

            ps.setString(
                1,
                doctor.getFullName()
            );

            ps.setString(
                2,
                doctor.getSpecialization()
            );

            ps.setString(
                3,
                doctor.getDisease()
            );

            ps.setString(
                4,
                doctor.getPhoneNumber()
            );

            ps.setString(
                5,
                doctor.getEmail()
            );

            ps.setInt(
                6,
                doctor.getExperience()
            );

            ps.setDouble(
                7,
                doctor.getConsultationFee()
            );

            int rows =
                ps.executeUpdate();

            if (rows > 0) {
                status = true;
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                "Error while registering doctor"
            );

            e.printStackTrace();
        }

        return status;
    }


    // =====================================================
    // 2. GET ALL DOCTORS
    // =====================================================

    public List<Doctor> getAllDoctors() {

        List<Doctor> doctorList =
            new ArrayList<>();

        String sql =
            "SELECT * FROM doctors";

        try {

            Connection con =
                DBConnection.getConnection();

            PreparedStatement ps =
                con.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery();

            while (rs.next()) {

                Doctor doctor =
                    new Doctor();

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

                doctorList.add(doctor);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                "Error while getting doctors"
            );

            e.printStackTrace();
        }

        return doctorList;
    }


    // =====================================================
    // 3. SEARCH DOCTOR BY DISEASE
    // =====================================================

    public List<Doctor> searchDoctorsByDisease(
            String disease) {

        List<Doctor> doctorList =
            new ArrayList<>();

        /*
         * LOWER()       -> uppercase/lowercase difference ignore
         *
         * REPLACE()     -> spaces ignore
         *
         * LIKE '%...%'  -> partial word matching
         *
         * Example:
         *
         * Database: Fever, Cold
         *
         * Search:
         * fever       -> MATCH
         * FEVER       -> MATCH
         * cold        -> MATCH
         * COLD        -> MATCH
         * fever,cold  -> MATCH
         */

        String sql =
            "SELECT * FROM doctors " +
            "WHERE LOWER(REPLACE(disease, ' ', '')) " +
            "LIKE CONCAT('%', " +
            "LOWER(REPLACE(?, ' ', '')), '%')";

        try {

            Connection con =
                DBConnection.getConnection();

            PreparedStatement ps =
                con.prepareStatement(sql);

            // Remove spaces before searching
            ps.setString(
                1,
                disease.trim()
            );

            System.out.println(
                "========== DOCTOR SEARCH =========="
            );

            System.out.println(
                "Disease received: "
                + disease
            );

            ResultSet rs =
                ps.executeQuery();

            while (rs.next()) {

                Doctor doctor =
                    new Doctor();

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

                doctorList.add(doctor);
            }

            System.out.println(
                "Doctors found: "
                + doctorList.size()
            );

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                "Error while searching doctors"
            );

            e.printStackTrace();
        }

        return doctorList;
    }
}