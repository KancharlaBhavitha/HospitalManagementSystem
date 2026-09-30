package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hospital.util.DBConnection;

public class DoctorListDAO {

    public void getDoctors() {

        try {

            Connection con=DBConnection.getConnection();

            String sql="SELECT * FROM doctor";

            PreparedStatement ps=con.prepareStatement(sql);

            ResultSet rs=ps.executeQuery();

            while(rs.next()){

                System.out.println(rs.getString("full_name"));

            }

            rs.close();
            ps.close();
            con.close();

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}