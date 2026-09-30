package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hospital.model.Register;
import com.hospital.util.DBConnection;

public class RegisterDAO {

    public boolean registerPatient(Register patient) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO patient(full_name,age,gender,blood_group,phone_number,address,emergency_number,medical_history) VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, patient.getFullName());
            ps.setInt(2, patient.getAge());
            ps.setString(3, patient.getGender());
            ps.setString(4, patient.getBloodGroup());
            ps.setString(5, patient.getPhoneNumber());
            ps.setString(6, patient.getAddress());
            ps.setString(7, patient.getEmergencyNumber());
            ps.setString(8, patient.getMedicalHistory());

            int i = ps.executeUpdate();

            if(i > 0) {
                status = true;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return status;
    }

}