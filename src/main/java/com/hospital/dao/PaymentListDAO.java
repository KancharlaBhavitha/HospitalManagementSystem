package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.hospital.model.PaymentList;
import com.hospital.util.DBConnection;

public class PaymentListDAO {

    public ArrayList<PaymentList> getPaymentsByPatientId(int patientId) {

        ArrayList<PaymentList> list =
                new ArrayList<>();

        // -----------------------------------------
        // Get only logged-in patient's payments
        // -----------------------------------------
        String sql =
                "SELECT "
                + "p.payment_id, "
                + "p.bill_id, "
                + "p.patient_id, "
                + "pt.full_name AS patient_name, "
                + "p.amount AS bill_amount, "
                + "p.payment_method, "
                + "p.payment_date, "
                + "p.payment_status "
                + "FROM payments p "
                + "JOIN patients pt "
                + "ON p.patient_id = pt.patient_id "
                + "WHERE p.patient_id = ? "
                + "ORDER BY p.payment_id DESC";

        try {

            Connection con =
                    DBConnection.getConnection();

            if (con == null) {

                System.out.println(
                        "DATABASE CONNECTION FAILED"
                );

                return list;
            }

            PreparedStatement ps =
                    con.prepareStatement(sql);

            // Logged-in patient's ID
            ps.setInt(1, patientId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                PaymentList payment =
                        new PaymentList();

                // -----------------------------------------
                // Payment ID
                // -----------------------------------------
                payment.setPaymentId(
                        rs.getInt("payment_id")
                );

                // -----------------------------------------
                // Bill ID
                // -----------------------------------------
                payment.setBillId(
                        rs.getInt("bill_id")
                );

                // -----------------------------------------
                // Patient Name
                // -----------------------------------------
                payment.setPatientName(
                        rs.getString("patient_name")
                );

                // -----------------------------------------
                // Amount
                // -----------------------------------------
                payment.setBillAmount(
                        rs.getDouble("bill_amount")
                );

                // -----------------------------------------
                // Payment Method
                // -----------------------------------------
                payment.setPaymentMethod(
                        rs.getString("payment_method")
                );

                // -----------------------------------------
                // Payment Date
                // -----------------------------------------
                payment.setPaymentDate(
                        rs.getString("payment_date")
                );

                // -----------------------------------------
                // Payment Status
                // -----------------------------------------
                payment.setPaymentStatus(
                        rs.getString("payment_status")
                );

                list.add(payment);
            }

            rs.close();
            ps.close();
            con.close();

            System.out.println(
                    "Patient payments found: "
                    + list.size()
            );

        } catch (Exception e) {

            System.out.println(
                    "ERROR WHILE GETTING PATIENT PAYMENTS"
            );

            e.printStackTrace();
        }

        return list;
    }
}