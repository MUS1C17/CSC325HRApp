package com.hrapp;

public class SprintEvaluations 
{
    private int evaluationId;
    private int employeeId;
    private String notes;
    private boolean isSubmitted;

    // Constructor, getters, and setters

    public SprintEvaluations(int evaluationId, int employeeId, String notes, boolean isSubmitted) 
    {
        this.evaluationId = evaluationId;
        this.employeeId = employeeId;
        this.notes = notes;
        this.isSubmitted = isSubmitted;
    }

    public boolean isSubmitted() 
    { 
        return isSubmitted; 
    }

    // Additional methods if needed
}
