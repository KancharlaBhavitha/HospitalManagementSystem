package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hospital.model.Admin;
import com.hospital.model.DoctorActivity;
import com.hospital.util.DBConnection;

public class AdminDAO {

    // =========================
    // ADMIN LOGIN
    // =========================

    public boolean login(Admin admin) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM admin WHERE username=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                status = true;
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }


    // =========================
    // TOTAL PATIENTS
    // =========================

    public int getTotalPatients() {

        int count = 0;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM patients";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }


    // =========================
    // TOTAL DOCTORS
    // =========================

    public int getTotalDoctors() {

        int count = 0;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM doctors";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }


    // =========================
    // TOTAL APPOINTMENTS
    // =========================

    public int getTotalAppointments() {

        int count = 0;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM appointments";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }


    // =========================
    // THIS MONTH REVENUE
    // =========================

    public double getThisMonthRevenue() {

        double revenue = 0;

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT COALESCE(SUM(amount), 0) " +
                    "FROM payments " +
                    "WHERE payment_status='Paid' " +
                    "AND MONTH(payment_date)=MONTH(CURDATE()) " +
                    "AND YEAR(payment_date)=YEAR(CURDATE())";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                revenue = rs.getDouble(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return revenue;
    }


    // =========================
    // DOCTOR ACTIVITY
    // =========================

    public List<DoctorActivity> getDoctorActivity() {

        List<DoctorActivity> list = new ArrayList<>();

        String sql =
                "SELECT d.doctor_id, d.full_name, " +
                "COUNT(DISTINCT CASE " +
                "WHEN a.status='Completed' THEN a.patient_id END) AS patients_checked, " +
                "SUM(CASE " +
                "WHEN a.status='Completed' THEN 1 ELSE 0 END) AS completed_appointments " +
                "FROM doctors d " +
                "LEFT JOIN appointments a " +
                "ON d.doctor_id = a.doctor_id " +
                "GROUP BY d.doctor_id, d.full_name " +
                "ORDER BY d.doctor_id";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                DoctorActivity activity = new DoctorActivity();

                activity.setDoctorId(
                        rs.getInt("doctor_id")
                );

                activity.setDoctorName(
                        rs.getString("full_name")
                );

                activity.setPatientsChecked(
                        rs.getInt("patients_checked")
                );

                activity.setCompletedAppointments(
                        rs.getInt("completed_appointments")
                );

                list.add(activity);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}