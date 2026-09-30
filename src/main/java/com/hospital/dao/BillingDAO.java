package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.hospital.model.Billing;
import com.hospital.util.DBConnection;

public class BillingDAO {


// ==========================================
// Create Consultation Bill
// ==========================================
public int createBillAndGetId(Billing billing) {

    int billId = 0;

    String sql =
            "INSERT INTO billing "
            + "(appointment_id, patient_id, doctor_id, "
            + "consultation_fee, additional_charges, "
            + "total_amount, bill_status) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try {

        Connection con = DBConnection.getConnection();

        if (con == null) {
            return 0;
        }

        PreparedStatement ps =
                con.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                );

        ps.setInt(1, billing.getAppointmentId());
        ps.setInt(2, billing.getPatientId());
        ps.setInt(3, billing.getDoctorId());
        ps.setDouble(4, billing.getConsultationFee());
        ps.setDouble(5, billing.getAdditionalCharges());
        ps.setDouble(6, billing.getTotalAmount());
        ps.setString(7, "Pending");

        int rows = ps.executeUpdate();

        if (rows > 0) {

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                billId = rs.getInt(1);
            }

            rs.close();
        }

        ps.close();
        con.close();

    } catch (Exception e) {

        System.out.println("❌ Error creating consultation bill");
        e.printStackTrace();
    }

    return billId;
}


// ==========================================
// Get Doctor Consultation Fee
// ==========================================
public double getDoctorConsultationFee(int doctorId) {

    double fee = 0;

    String sql =
            "SELECT consultation_fee "
            + "FROM doctors "
            + "WHERE doctor_id = ?";

    try {

        Connection con = DBConnection.getConnection();

        if (con == null) {
            return 0;
        }

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setInt(1, doctorId);

        ResultSet rs =
                ps.executeQuery();

        if (rs.next()) {

            fee = rs.getDouble("consultation_fee");
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {

        System.out.println(
                "❌ Error getting consultation fee"
        );

        e.printStackTrace();
    }

    return fee;
}


// ==========================================
// Create Medicine Bill
// ==========================================
public int createMedicineBill(
        int appointmentId,
        int patientId,
        int doctorId,
        double medicineAmount) {

    int billId = 0;

    String sql =
            "INSERT INTO billing "
            + "(appointment_id, patient_id, doctor_id, "
            + "consultation_fee, additional_charges, "
            + "total_amount, bill_status) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try {

        Connection con = DBConnection.getConnection();

        if (con == null) {
            return 0;
        }

        PreparedStatement ps =
                con.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                );

        ps.setInt(1, appointmentId);
        ps.setInt(2, patientId);
        ps.setInt(3, doctorId);

        // No consultation fee for medicine bill
        ps.setDouble(4, 0.0);

        // Medicine amount
        ps.setDouble(5, medicineAmount);

        // Total amount
        ps.setDouble(6, medicineAmount);

        ps.setString(7, "Pending");

        int rows = ps.executeUpdate();

        if (rows > 0) {

            ResultSet rs =
                    ps.getGeneratedKeys();

            if (rs.next()) {

                billId = rs.getInt(1);
            }

            rs.close();
        }

        ps.close();
        con.close();

    } catch (Exception e) {

        System.out.println(
                "❌ Error creating medicine bill"
        );

        e.printStackTrace();
    }

    return billId;
}


}
