package com.hrapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String query = "SELECT * FROM Employees WHERE isDeleted = 0";

        try(ResultSet result = executer.getDataFromDatabase(query))
        {
            while(result.next())
            {
                Employee employee = new Employee(
                    result.getString("FirstName"),
                    result.getString("LastName"),
                    result.getDate("DateOfBirth").toLocalDate(),
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

    /**
     * Adds a new employee to the database.
     * 
     * @param employee The Employee object to add.
     * @throws SQLException If a database access error occurs.
     */
    public void addEmployee(Employee employee) throws SQLException
    {
        String query = "INSERT INTO Employees (firstName, lastName, dateOfBirth, jobTitle, department, workLocation, employmentStatus, " + 
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
        String query = "UPDATE Employees SET isDeleted = 1 WHERE EmployeeID = ?";
        executer.setDataInDatabase(query, employeeID);
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
