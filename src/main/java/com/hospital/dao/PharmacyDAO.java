package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hospital.model.Pharmacy;
import com.hospital.util.DBConnection;

public class PharmacyDAO {

    public boolean addMedicine(Pharmacy medicine) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO pharmacy(medicine_name,category,manufacturer,quantity,price,expiry_date) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, medicine.getMedicineName());
            ps.setString(2, medicine.getCategory());
            ps.setString(3, medicine.getManufacturer());
            ps.setInt(4, medicine.getQuantity());
            ps.setDouble(5, medicine.getPrice());
            ps.setString(6, medicine.getExpiryDate());

            int rows = ps.executeUpdate();

            if(rows > 0){
                status = true;
            }

            ps.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return status;
    }

}