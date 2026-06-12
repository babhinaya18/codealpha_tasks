package com.stocktrading;

import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/vstread_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver context missing.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static String[] getUserDetails(String username) {
        String query = "SELECT full_name, email FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new String[]{rs.getString("full_name"), rs.getString("email")};
            }
        } catch (SQLException e) {/**/}
        return new String[]{"Abhinaya Sharma", "abhinaya@vstread.com"};
    }

    public static double getBalance(String username) {
        String query = "SELECT balance FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
        } catch (SQLException e) {/**/}
        return 100000.0;
    }

    public static void updateBalance(String username, double newBalance) {
        String query = "UPDATE users SET balance = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {/**/}
    }

    public static HashMap<String, Integer> loadPortfolio(String username) {
        HashMap<String, Integer> holdings = new HashMap<>();
        String query = "SELECT stock_name, quantity FROM portfolio WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                holdings.put(rs.getString("stock_name"), rs.getInt("quantity"));
            }
        } catch (SQLException e) {/**/}
        return holdings;
    }

    public static void saveStockToPortfolio(String username, String stockName, int quantity) {
        String query = "INSERT INTO portfolio (username, stock_name, quantity) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, stockName);
            stmt.setInt(3, quantity);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {/**/}
    }

    public static void removeStockFromPortfolio(String username, String stockName) {
        String query = "DELETE FROM portfolio WHERE username = ? AND stock_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, stockName);
            stmt.executeUpdate();
        } catch (SQLException e) {/**/}
    }

    public static void logTransaction(String username, String type, String stockName, int quantity, double price) {
        String query = "INSERT INTO transactions (username, type, stock_name, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, type);
            stmt.setString(3, stockName);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, price);
            stmt.executeUpdate();
        } catch (SQLException e) {/**/}
    }

    public static ArrayList<String[]> loadTransactions(String username) {
        ArrayList<String[]> logs = new ArrayList<>();
        String query = "SELECT type, stock_name, quantity, price FROM transactions WHERE username = ? ORDER BY timestamp DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(new String[]{
                        rs.getString("type"),
                        rs.getString("stock_name"),
                        String.valueOf(rs.getInt("quantity")),
                        "₹" + String.format("%.2f", rs.getDouble("price"))
                });
            }
        } catch (SQLException e) {/**/}
        return logs;
    }

    public static void addToWatchlist(String username, String stockName) {
        String query = "INSERT IGNORE INTO watchlist (username, stock_name) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, stockName);
            stmt.executeUpdate();
        } catch (SQLException e) {/**/}
    }

    public static ArrayList<String> loadWatchlist(String username) {
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT stock_name FROM watchlist WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("stock_name"));
            }
        } catch (SQLException e) {/**/}
        return list;
    }
}