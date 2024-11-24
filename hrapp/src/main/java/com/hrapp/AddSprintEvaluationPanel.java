package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class AddSprintEvaluationPanel extends JPanel {

    //Properties
    private TextField feelingsField;
    private TextField favoriteTaskField;
    private TextField proficientTaskField;
    private TextField dreadTaskField;
    private TextField potentialTaskField;
    private TextField notesArea;
    private SprintEvaluationPanel sprintEvaluationPanel;
    private MainApplication mainApp;
    private SprintEvaluationDAO sprintDAO;
    private Employee employee;

    // Constructor with parentFrame and SprintEvaluationPanel passed in
    public AddSprintEvaluationPanel(MainApplication mainApp) 
    {
        this.mainApp = mainApp;  // Set parentFrame reference
        setLayout(new BorderLayout());
        
        try
        {
            sprintDAO = new SprintEvaluationDAO();
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
    }

    //Method to initialize all the UI elements
    public void initUI()
    {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Create labels and text fields for each question

        //Feelings
        panel.add(new Label("Over the last two weeks, did you notice any feelings of positivity or negativity while performing specific job tasks and if so, how would you describe those feelings?"));
        feelingsField = new TextField();
        panel.add(feelingsField);

        //Favorite Task
        panel.add(new Label("If you could do one task at work all day which task would you choose and why?"));
        favoriteTaskField = new TextField();
        panel.add(favoriteTaskField);

         //Proficient Task
        panel.add(new Label("Are there any tasks you perform in your job that you feel you are really good at and if so, what are they?"));
        proficientTaskField = new TextField();
        panel.add(proficientTaskField);

        //Dread Task
        panel.add(new Label("Are there any tasks in your job you dread having to do and if so, what are they and what about them makes you dread them?"));
        dreadTaskField = new TextField();
        panel.add(dreadTaskField);

        //Potential Task
        panel.add(new Label("Are there any tasks in your job you look forward to doing and if so, what are they and why do you look forward to them?"));
        potentialTaskField = new TextField();
        panel.add(potentialTaskField);

        //Notes Area
        panel.add(new Label("Recommendations/notes on current job satisfaction, proficiency, and efficiency"));
        notesArea = new TextField();
        panel.add(notesArea);

        //Add panel to the AddSprintEvaluationPanel
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
                //Refresh Evaluations
                mainApp.refreshSprintEvaluations(employee);
            }
        });

        // Cancel button to return to SprintEvaluationPanel
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                mainApp.switchToPanel("EmployeeDetailPanel");
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        //Add Button Panel to the main panel
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    //Setter method for Employee to get EmployeeID
    public void setEmployee(Employee employee)
    {
        this.employee = employee;
        initUI();
    }

    // Handle form submission
    private void handleSubmit() 
    {
        try 
        {
            // Save SprintEvaluation using addSprintEvaluation method from SprintEvaluationDAO class
            sprintDAO.addSprintEvaluation(new SprintEvaluation
            (
                employee.getEmployeeID(),
                feelingsField.getText(),
                favoriteTaskField.getText(),
                proficientTaskField.getText(),
                dreadTaskField.getText(),
                potentialTaskField.getText(),
                notesArea.getText(),
                LocalDate.now()
            )); 

            // Confirmation dialog and return to SprintEvaluationPanel
            JOptionPane.showMessageDialog(mainApp, "Sprint Evaluation submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            mainApp.switchToPanel("EmployeeDetailPanel");

        } 
        catch (Exception ex) 
        {
            //Show Error if an error happens
            JOptionPane.showMessageDialog(mainApp, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}