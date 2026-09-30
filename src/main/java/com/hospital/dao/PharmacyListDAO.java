package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.hospital.model.PharmacyList;
import com.hospital.util.DBConnection;

public class PharmacyListDAO {

    public ArrayList<PharmacyList> getAllMedicines() {

        ArrayList<PharmacyList> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM pharmacy";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                PharmacyList medicine = new PharmacyList();

                medicine.setMedicineId(rs.getInt("medicine_id"));
                medicine.setMedicineName(rs.getString("medicine_name"));
                medicine.setCategory(rs.getString("category"));
                medicine.setManufacturer(rs.getString("manufacturer"));
                medicine.setQuantity(rs.getInt("quantity"));
                medicine.setPrice(rs.getDouble("price"));
                medicine.setExpiryDate(rs.getString("expiry_date"));

                list.add(medicine);
            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}