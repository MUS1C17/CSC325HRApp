package com.hrapp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class EmployeeDetailPanel extends JPanel 
{

    // Properties
    private MainApplication mainApp;
    private Employee employee;
    private EmployeeDAO employeeDAO;

    // Navigation and content panels
    private JPanel navigationPanel;
    private JPanel contentPanel;
    private CardLayout contentCardLayout;

    // Sub-panels
    private DetailsPanel detailsPanel;
    private JobHistoryPanel jobHistoryPanel;
    private SprintEvaluationPanel sprintEvaluationPanel;

    //Buttons
    private JButton detailsButton;
    private JButton jobHistoryButton;
    private JButton sprintEvaluationButton;
    private JButton backButton;
    private JButton editButton;
    private JButton saveButton;
    //This is to verify if edit mode is active
    private boolean isEditMode = false;

    public EmployeeDetailPanel(MainApplication mainApp) 
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        try 
        {
            employeeDAO = new EmployeeDAO();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        initUI();
    }

    public void setEmployee(Employee employee) 
    {
        this.employee = employee;
        detailsPanel.setEmployee(employee);
        // If JobHistoryPanel and SprintEvaluationPanel need employee data, pass it to them here
    }

    private void initUI() 
    {
        // Left navigation panel
        navigationPanel = new JPanel();

        // Use BoxLayout with vertical alignment
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Optional padding
        
        //Initialize buttons
        detailsButton = new JButton("Details");
        jobHistoryButton = new JButton("Job History");
        sprintEvaluationButton = new JButton("Sprint Evaluations");
        backButton = new JButton("Back");
        editButton = new JButton("Edit");
        saveButton = new JButton("Save Changes");
        saveButton.setVisible(false); //initially hidden

        // Create a vertical separator
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(1, 0)); // 1 pixel wide, height adjusts automatically

       // Container panel to hold navigationPanel and separator
       JPanel leftPanelContainer = new JPanel(new BorderLayout());
       leftPanelContainer.add(navigationPanel, BorderLayout.WEST);
       leftPanelContainer.add(separator, BorderLayout.EAST);

        //Dimenstion for the buttons
        Dimension buttonSize = new Dimension(200, 35);
        detailsButton.setMaximumSize(buttonSize);
        jobHistoryButton.setMaximumSize(buttonSize);
        sprintEvaluationButton.setMaximumSize(buttonSize);
        backButton.setMaximumSize(buttonSize);
        editButton.setMaximumSize(buttonSize);
        saveButton.setMaximumSize(buttonSize);


        navigationPanel.add(detailsButton);
        navigationPanel.add(jobHistoryButton);
        navigationPanel.add(sprintEvaluationButton);
        navigationPanel.add(editButton);


        // Add vertical glue to push the backButton to the bottom
        navigationPanel.add(Box.createVerticalGlue());
        
        // Add the backButton at the bottom
        navigationPanel.add(backButton);
        //navigationPanel.add(backButton);

        //Add vertical glue to push the save button to the bottom
        navigationPanel.add(Box.createVerticalGlue());

        //Add the saveButton at the bottom
        navigationPanel.add(saveButton);

        // Content panel with CardLayout
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);

        // Initialize sub-panels
        detailsPanel = new DetailsPanel(mainApp);
        jobHistoryPanel = new JobHistoryPanel();
        sprintEvaluationPanel = new SprintEvaluationPanel();

        
        // Add sub-panels to content panel
        contentPanel.add(detailsPanel, "DetailsPanel");
        contentPanel.add(jobHistoryPanel, "JobHistoryPanel");
        contentPanel.add(sprintEvaluationPanel, "SprintEvaluationPanel");

        //Action of Details button:
            //Switch to Deatils Panel
            //Enable Job History and Sprint Evaluation button
            //Disable Details button
        detailsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentCardLayout.show(contentPanel, "DetailsPanel");
                detailsButton.setEnabled(false);            //Disable details button
                jobHistoryButton.setEnabled(true);          //Enable Job History button
                sprintEvaluationButton.setEnabled(true);    //Enable Sprint Evaluation button
            }
        });

        //Action of the Job History button:
            //Switch to Job History Panel
            //Enable Details and Sprint Evaluation button
            //Disable Job History button
        jobHistoryButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentCardLayout.show(contentPanel, "JobHistoryPanel");
                detailsButton.setEnabled(true);             //Enable details button
                jobHistoryButton.setEnabled(false);         //Disable job history button
                sprintEvaluationButton.setEnabled(true);    //Enable sprint evaluation button
            }
        });

        //Action of the Sprint Evaluation button
            //Switch to Sprint Evaluation Panel
            //Enable Details and Job History button
            //Disable Sprint Evaluation button
        sprintEvaluationButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentCardLayout.show(contentPanel, "SprintEvaluationPanel");
                detailsButton.setEnabled(true);             //Enable details button
                jobHistoryButton.setEnabled(true);          //Enable job hisotry button
                sprintEvaluationButton.setEnabled(false);   //Disable sprint evaluation button
            }
        });

        //Action of the Back button:
            //Swith to detail panel to force opening details panel in future
            //Switch to HomePanel
            //Enable Details, Job History, Sprint Evaluations buttons
            //Enable buttons to prevent bugs with disabled buttons when constantly switching between panels
        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentCardLayout.show(contentPanel, "DetailsPanel");
                mainApp.switchToPanel("HomePanel");
                detailsButton.setEnabled(true);
                jobHistoryButton.setEnabled(true);
                sprintEvaluationButton.setEnabled(true);
            }
        });

        //Action of the Edit button:
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableEditing(true); //Enable edit mode in the details panel
            }
        });

        //Action of the Save Button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Save edited employee data
                employee.setFirstName(firstNameField.getText());
                employee.setLastName(lastNameField.getText());
                employee.setEmail(emailField.getText());
                // Date of Birth - Convert from String to LocalDate
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // adjust format if necessary
                    employee.setDateOfBirth(LocalDate.parse(dateofBirthField.getText(), formatter));
                } catch (DateTimeParseException ex) {
                    System.out.println("Invalid Date of Birth format.");
                }
                employee.setDepartment(departmentField.getText());
                employee.setEmail(emailField.getText());
                 // Employee ID - Convert from String to int
                try {
                    employee.setEmployeeID(Integer.parseInt(employeeIDField.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid Employee ID format.");
                }
                employee.setPhoneNumber(phoneField.getText());
                employee.setJobTitle(jobTitleField.getText());
                employee.setDepartment(departmentField.getText());
                employee.setWorkLocation(workLocationField.getText());
                employee.setEmploymentStatus(employmentStatusField.getText());
                 // Hourly Rate - Convert from String to BigDecimal
                try {
                    employee.setHourlyRate(new BigDecimal(hourlyRateField.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid Hourly Rate format.");
                }
                employee.setNotes(notesField.getText());
                employee.setHardSkill1(hardSkill1Field.getText());
                employee.setHardSkill2(hardSkill2Field.getText());
                employee.setSoftSkill1(softSkill1Field.getText());
                employee.setSoftSkill2(softSkill2Field.getText());

                setEditMode(false); //Exits Edit mode after changes are saved
            }
        });
        
    
        // Add components to EmployeeDetailPanel
        add(leftPanelContainer, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Show the details panel by default
        contentCardLayout.show(contentPanel, "DetailsPanel");
    }

    //Method to set details button status
        //Note currently this method is used to set details button to disabled
        //when details panel is open
    public void setDetailsButtonStatus(boolean status)
    {
        detailsButton.setEnabled(status);
    }
    
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateofBirthField;
    private JTextField emailField;
    private JTextField employeeIDField;
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

    //Method to enable or disable editing fields
    public void enableEditing(boolean enable) {
        firstNameField.setEditable(enable);
        lastNameField.setEditable(enable);
        dateofBirthField.setEditable(enable);
        emailField.setEditable(enable);
        employeeIDField.setEditable(enable);
        phoneField.setEditable(enable);
        jobTitleField.setEditable(enable);
        departmentField.setEditable(enable);
        workLocationField.setEditable(enable);
        employmentStatusField.setEditable(enable);
        hourlyRateField.setEditable(enable);
        notesField.setEditable(enable);
        hardSkill1Field.setEditable(enable);
        hardSkill2Field.setEditable(enable);
        softSkill1Field.setEditable(enable);
        softSkill2Field.setEditable(enable);

        saveButton.setVisible(enable);
    }   

    private void setEditMode(boolean enable) {
        isEditMode = enable;
        enableEditing(enable); // Enable edit mode in DetailsPanel
        saveButton.setVisible(enable);      // Show save button only in edit mode
        editButton.setEnabled(!enable);     // Disable edit button in edit mode
    }

    
    

}