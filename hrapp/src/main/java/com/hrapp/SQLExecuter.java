package com.hrapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecuter {
    
    private final String pathToDataBase = "jdbc:sqlite:C:/Users/Mark/Desktop/Рабочий стол/USA/Murray State University/Fall 2024/CSC 325/HRApp/CSC325HRApp/database/testDB.db";

    // Connection object
    private Connection connection = null;

    // Constructor to establish the connection
    public SQLExecuter() throws SQLException 
    {
        try 
        {
            
            connection = DriverManager.getConnection(pathToDataBase);

            System.out.println("Connection to SQLite has been established."); 
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            throw new SQLException("Error connecting to the SQLite database.");
        }
    }

    // Method to execute a query
    public ResultSet executeSQLQuery(String query) throws SQLException 
    {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

     // Method to close the database connection
    public void closeConnection() 
    {
        try 
        {
            if (connection != null && !connection.isClosed()) 
            {
                connection.close();
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

}

