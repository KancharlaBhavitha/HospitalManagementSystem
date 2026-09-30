package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hospital.model.Invoice;
import com.hospital.util.DBConnection;

public class InvoiceDAO {

    public Invoice getInvoiceByBillNumber(int billNumber, int patientId) {

        Invoice invoice = null;

        String sql =
                "SELECT " +
                "b.bill_id, " +
                "b.appointment_id, " +
                "b.patient_id, " +
                "b.doctor_id, " +
                "p.full_name AS patient_name, " +
                "d.full_name AS doctor_name, " +
                "b.consultation_fee, " +
                "b.additional_charges, " +
                "b.total_amount, " +
                "b.bill_status, " +
                "pay.payment_method, " +
                "pay.payment_status, " +
                "pay.payment_date " +
                "FROM billing b " +
                "JOIN patients p " +
                "ON b.patient_id = p.patient_id " +
                "JOIN doctors d " +
                "ON b.doctor_id = d.doctor_id " +
                "LEFT JOIN payments pay " +
                "ON b.bill_id = pay.bill_id " +
                "WHERE b.bill_id = ? " +
                "AND b.patient_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("DATABASE CONNECTION FAILED");
                return null;
            }

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, billNumber);
            ps.setInt(2, patientId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                invoice = new Invoice();

                invoice.setInvoiceId(
                        rs.getInt("bill_id")
                );

                invoice.setBillNumber(
                        rs.getInt("bill_id")
                );

                invoice.setAppointmentId(
                        rs.getInt("appointment_id")
                );

                invoice.setPatientId(
                        rs.getInt("patient_id")
                );

                invoice.setDoctorId(
                        rs.getInt("doctor_id")
                );

                invoice.setPatientName(
                        rs.getString("patient_name")
                );

                invoice.setDoctorName(
                        rs.getString("doctor_name")
                );

                invoice.setConsultationFee(
                        rs.getDouble("consultation_fee")
                );

                invoice.setAdditionalCharges(
                        rs.getDouble("additional_charges")
                );

                invoice.setTotalAmount(
                        rs.getDouble("total_amount")
                );

                invoice.setPaymentMethod(
                        rs.getString("payment_method")
                );

                invoice.setPaymentStatus(
                        rs.getString("payment_status")
                );

                invoice.setPaymentDate(
                        rs.getString("payment_date")
                );

                invoice.setInvoiceDate(
                        rs.getString("payment_date")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "ERROR WHILE GETTING INVOICE"
            );

            e.printStackTrace();
        }

        return invoice;
    }
}