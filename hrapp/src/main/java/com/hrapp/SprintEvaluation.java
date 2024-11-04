package com.hrapp;

import java.time.LocalDate;

public class SprintEvaluation {
    private int id;
    private LocalDate date;
    private int employeeId;
    private String content;

    // Constructor without ID (for creating new evaluations)
    public SprintEvaluation(LocalDate date, int employeeId, String content) {
        this.date = date;
        this.employeeId = employeeId;
        this.content = content;
    }

    // Constructor with ID (for retrieved evaluations)
    public SprintEvaluation(int id, LocalDate date, int employeeId, String content) {
        this.id = id;
        this.date = date;
        this.employeeId = employeeId;
        this.content = content;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
