package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;

public class AddJobPanel extends JPanel
{
    //Properties
    private MainApplication mainApp;
    private JobDAO jobDAO;
    private JFXPanel panelForStartDate;
    private JFXPanel panelForEndDate;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private int employeeID;

    //Instance variables for input fields (this is to fix bug with Calendar dissapearing)
    private JTextField jobTitle;
    private JTextField companyName;
    private JTextField city;
    private JTextField description;
    private JTextField quitReason;


    //Constructor
    public AddJobPanel(MainApplication mainApp)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        initUI();

        try
        {
            jobDAO = new JobDAO();
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
    }

    // Set employeeID
    public void setEmployeeID(int employeeID)
    {
        this.employeeID = employeeID;
    }

    public void initUI()
    {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Fill the Panel with labels and TextFields

        // Job title
        panel.add(new JLabel("Job Title:"));
        jobTitle = new JTextField();
        panel.add(jobTitle);

        // Company name
        panel.add(new JLabel("Company Name:"));
        companyName = new JTextField();
        panel.add(companyName);

        // Start date
        panel.add(new JLabel("Start Date:"));
        panelForStartDate = new JFXPanel();
        panel.add(panelForStartDate);

        // End date
        panel.add(new JLabel("End Date:"));
        panelForEndDate = new JFXPanel();
        panel.add(panelForEndDate);
        
        Platform.runLater(this::initFX);


        // city
        panel.add(new JLabel("City:"));
        city = new JTextField();
        panel.add(city);

        // Job description
        panel.add(new JLabel("Job Description:"));
        description = new JTextField();
        panel.add(description);

        // Quit reason
        panel.add(new JLabel("Reason for termination:"));
        quitReason = new JTextField();
        panel.add(quitReason);

        //Add panel to the AddJobPanel.
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();

        //Back button -- THIS WILL NEED TO BE ADJUSTED FOR JOB HISTORY!
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));

        //Add job button
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
                boolean allFieldsFilled = !jobTitle.getText().trim().isEmpty() &&
                                          !companyName.getText().trim().isEmpty() &&
                                          //!dateOfBirth.getText().trim().isEmpty() &&
                                          !city.getText().trim().isEmpty() &&
                                          (!description.getText().trim().isEmpty() ||
                                          !quitReason.getText().trim().isEmpty());
                add.setEnabled(allFieldsFilled);
            }
        };

        //Add the DocumentListener to the TextFields
        jobTitle.getDocument().addDocumentListener(documentListener);
        companyName.getDocument().addDocumentListener(documentListener);
        //dateOfBirth.getDocument().addDocumentListener(documentListener);
        city.getDocument().addDocumentListener(documentListener);
        description.getDocument().addDocumentListener(documentListener);
        quitReason.getDocument().addDocumentListener(documentListener);

        //Save all the information to the database
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    // Array to hold the dates
                    LocalDate[] dates = new LocalDate[2];
                    // Create a CountDownLatch initialized with 1
                    CountDownLatch latch = new CountDownLatch(1);

                    // Retrieve date values on the JavaFX Application Thread
                    Platform.runLater(() -> 
                    {
                        dates[0] = startDatePicker.getValue();
                        dates[1] = endDatePicker.getValue();
                        latch.countDown(); // Signal that dates are retrieved
                    });

                    // Wait for the latch to reach zero
                    latch.await();
                    
                    jobDAO.addJob(new Job(
                        jobTitle.getText(),
                        companyName.getText(),
                        startDatePicker.getValue(),
                        endDatePicker.getValue(),
                        city.getText(),
                        description.getText(),
                        quitReason.getText(),
                        0,
                        employeeID
                    ));

                    //Switch back to JobHistoryPanel
                    //mainApp.switchToJobHistoryPanel(employeeID);
                    
                }
                catch(Exception error)
                {
                    JOptionPane.showMessageDialog(AddJobPanel.this, 
                        "Error adding job: " + error.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                        error.printStackTrace();
                }
            }
        });

        //Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(add);
        
        //Add Button Panel to the main panel
        add(buttonPanel, BorderLayout.PAGE_END);

    }   

    //Resets values in the fields when a new instance of the Panel will be open
    public void resetFields()
    {
        jobTitle.setText("");
        companyName.setText("");
        city.setText("");
        description.setText("");
        quitReason.setText("");
    }

    // Method to initialize the JavaFX content
    public void initFX() 
    {
        Platform.runLater(() -> {
            // Initialize the root pane for the first panel
            StackPane rootForStartDate = new StackPane();
            startDatePicker = new DatePicker();
            startDatePicker.setPromptText("Select Start Date");
            rootForStartDate.getChildren().add(startDatePicker);

            // Create a separate scene for the start date panel and set it on the JFXPanel
            Scene sceneForStartDate = new Scene(rootForStartDate, 300, 200);
            panelForStartDate.setScene(sceneForStartDate);

            // Initialize the root pane for the second panel
            StackPane rootForEndDate = new StackPane();
            endDatePicker = new DatePicker();
            endDatePicker.setPromptText("Select End Date");
            rootForEndDate.getChildren().add(endDatePicker);

            // Create a separate scene for the end date panel and set it on the JFXPanel
            Scene sceneForEndDate = new Scene(rootForEndDate, 300, 200);
            panelForEndDate.setScene(sceneForEndDate);
        });   
    }
}
