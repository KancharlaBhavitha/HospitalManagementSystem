package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.hospital.model.BillingList;
import com.hospital.util.DBConnection;

public class BillingListDAO {

    public ArrayList<BillingList> getBillsByPatientId(int patientId) {

        ArrayList<BillingList> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM billing "
                + "WHERE patient_id = ? "
                + "ORDER BY bill_id DESC";

        try {

            Connection con =
                    DBConnection.getConnection();

            if (con == null) {
                return list;
            }

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, patientId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                BillingList bill =
                        new BillingList();

                bill.setBillId(
                        rs.getInt("bill_id")
                );

                bill.setAppointmentId(
                        rs.getInt("appointment_id")
                );

                bill.setPatientId(
                        rs.getInt("patient_id")
                );

                bill.setDoctorId(
                        rs.getInt("doctor_id")
                );

                bill.setConsultationFee(
                        rs.getDouble("consultation_fee")
                );

                bill.setAdditionalCharges(
                        rs.getDouble("additional_charges")
                );

                bill.setTotalAmount(
                        rs.getDouble("total_amount")
                );

                bill.setBillStatus(
                        rs.getString("bill_status")
                );

                list.add(bill);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "❌ Error getting patient billing list"
            );

            e.printStackTrace();
        }

        return list;
    }
}