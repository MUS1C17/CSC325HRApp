package com.hrapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * This class manages all CRUD operations for the SprintEvaluation entity.
 */

public class SprintEvaluationDAO {
    private SQLExecuter executer;

    // Constructor
    public SprintEvaluationDAO() throws SQLException {
        executer = new SQLExecuter();
    }

    /**
     * Adds a new SprintEvaluation to the database.
     * 
     * @param evaluation The SprintEvaluation object to add.
     */
    public void addSprintEvaluation(SprintEvaluation evaluation) throws SQLException {
        String query = "INSERT INTO SprintEvaluation (date, employeeId, content) VALUES (?, ?, ?)";
        
        // Ensure the date is properly formatted (yyyy-MM-dd)
        executer.setDataInDatabase(
            query,
            evaluation.getDate(), // Ensure this is in the correct format (yyyy-MM-dd)
            evaluation.getEmployeeID(),  // Changed here to match the method name
            evaluation.getContent()
        );
    }
    

    /**
     * Fetches all SprintEvaluations for a specific employee.
     * 
     * @param employeeId The ID of the employee whose evaluations are retrieved.
     * @return List of SprintEvaluation objects.
     */
    public List<SprintEvaluation> getAllSprintEvaluationsForEmployee(int employeeId) throws SQLException {
        List<SprintEvaluation> evaluations = new ArrayList<>();
        String query = "SELECT * FROM SprintEvaluation WHERE employeeId = ?";

        try (PreparedStatement pstmt = executer.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, employeeId);
            
            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) {
                    // Ensure the date is parsed correctly (LocalDate)
                    String date = result.getString("date");
                    SprintEvaluation evaluation = new SprintEvaluation(
                        result.getInt("id"),
                        employeeId,
                        date,
                        result.getInt("score"),
                        result.getString("content")
                    );
                    evaluations.add(evaluation);
                }
            }
        }

        return evaluations;
    }

    /**
     * Fetches details for a specific SprintEvaluation by ID.
     * 
     * @param id The ID of the SprintEvaluation.
     * @return The SprintEvaluation object if found, null otherwise.
     */
    public SprintEvaluation getSprintEvaluationDetails(int id) throws SQLException {
        String query = "SELECT * FROM SprintEvaluation WHERE id = ?";
        
        try (PreparedStatement pstmt = executer.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet result = pstmt.executeQuery()) {
                if (result.next()) {
                    // Ensure the date is parsed correctly (LocalDate)
                    String date = result.getString("date");
                    return new SprintEvaluation(
                        result.getInt("id"),
                        result.getInt("employeeId"),
                        date,
                        result.getInt("score"),
                        result.getString("content")
                    );
                }
            }
        }
                return null;

    }
}
