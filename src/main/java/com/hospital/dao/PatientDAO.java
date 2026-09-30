package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hospital.model.Patient;
import com.hospital.util.DBConnection;

public class PatientDAO {

    public boolean registerPatient(Patient patient) {

        Connection con = null;
        PreparedStatement patientPs = null;
        PreparedStatement loginPs = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {

                System.out.println(
                        "❌ Database connection failed"
                );

                return false;
            }


            con.setAutoCommit(false);


            // -----------------------------------------
            // Check email already exists in patients
            // -----------------------------------------

            String checkSql =
                    "SELECT patient_id "
                    + "FROM patients "
                    + "WHERE email = ?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkSql);

            checkPs.setString(
                    1,
                    patient.getEmail()
            );

            ResultSet rs =
                    checkPs.executeQuery();


            if (rs.next()) {

                System.out.println(
                        "❌ Patient already exists"
                );

                rs.close();
                checkPs.close();

                con.rollback();

                return false;
            }


            rs.close();
            checkPs.close();


            // -----------------------------------------
            // Insert into patients
            // -----------------------------------------

            String patientSql =
                    "INSERT INTO patients "
                    + "(full_name, email, phone_number, "
                    + "password, gender, date_of_birth, address) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";


            patientPs =
                    con.prepareStatement(
                            patientSql,
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );


            patientPs.setString(
                    1,
                    patient.getFullName()
            );

            patientPs.setString(
                    2,
                    patient.getEmail()
            );

            patientPs.setString(
                    3,
                    patient.getPhoneNumber()
            );

            patientPs.setString(
                    4,
                    patient.getPassword()
            );

            patientPs.setString(
                    5,
                    patient.getGender()
            );

            patientPs.setString(
                    6,
                    patient.getDateOfBirth()
            );

            patientPs.setString(
                    7,
                    patient.getAddress()
            );


            int patientRows =
                    patientPs.executeUpdate();


            if (patientRows == 0) {

                con.rollback();

                return false;
            }


            // Get generated ID

            ResultSet keys =
                    patientPs.getGeneratedKeys();

            if (keys.next()) {

                patient.setPatientId(
                        keys.getInt(1)
                );
            }

            keys.close();


            // -----------------------------------------
            // Insert into login
            // -----------------------------------------

            String loginSql =
                    "INSERT INTO login "
                    + "(email, password, role) "
                    + "VALUES (?, ?, ?)";


            loginPs =
                    con.prepareStatement(loginSql);


            loginPs.setString(
                    1,
                    patient.getEmail()
            );

            loginPs.setString(
                    2,
                    patient.getPassword()
            );

            loginPs.setString(
                    3,
                    "PATIENT"
            );


            int loginRows =
                    loginPs.executeUpdate();


            if (loginRows == 0) {

                con.rollback();

                return false;
            }


            // -----------------------------------------
            // Commit
            // -----------------------------------------

            con.commit();


            System.out.println(
                    "✅ Patient registration successful"
            );


            return true;


        } catch (Exception e) {

            System.out.println(
                    "❌ Patient registration failed"
            );

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (Exception ex) {

                ex.printStackTrace();
            }

            e.printStackTrace();

            return false;


        } finally {

            try {

                if (patientPs != null) {
                    patientPs.close();
                }

                if (loginPs != null) {
                    loginPs.close();
                }

                if (con != null) {

                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}