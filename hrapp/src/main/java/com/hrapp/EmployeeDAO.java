package com.hrapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


/*
 * This class will handle all CRUD (Create, Read, Update, Delete) operations for 
 * the Employee entity using the enhanced SQLExecuter
 */

public class EmployeeDAO //DAO - Data Access Object
{
    private SQLExecuter executer;

    //EmployeeDAO constractor
    public EmployeeDAO() throws SQLException 
    {
        executer = new SQLExecuter();
    }

    /**
     * Fetches all active (non-deleted) employees from the database.
     * 
     * @return List of Employee objects.
     */
    public List<Employee> getAllEmployees() throws SQLException 
    {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE isDeleted = 0";


        try(ResultSet result = executer.getDataFromDatabase(query))
        {
            
            String dateStr = result.getString("DateOfBirth");
            LocalDate dateOfBirth = null;

            // Parse the date string into LocalDate
            if (dateStr != null && !dateStr.isEmpty()) 
            {
                try 
                {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    dateOfBirth = LocalDate.parse(dateStr, formatter);
                } 
                catch (DateTimeParseException e) 
                {
                    e.printStackTrace();
                }
            }

            while(result.next())
            {
                Employee employee = new Employee(
                    result.getString("FirstName"),
                    result.getString("LastName"),
                    dateOfBirth,
                    result.getString("JobTitle"),
                    result.getString("Department"),
                    result.getString("WorkLocation"),
                    result.getString("EmploymentStatus"),
                    result.getString("Email"),
                    result.getString("PhoneNumber"),
                    result.getBigDecimal("HourlyRate"),
                    result.getString("Notes"),
                    result.getString("HardSkill1"),
                    result.getString("HardSkill2"),
                    result.getString("SoftSkill1"),
                    result.getString("SoftSkill2"),
                    result.getInt("IsManager"),
                    result.getInt("IsCEO")
                );
                employee.setEmployeeID(result.getInt("EmployeeID"));
                employee.setIsDeleted(result.getInt("IsDeleted"));
                employees.add(employee);
            }
        }

        return employees;
    }

    //Methd to get Details about employee that are not shown in the table 
    public Employee getEmployeeDetails(int id) throws SQLException
    {
        String query = "SELECT * FROM Employee WHERE EmployeeID = ?";
        try (PreparedStatement pstmt = executer.getConnection().prepareStatement(query)) 
        {
            pstmt.setInt(1, id);
            try (ResultSet result = pstmt.executeQuery()) 
            {
                
                if (result.next()) 
                {
                    String dateStr = result.getString("DateOfBirth");
                    LocalDate dateOfBirth = null;
                    if (dateStr != null && !dateStr.isEmpty()) 
                    {
                        try 
                        {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            dateOfBirth = LocalDate.parse(dateStr, formatter);
                        } 
                        catch (DateTimeParseException e) 
                        {
                            e.printStackTrace();
                        }
                    } 
                        //Create Employee instance
                        Employee employee = new Employee(
                            result.getString("FirstName"),
                            result.getString("LastName"),
                            dateOfBirth,
                            result.getString("JobTitle"),
                            result.getString("Department"),
                            result.getString("WorkLocation"),
                            result.getString("EmploymentStatus"),
                            result.getString("Email"),
                            result.getString("PhoneNumber"),
                            result.getBigDecimal("HourlyRate"),
                            result.getString("Notes"),
                            result.getString("HardSkill1"),
                            result.getString("HardSkill2"),
                            result.getString("SoftSkill1"),
                            result.getString("SoftSkill2"),
                            result.getInt("IsManager"),
                            result.getInt("IsCEO")
                        );

                        employee.setEmployeeID(result.getInt("EmployeeID"));
                        employee.setIsDeleted(result.getInt("IsDeleted"));
                        return employee;
                                       
                } 
                else 
                {
                    // Employee not found
                    return null;
                }
            }
        }
    }
    

    /**
     * Adds a new employee to the database.
     * 
     * @param employee The Employee object to add.
     * @throws SQLException If a database access error occurs.
     */
    public void addEmployee(Employee employee) throws SQLException
    {
        String query = "INSERT INTO Employee (firstName, lastName, dateOfBirth, jobTitle, department, workLocation, employmentStatus, " + 
                       "email, phoneNumber, hourlyRate, notes, hardSkill1, hardSkill2, softSkill1, softSkill2, isManager, isCEO)" +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        executer.setDataInDatabase(
            query,
            employee.getFirstName(),
            employee.getLastName(),
            employee.getDateOfBirth(),
            employee.getJobTitle(),
            employee.getDepartment(),
            employee.getWorkLocation(),
            employee.getEmploymentStatus(),
            employee.getEmail(),
            employee.getPhoneNumber(),
            employee.getHourlyrate(),
            employee.getNotes(),
            employee.getHardSkill1(),
            employee.getHardSkill2(),
            employee.getSoftSkill1(),
            employee.getSoftSkill2(),
            employee.getIsManager(),
            employee.getIsCEO()        
        );
    }

     /**
     * Marks an employee as deleted in the database.
     * 
     * @param employeeID The ID of the employee to delete.
     * @throws SQLException If a database access error occurs.
     */
    public void deleteEmployee(int employeeID) throws SQLException {
        String query = "UPDATE Employee SET isDeleted = 1 WHERE EmployeeID = ?";
        executer.setDataInDatabase(query, employeeID);
        executer.closeConnection();
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        if (executer != null) {
            executer.closeConnection();
        }
    }
}
