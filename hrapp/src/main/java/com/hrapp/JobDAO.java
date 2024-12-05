package com.hrapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/*
 * The JobDAO class handles database operations for job history records. It includes methods to:
 * - Retrieve jobs for an employee,
 * - Add, update, and delete job records,
 * - Handle job data with appropriate date formatting.
 */

public class JobDAO // Like the EmployeeDAO (Data access object), but for jobs.
{
    private SQLExecuter executor;

    public JobDAO() throws SQLException
    {
        executor = new SQLExecuter();
    }

    public ArrayList<Job> getJobs(int employeeID) throws SQLException
    {
    ArrayList<Job> jobs = new ArrayList<>();
    String query = "SELECT * FROM JobHistory WHERE isDeleted = 0 AND employeeID = " + employeeID; // Database query to get all job history data that is NOT deleted and relates to employee ID.

    try (ResultSet result = executor.getDataFromDatabase(query)) {
        while (result.next()) {
            // Retrieve and parse start and end dates for each row.
            LocalDate startDate = null;
            LocalDate endDate = null;

            String startStr = result.getString("StartDate");
            String endStr = result.getString("EndDate");

            if (startStr != null && !startStr.isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    startDate = LocalDate.parse(startStr, formatter);
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }
            }

            if (endStr != null && !endStr.isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    endDate = LocalDate.parse(endStr, formatter);
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }
            }

            // Create new Job object and add it to the list
            Job job = new Job(
                result.getString("JobTitle"),
                result.getString("CompanyName"),
                startDate,
                endDate,
                result.getString("City"),
                result.getString("Description"),
                result.getString("QuitReason"),
                result.getInt("IsDeleted")
            );
            job.setEmployeeID(result.getInt("EmployeeID"));
            job.setJobID(result.getInt("JobHistoryID"));
            jobs.add(job);
        }
    }
    return jobs;
    }

    public void addJob(Job job) throws SQLException
    {
        String query = "INSERT INTO JobHistory (employeeID, jobTitle, companyName, startDate, "+
        "endDate, city, description, quitReason) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        executor.setDataInDatabase(query, 
        job.getEmployeeID(),
        job.getJobTitle(),
        job.getCompanyName(),
        job.getStartDate(),
        job.getEndDate(),
        job.getCity(),
        job.getJobDescription(),
        job.getQuitReason()
        );

        //executor.closeConnection();
    }

    public void deleteJob(int jobID) throws SQLException
    {
        String query = "UPDATE JobHistory SET isDeleted = 1 WHERE JobHistoryID = ?";
        executor.setDataInDatabase(query, jobID);
        //executor.closeConnection();
    }

    // Updates job in database with specified information.
    public void updateJob(Job job, int jobID) throws SQLException
    {
        String query = "UPDATE JobHistory SET jobTitle = ?, companyName = ?, city = ?, startDate = ?, " +
        "endDate = ?, description = ?, quitReason = ? WHERE JobHistoryID = ?";
        executor.setDataInDatabase(query, 
        job.getJobTitle(),
        job.getCompanyName(),
        job.getCity(),
        job.getStartDate(),
        job.getEndDate(),
        job.getJobDescription(),
        job.getQuitReason(),
        jobID);
    }

    public void close()
    {
        if (executor != null)
            executor.closeConnection();
    }
}
