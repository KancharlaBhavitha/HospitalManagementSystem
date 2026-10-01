package com.hospital.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String host = System.getenv().getOrDefault(
                    "DB_HOST", "localhost"
            );

            String port = System.getenv().getOrDefault(
                    "DB_PORT", "3306"
            );

            String database = System.getenv().getOrDefault(
                    "DB_NAME", "hospital_db"
            );

            String user = System.getenv().getOrDefault(
                    "DB_USER", "root"
            );

            String password = System.getenv().getOrDefault(
                    "DB_PASSWORD", "root"
            );

            String sslMode = System.getenv().getOrDefault(
                    "DB_SSL_MODE", "DISABLED"
            );

            String URL = "jdbc:mysql://" + host + ":" + port
                    + "/" + database
                    + "?sslMode=" + sslMode;

            con = DriverManager.getConnection(
                    URL,
                    user,
                    password
            );

            System.out.println(
                    "Database Connected Successfully"
            );

        } catch (Exception e) {

            System.out.println(
                    "❌ Database Connection Failed"
            );

            e.printStackTrace();
        }

        return con;
    }
}