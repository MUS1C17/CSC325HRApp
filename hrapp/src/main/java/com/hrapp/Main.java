package com.hrapp;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        
        /*// Create an instance of SQLExecuter
        SQLExecuter testExecute = new SQLExecuter();
        ResultSet resultFromQuery = null;
        String firstName;

        try
        {
            testExecute.setDataInDatabase("DELETE FROM Employee WHERE EmployeeID = 14");
            resultFromQuery = testExecute.getDataFromDatabase("select * from Employee where EmployeeID = 3");
            if (resultFromQuery.next())
            {
                firstName = resultFromQuery.getString("FirstName");
                System.out.println("The first name is: " + firstName);
            }
            
            testExecute.setDataInDatabase("UPDATE Employee SET FirstName = 'testMark' WHERE EmployeeID = 5");
        }
        catch(SQLException e)
        {
            // Handle any SQL exceptions
            e.getMessage();
        }
        finally
        {
            testExecute.closeConnection();
        }*/

        new SimpleGUI();
    
    }
}