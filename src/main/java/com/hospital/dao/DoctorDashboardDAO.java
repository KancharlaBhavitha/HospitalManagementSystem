package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hospital.util.DBConnection;

public class DoctorDashboardDAO {

    public int getPatientCount(int doctorId) {

        int count = 0;

        String sql =
                "SELECT COUNT(DISTINCT patient_id) " +
                "FROM appointments " +
                "WHERE doctor_id = ? " +
                "AND appointment_date >= CURDATE()";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, doctorId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("Error while getting patient count");
            e.printStackTrace();
        }

        return count;
    }


    public int getUpcomingAppointmentCount(int doctorId) {

        int count = 0;

        String sql =
                "SELECT COUNT(*) " +
                "FROM appointments " +
                "WHERE doctor_id = ? " +
                "AND appointment_date >= CURDATE() " +
                "AND status = 'Pending'";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, doctorId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "Error while getting upcoming appointment count"
            );

            e.printStackTrace();
        }

        return count;
    }


    public List<String> getTodaysPatients(int doctorId) {

        List<String> patients = new ArrayList<>();

        String sql =
                "SELECT DISTINCT p.full_name " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "WHERE a.doctor_id = ? " +
                "AND a.appointment_date = CURDATE() " +
                "ORDER BY p.full_name";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, doctorId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                patients.add(rs.getString("full_name"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "Error while getting today's patients"
            );

            e.printStackTrace();
        }

        return patients;
    }
}