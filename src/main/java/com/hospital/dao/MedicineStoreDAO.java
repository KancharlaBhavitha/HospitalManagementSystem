package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hospital.model.MedicineStore;
import com.hospital.util.DBConnection;

public class MedicineStoreDAO {

    // -----------------------------------------
    // Get Prescription Medicines
    // -----------------------------------------

    public List<MedicineStore> getPrescriptionMedicines(
            int appointmentId,
            int patientId) {

        List<MedicineStore> list =
                new ArrayList<>();

        String sql =
                "SELECT prescription_id, appointment_id, "
                + "patient_id, medicine_name, dosage, "
                + "duration, instructions "
                + "FROM prescriptions "
                + "WHERE appointment_id = ? "
                + "AND patient_id = ? "
                + "ORDER BY prescription_id";

        try {

            Connection con =
                    DBConnection.getConnection();

            if (con == null) {
                return list;
            }

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, appointmentId);
            ps.setInt(2, patientId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                MedicineStore medicine =
                        new MedicineStore();

                medicine.setPrescriptionId(
                        rs.getInt("prescription_id")
                );

                medicine.setAppointmentId(
                        rs.getInt("appointment_id")
                );

                medicine.setPatientId(
                        rs.getInt("patient_id")
                );

                medicine.setMedicineName(
                        rs.getString("medicine_name")
                );

                medicine.setDosage(
                        rs.getString("dosage")
                );

                medicine.setDuration(
                        rs.getString("duration")
                );

                medicine.setInstructions(
                        rs.getString("instructions")
                );

                list.add(medicine);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "❌ Error getting prescription medicines"
            );

            e.printStackTrace();
        }

        return list;
    }


    // -----------------------------------------
    // Save Medicine Amount
    // -----------------------------------------

    public int saveMedicineAmount(
            MedicineStore medicine) {

        int medicineId = 0;

        String sql =
                "INSERT INTO medicine_store "
                + "(prescription_id, appointment_id, "
                + "patient_id, medicine_name, dosage, "
                + "duration, instructions, amount) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection con =
                    DBConnection.getConnection();

            if (con == null) {
                return 0;
            }

            PreparedStatement ps =
                    con.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            ps.setInt(
                    1,
                    medicine.getPrescriptionId()
            );

            ps.setInt(
                    2,
                    medicine.getAppointmentId()
            );

            ps.setInt(
                    3,
                    medicine.getPatientId()
            );

            ps.setString(
                    4,
                    medicine.getMedicineName()
            );

            ps.setString(
                    5,
                    medicine.getDosage()
            );

            ps.setString(
                    6,
                    medicine.getDuration()
            );

            ps.setString(
                    7,
                    medicine.getInstructions()
            );

            ps.setDouble(
                    8,
                    medicine.getAmount()
            );

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                ResultSet rs =
                        ps.getGeneratedKeys();

                if (rs.next()) {

                    medicineId =
                            rs.getInt(1);
                }

                rs.close();
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "❌ Error saving medicine amount"
            );

            e.printStackTrace();
        }

        return medicineId;
    }
}