package com.hrapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.sql.ResultSet;

public class AddSprintEvaluationPanel {

    private SQLExecuter sqlExecuter;  // SQL executor to interact with the database

    // Constructor that initializes SQLExecuter
    public AddSprintEvaluationPanel(SQLExecuter sqlExecuter) {
        this.sqlExecuter = sqlExecuter;
    }

    // Method to add a new SprintEvaluation to the database
    public void addSprintEvaluation(SprintEvaluation evaluation) throws SQLException {
        // SQL query to insert a new evaluation
        String sql = "INSERT INTO SprintEvaluations (employee_id, date, content) VALUES (?, ?, ?)";
        try (Connection conn = sqlExecuter.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set values to the prepared statement
            pstmt.setInt(1, evaluation.getEmployeeId());
            pstmt.setString(2, evaluation.getDate().toString());  // Convert LocalDate to String
            pstmt.setString(3, evaluation.getContent());
            pstmt.executeUpdate();  // Execute the insert
        }
    }

    // Method to fetch all SprintEvaluations for a given employee
    public List<SprintEvaluation> getSprintEvaluationsForEmployee(int employeeId) throws SQLException {
        List<SprintEvaluation> evaluations = new ArrayList<>();
        String sql = "SELECT * FROM SprintEvaluations WHERE employee_id = ?";
        
        try (Connection conn = sqlExecuter.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, employeeId);  // Set employee ID to filter results
            ResultSet rs = pstmt.executeQuery();
            
            // Iterate through result set and build list of evaluations
            while (rs.next()) {
                SprintEvaluation evaluation = new SprintEvaluation(
                    rs.getInt("id"),
                    LocalDate.parse(rs.getString("date")),  // Parse the date string into LocalDate
                    rs.getInt("employee_id"),
                    rs.getString("content")
                );
                evaluations.add(evaluation);  // Add evaluation to list
            }
        }
        return evaluations;
    }
}
