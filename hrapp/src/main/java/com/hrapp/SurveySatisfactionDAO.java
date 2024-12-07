package com.hrapp;

import java.sql.SQLException;

/**
 * Data Access Object (DAO) for handling database operations related to the SurveySatisfaction class.
 * Provides methods for interacting with the database to add survey satisfaction data.
 */
public class SurveySatisfactionDAO 
{
    private SQLExecuter exectuer; // Utility object to execute SQL queries

    /**
     * Constructor to initialize the SurveySatisfactionDAO object.
     * Sets up the SQLExecuter for database operations.
     * @throws SQLException If a database access error occurs.
     */
    public SurveySatisfactionDAO() throws SQLException
    {
        exectuer = new SQLExecuter();
    }

    /**
     * Method to add a SurveySatisfaction object to the database.
     * Prepares and executes an SQL INSERT query to store the survey data.
     * 
     * @param survey The SurveySatisfaction object containing the survey data to be added.
     * @throws SQLException If a database access error occurs during query execution.
     */
    public void addSurveySatisfaction(SurveySatisfaction survey) throws SQLException
    {
        // Query to insert a new survey satisfaction record into the database
        String query = "INSERT INTO SurveySatisfaction (EmployeeID, SubmissionDate, SatisfactionLevel, GrowthOpportunities, FavoriteAspect, CommunicationRating, AdditionalComments)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Execute the query with the survey's data
        exectuer.setDataInDatabase(
            query,
            survey.getEmployeeID(),          // Employee ID
            survey.getSubmissionDate(),      // Submission date
            survey.getSatisfactionLevel(),   // Satisfaction level
            survey.getGrowthOpportunities(), // Growth opportunities feedback
            survey.getFavoriteAspect(),      // Favorite aspect of the workplace
            survey.getCommunicationRating(), // Communication rating
            survey.getAdditionalComments()   // Additional comments
        );
    }
}
