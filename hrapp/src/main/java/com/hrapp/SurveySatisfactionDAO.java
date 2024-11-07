package com.hrapp;


import java.sql.SQLException;


public class SurveySatisfactionDAO 
{
 
    private SQLExecuter exectuer;

    //SurveySatisfactionDAO constructor
    public SurveySatisfactionDAO() throws SQLException
    {
        exectuer = new SQLExecuter();
    }


    //Method to add Survey Satisfaction to the database
    public void addSurveySatisfaction(SurveySatisfaction survey) throws SQLException
    {
        //Query to add new SurveySatisfaction
        String query = "INSERT INTO SurveySatisfaction (EmployeeID, SubmissionDate, SatisfactionLevel, GrowthOpportunities, FavoriteAspect, CommunicationRating, AdditionalComments)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        exectuer.setDataInDatabase(
            query,
            survey.getEmployeeID(),
            survey.getSubmissionDate(),
            survey.getSatisfactionLevel(),
            survey.getGrowthOpportunities(),
            survey.getFavoriteAspect(),
            survey.getCommunicationRating(),
            survey.getAdditionalComments()
        );
    }

}
