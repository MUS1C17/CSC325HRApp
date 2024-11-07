package com.hrapp;

import java.time.LocalDate;

public class SprintEvaluation {

    private int SprintEvaluationID;
    private int EmployeeID;
    private LocalDate date;
    private int score;
    private String comments;

    // Constructor with SprintEvaluationID
    public SprintEvaluation(int SprintEvaluationID, int EmployeeID, LocalDate date, int score, String comments) {
        this.SprintEvaluationID = SprintEvaluationID;
        this.EmployeeID = EmployeeID;
        this.date = date;
        this.score = score;
        this.comments = comments;
    }

    // Constructor without SprintEvaluationID (for adding new evaluations)
    public SprintEvaluation(int EmployeeID, LocalDate date, int score, String comments) {
        this.EmployeeID = EmployeeID;
        this.date = date;
        this.score = score;
        this.comments = comments;
    }

    public SprintEvaluation(LocalDate date2, int employeeId2, String content) {
        //TODO Auto-generated constructor stub
    }

    public SprintEvaluation(int int1, int employeeId2, String date2, int int2, String string) {
        //TODO Auto-generated constructor stub
    }

    // Getter and setter methods
    public int getSprintEvaluationID() {
        return SprintEvaluationID;
    }

    public void setSprintEvaluationID(int SprintEvaluationID) {
        this.SprintEvaluationID = SprintEvaluationID;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    // New method to return content (maps to 'content' column in DB)
    public String getContent() {
        return comments;
    }
}
