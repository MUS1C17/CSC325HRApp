package com.hrapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SprintEvaluationDAO {

    private final SQLExecuter sqlExecuter;

    public SprintEvaluationDAO(SQLExecuter sqlExecuter) {
        this.sqlExecuter = sqlExecuter;
    }

    // Add a new Sprint Evaluation to the database
    public void addSprintEvaluation(SprintEvaluation evaluation) throws SQLException {
        String query = "INSERT INTO SprintEvaluations (date, employeeId, content) VALUES (?, ?, ?)";
        sqlExecuter.setDataInDatabase(query,
            java.sql.Date.valueOf(evaluation.getDate()),
            evaluation.getEmployeeId(),
            evaluation.getContent()
        );
    }

    // Retrieve all Sprint Evaluations for a specific employee
    public List<SprintEvaluation> getSprintEvaluationsForEmployee(int employeeId) throws SQLException {
        String query = "SELECT * FROM SprintEvaluations WHERE employeeId = ?";
        ResultSet rs = sqlExecuter.getDataFromDatabase(query, employeeId);

        List<SprintEvaluation> evaluations = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            LocalDate date = rs.getDate("date").toLocalDate();
            String content = rs.getString("content");
            evaluations.add(new SprintEvaluation(id, date, employeeId, content));
        }
        return evaluations;
    }

    // Implement other methods as needed (e.g., update or delete evaluations)
}
