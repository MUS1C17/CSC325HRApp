package com.hrapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch(ClassNotFoundException error)
        {
            error.printStackTrace();
        }
        System.out.println("Working Directory = " + System.getProperty("user.dir").replace("\\", "/"));
        String pathToDataBase =  System.getProperty("user.dir").replace("\\", "/") + "/database/testDB.db";
        try
        {
            
            Connection connection = DriverManager.getConnection(pathToDataBase);
            System.out.println("I finally connected");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        // Create an instance of SQLExecuter
        SQLExecuter testExecute = null;
        ResultSet resultFromQuery = null;

        String firstName;
        try
        {
            testExecute = new SQLExecuter();
            resultFromQuery = testExecute.executeSQLQuery("select * from TestTable");
            if (resultFromQuery.next())
            {
                firstName = resultFromQuery.getString("firstName");
                System.out.println("Result is: " + firstName);
            }
            
        }
        catch(SQLException e)
        {
            // Handle any SQL exceptions
            e.getMessage();
        }
        /*finally
        {
            testExecute.closeConnection();
        }*/
    
    }
}