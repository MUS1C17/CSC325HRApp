package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddSprintEvaluationPanel extends JPanel {

    //Properties
    private JTextField feelingsField;
    private JTextField favoriteTaskField;
    private JTextField proficientTaskField;
    private JTextField dreadTaskField;
    private JTextField potentialTaskField;
    private JTextArea notesArea;
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


    public void initUI()
    {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Create labels and text fields for each question
        panel.add(new JLabel("Over the last two weeks, did you notice any feelings of positivity or negativity while performing specific job tasks and if so, how would you describe those feelings?"));
        feelingsField = new JTextField();
        panel.add(feelingsField);

        panel.add(new JLabel("If you could do one task at work all day which task would you choose and why?"));
        favoriteTaskField = new JTextField();
        panel.add(favoriteTaskField);

        panel.add(new JLabel("Are there any tasks you perform in your job that you feel you are really good at and if so, what are they?"));
        proficientTaskField = new JTextField();
        panel.add(proficientTaskField);

        panel.add(new JLabel("Are there any tasks in your job you dread having to do and if so, what are they and what about them makes you dread them?"));
        dreadTaskField = new JTextField();
        panel.add(dreadTaskField);

        panel.add(new JLabel("Are there any tasks in your job you look forward to doing and if so, what are they and why do you look forward to them?"));
        potentialTaskField = new JTextField();
        panel.add(potentialTaskField);

        panel.add(new JLabel("Recommendations/notes on current job satisfaction, proficiency, and efficiency"));
        notesArea = new JTextArea(5, 20);
        panel.add(notesArea);

         //Add panel to the AddSprintEvaluationPanel
         add(panel, BorderLayout.CENTER);

         //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        // Cancel button to return to SprintEvaluationPanel
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
    private void handleSubmit() {
        try {
            // Collect data from fields
            String feelings = feelingsField.getText();
            String favoriteTask = favoriteTaskField.getText();
            String proficientTask = proficientTaskField.getText();
            String dreadTask = dreadTaskField.getText();
            String potentialTask = potentialTaskField.getText();
            String notes = notesArea.getText();
            LocalDate submissionDate = LocalDate.now();

            // Create SprintEvaluation object
            SprintEvaluation sprintEvaluation = new SprintEvaluation(employee.getEmployeeID(), feelings, favoriteTask, proficientTask, dreadTask, potentialTask, notes, submissionDate);

            // Save SprintEvaluation using DAO
            sprintDAO.addSprintEvaluation(sprintEvaluation);  // Ensure this method exists in your DAO

            // Confirmation dialog and return to SprintEvaluationPanel
            JOptionPane.showMessageDialog(mainApp, "Sprint Evaluation submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            mainApp.switchToPanel("EmployeeDetailPanel");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainApp, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
