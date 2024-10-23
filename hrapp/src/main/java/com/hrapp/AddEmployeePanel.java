package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;




public class AddEmployeePanel extends JPanel
{
    //Properties
    private MainApplication mainApp;
    private EmployeeDAO employeeDAO;
    private JFXPanel panelForDate;
    private DatePicker datePicker;
    private JButton addButton;

    //Borders are used to paint JtextFields in different colors depending if they are filled or no
    private Border defaultBorder;
    private Border errorBorder = BorderFactory.createLineBorder(Color.RED, 2);
    

    //Instance variables for input fields (this is to fix bug with Calendar dissapearing)
    private JTextField firstName;
    private JTextField lastName;
    private JTextField jobTitle;
    private JTextField email;
    private JTextField phoneNumber;
    private JTextField hourlyRate;
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
    public AddEmployeePanel(MainApplication mainApp)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        initUI();

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

        //First Name label and input text field with limit of 50 characters
        panel.add(new JLabel("First Name:"));
        firstName = new JTextField();
        firstName.setDocument(new LimitedPlainDocument(50)); 
        panel.add(firstName);
        
        //Get default border for future to use
        defaultBorder = firstName.getBorder();

        //Last Name
        panel.add(new JLabel("Last Name:"));
        lastName = new JTextField();
        lastName.setDocument(new LimitedPlainDocument(75));
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
        firstName.setDocument(new LimitedPlainDocument(100));
        panel.add(jobTitle);

        String[] dep = new String[]{null,"SLS", "DEV", "MNG", "SPT"};
        String[] workLoc = new String[]{null,"MSU", "Office", "Remote"};
        String[] status = new String[]{null, "Intern", "Full-time", "Part-time", "Contractor"};
        String [] yesOrNo = new String[]{"No", "Yes"};
        String[] hardSkills = new String[]{null, "Java", "Python", "C#"};
        String[] softSkills = new String[]{null, "Leadership", "Teamwork", "Time Management"};


        //Department
        panel.add(new JLabel("Department:"));
        department = new JComboBox(dep);
        panel.add(department);

        //Work Location
        panel.add(new JLabel("Work Location:"));
        workLocation = new JComboBox(workLoc);
        panel.add(workLocation);

        //Employment Status
        panel.add(new JLabel("Employment Status:"));
        employmentStatus = new JComboBox(status);
        panel.add(employmentStatus);

        //Email
        panel.add(new JLabel("Email:"));
        email = new JTextField();
        email.setDocument(new LimitedPlainDocument(255));
        panel.add(email);

        //Phone Number
        panel.add(new JLabel("Phone Number:"));
        phoneNumber = new JTextField();
        panel.add(phoneNumber);

        // Add FocusListeners to JTextFields
        addFocusListenerToField(firstName);
        addFocusListenerToField(lastName);
        addFocusListenerToField(jobTitle);
        addFocusListenerToField(email);
        addFocusListenerToField(phoneNumber);

        //Hourly Rate
        panel.add(new JLabel("Hourly Rate:"));
        hourlyRate = new JTextField();
        panel.add(hourlyRate);

        //Notes
        panel.add(new JLabel("Notes:"));
        notes = new JTextField();
        panel.add(notes);

        /*
         * TODO: MAKE COMBOBOXES FOR SKILLS
         */

        //Hard Skill 1
        panel.add(new JLabel("Main Hard Skill:"));
        hardSkillOne = new JComboBox(hardSkills);
        panel.add(hardSkillOne);

        //Hard Skill 2
        panel.add(new JLabel("Secondary Hard Skill:"));
        hardSkillTwo = new JComboBox(hardSkills);
        panel.add(hardSkillTwo);

        //Soft Skill 1
        panel.add(new JLabel("Main Soft Skill:"));
        softSkillOne = new JComboBox(softSkills);
        panel.add(softSkillOne);

        //Soft Skill 2
        panel.add(new JLabel("Secondary Soft Skill:"));
        softSkillTwo = new JComboBox(softSkills);
        panel.add(softSkillTwo);

        //isManager
        panel.add(new JLabel("Manager:"));
        isManager = new JComboBox(yesOrNo);
        panel.add(isManager);

        //isCEO
        panel.add(new JLabel("CEO:"));
        isCEO = new JComboBox(yesOrNo);
        panel.add(isCEO);
        
        //Add panel to the AddEmployeePanel
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();

        //Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));

        //Add Employee button
        addButton = new JButton("Add");
        addButton.setEnabled(false);

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
        };

        //Add the DocumentListener to the TextFields
            // Note: DatePicker is handled separately
        firstName.getDocument().addDocumentListener(documentListener);
        lastName.getDocument().addDocumentListener(documentListener);
        jobTitle.getDocument().addDocumentListener(documentListener);
        email.getDocument().addDocumentListener(documentListener);
        phoneNumber.getDocument().addDocumentListener(documentListener);

        //Save all the information to the database
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    employeeDAO.addEmployee(new Employee(
                        firstName.getText(),
                        lastName.getText(),
                        datePicker.getValue(),
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
                    ));

                    //Refresh the employee table in HomePanel
                    mainApp.getHomePanel().refreshEmployeeTable();

                    //Switch back to HomePanel
                    mainApp.switchToPanel("HomePanel");
                    
                }
                catch(Exception error)
                {
                    JOptionPane.showMessageDialog(AddEmployeePanel.this, 
                        "Error adding employee: " + error.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                        error.printStackTrace();
                }
            }
        });
    
        //Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(addButton);
        
        //Add Button Panel to the main panel
        add(buttonPanel, BorderLayout.PAGE_END);

    }   
    
    //Creates a limit for the input amount on the JTextField
    public class LimitedPlainDocument extends PlainDocument 
    {
        private final int limit;
    
        public LimitedPlainDocument(int limit) 
        {
            if (limit <= 0) 
            {
                throw new IllegalArgumentException("Limit must be positive.");
            }
            this.limit = limit;
        }
    
        @Override
        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException 
        {
            if (str == null)
                return;
    
            if ((getLength() + str.length()) <= limit) 
            {
                super.insertString(offset, str, attr);
            } 
        }
    }

    //Resets values in the fields when a new instance of the Panel will be open
    public void resetFields()
    {
        firstName.setText("");
        lastName.setText("");
        datePicker.setValue(null);
        Platform.runLater(() -> datePicker.setStyle("")); //Sets DatePicker field back to default style
        jobTitle.setText("");
        department.setSelectedIndex(0);
        workLocation.setSelectedIndex(0);
        employmentStatus.setSelectedIndex(0);
        email.setText("");
        phoneNumber.setText("");
        hourlyRate.setText("");
        notes.setText("");
        hardSkillOne.setSelectedIndex(0);
        hardSkillTwo.setSelectedIndex(0);
        softSkillOne.setSelectedIndex(0);
        softSkillTwo.setSelectedIndex(0);
        isManager.setSelectedIndex(0);
        isCEO.setSelectedIndex(0);

        //Set Borders of the required JTextFields to default color
        SwingUtilities.invokeLater(() -> 
        {
            firstName.setBorder(defaultBorder);
            lastName.setBorder(defaultBorder);
            jobTitle.setBorder(defaultBorder);
            email.setBorder(defaultBorder);
            phoneNumber.setBorder(defaultBorder);
        });
    }

    // Method to initialize the JavaFX content
    public void initFX() 
    {
        StackPane root = new StackPane();

        // Create a DatePicker
        datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");

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

     // Method to update the state of the Add button
     private void updateButtonState() 
     {
        // Retrieve the datePicker value safely
        java.time.LocalDate selectedDate = null;
        if (datePicker != null) 
        {
            selectedDate = datePicker.getValue();
        }

        // Check if all required fields are filled
        boolean allFieldsFilled = !firstName.getText().trim().isEmpty() &&
                                  !lastName.getText().trim().isEmpty() &&
                                  (selectedDate != null) &&
                                  !jobTitle.getText().trim().isEmpty() &&
                                  !email.getText().trim().isEmpty() &&
                                    !phoneNumber.getText().trim().isEmpty();

        //Add Button becomes enabled if all the required fields are filled in
        addButton.setEnabled(allFieldsFilled);
    }

    //Validates date picker and if it is null
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

    // Helper method to add FocusListener to a JTextField
    private void addFocusListenerToField(JTextField textField) 
    {
        textField.addFocusListener(new FocusListener() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                // Optionally, you can remove the error border when the user focuses on the field
                textField.setBorder(defaultBorder);
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                validateFieldOnFocusLost(textField);
                updateButtonState();
            }
        });
    }

    // Method to validate a single JTextField when focus is lost
    private void validateFieldOnFocusLost(JTextField textField) 
    {
        if (textField.getText().trim().isEmpty()) 
        {
            textField.setBorder(errorBorder);
        } 
        else 
        
            textField.setBorder(defaultBorder);
        }
    }

