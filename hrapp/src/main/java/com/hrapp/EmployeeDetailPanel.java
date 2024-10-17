package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A dialog to display detailed information about an employee.
 */
public class EmployeeDetailPanel extends JPanel {

    private MainApplication mainApp;
    private Employee employee;

    public EmployeeDetailPanel(Window parent, Employee employee)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
    }




        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Populate the dialog with employee details
        panel.add(new JLabel("Employee ID:"));
        panel.add(new JLabel(String.valueOf(employee.getEmployeeID())));

        panel.add(new JLabel("First Name:"));
        panel.add(new JLabel(employee.getFirstName()));

        panel.add(new JLabel("Last Name:"));
        panel.add(new JLabel(employee.getLastName()));

        panel.add(new JLabel("Date of Birth:"));
        panel.add(new JLabel(employee.getDateOfBirth() != null ? employee.getDateOfBirth().toString() : "N/A"));

        panel.add(new JLabel("Job Title:"));
        panel.add(new JLabel(employee.getJobTitle() != null ? employee.getJobTitle() : "N/A"));

        panel.add(new JLabel("Department:"));
        panel.add(new JLabel(employee.getDepartment() != null ? employee.getDepartment() : "N/A"));

        panel.add(new JLabel("Work Location:"));
        panel.add(new JLabel(employee.getWorkLocation() != null ? employee.getWorkLocation() : "N/A"));

        panel.add(new JLabel("Employment Status:"));
        panel.add(new JLabel(employee.getEmploymentStatus() != null ? employee.getEmploymentStatus() : "N/A"));

        panel.add(new JLabel("Email:"));
        panel.add(new JLabel(employee.getEmail()));

        panel.add(new JLabel("Phone Number:"));
        panel.add(new JLabel(employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "N/A"));

        panel.add(new JLabel("Hourly Rate:"));
        panel.add(new JLabel(employee.getHourlyrate() != null ? employee.getHourlyrate().toString() : "N/A"));

        panel.add(new JLabel("Notes:"));
        panel.add(new JLabel(employee.getNotes() != null ? employee.getNotes() : "N/A"));

        panel.add(new JLabel("Hard Skill 1:"));
        panel.add(new JLabel(employee.getHardSkill1() != null ? employee.getHardSkill1() : "N/A"));

        panel.add(new JLabel("Hard Skill 2:"));
        panel.add(new JLabel(employee.getHardSkill2() != null ? employee.getHardSkill2() : "N/A"));

        panel.add(new JLabel("Soft Skill 1:"));
        panel.add(new JLabel(employee.getSoftSkill1() != null ? employee.getSoftSkill1() : "N/A"));

        panel.add(new JLabel("Soft Skill 2:"));
        panel.add(new JLabel(employee.getSoftSkill2() != null ? employee.getSoftSkill2() : "N/A"));

        panel.add(new JLabel("Is Manager:"));
        panel.add(new JLabel(employee.getIsManager() == 1 ? "Yes" : "No"));

        panel.add(new JLabel("Is CEO:"));
        panel.add(new JLabel(employee.getIsCEO() == 1 ? "Yes" : "No"));

        getContentPane().add(panel, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
}
