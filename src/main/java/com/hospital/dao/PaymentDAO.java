package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hospital.model.Payment;
import com.hospital.util.DBConnection;

public class PaymentDAO {

    public boolean makePayment(Payment payment) {

        boolean status = false;

        Connection con = null;
        PreparedStatement paymentPs = null;
        PreparedStatement billingPs = null;

        try {

            con = DBConnection.getConnection();

            if (con == null) {
                return false;
            }

            // -----------------------------------------
            // Insert Payment
            // -----------------------------------------

            String paymentSql =
                    "INSERT INTO payments "
                    + "(bill_id, patient_id, amount, "
                    + "payment_method, payment_date, "
                    + "payment_status) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            paymentPs =
                    con.prepareStatement(paymentSql);

            paymentPs.setInt(
                    1,
                    payment.getBillId()
            );

            paymentPs.setInt(
                    2,
                    payment.getPatientId()
            );

            paymentPs.setDouble(
                    3,
                    payment.getBillAmount()
            );

            paymentPs.setString(
                    4,
                    payment.getPaymentMethod()
            );

            paymentPs.setString(
                    5,
                    payment.getPaymentDate()
            );

            paymentPs.setString(
                    6,
                    payment.getPaymentStatus()
            );

            int paymentRows =
                    paymentPs.executeUpdate();


            // -----------------------------------------
            // Update Billing Status
            // Pending → Paid
            // -----------------------------------------

            if (paymentRows > 0) {

                String billingSql =
                        "UPDATE billing "
                        + "SET bill_status = 'Paid' "
                        + "WHERE bill_id = ? "
                        + "AND patient_id = ?";

                billingPs =
                        con.prepareStatement(
                                billingSql
                        );

                billingPs.setInt(
                        1,
                        payment.getBillId()
                );

                billingPs.setInt(
                        2,
                        payment.getPatientId()
                );

                int billingRows =
                        billingPs.executeUpdate();

                if (billingRows > 0) {
                    status = true;
                }
            }


        } catch (Exception e) {

            System.out.println(
                    "❌ Error while making payment"
            );

            e.printStackTrace();

        } finally {

            try {
                if (paymentPs != null) {
                    paymentPs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (billingPs != null) {
                    billingPs.close();
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

        return status;
    }
}