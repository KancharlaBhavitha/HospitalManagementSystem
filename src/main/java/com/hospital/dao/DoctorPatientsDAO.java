package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hospital.util.DBConnection;

public class DoctorPatientsDAO {

    public List<String> getPatientsByDoctorId(int doctorId) {

        List<String> patientList = new ArrayList<>();

        String sql =
                "SELECT DISTINCT p.full_name " +
                "FROM appointments a " +
                "JOIN patients p " +
                "ON a.patient_id = p.patient_id " +
                "WHERE a.doctor_id = ? " +
                "ORDER BY p.full_name";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, doctorId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                patientList.add(
                        rs.getString("full_name")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "Error while getting doctor patients"
            );

            e.printStackTrace();
        }

        return patientList;
    }
}