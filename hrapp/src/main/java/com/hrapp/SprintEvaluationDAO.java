package com.hrapp;

import java.sql.SQLException;

public class SprintEvaluationDAO 
{
    private SQLExecuter executer;

    public SprintEvaluationDAO() throws SQLException
    {
        executer = new SQLExecuter();
    }
}
