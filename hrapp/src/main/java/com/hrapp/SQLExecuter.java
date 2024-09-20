package com.hrapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecuter {
    
    //path to database and Connection object
    private final String pathToDataBase = "jdbc:sqlite:" + System.getProperty("user.dir").replace("\\", "/") + "/database/testDB.db";
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

    // Method to execute a SELECT query
    public ResultSet getDataFromDatabase(String query) throws SQLException 
    {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    //Method to execute an UPDATE/DELETE/INSERT method
    public void setDataInDatabase(String query) throws SQLException
    {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
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

