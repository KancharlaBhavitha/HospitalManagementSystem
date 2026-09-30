package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hospital.model.PatientRegister;
import com.hospital.util.DBConnection;

public class PatientRegisterDAO {

    public boolean registerPatient(PatientRegister patient) {

        Connection con = null;
        PreparedStatement patientPs = null;
        PreparedStatement loginPs = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("❌ Database connection failed");
                return false;
            }

            // Start transaction
            con.setAutoCommit(false);

            // ------------------------------------
            // 1. Insert patient details
            // ------------------------------------

            String patientSql =
                    "INSERT INTO patient_register " +
                    "(full_name, email, phone_number, password, gender, date_of_birth, address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            patientPs = con.prepareStatement(patientSql);

            patientPs.setString(1, patient.getFullName());
            patientPs.setString(2, patient.getEmail());
            patientPs.setString(3, patient.getPhoneNumber());
            patientPs.setString(4, patient.getPassword());
            patientPs.setString(5, patient.getGender());
            patientPs.setString(6, patient.getDateOfBirth());
            patientPs.setString(7, patient.getAddress());

            int patientRows = patientPs.executeUpdate();

            if (patientRows <= 0) {
                con.rollback();
                return false;
            }

            System.out.println("✅ Patient details inserted");


            // ------------------------------------
            // 2. Insert login details
            // ------------------------------------

            String loginSql =
                    "INSERT INTO login (email, password, role) " +
                    "VALUES (?, ?, ?)";

            loginPs = con.prepareStatement(loginSql);

            loginPs.setString(1, patient.getEmail());
            loginPs.setString(2, patient.getPassword());
            loginPs.setString(3, "PATIENT");

            int loginRows = loginPs.executeUpdate();

            if (loginRows <= 0) {
                con.rollback();
                return false;
            }

            System.out.println("✅ Patient login details inserted");


            // ------------------------------------
            // 3. Commit both inserts
            // ------------------------------------

            con.commit();

            System.out.println("=================================");
            System.out.println("✅ PATIENT REGISTRATION SUCCESS");
            System.out.println("Email : " + patient.getEmail());
            System.out.println("Role  : PATIENT");
            System.out.println("=================================");

            return true;

        } catch (Exception e) {

            System.out.println("❌ PATIENT REGISTRATION FAILED");

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();

            return false;

        } finally {

            try {
                if (patientPs != null) {
                    patientPs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (loginPs != null) {
                    loginPs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}