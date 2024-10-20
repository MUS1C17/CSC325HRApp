package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class AddEmployeePanel extends JPanel
{
    //Properties
    private MainApplication mainApp;
    private EmployeeDAO employeeDAO;

    //Constructor
    public AddEmployeePanel(MainApplication mainApp)
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

    public void initUI()
    {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Fill the Panel with labels and TextFields

        //First Name
        panel.add(new JLabel("First Name:"));
        JTextField firstName = new JTextField();
        panel.add(firstName);

        //Last Name
        panel.add(new JLabel("Last Name:"));
        JTextField lastName = new JTextField();
        panel.add(lastName);

        //Date of Birth
        panel.add(new JLabel("Date of Birth:"));
        JTextField dateOfBirth = new JTextField();
        panel.add(dateOfBirth);

        //JobTitle
        panel.add(new JLabel("Job Title:"));
        JTextField jobTitle = new JTextField();
        panel.add(jobTitle);

        String[] dep = new String[]{null,"SLS", "DEV", "MNG", "SPT"};
        String[] workLoc = new String[]{null,"MSU", "Office", "Remote"};
        String[] status = new String[]{null, "Intern", "Full-time", "Part-time", "Contractor"};
        String [] yesOrNo = new String[]{"No", "Yes"};
        String[] hardSkills = new String[]{null, "Java", "Python", "C#"};
        String[] softSkills = new String[]{null, "Leadership", "Teamwork", "Time Management"};


        //Department
        panel.add(new JLabel("Department:"));
        JComboBox department = new JComboBox(dep);
        panel.add(department);

        //Work Location
        panel.add(new JLabel("Work Location:"));
        JComboBox workLocation = new JComboBox(workLoc);
        panel.add(workLocation);

        //Employment Status
        panel.add(new JLabel("Employment Status:"));
        JComboBox employmentStatus = new JComboBox(status);
        panel.add(employmentStatus);

        //Email
        panel.add(new JLabel("Email:"));
        JTextField email = new JTextField();
        panel.add(email);

        //Phone Number
        panel.add(new JLabel("Phone Number:"));
        JTextField phoneNumber = new JTextField();
        panel.add(phoneNumber);

        //Hourly Rate
        panel.add(new JLabel("Hourly Rate:"));
        JTextField hourlyRate = new JTextField();
        panel.add(hourlyRate);

        //Notes
        panel.add(new JLabel("Notes:"));
        JTextField notes = new JTextField();
        panel.add(notes);

        /*
         * TODO: MAKE COMBOBOXES FOR SKILLS
         */

        //Hard Skill 1
        panel.add(new JLabel("Main Hard Skill:"));
        JComboBox hardSkillOne = new JComboBox(hardSkills);
        panel.add(hardSkillOne);

        //Hard Skill 2
        panel.add(new JLabel("Secondary Hard Skill:"));
        JComboBox hardSkillTwo = new JComboBox(hardSkills);
        panel.add(hardSkillTwo);

        //Soft Skill 1
        panel.add(new JLabel("Main Soft Skill:"));
        JComboBox softSkillOne = new JComboBox(softSkills);
        panel.add(softSkillOne);

        //Soft Skill 2
        panel.add(new JLabel("Secondary Soft Skill:"));
        JComboBox softSkillTwo = new JComboBox(softSkills);
        panel.add(softSkillTwo);

        //isManager
        panel.add(new JLabel("Manager:"));
        JComboBox isManager = new JComboBox(yesOrNo);
        panel.add(isManager);

        //isCEO
        panel.add(new JLabel("CEO:"));
        JComboBox isCEO = new JComboBox(yesOrNo);
        panel.add(isCEO);
        
        //Add panel to the AddEmployeePanel
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();

        //Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));

        //Add Employee button
        JButton add = new JButton("Add");
        add.setEnabled(false);

        //Document listener to update state of the Add button
        DocumentListener documentListener = new DocumentListener()
        {
            public void changedUpdate(DocumentEvent e) 
            {
                updateButtonState();
            }
            public void removeUpdate(DocumentEvent e) 
            {
                updateButtonState();
            }
            public void insertUpdate(DocumentEvent e) 
            {
                updateButtonState();
            }

            private void updateButtonState() {
                // Check if all text fields contain text
                boolean allFieldsFilled = !firstName.getText().trim().isEmpty() &&
                                          !lastName.getText().trim().isEmpty() &&
                                          !dateOfBirth.getText().trim().isEmpty() &&
                                          !jobTitle.getText().trim().isEmpty() &&
                                          (!email.getText().trim().isEmpty() ||
                                          !phoneNumber.getText().trim().isEmpty());
                add.setEnabled(allFieldsFilled);
            }
        };

        //Add the DocumentListener to the TextFields
        firstName.getDocument().addDocumentListener(documentListener);
        lastName.getDocument().addDocumentListener(documentListener);
        dateOfBirth.getDocument().addDocumentListener(documentListener);
        jobTitle.getDocument().addDocumentListener(documentListener);
        email.getDocument().addDocumentListener(documentListener);
        phoneNumber.getDocument().addDocumentListener(documentListener);

        //Save all the information to the database
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                /*employeeDAO.addEmployee(new Employee(
                    firstName.getText(),
                    lastName.getText(),
                    dateOfBirth.getText(),
                    jobTitle.getText(),
                    department.getSelectedItem().toString(),
                    workLocation.getSelectedItem().toString(),
                    employmentStatus.getSelectedItem().toString(),
                    email.getText(),
                    phoneNumber.getText(),
                    new BigDecimal(hourlyRate.getText()),
                    notes.getText(),
                    hardSkillOne.getSelectedItem().toString(),
                    hardSkillTwo.getSelectedItem().toString(),
                    softSkillOne.getSelectedItem().toString(),
                    softSkillTwo.getSelectedItem().toString(),
                    isManager.getSelectedItem().equals("No") ? 0 : 1,
                    isCEO.getSelectedItem().equals("No") ? 0 : 1
                ));*/
            }
        });

        //Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(add);
        
        //Add Button Panel to the main panel
        add(buttonPanel, BorderLayout.PAGE_END);

    }   
}
