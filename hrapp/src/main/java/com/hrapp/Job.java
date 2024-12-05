package com.hrapp;

import java.time.LocalDate;

/*
 * The Job class represents an employee's job history, including details such as job title, company, dates, city, job description, quit reason, and deletion status.
 * It provides constructors for initialization and getter/setter methods for all fields, as well as formatted date methods for start and end dates.
 */


public class Job 
{
    // Properties
    private String jobTitle;
    private String companyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String city;
    private String jobDescription;
    private String quitReason;
    private int isDeleted;
    private int employeeID;
    private int jobID;

    public Job(String jobTitle, String companyName, LocalDate startDate, LocalDate endDate, String city, String jobDescription, String quitReason, int isDeleted)
    {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
        this.jobDescription = jobDescription;
        this.quitReason = quitReason;
        this.isDeleted = isDeleted;
    }

    // Overloaded constructor for initializing employeeID.
    public Job(String jobTitle, String companyName, LocalDate startDate, LocalDate endDate, String city, String jobDescription, String quitReason, int isDeleted, int employeeID)
    {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
        this.jobDescription = jobDescription;
        this.quitReason = quitReason;
        this.isDeleted = isDeleted;
        this.employeeID = employeeID;
    }

    // getters
    public String getJobTitle()
    {
        return jobTitle;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public String getStartDateStringFormat()
    {
        return startDate.toString().substring(5,7) + "/" + startDate.toString().substring(8) + "/" + startDate.toString().substring(0, 4);
    }

    public String getEndDateStringFormat()
    {
        return endDate.toString().substring(5,7) + "/" + endDate.toString().substring(8) + "/" + endDate.toString().substring(0, 4);
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public String getCity()
    {
        return city;
    }

    public String getJobDescription()
    {
        return jobDescription;
    }

    public String getQuitReason()
    {
        return quitReason;
    }

    public int getIsDeleted()
    {
        return isDeleted;
    }

    public int getEmployeeID()
    {
        return employeeID;
    }

    public int getJobID()
    {
        return jobID;
    }

    // setters
    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setJobDescription(String jobDescription)
    {
        this.jobDescription = jobDescription;
    }

    public void setQuitReason(String quitReason)
    {
        this.quitReason = quitReason;
    }

    public void setIsDeleted(int isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public void setEmployeeID(int employeeID)
    {
        this.employeeID = employeeID;
    }

    public void setJobID(int jobID)
    {
        this.jobID = jobID;
    }
}
