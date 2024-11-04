package com.hrapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    // Path to database
    private final String pathToDataBase = "jdbc:sqlite:" + System.getProperty("user.dir").replace("\\", "/") + "/database/testDB.db";

    // Method to create the necessary tables
    public void initializeDatabase() {
        String createEmployeesTable = "CREATE TABLE IF NOT EXISTS employees ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "position TEXT NOT NULL,"
                + "salary REAL NOT NULL,"
                + "department TEXT NOT NULL);";

        try (Connection connection = DriverManager.getConnection(pathToDataBase);
             Statement stmt = connection.createStatement()) {
            // Create the employees table if it does not exist
            stmt.execute(createEmployeesTable);
            System.out.println("Employees table created successfully or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
