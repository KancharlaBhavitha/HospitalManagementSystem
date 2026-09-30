package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hospital.model.PatientDoctorActivity;
import com.hospital.util.DBConnection;

public class AdminPatientDAO {

    public List<PatientDoctorActivity> getPatientDoctorDetails() {

        List<PatientDoctorActivity> list = new ArrayList<>();

        String sql =
                "SELECT p.patient_id, " +
                "p.full_name AS patient_name, " +
                "d.full_name AS doctor_name, " +
                "a.reason, " +
                "a.status " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                "ORDER BY a.appointment_id";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                PatientDoctorActivity activity =
                        new PatientDoctorActivity();

                activity.setPatientId(
                        rs.getInt("patient_id")
                );

                activity.setPatientName(
                        rs.getString("patient_name")
                );

                activity.setDoctorName(
                        rs.getString("doctor_name")
                );

                activity.setReason(
                        rs.getString("reason")
                );

                activity.setStatus(
                        rs.getString("status")
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