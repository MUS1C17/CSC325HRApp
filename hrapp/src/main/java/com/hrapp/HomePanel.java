package com.hrapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.sql.SQLException;


public class HomePanel extends JPanel {
    private EmployeeTablePanel employeeTablePanel;
    private JTextField searchField;
    private JButton addEmployeeButton;
    private JButton sprintEvaluationButton; // New button for Sprint Evaluations
    private MainApplication mainApp;

    public HomePanel(boolean isManagerOrCEO, MainApplication mainApp) {
        this.mainApp = mainApp;
        
        setLayout(new BorderLayout());

        // Top Panel containing search bar and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Logo in top left
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("resources\\FRONTLINE_HR_Color_Version__1_-removebg-preview.png"));
        topPanel.add(logo);
        
        // Search bar
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(20);
        topPanel.add(searchLabel);
        topPanel.add(searchField);

        // Add Employee button
        addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.setVisible(isManagerOrCEO); // Only visible for Managers/CEOs
        topPanel.add(addEmployeeButton);

        // New Sprint Evaluation button
        sprintEvaluationButton = new JButton("Sprint Evaluations");
        sprintEvaluationButton.setVisible(isManagerOrCEO); // Only visible for Managers/CEOs
        topPanel.add(sprintEvaluationButton);

        add(topPanel, BorderLayout.NORTH);

        // Employee Table Panel
        employeeTablePanel = new EmployeeTablePanel();
        add(employeeTablePanel, BorderLayout.CENTER);
        
        // Add Action Listener for search functionality
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }
            private void filter() {
                String query = searchField.getText();
                employeeTablePanel.filterTable(query);
            }
        });

        // Open Detailed view panel
        employeeTablePanel.setEmployeeSelectionListener(new EmployeeTablePanel.EmployeeSelectionListener() {
            @Override
            public void employeeSelected(Employee employee) {
                // Open the detailed view panel
                mainApp.showEmployeeDetails(employee);
            }
        });

        // Open AddEmployeePanel when clicking on Add Employee button
        addEmployeeButton.addActionListener(e -> mainApp.switchToAddEmployeePanel("AddEmployeePanel"));
        
        // Open SprintEvaluationPanel when clicking on Sprint Evaluation button
        sprintEvaluationButton.addActionListener(e -> mainApp.switchToPanel("SprintEvaluationPanel"));
    }    

    // Refresh employee table
    public void refreshEmployeeTable() throws SQLException {
        employeeTablePanel.loadEmployeeData();
    }
    
    // Closes resources when the panel is no longer needed
    public void closeResources() {
        employeeTablePanel.closeDAO();
    }
}
