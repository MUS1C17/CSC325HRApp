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
    private JTextField contentField, employeeIdField;

    // No-argument constructor
    public SprintEvaluationPanel() {
        this.evaluationDAO = null; // Or handle initialization differently
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

        // UI Components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel employeeLabel = new JLabel("Employee ID:");
        employeeIdField = new JTextField();

        JLabel contentLabel = new JLabel("Content:");
        contentField = new JTextField();

        JButton addButton = new JButton("Add Evaluation");
        JButton fetchButton = new JButton("Fetch Evaluations");

        // Add components to input panel
        inputPanel.add(employeeLabel);
        inputPanel.add(employeeIdField);
        inputPanel.add(contentLabel);
        inputPanel.add(contentField);
        inputPanel.add(addButton);
        inputPanel.add(fetchButton);

        // Display Area
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Action Listeners
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
            displayArea.append("Error adding evaluation.\n");
        } catch (NumberFormatException ex) {
            displayArea.append("Invalid employee ID.\n");
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
            List<SprintEvaluation> evaluations = evaluationDAO.getSprintEvaluationsForEmployee(employeeId);

            displayArea.setText(""); // Clear the area before displaying results
            for (SprintEvaluation eval : evaluations) {
                displayArea.append("Date: " + eval.getDate() + ", Content: " + eval.getContent() + "\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            displayArea.append("Error fetching evaluations.\n");
        } catch (NumberFormatException ex) {
            displayArea.append("Invalid employee ID.\n");
        }
    }
}
