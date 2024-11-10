package com.hrapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SprintEvaluationDAO 
{
    private SQLExecuter executer;

    public SprintEvaluationDAO() throws SQLException
    {
        executer = new SQLExecuter();
    }

    //Method to add Sprint Evaluation to the database
    //Pass SprintEvaluation object into the method
    public void addSprintEvaluation(SprintEvaluation sprintEvaluation) throws SQLException
    {
        //SQL Query that will insert a new statement into the database
        String query = "INSERT INTO SprintEvaluation (employeeID, Feelings, FavoriteTask, ProficientTask, DreadTask, PotentialTask, Notes," + 
                        "SubmissionDate, isSubmitted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

    public List<SprintEvaluation> getEmployeeSprintEvaluations(int employeeID) throws SQLException
    {
        List<SprintEvaluation> sprintEvaluations = new ArrayList<>();
        String query = "SELECT * FROM SprintEvaluation WHERE EmployeeID = " + employeeID;

        try(ResultSet result = executer.getDataFromDatabase(query))
        {
            while(result.next())
            {
                LocalDate submissionDate = null;

                String submissionStr = result.getString("SubmissionDate");

                if(submissionStr!=null)
                {
                    try
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        submissionDate = LocalDate.parse(submissionStr, formatter);
                    }
                    catch(DateTimeException e)
                    {
                        e.getMessage();
                    }
                }

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

                sprintEvaluation.setSprintEvaulationID(result.getInt("SprintEvaluationID"));
                sprintEvaluations.add(sprintEvaluation);
            }
        }
        return sprintEvaluations;
    }
}
