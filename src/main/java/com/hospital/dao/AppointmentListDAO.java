package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hospital.model.AppointmentList;
import com.hospital.util.DBConnection;

public class AppointmentListDAO {


    public List<AppointmentList> getAppointmentsByPatientId(
            int patientId) {

        List<AppointmentList> list =
                new ArrayList<>();

        String sql =
                "SELECT a.appointment_id, " +
                "a.patient_id, " +
                "p.full_name AS patient_name, " +
                "d.full_name AS doctor_name, " +
                "a.appointment_date, " +
                "a.appointment_time, " +
                "a.reason AS problem, " +
                "a.status " +
                "FROM appointments a " +
                "JOIN patients p " +
                "ON a.patient_id = p.patient_id " +
                "JOIN doctors d " +
                "ON a.doctor_id = d.doctor_id " +
                "WHERE a.patient_id = ? " +
                "ORDER BY a.appointment_id DESC";


        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, patientId);

            ResultSet rs =
                    ps.executeQuery();


            while (rs.next()) {

                AppointmentList appointment =
                        new AppointmentList();

                appointment.setAppointmentId(
                        rs.getInt("appointment_id")
                );

                appointment.setPatientId(
                        rs.getInt("patient_id")
                );

                appointment.setPatientName(
                        rs.getString("patient_name")
                );

                appointment.setDoctorName(
                        rs.getString("doctor_name")
                );

                appointment.setAppointmentDate(
                        rs.getString("appointment_date")
                );

                appointment.setAppointmentTime(
                        rs.getString("appointment_time")
                );

                appointment.setProblem(
                        rs.getString("problem")
                );

                appointment.setStatus(
                        rs.getString("status")
                );

                list.add(appointment);
            }


            rs.close();
            ps.close();
            con.close();


        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }



    public List<AppointmentList> getAppointmentsByDoctorId(
            int doctorId) {

        List<AppointmentList> list =
                new ArrayList<>();

        String sql =
                "SELECT a.appointment_id, " +
                "a.patient_id, " +
                "p.full_name AS patient_name, " +
                "d.full_name AS doctor_name, " +
                "a.appointment_date, " +
                "a.appointment_time, " +
                "a.reason AS problem, " +
                "a.status " +
                "FROM appointments a " +
                "JOIN patients p " +
                "ON a.patient_id = p.patient_id " +
                "JOIN doctors d " +
                "ON a.doctor_id = d.doctor_id " +
                "WHERE a.doctor_id = ? " +
                "ORDER BY a.appointment_id DESC";


        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, doctorId);

            ResultSet rs =
                    ps.executeQuery();


            while (rs.next()) {

                AppointmentList appointment =
                        new AppointmentList();

                appointment.setAppointmentId(
                        rs.getInt("appointment_id")
                );

                appointment.setPatientId(
                        rs.getInt("patient_id")
                );

                appointment.setPatientName(
                        rs.getString("patient_name")
                );

                appointment.setDoctorName(
                        rs.getString("doctor_name")
                );

                appointment.setAppointmentDate(
                        rs.getString("appointment_date")
                );

                appointment.setAppointmentTime(
                        rs.getString("appointment_time")
                );

                appointment.setProblem(
                        rs.getString("problem")
                );

                appointment.setStatus(
                        rs.getString("status")
                );

                list.add(appointment);
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