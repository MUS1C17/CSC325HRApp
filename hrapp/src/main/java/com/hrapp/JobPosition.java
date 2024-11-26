package com.hrapp;

public class JobPosition 
{
    //Properties
    private int jobPositionID;
    private String jobPositionName;
    private String hardSkill1;
    private String hardSkill2;
    private String softSkill1;
    private String softSkill2;
    private int isDeleted;

    //Constructor
    public JobPosition(String jobPostionName, String hardSkill1, String hardSkill2,
                        String softSkill1, String softSkill2)
    {
        this.jobPositionName = jobPostionName;
        this.hardSkill1 = hardSkill1;
        this.hardSkill2 = hardSkill2;
        this.softSkill1 = softSkill1;
        this.softSkill2 = softSkill2;
    }

    //Constructor with ID
    public JobPosition(int jobPositionID, String jobPostionName, String hardSkill1, String hardSkill2,
                        String softSkill1, String softSkill2)
    {
        this.jobPositionID = jobPositionID;
        this.jobPositionName = jobPostionName;
        this.hardSkill1 = hardSkill1;
        this.hardSkill2 = hardSkill2;
        this.softSkill1 = softSkill1;
        this.softSkill2 = softSkill2;
    }


    //Setter methods

    public void setJobPositionID(int jobPositionID)
    {
        this.jobPositionID = jobPositionID;
    }

    public void setJobPositionName(String name)
    {
        this.jobPositionName = name;
    }

    public void setHardSkill1(String skill)
    {
        this.hardSkill1 = skill;
    }

    public void setHardSkill2(String skill)
    {
        this.hardSkill2 = skill;
    }

    public void setSoftSkill1(String skill)
    {
        this.softSkill1 = skill;
    }

    public void setSoftSkill2(String skill)
    {
        this.softSkill2 = skill;
    }

    public void setIsDeleted(int isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    //Getter methods
    public int getJobPositionID()
    {
        return jobPositionID;
    }

    public String getJobPositionName()
    {
        return jobPositionName;
    }

    public String getHardSkill1()
    {
        return hardSkill1;
    }

    public String getHardSkill2()
    {
        return hardSkill2;
    }

    public String getSoftSkill1()
    {
        return softSkill1;
    }

    public String getSoftSkill2()
    {
        return softSkill2;
    }

    public int getIsDeleted()
    {
        return isDeleted;
    }
}
