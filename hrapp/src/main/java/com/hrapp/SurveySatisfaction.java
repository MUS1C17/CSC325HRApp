package com.hrapp;

import java.time.LocalDate;

/**
 * This class represents a survey on employee satisfaction.
 * It captures various aspects of employee feedback including satisfaction level,
 * growth opportunities, communication rating, and more.
 */
public class SurveySatisfaction 
{
    // Class properties
    private int surveyID; // Unique identifier for the survey
    private int employeeID; // Identifier for the employee taking the survey
    private LocalDate submissionDate; // Date when the survey was submitted
    private int satisfactionLevel; // Satisfaction level rated by the employee
    private String growthOpportunities; // Employee's feedback on growth opportunities
    private String favoriteAspect; // Employee's favorite aspect of the workplace
    private int communicationRating; // Rating for workplace communication
    private String additionalComments; // Additional comments or feedback by the employee

    /**
     * Constructor to initialize a SurveySatisfaction object with provided details.
     * @param employeeID The ID of the employee taking the survey.
     * @param submissionDate The date the survey was submitted.
     * @param satisfactionLevel The employee's satisfaction level (e.g., on a scale of 1-5).
     * @param growthOpportunites Feedback on growth opportunities provided by the organization.
     * @param favoriteAspect The employee's favorite aspect of the organization.
     * @param communicationRating Rating for communication within the organization.
     * @param additionalComments Any additional comments provided by the employee.
     */
    public SurveySatisfaction(int employeeID, LocalDate submissionDate, 
                                int satisfactionLevel, String growthOpportunites, String favoriteAspect,
                                int communicationRating, String additionalComments)
    {
        this.employeeID = employeeID;
        this.submissionDate = submissionDate;
        this.satisfactionLevel = satisfactionLevel;
        this.growthOpportunities = growthOpportunites;
        this.favoriteAspect = favoriteAspect;
        this.communicationRating = communicationRating;
        this.additionalComments = additionalComments;
    }    

    // Getters for the properties

    /**
     * Gets the survey ID.
     * @return The survey ID.
     */
    public int getSurveyID()
    {
        return surveyID;
    }

    /**
     * Gets the employee ID.
     * @return The employee ID.
     */
    public int getEmployeeID()
    {
        return employeeID;
    }

    /**
     * Gets the submission date.
     * @return The submission date as a LocalDate object.
     */
    public LocalDate getSubmissionDate()
    {
        return submissionDate;
    }

    /**
     * Gets the submission date in MM/DD/YYYY format.
     * @return The formatted submission date as a string.
     */
    public String getSubmissionDateStringFormat()
    {
        return submissionDate.toString().substring(5,7) + "/" + submissionDate.toString().substring(8) + "/" + submissionDate.toString().substring(0, 4);
    }

    /**
     * Gets the satisfaction level.
     * @return The satisfaction level.
     */
    public int getSatisfactionLevel()
    {
        return satisfactionLevel;
    }

    /**
     * Gets the feedback on growth opportunities.
     * @return The growth opportunities feedback.
     */
    public String getGrowthOpportunities()
    {
        return growthOpportunities;
    }

    /**
     * Gets the employee's favorite aspect.
     * @return The favorite aspect of the organization.
     */
    public String getFavoriteAspect()
    {
        return favoriteAspect;
    }

    /**
     * Gets the communication rating.
     * @return The communication rating.
     */
    public int getCommunicationRating()
    {
        return communicationRating;
    }

    /**
     * Gets any additional comments provided by the employee.
     * @return The additional comments.
     */
    public String getAdditionalComments()
    {
        return additionalComments;
    }

    // Setters for the properties

    /**
     * Sets the survey ID.
     * @param surveyID The survey ID to set.
     */
    public void setSurveyID(int surveyID)
    {
        this.surveyID = surveyID;
    }

    /**
     * Sets the employee ID.
     * @param employeeID The employee ID to set.
     */
    public void setEmployeeID(int employeeID)
    {
        this.employeeID = employeeID;
    }

    /**
     * Sets the submission date.
     * @param submissionDate The submission date to set.
     */
    public void setSubmissionDate(LocalDate submissionDate)
    {
        this.submissionDate = submissionDate;
    }

    /**
     * Sets the satisfaction level.
     * @param satisfactionLevel The satisfaction level to set.
     */
    public void setSatisfactionLevel(int satisfactionLevel)
    {
        this.satisfactionLevel = satisfactionLevel;
    }

    /**
     * Sets the feedback on growth opportunities.
     * @param growthOpportunities The growth opportunities feedback to set.
     */
    public void setGrowthOpportunities(String growthOpportunities)
    {
        this.growthOpportunities = growthOpportunities;
    }

    /**
     * Sets the employee's favorite aspect.
     * @param favoriteAspect The favorite aspect to set.
     */
    public void setFavoriteAspect(String favoriteAspect)
    {
        this.favoriteAspect = favoriteAspect;
    }

    /**
     * Sets the communication rating.
     * @param communicationRating The communication rating to set.
     */
    public void setCommunicationRating(int communicationRating)
    {
        this.communicationRating = communicationRating;
    }

    /**
     * Sets any additional comments provided by the employee.
     * @param comments The additional comments to set.
     */
    public void setAdditionalComments(String comments)
    {
        this.additionalComments = comments;
    } 
}
