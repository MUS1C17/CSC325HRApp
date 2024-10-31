package com.hrapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SprintEvaluations extends JPanel {
    private JTable evaluationsTable;
    private DefaultTableModel model;
    private JButton createEvaluationButton;
    private int employeeID;
    private boolean isSupervisor;

    public SprintEvaluations(int employeeID, boolean isSupervisor) {
        this.employeeID = employeeID;
        this.isSupervisor = isSupervisor;
        
        setLayout(new BorderLayout());

        // Initialize table model with column names
        model = new DefaultTableModel(new String[]{"ID", "Date", "Rating", "Comments", "Submitted"}, 0);
        evaluationsTable = new JTable(model);
        add(new JScrollPane(evaluationsTable), BorderLayout.CENTER);

        // Button to create new Sprint Evaluation
        createEvaluationButton = new JButton("Create Sprint Evaluation");
        createEvaluationButton.addActionListener((ActionEvent e) -> openEvaluationForm());
        add(createEvaluationButton, BorderLayout.SOUTH);

        // Button to add evaluation in Employee Table Panel
        JButton addEvaluationButton = new JButton("Add Evaluation");
        addEvaluationButton.addActionListener(e -> addEvaluation());
        add(addEvaluationButton, BorderLayout.SOUTH);

        // Load evaluations from database
        loadEvaluations();
        
        // Disable create button for supervisors
        createEvaluationButton.setEnabled(!isSupervisor);
    }

    private void loadEvaluations() {
        model.setRowCount(0); // Clear existing rows

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb", "root", "password")) {
            String query = isSupervisor ? 
                "SELECT * FROM SprintEvaluations" : 
                "SELECT * FROM SprintEvaluations WHERE employeeID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            if (!isSupervisor) stmt.setInt(1, employeeID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("evaluationID"),
                    rs.getDate("evaluationDate"),
                    rs.getInt("rating"),
                    rs.getString("comments"),
                    rs.getBoolean("submitted")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openEvaluationForm() {
        // Open form to create new evaluation
        new CreateEvaluationForm(employeeID, this).setVisible(true);
    }

    private void addEvaluation() {
        int selectedRow = evaluationsTable.getSelectedRow();
        if (selectedRow != -1) {
            int employeeId = (Integer) model.getValueAt(evaluationsTable.convertRowIndexToModel(selectedRow), 0);
            openEvaluationDialog(employeeId);
        } else {
            JOptionPane.showMessageDialog(this, "Select an employee to add an evaluation.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method to open the evaluation dialog
    private void openEvaluationDialog(int employeeId) {
        EvaluationDialog dialog = new EvaluationDialog(SwingUtilities.getWindowAncestor(this), employeeId);
        dialog.setVisible(true);

        // Reload table data after evaluation is added
        if (dialog.isEvaluationSaved()) {
            loadEvaluations();
        }
    }
}
