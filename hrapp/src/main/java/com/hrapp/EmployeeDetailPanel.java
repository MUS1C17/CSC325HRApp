package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField; //New Import
import javax.swing.JSpinner; //New Import
import javax.swing.SpinnerDateModel; //New Import

/**
 * A Panel  to display detailed information about an employee.
 */
public class EmployeeDetailPanel extends JPanel 
{

    //Properties
    private MainApplication mainApp;
    private Employee employee;
    private EmployeeDAO employeeDAO;

    //Added properties for editing employee details
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField jobTitleField;
    private JTextField departmentField;
    private JTextField workLocationField;
    private JTextField employmentStatusField;
    private JTextField hourlyRateField;
    private JTextField notesField;
    private JTextField hardSkill1Field;
    private JTextField hardSkill2Field;
    private JTextField softSkill1Field;
    private JTextField softSkill2Field;
    private JSpinner dobSpinner; //Spinner for date of birth
    private JButton editButton; //Edit button
    private JButton saveButton; //Save Button
    private boolean isEditMode = false; //Flag to check if edit mode is active

    public EmployeeDetailPanel(MainApplication mainApp)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        try
        {
            employeeDAO = new EmployeeDAO();
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
    }

    public void setEmployee(Employee employee) 
    {
        this.employee = employee;
        removeAll(); // Clear previous content
        initUI();
        revalidate();
        repaint();
    }


    public void initUI()
    {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Populate the panel with employee details
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
        panel.add(new JLabel(employee.getPhoneNumber() != null && employee.getPhoneNumber().length() == 10 ? String.format("(%s)-%s-%s",
            employee.getPhoneNumber().substring(0, 3),
            employee.getPhoneNumber().substring(3, 6),
            employee.getPhoneNumber().substring(6, 10))
        : "N/A")); //This outputs phone number in the format (123)-456-7890

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
    
        // Add the panel to the center
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();

        //Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));

        //Delete Employee button
        JButton deleteEmployeeButton = new JButton("Delete Employee");
        deleteEmployeeButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent event)
           {
        
            int confirm = JOptionPane.showConfirmDialog(EmployeeDetailPanel.this,
                    "Are you sure you want to delete employee ID " + employee.getEmployeeID() + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if(confirm == JOptionPane.YES_OPTION)
                {
                    try
                    {
                        employeeDAO.deleteEmployee(employee.getEmployeeID()); // Delete employee in database
        
                        // Refresh the employee table in HomePanel
                        mainApp.getHomePanel().refreshEmployeeTable();
        
                        // Switch back to HomePanel
                        mainApp.switchToPanel("HomePanel");
                    }
                    catch(Exception error)
                    {
                        JOptionPane.showMessageDialog(EmployeeDetailPanel.this, 
                        "Error deleting employee: " + error.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                        error.printStackTrace();
                    }    
                }
           } 
        });

        //Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(deleteEmployeeButton);

        //Edit button added
        editButton = new JButton("Edit");
        editButton.addActionListener(e -> enableEditing());
        buttonPanel.add(editButton);

        //Save button added
        saveButton = new JButton("Save");
        saveButton.setVisible(false);
        saveButton.addActionListener(e -> saveChanges());
        buttonPanel.add(saveButton);



        add(buttonPanel, BorderLayout.PAGE_END);
    }

    //Method to edit employees
    private void enableEditing() {
        firstNameField = new
        JTextField(employee.getFirstName());
        lastNameField = new
        JTextField(employee.getLastName());

        //Date of Birth (NEED HELP)

        emailField = new JTextField(employee.getEmail());

        phoneField = new JTextField(employee.getPhoneNumber());

        jobTitleField = new JTextField(employee.getJobTitle());

        departmentField = new JTextField(employee.getDepartment());

        workLocationField = new JTextField(employee.getWorkLocation());

        employmentStatusField = new JTextField(employee.getEmploymentStatus());

        hourlyRateField = new JTextField(employee.getHourlyrate().toString());
        
        notesField = new JTextField(employee.getNotes());

        hardSkill1Field = new JTextField(employee.getHardSkill1());

        hardSkill2Field = new JTextField(employee.getHardSkill2());

        softSkill1Field = new JTextField(employee.getSoftSkill1());

        softSkill2Field = new JTextField(employee.getSoftSkill2());

        //Updates the panel to show the text fields
        JPanel editPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        editPanel.add(new JLabel("Employee ID:")); 
        editPanel.add(new JLabel(String.valueOf(employee.getEmployeeID()))); // Keep this as JLabel (THIS IS NEW)

        editPanel.add(new JLabel("First Name:")); 
        editPanel.add(firstNameField); 

        editPanel.add(new JLabel("Last Name:"));
        editPanel.add(lastNameField); 


        editPanel.add(new JLabel("Job Title:")); 
        editPanel.add(jobTitleField); 

        editPanel.add(new JLabel("Department:")); 
        editPanel.add(departmentField); 

        editPanel.add(new JLabel("Work Location:")); 
        editPanel.add(workLocationField); 

        editPanel.add(new JLabel("Employment Status:")); 
        editPanel.add(employmentStatusField);

        editPanel.add(new JLabel("Email:")); 
        editPanel.add(emailField);

        editPanel.add(new JLabel("Phone Number:")); 
        editPanel.add(phoneField); 

        editPanel.add(new JLabel("Hourly Rate:")); 
        editPanel.add(hourlyRateField);

        editPanel.add(new JLabel("Notes:")); 
        editPanel.add(notesField); 

        editPanel.add(new JLabel("Hard Skill 1:"));
        editPanel.add(hardSkill1Field);

        editPanel.add(new JLabel("Hard Skill 2:"));
        editPanel.add(hardSkill2Field);

        editPanel.add(new JLabel("Soft Skill 1:"));
        editPanel.add(softSkill1Field);

        editPanel.add(new JLabel("Soft Skill 2:"));
        editPanel.add(softSkill2Field); 

        editPanel.add(new JLabel("Is Manager:"));

        editPanel.add(new JLabel(employee.getIsManager() == 1 ? "Yes" : "No"));

        editPanel.add(new JLabel("Is CEO: "));
        editPanel.add(new JLabel(employee.getIsCEO() == 1 ? "Yes" : "No"));

        //Clear existing panel and add edit panel
        removeAll();
        add(editPanel, BorderLayout.CENTER);
        saveButton.setVisible(true);
        revalidate();
        repaint();
    }

    //Method to save the changes that were made
    private void saveChanges() {
        try {
            employee.setFirstName(firstNameField.getText());

            employee.setLastName(lastNameField.getText());

            employee.setEmail(emailField.getText()); 

            employee.setPhoneNumber(phoneField.getText()); 

            employee.setJobTitle(jobTitleField.getText()); 

            employee.setDepartment(departmentField.getText());

            employee.setWorkLocation(workLocationField.getText()); 

            employee.setEmploymentStatus(employmentStatusField.getText()); 

            employee.setHourlyRate(new BigDecimal(hourlyRateField.getText())); 

            employee.setNotes(notesField.getText()); 

            employee.setHardSkill1(hardSkill1Field.getText()); 

            employee.setHardSkill2(hardSkill2Field.getText()); 

            employee.setSoftSkill1(softSkill1Field.getText()); 

            employee.setSoftSkill2(softSkill2Field.getText()); 

            //Update in the database
            employeeDAO.updateEmployee(employee);

            JOptionPane.showMessageDialog(this, "Employee details updated successfully.");
            setEmployee(employee); //Refreses the employee details view
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        }
    }



