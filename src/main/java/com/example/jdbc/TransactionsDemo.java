package com.example.jdbc;

import java.sql.*;

public class TransactionsDemo {
    private static final String URL = "jdbc:mysql://localhost:3306/Demo_PU";
    private static final String User = "root";
    private static final String Password = "Priyanshu@07";


    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, User, Password)) {
            System.out.println("Connected to the Database!");

            con.setAutoCommit(false);

            try {
                ///  Order, Order items
                // INSERT IN TO ORDER
                int orderId = insertOrder(con, 101, "Avni08", 2500.0);

                // INSERT IN TO ORDER ITEM
                insertOderItem(con, orderId, "Laptop01", 1, 1500.0);

                con.commit();
                System.out.println("Transaction Committed Successfully");
            } catch (Exception e) {
                e.printStackTrace();
                con.rollback();
                System.out.println("Operation Rollback Successfully");
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertOderItem(Connection con, int orderId, String productName, int quantity, double price) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, product_name, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.setString(2, productName);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, price);
            pstmt.executeUpdate();
            int x = 10/0;
        } // Kisi catch block ki zaroorat nahi, error seedhe main method me jayegi jahan rollback handle ho raha hai!
    }

    private static int insertOrder(Connection con, int CustomerId, String CustomerName, double Price) {
        String sql = "INSERT INTO orders (user_id,customer_name, total_amount) " +
                " VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, CustomerId);
            pstmt.setString(2, CustomerName);
            pstmt.setDouble(3, Price);
            int rows = pstmt.executeUpdate();
            System.out.println("INSERT INTO orders: " + rows);

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    System.out.println("ORDER ID: " + orderId);
                    return orderId;
                } else {
                    throw new SQLException("Order Id not gernated");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}



