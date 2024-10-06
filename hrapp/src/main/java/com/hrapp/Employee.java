package com.hrapp;

import java.math.BigDecimal; //import for storing date
import java.time.LocalDate; //import for storing hourlyRate

public class Employee 
{
    //Properties for of the Employee class
    private int employeeID;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String jobTitle;
    private String department;
    private String workLocation;
    private String employmentStatus;
    private String email;
    private String phoneNumber;
    private BigDecimal hourlyRate;
    private String notes;
    private int isDeleted;
    private String hardSkill1;
    private String hardSkill2;
    private String softSkill1;
    private String softSkill2;
    private int isManager;
    private int isCEO; 

    public Employee(String firstName, String lastName, LocalDate dateofBirth, String jobTitle, String department, String workLocation, String employmentStatus, String email, String phoneNumber, BigDecimal hourlyRate, String notes, String hardSkill1, String hardSkill2, String softSkill1, String softSkill2, int isManager, int isCEO)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateofBirth;
        this.jobTitle = jobTitle;
        this.department = department;
        this.workLocation = workLocation;
        this.employmentStatus = employmentStatus;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hourlyRate = hourlyRate;
        this.notes = notes;
        this.hardSkill1 = hardSkill1;
        this.hardSkill2 = hardSkill2;
        this.softSkill1 = softSkill1;
        this.softSkill2 = softSkill2;
        this.isManager = isManager;
        this.isCEO = isCEO;
    }    

    //Setters for each property
    public void setEmployeeID(int employeeID)
    {
        this.employeeID = employeeID;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public void setWorkLocation(String workLocation)
    {
        this.workLocation = workLocation;
    }

    public void setEmploymentStatus(String employmentStatus)
    {
        this.employmentStatus = employmentStatus;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setHourlyRate(BigDecimal hourlyRate)
    {
        this.hourlyRate = hourlyRate;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public void setIsDeleted(int isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public void setHardSkill1(String hardSkill1)
    {
        this.hardSkill1 = hardSkill1;
    }

    public void setHardSkill2(String hardSkill2)
    {
        this.hardSkill2 = hardSkill2;
    }

    public void setSoftSkill1(String softSkill1)
    {
        this.softSkill1 = softSkill1;
    }

    public void setSoftSkill2(String softSkill2)
    {
        this.softSkill2 = softSkill2;
    }

    public void setIsManager(int isManager)
    {
        this.isManager = isManager;
    }

    public void setIsCEO(int isCEO)
    {
        this.isCEO = isCEO;
    }


    //Getter for each property
    public int getEmployeeID()
    {
        return employeeID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public String getJobTitle()
    {
        return jobTitle;
    }

    public String getDepartment()
    {
        return department;
    }

    public String getWorkLocation()
    {
        return workLocation;
    }

    public String getEmployementStatus()
    {
        return employmentStatus;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public BigDecimal getHourlyrate()
    {
        return hourlyRate;
    }

    public String getNotes()
    {
        return notes;
    }

    public int getIsDeleted()
    {
        return isDeleted;
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
    
    public int getIsManager()
    {
        return isManager;
    }

    public int getIsCEO()
    {
        return isCEO;
    }
}
