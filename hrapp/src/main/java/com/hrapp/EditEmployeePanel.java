package com.hrapp;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;

import com.hrapp.AddEmployeePanel.LimitedPlainDocument;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;

public class EditEmployeePanel extends JPanel {
    private MainApplication mainApp;
    private EmployeeDAO employeeDAO;
    private Employee employeeToEdit;

    private JFXPanel panelForDate;
    private DatePicker datePicker;
    private JButton saveButton;

    private Border defaultBorder;
    private Border errorBorder = BorderFactory.createLineBorder(Color.RED, 2);

    private JTextField firstName;
    private JTextField lastName;
    private JTextField jobTitle;
    private JTextField email;
    private JTextField phoneNumber;
    private JFormattedTextField hourlyRate;
    private JTextField notes;
    private JComboBox<String> department;
    private JComboBox<String> workLocation;
    private JComboBox<String> employmentStatus;
    private JComboBox<String> hardSkillOne;
    private JComboBox<String> hardSkillTwo;
    private JComboBox<String> softSkillOne;
    private JComboBox<String> softSkillTwo;
    private JComboBox<String> isManager;
    private JComboBox<String> isCEO;

     // Constructor
     public EditEmployeePanel(MainApplication mainApp, Employee employee) {
        this.mainApp = mainApp;
        this.employeeToEdit = employee;

        setLayout(new BorderLayout());
        initUI();

        try {
            employeeDAO = new EmployeeDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        populateFields(employee);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                panel.add(new JLabel("First Name:"));
        firstName = new JTextField();
        firstName.setDocument(new LimitedPlainDocument(50));
        panel.add(firstName);

        defaultBorder = firstName.getBorder();

        panel.add(new JLabel("Last Name:"));
        lastName = new JTextField();
        lastName.setDocument(new LimitedPlainDocument(75));
        panel.add(lastName);

        panel.add(new JLabel("Date of Birth:"));
        panelForDate = new JFXPanel();
        panel.add(panelForDate);
        Platform.runLater(this::initFX);

        panel.add(new JLabel("Job Title:"));
        jobTitle = new JTextField();
        jobTitle.setDocument(new LimitedPlainDocument(100));
        panel.add(jobTitle);

        String[] dep = new String[]{null, "SLS", "DEV", "MNG", "SPT"};
        String[] workLoc = new String[]{null, "MSU", "Office", "Remote"};
        String[] status = new String[]{null, "Intern", "Full-time", "Part-time", "Contractor"};
        String[] yesOrNo = new String[]{"No", "Yes"};
        String[] hardSkills = new String[]{null, "Java", "Python", "C#"};
        String[] softSkills = new String[]{null, "Leadership", "Teamwork", "Time Management"};

        panel.add(new JLabel("Department:"));
        department = new JComboBox(dep);
        panel.add(department);

        panel.add(new JLabel("Work Location:"));
        workLocation = new JComboBox(workLoc);
        panel.add(workLocation);

        panel.add(new JLabel("Employment Status:"));
        employmentStatus = new JComboBox(status);
        panel.add(employmentStatus);

        panel.add(new JLabel("Email:"));
        email = new JTextField();
        email.setDocument(new LimitedPlainDocument(255));
        panel.add(email);

        panel.add(new JLabel("Phone Number:"));
        phoneNumber = new JTextField();
        phoneNumber.setDocument(new LimitedPlainDocument(10));
        panel.add(phoneNumber);

        panel.add(new JLabel("Hourly Rate:"));
        hourlyRate = new JFormattedTextField();
        ((AbstractDocument) hourlyRate.getDocument()).setDocumentFilter(new NumberDocumentFilter());
        panel.add(hourlyRate);

        panel.add(new JLabel("Notes:"));
        notes = new JTextField();
        notes.setDocument(new LimitedPlainDocument(350));
        panel.add(notes);

        panel.add(new JLabel("Main Hard Skill:"));
        hardSkillOne = new JComboBox(hardSkills);
        panel.add(hardSkillOne);

        panel.add(new JLabel("Secondary Hard Skill:"));
        hardSkillTwo = new JComboBox(hardSkills);
        panel.add(hardSkillTwo);

        panel.add(new JLabel("Main Soft Skill:"));
        softSkillOne = new JComboBox(softSkills);
        panel.add(softSkillOne);

        panel.add(new JLabel("Secondary Soft Skill:"));
        softSkillTwo = new JComboBox(softSkills);
        panel.add(softSkillTwo);

        panel.add(new JLabel("Manager:"));
        isManager = new JComboBox(yesOrNo);
        panel.add(isManager);

        panel.add(new JLabel("CEO:"));
        isCEO = new JComboBox(yesOrNo);
        panel.add(isCEO);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));
        buttonPanel.add(backButton);

        saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveChanges());
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.PAGE_END);
    }

    private void initFX() {
        StackPane root = new StackPane();
        datePicker = new DatePicker();
        root.getChildren().add(datePicker);

        Scene scene = new Scene(root, 300, 200);
        panelForDate.setScene(scene);
    }

    private void populateFields(Employee employee) {
        firstName.setText(employee.getFirstName());
        lastName.setText(employee.getLastName());
        Platform.runLater(() -> datePicker.setValue(employee.getDateOfBirth()));
        jobTitle.setText(employee.getJobTitle());
        department.setSelectedItem(employee.getDepartment());
        workLocation.setSelectedItem(employee.getWorkLocation());
        employmentStatus.setSelectedItem(employee.getEmploymentStatus());
        email.setText(employee.getEmail());
        phoneNumber.setText(employee.getPhoneNumber());
        hourlyRate.setText(employee.getHourlyrate().toString());
        notes.setText(employee.getNotes());
        hardSkillOne.setSelectedItem(employee.getHardSkill1());
        hardSkillTwo.setSelectedItem(employee.getHardSkill2());
        softSkillOne.setSelectedItem(employee.getSoftSkill1());
        softSkillTwo.setSelectedItem(employee.getSoftSkill2());
    }

    private void saveChanges() {
        try {
            // Create the updated Employee object with the new values
            Employee updatedEmployee = new Employee(
                firstName.getText(),
                lastName.getText(),
                datePicker.getValue(),
                jobTitle.getText(),
                department.getSelectedItem() != null ? department.getSelectedItem().toString() : null,
                workLocation.getSelectedItem() != null ? workLocation.getSelectedItem().toString() : null,
                employmentStatus.getSelectedItem() != null ? employmentStatus.getSelectedItem().toString() : null,
                email.getText(),
                phoneNumber.getText(),
                new BigDecimal(!hourlyRate.getText().isEmpty() ? hourlyRate.getText() : "0"),
                notes.getText(),
                hardSkillOne.getSelectedItem() != null ? hardSkillOne.getSelectedItem().toString() : null,
                hardSkillTwo.getSelectedItem() != null ? hardSkillTwo.getSelectedItem().toString() : null,
                softSkillOne.getSelectedItem() != null ? softSkillOne.getSelectedItem().toString() : null,
                softSkillTwo.getSelectedItem() != null ? softSkillTwo.getSelectedItem().toString() : null,
                isManager.getSelectedItem().equals("Yes") ? 1 : 0,
                isCEO.getSelectedItem().equals("Yes") ? 1 : 0
            );
    
            // Get the existing employee ID from employeeToEdit
            updatedEmployee.setEmployeeID(employeeToEdit.getEmployeeID());
    
            // Now pass both the updated Employee object and its ID to the updateEmployee method
            employeeDAO.updateEmployee(updatedEmployee, updatedEmployee.getEmployeeID());
    
            // Show success message
            JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Switch back to the home panel or appropriate panel
            mainApp.switchToPanel("HomePanel");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
}