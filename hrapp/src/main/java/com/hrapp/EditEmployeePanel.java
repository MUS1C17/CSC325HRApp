package com.hrapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditEmployeePanel extends JPanel{
    private Employee employee;
    private EmployeeDAO employeeDAO;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField jobTitleField;
    private JTextField departmentField;
    private JTextField workLocationField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JTextField hourlyRateField;
    private JTextField notesField;
    private JTextField dateOfBirthField; // format as yyyy-MM-dd
    private JButton saveButton;
    private JButton cancelButton;

    public EditEmployeePanel(Employee employee) {
        this.employee = employee;
        try {
            employeeDAO = new EmployeeDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initUI();
        populateFields();
        }
    

    private void initUI() {
        setLayout(new GridLayout(0, 2, 10, 10));

        // Labels and Fields
        add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        add(lastNameField);

        add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        dateOfBirthField = new JTextField();
        add(dateOfBirthField);

        add(new JLabel("Job Title:"));
        jobTitleField = new JTextField();
        add(jobTitleField);

        add(new JLabel("Department:"));
        departmentField = new JTextField();
        add(departmentField);

        add(new JLabel("Work Location:"));
        workLocationField = new JTextField();
        add(workLocationField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField();
        add(phoneNumberField);

        add(new JLabel("Hourly Rate:"));
        hourlyRateField = new JTextField();
        add(hourlyRateField);

        add(new JLabel("Notes:"));
        notesField = new JTextField();
        add(notesField);

        // Save and Cancel Buttons
        saveButton = new JButton("Save");
        add(saveButton);

        cancelButton = new JButton("Cancel");
        add(cancelButton);

        // Add action listeners
        saveButton.addActionListener(new SaveButtonListener());
        cancelButton.addActionListener(e -> goBackToDetailPanel());
    }

    private void populateFields() {
        firstNameField.setText(employee.getFirstName());
        lastNameField.setText(employee.getLastName());
        dateOfBirthField.setText(employee.getDateOfBirth() != null ? employee.getDateOfBirth().toString() : "");
        jobTitleField.setText(employee.getJobTitle());
        departmentField.setText(employee.getDepartment());
        workLocationField.setText(employee.getWorkLocation());
        emailField.setText(employee.getEmail());
        phoneNumberField.setText(employee.getPhoneNumber());
        hourlyRateField.setText(employee.getHourlyrate() != null ? employee.getHourlyrate().toString() : "");
        notesField.setText(employee.getNotes());
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                employee.setFirstName(firstNameField.getText());
                employee.setLastName(lastNameField.getText());
                employee.setDateOfBirth(LocalDate.parse(dateOfBirthField.getText()));
                employee.setJobTitle(jobTitleField.getText());
                employee.setDepartment(departmentField.getText());
                employee.setWorkLocation(workLocationField.getText());
                employee.setEmail(emailField.getText());
                employee.setPhoneNumber(phoneNumberField.getText());
                employee.setHourlyRate(new BigDecimal(hourlyRateField.getText()));
                employee.setNotes(notesField.getText());

                employeeDAO.updateEmployee(employee, employee.getEmployeeID());
                JOptionPane.showMessageDialog(EditEmployeePanel.this, "Employee updated successfully!");

                goBackToDetailPanel();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(EditEmployeePanel.this, "Error updating employee: " + ex.getMessage());
            }
        }
    }

    private void goBackToDetailPanel() {
        // Logic to switch back to EmployeeDetailPanel or the previous panel
    }

}
