package com.hrapp;

public class JobHistory {

    private String JobHistoryID;
    protected String jobTitle;
    protected String companyName;
    protected String startDate;
    protected String endDate;

    public JobHistory(String jobTitile, String companyName, String startDate, String endDate)
    {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    
}
