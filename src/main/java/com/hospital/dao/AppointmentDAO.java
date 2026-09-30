package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.hospital.model.Appointment;
import com.hospital.util.DBConnection;

public class AppointmentDAO {

    public int bookAppointment(Appointment appointment) {

        int appointmentId = 0;

        String sql =
                "INSERT INTO appointments "
                + "(patient_id, doctor_id, appointment_date, "
                + "appointment_time, reason, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    )
        ) {

            ps.setInt(
                    1,
                    appointment.getPatientId()
            );

            ps.setInt(
                    2,
                    appointment.getDoctorId()
            );

            ps.setString(
                    3,
                    appointment.getAppointmentDate()
            );

            ps.setString(
                    4,
                    appointment.getAppointmentTime()
            );

            ps.setString(
                    5,
                    appointment.getReason()
            );

            ps.setString(
                    6,
                    "Pending"
            );


            int rows =
                    ps.executeUpdate();


            if (rows > 0) {

                ResultSet rs =
                        ps.getGeneratedKeys();

                if (rs.next()) {

                    appointmentId =
                            rs.getInt(1);
                }

                rs.close();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return appointmentId;
    }
}