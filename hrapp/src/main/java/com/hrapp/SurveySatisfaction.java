package com.hrapp;

import java.time.LocalDate;

public class SurveySatisfaction 
{
    //Class properites
    private int surveyID;
    private int employeeID;
    private LocalDate submissionDate;
    private int satisfactionLevel;
    private String growthOpportunities;
    private String favoriteAspect;
    private int communicationRating;
    private String additionalComments;


    //Constractor
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

    //Getters for the properties
    public int getSurveyID()
    {
        return surveyID;
    }

    public int getEmployeeID()
    {
        return employeeID;
    }

    public LocalDate getSubmissionDate()
    {
        return submissionDate;
    }

    public String getSubmissionDateStringFormat()
    {
        return submissionDate.toString().substring(5,7) + "/" + submissionDate.toString().substring(8) + "/" + submissionDate.toString().substring(0, 4);
    }

    public int getSatisfactionLevel()
    {
        return satisfactionLevel;
    }

    public String getGrowthOpportunities()
    {
        return growthOpportunities;
    }

    public String getFavoriteAspect()
    {
        return favoriteAspect;
    }

    public int getCommunicationRating()
    {
        return communicationRating;
    }

    public String getAdditionalComments()
    {
        return additionalComments;
    }

    //Setters for the properties
    public void setSurveyID(int surveyID)
    {
        this.surveyID = surveyID;
    }

    public void setEmployeeID(int employeeID)
    {
        this.employeeID = employeeID;
    }

    public void setSubmissionDate(LocalDate submissionDate)
    {
        this.submissionDate = submissionDate;
    }

    public void setSatisfactionLevel(int satisfactionLevel)
    {
        this.satisfactionLevel = satisfactionLevel;
    }

    public void setGrowthOpportunities(String growthOpportunities)
    {
        this.growthOpportunities = growthOpportunities;
    }

    public void setFavoriteAspect(String favoriteAspect)
    {
        this.favoriteAspect = favoriteAspect;
    }

    public void setCommunicationRating(int communicationRating)
    {
        this.communicationRating = communicationRating;
    }

    public void setAdditionalComments(String comments)
    {
        this.additionalComments = comments;
    } 
}
