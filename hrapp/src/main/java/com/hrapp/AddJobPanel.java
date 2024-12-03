package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
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
    private JCheckBox presentDateCheckBox;
    private JFXPanel panelForEndDate;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private int employeeID;
    private Employee employee;
    private LocalDate endDate;
     // Variables to hold date picker values
     private LocalDate startDateValue = null;
     private LocalDate endDateValue = null;

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
    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public void initUI()
    {
        // Top Panel containing logo and page title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 137, 216));

        // Add logo and page title
        JLabel logo = new JLabel(new ImageIcon("resources\\FRONTLINE_HR_Color_Version__1_-removebg-preview.png"));
        topPanel.add(logo);
        topPanel.add(Box.createHorizontalStrut(50));

        topPanel.add(new Label("Add Job", 32, Color.WHITE));

        add(topPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Fill the Panel with labels and TextFields

        // Job title
        panel.add(new Label("Job Title:"));
        jobTitle = new TextField();
        panel.add(jobTitle);

        // Company name
        panel.add(new Label("Company Name:"));
        companyName = new TextField();
        panel.add(companyName);

        // Start date
        panel.add(new Label("Start Date:"));
        panelForStartDate = new JFXPanel();
        panel.add(panelForStartDate);

        // Present checkbox
        panel.add(new Label("")); // Empty label to keep formatting consistent.
        presentDateCheckBox = new JCheckBox("Ongoing job?");

        //Added action listener to the checkbox to disable end date datepicker and set it's value to null
        //if the box is checked, if unchecked then enable the endDatePicker
        presentDateCheckBox.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //If checkbox is checked, make endDatePickerDisabled and remove the value
                if(presentDateCheckBox.isSelected())
                {
                    endDatePicker.setDisable(true);
                    endDatePicker.setValue(null);
                    endDateValue = null;
                    SwingUtilities.invokeLater(() -> 
                    {
                        updateButtonState();
                    });
                }
                else
                {
                    //If unchecked enable the endDatePicker
                    endDatePicker.setDisable(false);
                    Platform.runLater(() -> 
                    {
                        endDateValue = endDatePicker.getValue();
                        SwingUtilities.invokeLater(() -> 
                        {
                            updateButtonState();
                        });
                    });
                }
            }    
        });

        panel.add(presentDateCheckBox);

        // End date
        panel.add(new Label("End Date:"));
        panelForEndDate = new JFXPanel();
        panel.add(panelForEndDate);
        
        //Initialize the datepickers
        Platform.runLater(this::initFX);

        // city
        panel.add(new Label("City:"));
        city = new TextField();
        panel.add(city);

        // Job description
        panel.add(new Label("Job Description:"));
        description = new TextField();
        panel.add(description);

        // Quit reason
        panel.add(new Label("Reason for termination:"));
        quitReason = new TextField();
        panel.add(quitReason);

        //Add panel to the AddJobPanel.
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(17, 59, 95));

        //Back button
        JButton backButton = new Button("resources\\BackButtons\\Back button (no hover).png", "resources\\BackButtons\\Back button (hover).png");
        backButton.addActionListener(e -> mainApp.showJobHistoryDetails(employee));

        //Add job button
        JButton add = new Button("resources\\AddButtons\\Add button (no hover).png", "resources\\AddButtons\\Add button (hover).png");
        add.setIcon(new ImageIcon("resources\\AddButtons\\Add button (no hover).png"));
        add.setDisabledIcon(new ImageIcon("resources\\AddButtons\\Add button (disabled).png"));
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

            private void updateButtonState() 
            {
                // Check if all text fields contain text
                boolean allFieldsFilled = !jobTitle.getText().trim().isEmpty() &&
                                          !companyName.getText().trim().isEmpty() &&
                                          !city.getText().trim().isEmpty() &&
                                          endDatePicker.getValue() != null
                                          && startDatePicker.getValue() != null &&
                                          (!description.getText().trim().isEmpty() ||
                                          !quitReason.getText().trim().isEmpty());
                add.setEnabled(allFieldsFilled);
            }
        };

        
        

        //Add the DocumentListener to the TextFields
        jobTitle.getDocument().addDocumentListener(documentListener);
        companyName.getDocument().addDocumentListener(documentListener);
        city.getDocument().addDocumentListener(documentListener);
        description.getDocument().addDocumentListener(documentListener);
        quitReason.getDocument().addDocumentListener(documentListener);

        //Save all the information to the database
        add.addActionListener(new ActionListener() 
        {
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
                        if (!presentDateCheckBox.isSelected())
                        {
                            dates[1] = endDatePicker.getValue();
                        }
                        else
                        {
                            dates[1] = LocalDate.of(0001, 01, 01);
                        }
                        latch.countDown(); // Signal that dates are retrieved
                    });
                    
                    // Wait for the latch to reach zero
                    latch.await();
                    

                    /*// Checks if current checkbox is selected.
                    if (!presentDateCheckBox.isSelected())
                    {
                        endDate = endDatePicker.getValue();
                    }
                    else
                    {
                        endDate = LocalDate.of(0001, 01, 01);
                    }*/
                    
                    jobDAO.addJob(new Job(
                        jobTitle.getText(),
                        companyName.getText(),
                        startDatePicker.getValue(),
                        dates[1],
                        city.getText(),
                        description.getText(),
                        quitReason.getText(),
                        0,
                        employee.getEmployeeID()
                    ));

                    //Switch back to JobHistoryPanel
                    mainApp.switchToJobHistoryPanel();
                    
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
        startDatePicker.setValue(null);
        presentDateCheckBox.setSelected(false);
        endDatePicker.setValue(null);
        endDatePicker.setDisable(false); //Force enable endDatePicker
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

              // Add listener to startDatePicker
            startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> 
            {
                startDateValue = newValue;
                SwingUtilities.invokeLater(() -> 
                {
                    updateButtonState();
                });
            });

            // Add listener to endDatePicker
            endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> 
            {
                endDateValue = newValue;
                SwingUtilities.invokeLater(() -> 
                {
                    updateButtonState();
                });
            });
        });   
    }

    private void updateButtonState() {
        boolean allFieldsFilled = !jobTitle.getText().trim().isEmpty() &&
                                  !companyName.getText().trim().isEmpty() &&
                                  !city.getText().trim().isEmpty() &&
                                  startDateValue != null &&
                                  (presentDateCheckBox.isSelected() || endDateValue != null) &&
                                  (!description.getText().trim().isEmpty() ||
                                  !quitReason.getText().trim().isEmpty());
        add.setEnabled(allFieldsFilled);
    }

    private void validateDatePickerOnFocusLost() 
    {
        Platform.runLater(() -> 
        {


        });
    }
}
