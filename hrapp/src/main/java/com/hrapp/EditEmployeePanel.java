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
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;

/*
 * This is the panel for editing employees. It performs actions such as:
 * -being able to update an employee's information in the database
 * -displaying employee information and allowing that to be edited
 * -allowing changes to be saved only when the correct information is inputed
 */

public class EditEmployeePanel extends JPanel
{
    //Properties
    private MainApplication mainApp;
    private Employee employee;          //Employee ojbect that is to be edited
    private Employee updatedEmployee;   //Employee object after it was edited
    private EmployeeDAO employeeDAO;

    //UI components for date selection
    private JFXPanel panelForDate;      
    private DatePicker datePicker;   
    
    //UI components for required fields and buttons
    private JTextField[] requiredFields;
    private JButton updateButton;

    //Instance variables for input fields
    private JTextField employeeID;
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

    //Constructor
    public EditEmployeePanel(MainApplication mainApp, Employee employee)
    {
        this.mainApp = mainApp; //Instance of mainapp
        this.employee = employee; //Instance of employee
        setLayout(new BorderLayout());
        initUI();
        //Initialize the DAO for database connection
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

        //EmployeeID but make it not editable
        panel.add(new JLabel("Employee ID"));
        employeeID = new JTextField(Integer.toString(employee.getEmployeeID()));
        employeeID.setEditable(false);  //Set this field to not editable since it is a primary key in database
        panel.add(employeeID);

        //First Name label and input text field with limit of 50 characters
        panel.add(new JLabel("First Name:"));
        firstName = new JTextField();
        firstName.setDocument(new EmployeeFormValidator.LimitedPlainDocument(50));
        firstName.setName("firstName");
        firstName.setText(employee.getFirstName());
        panel.add(firstName);

         //Last Name
        panel.add(new JLabel("Last Name:"));
        lastName = new JTextField();
        lastName.setDocument(new EmployeeFormValidator.LimitedPlainDocument(75));
        lastName.setName("lastName");
        lastName.setText(employee.getLastName());
        panel.add(lastName);

        //Date of Birth
        panel.add(new JLabel("Date of Birth:"));
        panelForDate = new JFXPanel();
        panel.add(panelForDate);
        //Shows the calendar
        Platform.runLater(this::initFX);

        //JobTitle
        panel.add(new JLabel("Job Title:"));
        jobTitle = new JTextField();
        jobTitle.setDocument(new EmployeeFormValidator.LimitedPlainDocument(100));
        jobTitle.setName("jobTitle");
        jobTitle.setText(employee.getJobTitle());
        panel.add(jobTitle);

        /*
         * dropdown options for:
         * -department
         * -work location
         * -employment status
         * -manager or CEO
         * -hardskills
         * -softskills
         */
        String[] dep = new String[]{null,"SLS", "DEV", "MNG", "SPT"};
        String[] workLoc = new String[]{null,"MSU", "Office", "Remote"};
        String[] status = new String[]{null, "Intern", "Full-time", "Part-time", "Contractor"};
        String [] yesOrNo = new String[]{"No", "Yes"};
        String[] hardSkills = new String[]{null, "Java", "Python", "C#"};
        String[] softSkills = new String[]{null, "Leadership", "Teamwork", "Time Management"};


        //Department
        panel.add(new JLabel("Department:"));
        department = new JComboBox(dep);
        department.setSelectedItem(employee.getDepartment());
        panel.add(department);

        //Work Location
        panel.add(new JLabel("Work Location:"));
        workLocation = new JComboBox(workLoc);
        workLocation.setSelectedItem(employee.getWorkLocation());
        panel.add(workLocation);

        //Employment Status
        panel.add(new JLabel("Employment Status:"));
        employmentStatus = new JComboBox(status);
        employmentStatus.setSelectedItem(employee.getEmploymentStatus());
        panel.add(employmentStatus);

        //Email
        panel.add(new JLabel("Email:"));
        email = new JTextField();
        email.setDocument(new EmployeeFormValidator.LimitedPlainDocument(255));
        email.setName("email");
        email.setText(employee.getEmail());
        panel.add(email);

        //Phone Number
        panel.add(new JLabel("Phone Number:"));
        phoneNumber = new JTextField();
        phoneNumber.setDocument(new EmployeeFormValidator.LimitedPlainDocument(10));
        phoneNumber.setName("phoneNumber");
        phoneNumber.setText(employee.getPhoneNumber());
        panel.add(phoneNumber);

        // Add FocusListeners to required fields
        EmployeeFormValidator.addFocusListenerToField(firstName);
        EmployeeFormValidator.addFocusListenerToField(lastName);
        EmployeeFormValidator.addFocusListenerToField(jobTitle);
        EmployeeFormValidator.addFocusListenerToField(email);
        EmployeeFormValidator.addFocusListenerToField(phoneNumber);

        //Required fields array
        requiredFields = new JTextField[]{firstName, lastName, jobTitle, email, phoneNumber};

        //Hourly Rate
        panel.add(new JLabel("Hourly Rate:"));
        hourlyRate = new JFormattedTextField();
        ((AbstractDocument) hourlyRate.getDocument()).setDocumentFilter(new NumberDocumentFilter());
        hourlyRate.setText(employee.getHourlyrate().toString());
        panel.add(hourlyRate);

        //Notes
        panel.add(new JLabel("Notes:"));
        notes = new JTextField(employee.getNotes());
        panel.add(notes);

        /*
         * TODO: MAKE COMBOBOXES FOR SKILLS WITH DATA FROM DATABASE
         */

        //Hard Skill 1
        panel.add(new JLabel("Main Hard Skill:"));
        hardSkillOne = new JComboBox(hardSkills);
        hardSkillOne.setSelectedItem(employee.getHardSkill1());
        panel.add(hardSkillOne);

        //Hard Skill 2
        panel.add(new JLabel("Secondary Hard Skill:"));
        hardSkillTwo = new JComboBox(hardSkills);
        hardSkillTwo.setSelectedItem(employee.getHardSkill2());
        panel.add(hardSkillTwo);

        //Soft Skill 1
        panel.add(new JLabel("Main Soft Skill:"));
        softSkillOne = new JComboBox(softSkills);
        softSkillOne.setSelectedItem(employee.getSoftSkill1());
        panel.add(softSkillOne);

        //Soft Skill 2
        panel.add(new JLabel("Secondary Soft Skill:"));
        softSkillTwo = new JComboBox(softSkills);
        softSkillTwo.setSelectedItem(employee.getSoftSkill2());
        panel.add(softSkillTwo);

        //isManager
        panel.add(new JLabel("Manager:"));
        isManager = new JComboBox(yesOrNo);
        isManager.setSelectedItem(employee.getIsManager());
        panel.add(isManager);

        //isCEO
        panel.add(new JLabel("CEO:"));
        isCEO = new JComboBox(yesOrNo);
        isCEO.setSelectedItem(employee.getIsCEO());
        panel.add(isCEO);

        //Add panel to the EditEmployeePanel
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();

        //Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainApp.showEmployeeDetails(employee);
            }
        });

        //save information button
        updateButton = new JButton("Update");

        //Document listener to update state of the Add button depending if the field has expected values
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
        };

        // Add DocumentListener to required fields
        for (JTextField field : requiredFields) 
        {
            field.getDocument().addDocumentListener(documentListener);
        }
        updateButtonState();

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    //Assign updateEmployee with an updated Employee object
                    updatedEmployee = new Employee
                    (
                        Integer.parseInt(employeeID.getText()),
                        firstName.getText(),
                        lastName.getText(),
                        datePicker.getValue(),
                        jobTitle.getText(),
                        department.getSelectedItem() != null ? department.getSelectedItem().toString() : null,                 //Pass null if department isn't specified
                        workLocation.getSelectedItem() != null ? workLocation.getSelectedItem().toString() : null,             //Pass null if work location isn't specified
                        employmentStatus.getSelectedItem() != null ? employmentStatus.getSelectedItem().toString() : null,     //Pass null if employment status isn't specified
                        email.getText(),
                        phoneNumber.getText(),
                        new BigDecimal(!hourlyRate.getText().isEmpty() ? hourlyRate.getText() : "0"),                          //Pass 0 if hourly rate is not specified
                        notes.getText(),
                        hardSkillOne.getSelectedItem() != null ? hardSkillOne.getSelectedItem().toString() : null,             //Pass null if hard skill one status isn't specified
                        hardSkillTwo.getSelectedItem() != null ? hardSkillTwo.getSelectedItem().toString() : null,             //Pass null if hard skill two status isn't specified
                        softSkillOne.getSelectedItem() != null ? softSkillOne.getSelectedItem().toString() : null,             //Pass null if soft skill one status isn't specified
                        softSkillTwo.getSelectedItem() != null ? softSkillTwo.getSelectedItem().toString() : null,             //Pass null if soft skill two status isn't specified
                        isManager.getSelectedItem().equals("No") ? 0 : 1,
                        isCEO.getSelectedItem().equals("No") ? 0 : 1
                    );

                    //Pass updatedEmployee object into updateEmployee method
                    employeeDAO.updateEmployee(updatedEmployee);

                    //Refresh the employee table in HomePanel
                    mainApp.getHomePanel().refreshEmployeeTable();

                    //Switch back to EmployeeDetailsPanel to show updated information for the given employee
                    mainApp.showEmployeeDetails(updatedEmployee);
                }
                catch(Exception error)
                {
                    JOptionPane.showMessageDialog(EditEmployeePanel.this, 
                        "Error editing employee: " + error.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                        error.printStackTrace();
                }
            }
        });
        
        //Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(updateButton);

        //Add button panel to the bottme of the screen
        add(buttonPanel, BorderLayout.SOUTH);
    }

    //Method to make the update button turn OFF if the field requirements are not met
    private void updateButtonState() 
    {
        EmployeeFormValidator.updateButtonState(updateButton, requiredFields, datePicker);
    }

     // Method to initialize the JavaFX content
    public void initFX() 
    {
        StackPane root = new StackPane();

        // Create a DatePicker
        datePicker = new DatePicker();
        datePicker.setValue(employee.getDateOfBirth());

        // Add the DatePicker to the root StackPane
        root.getChildren().add(datePicker);


        // Create the scene and set it on the JFXPanel
        Scene scene = new Scene(root, 300, 200);
        panelForDate.setScene(scene);

        // Add listener to DatePicker's valueProperty
         datePicker.focusedProperty().addListener((observable, oldValue, newValue) -> 
         {
            if (!newValue) 
            { // Focus lost
                validateDatePickerOnFocusLost();
                SwingUtilities.invokeLater(() -> updateButtonState());
            }
        });
    }

    private void validateDatePickerOnFocusLost() 
    {
        Platform.runLater(() -> 
        {
            if (datePicker.getValue() == null) 
            {
                datePicker.setStyle("-fx-border-color: red;");
            } 
            else 
            {
                datePicker.setStyle(""); // Reset to default
            }
        });
    }



}
