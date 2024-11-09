package com.hrapp;

import java.time.LocalDate;

/*
 * Sprint evaluation stores answers to the following questions:
 * 
 * Over the last two weeks, did you notice any feelings of positivity or negativity while performing specific job tasks and if so, 
   how would you describe those feelings? — feelings
 *  
 * If you could do one task at work all day which task would you choose and why? — favoriteTask
 * 
 * Are there any tasks you perform in your job that you feel you are really good at and if so, what are they? — proficientTask
 * 
 * Are there any tasks in your job you dread having to do and if so, what are they and what about them makes you dread them? — dreadTask
 * 
 * Are there any tasks in your job you look forward to doing and if so, what are they and why do you look forward to them? — potentialTask
 * 
 * Notes on current job satisfaction, proficiency, and efficiency — notes
 */

public class SprintEvaluation 
{
    //Properties
    private int employeeID;
    private int sprintEvaluationID;
    private String feelings;
    private String favoriteTask;
    private String proficientTask;
    private String dreadTask;
    private String potentialTask;
    private String notes;
    private LocalDate submissionDate;
    private int isSubmitted;
    private int isDeleted;

    //Constructor
    public SprintEvaluation(int employeeID, String feelings, String favoriteTask, String proficientTask, String dreadTask, 
                            String potentialTask, String notes, LocalDate submissionDate, int isSubmitted)
    {
        this.employeeID = employeeID;
        this.feelings = feelings;
        this.favoriteTask = favoriteTask;
        this.proficientTask = proficientTask;
        this.dreadTask = dreadTask;
        this.potentialTask = potentialTask;
        this.notes = notes;
        this.submissionDate = submissionDate;
        this.isSubmitted = isSubmitted;
    }

    //Setters for each property
    public void setSprintEvaulationID(int sprintID)
    {
        this.sprintEvaluationID = sprintID;
    }

    public void setEmployeeID(int employeeID)
    {
        this.employeeID = employeeID;
    }

    public void setFellings(String feelings)
    {
        this.feelings = feelings;
    }

    public void setFavoriteTask(String favoriteTask)
    {
        this.favoriteTask = favoriteTask;
    }

    public void setProficientTask(String proficientTask)
    {
        this.proficientTask = proficientTask;
    }

    public void setDreadTask(String dreadTask)
    {
        this.dreadTask = dreadTask;
    }

    public void setPotentialTask(String potentialTask)
    {
        this.potentialTask = potentialTask;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public void setSubmissionDate(LocalDate date)
    {
        this.submissionDate = date;
    }

    public void setIsSubmitted(int isSubmitted)
    {
        this.isSubmitted = isSubmitted;
    }

    public void setIsDeleted(int isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    //Getters for each property
    public int getSprintEvaluationID()
    {
        return sprintEvaluationID;
    }

    public int getEmployeeID()
    {
        return employeeID;
    }

    public String getFeelings()
    {
        return feelings;
    }

    public String getFavoriteTask()
    {
        return favoriteTask;
    }

    public String getProficientTask()
    {
        return proficientTask;
    }

    public String getDreadTask()
    {
        return dreadTask;
    }

    public String getPotentialTask()
    {
        return potentialTask;
    }
    
    public String getNotes()
    {
        return notes;
    }

    public LocalDate getSubmissionDate()
    {
        return submissionDate;
    }

    //This method returns date in the format MM/DD/YYYY. Use it when outputing date on the panel for users to see
    public String getSubmissionDateStringFormat()
    {
        return submissionDate.toString().substring(5, 7) + "/" + submissionDate.toString().substring(8) + "/" + submissionDate.toString().substring(0, 4);
    }

    public int getIsSubmitted()
    {
        return isSubmitted;
    }

    public int getIsDeleted()
    {
        return isDeleted;
    }

}
