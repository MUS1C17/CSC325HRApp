package com.hrapp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SprintEvaluationDAO {
    
    private final SQLExecuter sqlExecuter;

    public SprintEvaluationDAO(SQLExecuter sqlExecuter) {
        this.sqlExecuter = sqlExecuter;
    }

    public void addSprintEvaluation(SprintEvaluation evaluation) throws SQLException {
        String query = "INSERT INTO SprintEvaluations (date, employeeId, content) VALUES (?, ?, ?)";
        try (PreparedStatement statement = sqlExecuter.getConnection().prepareStatement(query)) {
            statement.setDate(1, java.sql.Date.valueOf(evaluation.getDate()));
            statement.setInt(2, evaluation.getEmployeeId());
            statement.setString(3, evaluation.getContent());
            statement.executeUpdate();
        }
    }

    public List<SprintEvaluation> getSprintEvaluationsForEmployee(int employeeId) throws SQLException {
        List<SprintEvaluation> evaluations = new ArrayList<>();
        String query = "SELECT * FROM SprintEvaluations WHERE employeeId = ?";
        try (PreparedStatement statement = sqlExecuter.getConnection().prepareStatement(query)) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    String content = resultSet.getString("content");
                    evaluations.add(new SprintEvaluation(id, date, employeeId, content));
                }
            }
        }
        return evaluations;
    }
    
    // Implement other methods like deleteSprintEvaluation, updateSprintEvaluation, etc.
}
