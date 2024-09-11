package com.hrapp;

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
                System.out.println("The first name is: " + firstName);
            }
            
        }
        catch(SQLException e)
        {
            // Handle any SQL exceptions
            e.getMessage();
        }
        finally
        {
            testExecute.closeConnection();
        }
    
    }
}