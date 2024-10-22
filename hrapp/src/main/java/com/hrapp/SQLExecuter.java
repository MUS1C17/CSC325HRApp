package com.hrapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    // Method to execute a SELECT query using PreparedStatement
    public ResultSet getDataFromDatabase(String query, Object...parameters) throws SQLException 
    {
        PreparedStatement pstmt = connection.prepareStatement(query);
        setParameters(pstmt, parameters);
        return pstmt.executeQuery();
    }

    //Method to execute an UPDATE/DELETE/INSERT method useing PreparedStatement
    public void setDataInDatabase(String query, Object...parameters) throws SQLException
    {
        PreparedStatement pstmt = connection.prepareStatement(query);
        setParameters(pstmt, parameters);
        pstmt.executeUpdate();
    }

    private void setParameters(PreparedStatement pstmt, Object...parameters) throws SQLException
    {
        for(int i = 0; i < parameters.length; i++)
        {
            pstmt.setObject(i + 1, parameters[i]);
        }
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
     
    //Returns the current database connection.
    public Connection getConnection() 
    {
        return connection;
    }
}

