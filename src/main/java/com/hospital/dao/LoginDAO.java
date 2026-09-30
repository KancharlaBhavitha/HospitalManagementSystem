package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hospital.model.Login;
import com.hospital.util.DBConnection;

public class LoginDAO {

    // -----------------------------------------
    // Validate Login
    // -----------------------------------------

    public boolean validate(Login login) {

        // =========================
        // ADMIN LOGIN
        // =========================

        if (login.getRole().equalsIgnoreCase("ADMIN")) {

            String adminSql =
                    "SELECT admin_id FROM admin "
                    + "WHERE username = ? AND password = ?";

            try {

                Connection con =
                        DBConnection.getConnection();

                if (con == null) {

                    System.out.println(
                            "❌ Database connection failed"
                    );

                    return false;
                }

                PreparedStatement ps =
                        con.prepareStatement(adminSql);

                ps.setString(1, login.getEmail());
                ps.setString(2, login.getPassword());

                ResultSet rs =
                        ps.executeQuery();

                if (rs.next()) {

                    System.out.println(
                            "✅ Admin Login successful"
                    );

                    rs.close();
                    ps.close();
                    con.close();

                    return true;
                }

                System.out.println(
                        "❌ Invalid Admin login details"
                );

                rs.close();
                ps.close();
                con.close();

            } catch (Exception e) {

                System.out.println(
                        "❌ Admin login error"
                );

                e.printStackTrace();
            }

            return false;
        }


        // =========================
        // PATIENT / DOCTOR LOGIN
        // =========================

        String sql =
                "SELECT login_id FROM login "
                + "WHERE email = ? AND password = ? AND role = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            if (con == null) {

                System.out.println(
                        "❌ Database connection failed"
                );

                return false;
            }

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, login.getEmail());
            ps.setString(2, login.getPassword());
            ps.setString(3, login.getRole());

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                login.setLoginId(
                        rs.getInt("login_id")
                );

                System.out.println(
                        "✅ Login successful"
                );

                rs.close();
                ps.close();
                con.close();

                return true;
            }

            System.out.println(
                    "❌ Invalid login details"
            );

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "❌ Login error"
            );

            e.printStackTrace();
        }

        return false;
    }


    // -----------------------------------------
    // Check Patient Profile
    // -----------------------------------------

    public boolean patientProfileExists(String email) {

        String sql =
                "SELECT patient_id "
                + "FROM patients "
                + "WHERE email = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            if (con == null) {
                return false;
            }

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs =
                    ps.executeQuery();

            boolean exists =
                    rs.next();

            rs.close();
            ps.close();
            con.close();

            return exists;

        } catch (Exception e) {

            System.out.println(
                    "❌ Error checking patient profile"
            );

            e.printStackTrace();

            return false;
        }
    }


    // -----------------------------------------
    // Get Patient ID By Email
    // -----------------------------------------

    public int getPatientIdByEmail(String email) {

        int patientId = 0;

        String sql =
                "SELECT patient_id "
                + "FROM patients "
                + "WHERE email = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            if (con == null) {
                return 0;
            }

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                patientId =
                        rs.getInt("patient_id");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "❌ Error getting patient ID"
            );

            e.printStackTrace();
        }

        return patientId;
    }


    // -----------------------------------------
    // Get Doctor ID By Email
    // -----------------------------------------

    public int getDoctorIdByEmail(String email) {

        int doctorId = 0;

        String sql =
                "SELECT doctor_id "
                + "FROM doctors "
                + "WHERE email = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            if (con == null) {
                return 0;
            }

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                doctorId =
                        rs.getInt("doctor_id");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "❌ Error getting doctor ID"
            );

            e.printStackTrace();
        }

        return doctorId;
    }
}