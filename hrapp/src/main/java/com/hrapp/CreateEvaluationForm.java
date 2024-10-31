package com.hrapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class CreateEvaluationForm extends JFrame {
    private JTextArea commentsArea;
    private JTextField ratingField;
    private JButton submitButton;
    private int employeeID;
    private SprintEvaluations parentPanel;

    public CreateEvaluationForm(int employeeID, SprintEvaluations parentPanel) {
        this.employeeID = employeeID;
        this.parentPanel = parentPanel;

        setTitle("New Sprint Evaluation");
        setLayout(new BorderLayout());

        // Comments Field
        commentsArea = new JTextArea(5, 20);
        add(new JScrollPane(commentsArea), BorderLayout.CENTER);

        // Rating Field
        JPanel ratingPanel = new JPanel();
        ratingPanel.add(new JLabel("Rating:"));
        ratingField = new JTextField(5);
        ratingPanel.add(ratingField);
        add(ratingPanel, BorderLayout.NORTH);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.addActionListener((ActionEvent e) -> submitEvaluation());
        add(submitButton, BorderLayout.SOUTH);

        pack();
    }

    private void submitEvaluation() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb", "root", "password")) {
            String query = "INSERT INTO SprintEvaluations (employeeID, evaluationDate, comments, rating, submitted) VALUES (?, NOW(), ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, employeeID);
            stmt.setString(2, commentsArea.getText());
            stmt.setInt(3, Integer.parseInt(ratingField.getText()));
            stmt.setBoolean(4, true); // Set as submitted

            stmt.executeUpdate();
            parentPanel.loadEvaluations(); // Refresh table in parent panel
            JOptionPane.showMessageDialog(this, "Sprint Evaluation submitted successfully!");
            dispose(); // Close form after submission
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
