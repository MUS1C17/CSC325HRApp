package com.hrapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 * Data Access Object (DAO) class for handling SprintEvaluation data.
 * This class provides methods to interact with the database for SprintEvaluations.
 */
public class SprintEvaluationDAO 
{
     // Instance of SQLExecuter to perform database operations
    private SQLExecuter executer;

      /**
     * Constructor for SprintEvaluationDAO.
     * Initializes the SQLExecuter instance.
     *
     * @throws SQLException if a database access error occurs
     */
    public SprintEvaluationDAO() throws SQLException
    {
        executer = new SQLExecuter();
    }

     /**
     * Adds a new SprintEvaluation to the database.
     *
     * @param sprintEvaluation the SprintEvaluation object to add
     * @throws SQLException if a database access error occurs
     */
    public void addSprintEvaluation(SprintEvaluation sprintEvaluation) throws SQLException
    {
         // SQL query to insert a new SprintEvaluation record into the database
        String query = "INSERT INTO SprintEvaluation (employeeID, Feelings, FavoriteTask, ProficientTask, DreadTask, PotentialTask, Notes," + 
                        "SubmissionDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Execute the query with the provided parameters from the SprintEvaluation object
        executer.setDataInDatabase(
            query,
            sprintEvaluation.getEmployeeID(),
            sprintEvaluation.getFeelings(),
            sprintEvaluation.getFavoriteTask(),
            sprintEvaluation.getProficientTask(),
            sprintEvaluation.getDreadTask(),
            sprintEvaluation.getPotentialTask(),
            sprintEvaluation.getNotes(),
            sprintEvaluation.getSubmissionDate()
        );
    }


    /**
     * Retrieves a list of SprintEvaluations for a specific employee.
     *
     * @param employeeID the ID of the employee
     * @return a List of SprintEvaluation objects associated with the employee
     * @throws SQLException if a database access error occurs
     */
    public List<SprintEvaluation> getEmployeeSprintEvaluations(int employeeID) throws SQLException
    {
         // Initialize a list to hold the retrieved SprintEvaluations
        List<SprintEvaluation> sprintEvaluations = new ArrayList<>();

        // SQL query to select all SprintEvaluations for the given employeeID
        String query = "SELECT * FROM SprintEvaluation WHERE EmployeeID = ? ORDER BY SprintEvaluationID DESC";

        // Execute the query and obtain the result set
        try(ResultSet result = executer.getDataFromDatabase(query, employeeID))
        {
            // Iterate through the result set
            while(result.next())
            {
                 // Initialize the submissionDate to null
                LocalDate submissionDate = null;

                 // Retrieve the SubmissionDate as a String
                String submissionStr = result.getString("SubmissionDate");

                 // Parse the SubmissionDate if it's not null
                if(submissionStr!=null)
                {
                    try
                    {
                        // Define the date format pattern
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                        // Parse the date string into a LocalDate object
                        submissionDate = LocalDate.parse(submissionStr, formatter);
                    }
                    catch(DateTimeException e)
                    {
                        e.getMessage();
                    }
                }

                // Create a new SprintEvaluation object with data from the current row
                SprintEvaluation sprintEvaluation = new SprintEvaluation(
                    result.getInt("EmployeeID"),
                    result.getString("Feelings"),
                    result.getString("FavoriteTask"),
                    result.getString("ProficientTask"),
                    result.getString("DreadTask"),
                    result.getString("PotentialTask"),
                    result.getString("Notes"),
                    submissionDate
                );

                 // Set the SprintEvaluationID for the object
                sprintEvaluation.setSprintEvaulationID(result.getInt("SprintEvaluationID"));
                
                // Add the SprintEvaluation object to the list
                sprintEvaluations.add(sprintEvaluation);
            }
        }
        // Return the list of SprintEvaluations
        return sprintEvaluations;
    }
}