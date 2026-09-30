package com.hospital.util;

import java.sql.Connection;

public class DBTest {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("SUCCESS - Java connected to MySQL");
        } else {
            System.out.println("FAILED - Java could not connect to MySQL");
        }
    }
}