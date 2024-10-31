package com.hrapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SprintEvaluationDAO {
    private Connection conn;

    public SprintEvaluationDAO() throws SQLException {
        conn = DriverManager.getConnection("jdbc:your_database_url_here", "username", "password");
    }

    public List<SprintEvaluation> getEvaluationsByEmployeeId(int employeeId) throws SQLException {
        String sql = "SELECT * FROM sprint_evaluations WHERE employee_id = ?";
        List<SprintEvaluation> evaluations = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SprintEvaluation evaluation = new SprintEvaluation(
                );
                evaluations.add(evaluation);
            }
        }

        return evaluations;
    }

    public void addEvaluation(SprintEvaluation evaluation) throws SQLException {
        String sql = "INSERT INTO sprint_evaluations (employee_id, evaluation_date, performance_score, comments, submission_status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, evaluation.getEmployeeId());
            stmt.setDate(2, new Date(evaluation.getEvaluationDate().getTime()));
            stmt.setInt(3, evaluation.getPerformanceScore());
            stmt.setString(4, evaluation.getComments());
            stmt.setBoolean(5, evaluation.isSubmitted());
            stmt.executeUpdate();
        }
    }

    // Other DAO methods to update, delete, or fetch evaluations
}
