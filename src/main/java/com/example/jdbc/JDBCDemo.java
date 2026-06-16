package com.example.jdbc;

import java.sql.*;

public class JDBCDemo {
    private static final String URL = "jdbc:mysql://localhost:3306/Demo_PU";
    private static final String User = "root";
    private static final String Password = "Priyanshu@07";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, User, Password);
            System.out.println("Connected to the Database!");
            conn.setAutoCommit(false);


            insertStudent(conn, "Rahul", "rahul@gmail.com");
            insertStudent(conn, "Rohan", "rohan@gmail.com");

//            if (true) {
//                throw new SQLException("Internet Down Ya Server Crash Ho Gaya!");
//            }


            conn.commit();
            System.out.println("Transaction Committed!");

            selectStudent(conn);

        } catch (SQLException e) {
            System.out.println("Error Aayi! Rollback ho raha hai...");
            try {
                if (conn != null) {
                    // 4. Fail hone par wapas zero kar diya
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertStudent(Connection conn, String name, String email) throws SQLException {
        String sql = "INSERT INTO student (name, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("Inserted in staging: " + name);
        }
    }

    private static void selectStudent(Connection conn) {
        // Naya connection banana padega kyunki main wala close ho chuka hoga agar baad me chalayein
        try (Connection selectConn = DriverManager.getConnection(URL, User, Password);
             Statement stmt = selectConn.createStatement()) {
            String sql = "SELECT name, email FROM student";
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("\n--- Student List ---");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " : " + resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//       Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(URL, User, Password);
//            System.out.println("Connected to the Database!");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                conn.close();
//                System.out.println("Connection Closed!");
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
