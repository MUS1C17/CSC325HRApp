package com.hrapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AddSprintEvaluationPanel extends JPanel {
    private JTextField feelingsField;
    private JTextField favoriteTaskField;
    private JTextField proficientTaskField;
    private JTextField dreadTaskField;
    private JTextField potentialTaskField;
    private JTextArea notesArea;
    private SprintEvaluationPanel sprintEvaluationPanel;
    private MainApplication mainApp;

    // Constructor with parentFrame and SprintEvaluationPanel passed in
    public AddSprintEvaluationPanel(MainApplication mainApp) {
        this.mainApp = mainApp;  // Set parentFrame reference
        setLayout(new GridLayout(9, 2, 10, 10));

        // Create labels and text fields for each question
        add(new JLabel("Over the last two weeks, did you notice any feelings of positivity or negativity while performing specific job tasks and if so, how would you describe those feelings?"));
        feelingsField = new JTextField();
        add(feelingsField);

        add(new JLabel("If you could do one task at work all day which task would you choose and why?"));
        favoriteTaskField = new JTextField();
        add(favoriteTaskField);

        add(new JLabel("Are there any tasks you perform in your job that you feel you are really good at and if so, what are they?"));
        proficientTaskField = new JTextField();
        add(proficientTaskField);

        add(new JLabel("Are there any tasks in your job you dread having to do and if so, what are they and what about them makes you dread them?"));
        dreadTaskField = new JTextField();
        add(dreadTaskField);

        add(new JLabel("Are there any tasks in your job you look forward to doing and if so, what are they and why do you look forward to them?"));
        potentialTaskField = new JTextField();
        add(potentialTaskField);

        add(new JLabel("Recommendations/notes on current job satisfaction, proficiency, and efficiency"));
        notesArea = new JTextArea(5, 20);
        add(new JScrollPane(notesArea));

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
                showSprintEvaluationPanel();
            }
        });

        add(submitButton);
        add(cancelButton);
    }

    // Handle form submission
    private void handleSubmit() {
        try {
            // Collect data from fields
            int employeeID = 1; // Replace with actual employeeID source (e.g., from session or login context)
            String feelings = feelingsField.getText();
            String favoriteTask = favoriteTaskField.getText();
            String proficientTask = proficientTaskField.getText();
            String dreadTask = dreadTaskField.getText();
            String potentialTask = potentialTaskField.getText();
            String notes = notesArea.getText();
            LocalDate submissionDate = LocalDate.now();

            // Create SprintEvaluation object
            SprintEvaluation sprintEvaluation = new SprintEvaluation(employeeID, feelings, favoriteTask, proficientTask, dreadTask, potentialTask, notes, submissionDate);

            // Save SprintEvaluation using DAO
            SprintEvaluationDAO dao = new SprintEvaluationDAO();
            dao.addSprintEvaluation(sprintEvaluation);  // Ensure this method exists in your DAO

            // Confirmation dialog and return to SprintEvaluationPanel
            JOptionPane.showMessageDialog(mainApp, "Sprint Evaluation submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            showSprintEvaluationPanel();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainApp, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Return to the SprintEvaluationPanel
    private void showSprintEvaluationPanel() {
        if (mainApp != null) {
            mainApp.setContentPane(sprintEvaluationPanel);  // Switch to SprintEvaluationPanel
            mainApp.revalidate();  // Revalidate the layout
            mainApp.repaint();  // Repaint the frame
        } else {
            // If parentFrame is null, log an error or handle gracefully
            System.out.println("Parent Frame is: " + mainApp);

        }
    }
}
