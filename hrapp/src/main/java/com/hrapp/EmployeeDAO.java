package com.hrapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private SQLExecuter executer;

    // Constructor initializes the SQLExecuter for database interaction
    public EmployeeDAO() throws SQLException {
        executer = new SQLExecuter();
    }

    // Fetch all active employees (non-deleted) from the database
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE isDeleted = 0";  // Only get non-deleted employees
        
        try (ResultSet result = executer.getDataFromDatabase(query)) {
            // Iterate over each result and build Employee objects
            while (result.next()) {
                String dateStr = result.getString("DateOfBirth");
                LocalDate dateOfBirth = null;

                // Parse the date string into LocalDate, handling potential errors
                if (dateStr != null && !dateStr.isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        dateOfBirth = LocalDate.parse(dateStr, formatter);
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();  // Log parsing error
                    }
                }

                // Create a new Employee object and add it to the list
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
                employees.add(employee);  // Add the employee to the list
            }
        }
        return employees;
    }

    // Fetch detailed employee information based on employee ID
    public Employee getEmployeeDetails(int id) throws SQLException {
        String query = "SELECT * FROM Employee WHERE EmployeeID = ?";
        try (PreparedStatement pstmt = executer.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);  // Set employee ID parameter
            try (ResultSet result = pstmt.executeQuery()) {
                if (result.next()) {
                    // Parse and build the Employee object
                    String dateStr = result.getString("DateOfBirth");
                    LocalDate dateOfBirth = null;
                    if (dateStr != null && !dateStr.isEmpty()) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            dateOfBirth = LocalDate.parse(dateStr, formatter);
                        } catch (DateTimeParseException e) {
                            e.printStackTrace();
                        }
                    }

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
                    return employee;  // Return the employee details
                } else {
                    return null;  // Return null if no employee found
                }
            }
        }
    }

    // Adds a new employee to the database
    public void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO Employee (firstName, lastName, dateOfBirth, jobTitle, department, workLocation, employmentStatus, " + 
                       "email, phoneNumber, hourlyRate, notes, hardSkill1, hardSkill2, softSkill1, softSkill2, isManager, isCEO)" +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Insert employee data into the database
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
            employee.getHourlyRate(),
            employee.getNotes(),
            employee.getHardSkill1(),
            employee.getHardSkill2(),
            employee.getSoftSkill1(),
            employee.getSoftSkill2(),
            employee.getIsManager(),
            employee.getIsCEO()        
        );
    }

    // Marks an employee as deleted in the database
    public void deleteEmployee(int employeeID) throws SQLException {
        String query = "UPDATE Employee SET isDeleted = 1 WHERE EmployeeID = ?";
        executer.setDataInDatabase(query, employeeID);  // Mark employee as deleted
    }

    // Close the database connection when done
    public void close() {
        if (executer != null) {
            executer.closeConnection();
        }
    }
}
