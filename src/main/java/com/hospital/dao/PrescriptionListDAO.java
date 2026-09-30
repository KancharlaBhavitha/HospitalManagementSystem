package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hospital.util.DBConnection;

public class PrescriptionListDAO {

    public List<Object[]> getPrescriptionsByDoctorId(int doctorId) {

        List<Object[]> prescriptions = new ArrayList<>();

        String sql =
                "SELECT DISTINCT p.prescription_id, " +
                "p.appointment_id, " +
                "p.patient_id, " +
                "pt.full_name AS patient_name " +
                "FROM prescriptions p " +
                "JOIN patients pt ON p.patient_id = pt.patient_id " +
                "WHERE p.doctor_id = ? " +
                "ORDER BY p.prescription_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Object[] row = new Object[4];

                row[0] = rs.getInt("prescription_id");
                row[1] = rs.getInt("appointment_id");
                row[2] = rs.getInt("patient_id");
                row[3] = rs.getString("patient_name");

                prescriptions.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return prescriptions;
    }
}