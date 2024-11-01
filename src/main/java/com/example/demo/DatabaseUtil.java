package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static final String PROPERTIES_FILE = "application.properties";
    private static final Properties properties = new Properties();
    private static String jdbcUrl;
    private static String jdbcUsername;
    private static String jdbcPassword;

    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + PROPERTIES_FILE);
            }
            // load a properties file from class path, inside static method
            properties.load(input);

            jdbcUrl = properties.getProperty("jdbc.url");
            jdbcUsername = properties.getProperty("jdbc.username");
            jdbcPassword = properties.getProperty("jdbc.password");
            String jdbcDriver = properties.getProperty("jdbc.driver");

            Class.forName(jdbcDriver);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
    }

    public static void insertUser(User user) {
        String sql = "INSERT INTO users (originalUsername, originalPassword, currentUsername, currentPassword) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getOriginalUsername());
            stmt.setString(2, user.getOriginalPassword());
            stmt.setString(3, user.getCurrentUsername());
            stmt.setString(4, user.getCurrentPassword());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}