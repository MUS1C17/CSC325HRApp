package com.hrapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CreateEvaluationForm extends JFrame {
    private JTextArea feedbackField;
    private JTextField ratingField;
    private JButton submitButton;
    private int employeeID;

    public CreateEvaluationForm(int employeeID) {
        this.employeeID = employeeID;
        setTitle("Create Sprint Evaluation");
        setSize(400, 300);
        setLayout(new BorderLayout());

        feedbackField = new JTextArea("Enter feedback here...");
        ratingField = new JTextField("Enter rating (1-5)");

        submitButton = new JButton("Submit Evaluation");
        submitButton.addActionListener(e -> submitEvaluation());

        add(feedbackField, BorderLayout.CENTER);
        add(ratingField, BorderLayout.NORTH);
        add(submitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void submitEvaluation() {
        String feedback = feedbackField.getText();
        int rating = Integer.parseInt(ratingField.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO SprintEvaluations (employeeID, evaluationDate, feedback, rating, isSubmitted) VALUES (?, CURDATE(), ?, ?, TRUE)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employeeID);
            stmt.setString(2, feedback);
            stmt.setInt(3, rating);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Evaluation submitted successfully!");
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting evaluation.");
        }
    }
}
