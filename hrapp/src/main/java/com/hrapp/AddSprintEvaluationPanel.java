package com.hrapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddSprintEvaluationPanel {

    private SQLExecuter sqlExecuter; // Assuming you have this class to handle DB connections

    public SprintEvaluationDAO(SQLExecuter sqlExecuter) {
        this.sqlExecuter = sqlExecuter;
    }

    public void addSprintEvaluation(SprintEvaluation evaluation) throws SQLException {
        String sql = "INSERT INTO SprintEvaluations (employee_id, date, content) VALUES (?, ?, ?)";
        try (Connection conn = sqlExecuter.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, evaluation.getEmployeeId());
            pstmt.setString(2, evaluation.getDate().toString());
            pstmt.setString(3, evaluation.getContent());
            pstmt.executeUpdate();
        }
    }

    public List<SprintEvaluation> getSprintEvaluationsForEmployee(int employeeId) throws SQLException {
        List<SprintEvaluation> evaluations = new ArrayList<>();
        String sql = "SELECT * FROM SprintEvaluations WHERE employee_id = ?";
        try (Connection conn = sqlExecuter.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                SprintEvaluation evaluation = new SprintEvaluation(
                    rs.getInt("id"),
                    LocalDate.parse(rs.getString("date")),
                    rs.getInt("employee_id"),
                    rs.getString("content")
                );
                evaluations.add(evaluation);
            }
        }
        return evaluations;
    }
}
