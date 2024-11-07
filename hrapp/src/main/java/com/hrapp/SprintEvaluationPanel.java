package com.hrapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SprintEvaluationPanel extends JPanel {

    private SprintEvaluationDAO evaluationDAO;
    private JTextArea displayArea;
    private JTextField employeeIdField, contentField;

    // No-argument constructor
    public SprintEvaluationPanel() {
        try {
            this.evaluationDAO = new SprintEvaluationDAO();  // Proper initialization of evaluationDAO
        } catch (SQLException e) {
            e.printStackTrace();
            displayArea.append("Failed to initialize Evaluation DAO.\n");
        }
        initUI(); // Call to initialize UI components
    }

    // Parameterized constructor
    public SprintEvaluationPanel(SprintEvaluationDAO evaluationDAO) {
        this.evaluationDAO = evaluationDAO;
        initUI(); // Call to initialize UI components
    }

    // Common method to initialize UI components
    private void initUI() {
        setLayout(new BorderLayout());

        // Input Panel (for form fields)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns

        // Employee ID field
        JLabel employeeLabel = new JLabel("Employee ID:");
        employeeIdField = new JTextField();
        inputPanel.add(employeeLabel);
        inputPanel.add(employeeIdField);

        // Content field
        JLabel contentLabel = new JLabel("Evaluation Content:");
        contentField = new JTextField();
        inputPanel.add(contentLabel);
        inputPanel.add(contentField);

        // Buttons
        JButton addButton = new JButton("Add Evaluation");
        JButton fetchButton = new JButton("Fetch Evaluations");
        inputPanel.add(addButton);
        inputPanel.add(fetchButton);

        // Display Area (TextArea to show results or feedback)
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button Listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEvaluation();
            }
        });

        fetchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchEvaluations();
            }
        });
    }

    // Method to add a new evaluation
    private void addEvaluation() {
        if (evaluationDAO == null) {
            displayArea.append("Evaluation DAO is not initialized.\n");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdField.getText());
            String content = contentField.getText();
            LocalDate date = LocalDate.now();

            SprintEvaluation evaluation = new SprintEvaluation(date, employeeId, content);
            evaluationDAO.addSprintEvaluation(evaluation);

            displayArea.append("Evaluation added successfully!\n");
            employeeIdField.setText("");
            contentField.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();
            displayArea.append("Error adding evaluation: " + ex.getMessage() + "\n");
        } catch (NumberFormatException ex) {
            displayArea.append("Invalid Employee ID.\n");
        }
    }

    // Method to fetch evaluations for a given employee ID
    private void fetchEvaluations() {
        if (evaluationDAO == null) {
            displayArea.append("Evaluation DAO is not initialized.\n");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdField.getText());
            List<SprintEvaluation> evaluations = evaluationDAO.getAllSprintEvaluationsForEmployee(employeeId);

            displayArea.setText(""); // Clear the display area before showing results
            if (evaluations.isEmpty()) {
                displayArea.append("No evaluations found for employee ID: " + employeeId + "\n");
            } else {
                for (SprintEvaluation eval : evaluations) {
                    displayArea.append("Date: " + eval.getDate() + ", Content: " + eval.getContent() + "\n");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            displayArea.append("Error fetching evaluations: " + ex.getMessage() + "\n");
        } catch (NumberFormatException ex) {
            displayArea.append("Invalid Employee ID.\n");
        }
    }
}
