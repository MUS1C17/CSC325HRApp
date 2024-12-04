package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

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

public class EditJobPanel extends JPanel
{
    //Properties
    private MainApplication mainApp;
    private JobDAO jobDAO;
    private JFXPanel panelForStartDate;
    private JFXPanel panelForEndDate;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private int jobID;
    private Job job;
    private Employee employee;
    private LocalDate endDate;
    private JCheckBox presentDateCheckBox;

    private LocalDate startDateValue = null;
    private LocalDate endDateValue = null;

    //Instance variables for input fields (this is to fix bug with Calendar dissapearing)
    private JTextField jobTitle;
    private JTextField companyName;
    private JTextField city;
    private JTextField description;
    private JTextField quitReason;

    private JButton update;


    //Constructor
    public EditJobPanel(MainApplication mainApp)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        try
        {
            jobDAO = new JobDAO();
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
    }

    // Set jobID
    public void setInformation(int jobID, Job job, Employee employee)
    {
        this.jobID = jobID;
        this.job = job;
        this.employee = employee;
        removeAll(); // Clear previous content
        initUI();
        revalidate();
        repaint();
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

        topPanel.add(new Label("Edit Job", 32, Color.WHITE));

        add(topPanel, BorderLayout.NORTH);
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Fill the Panel with labels and TextFields

        // Job title
        panel.add(new Label("<html><span style='color:red;'>*</span>Job Title:"));
        jobTitle = new TextField(job.getJobTitle());
        panel.add(jobTitle);

        // Company name
        panel.add(new Label("<html><span style='color:red;'>*</span>Company Name:"));
        companyName = new TextField(job.getCompanyName());
        panel.add(companyName);

        // Start date
        panel.add(new Label("<html><span style='color:red;'>*</span>Start Date:"));
        panelForStartDate = new JFXPanel();
        panel.add(panelForStartDate);

        // Ongoing checkbox
        panel.add(new Label("")); // Blank label to keep formatting consistent.
        presentDateCheckBox = new JCheckBox("Ongoing job?");
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
        panel.add(new Label("<html><span style='color:red;'>*</span>End Date:"));
        panelForEndDate = new JFXPanel();
        panel.add(panelForEndDate);
        
        //Initialize the datepickers
        Platform.runLater(this::initFX);

        // city
        panel.add(new Label("<html><span style='color:red;'>*</span>City:"));
        city = new TextField(job.getCity());
        panel.add(city);

        // Job description
        panel.add(new Label("<html><span style='color:red;'>*</span>Job Description:"));
        description = new TextField(job.getJobDescription());
        panel.add(description);

        // Quit reason
        panel.add(new Label("Reason for termination:"));
        quitReason = new TextField(job.getQuitReason());
        panel.add(quitReason);

        //Add panel to the AddJobPanel.
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(17, 59, 95));

        //Back button
        JButton backButton = new Button("resources\\BackButtons\\Back button (no hover).png", "resources\\BackButtons\\Back button (hover).png");
        backButton.addActionListener(e -> mainApp.showJobHistoryDetails(employee));

        //Update job button.
        update = new Button("resources\\SaveButtons\\Save button (no hover).png", "resources\\SaveButtons\\Save button (hover).png");
        update.setIcon(new ImageIcon("resources\\SaveButtons\\Save button (no hover).png"));
        update.setDisabledIcon(new ImageIcon("resources\\SaveButtons\\Save button (disabled).png"));
        update.setEnabled(false);

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
        jobTitle.getDocument().addDocumentListener(documentListener);
        companyName.getDocument().addDocumentListener(documentListener);
        city.getDocument().addDocumentListener(documentListener);
        description.getDocument().addDocumentListener(documentListener);
        quitReason.getDocument().addDocumentListener(documentListener);

        //Save all the information to the database
        update.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Platform.runLater(() -> 
                {
                    try 
                    {
                        LocalDate startDate = startDatePicker.getValue();

                        // Check if current checkbox is selected.
                        if (!presentDateCheckBox.isSelected())
                        {
                            endDate = endDatePicker.getValue();
                        }
                        else
                        {
                            endDate = LocalDate.of(0001, 01, 01);
                        }
            
                        // Update the job in the database
                        jobDAO.updateJob(new Job(
                            jobTitle.getText(),
                            companyName.getText(),
                            startDate,
                            endDate,
                            city.getText(),
                            description.getText(),
                            quitReason.getText(),
                            0,
                            0
                        ), jobID);
            
                        // Switch back to JobHistoryPanel on the EDT
                        SwingUtilities.invokeLater(() -> mainApp.switchToJobHistoryPanel());
                    
                    }
                    catch(Exception error)
                    {
                        JOptionPane.showMessageDialog(EditJobPanel.this, 
                            "Error editing job: " + error.getMessage(), 
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                            error.printStackTrace();
                    }
                });
            }
        });

        // Required Fields indicator
        buttonPanel.add(new Label("Required fields marked with <html><span style='color:red;'>*</span></html>", 16, Color.WHITE));

        //Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(update);
        
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
        Platform.runLater(() -> 
        {
            // Start Date Picker
            StackPane rootForStartDate = new StackPane();
            startDatePicker = new DatePicker();
            startDatePicker.setPromptText("Select Start Date");
            startDatePicker.setValue(job.getStartDate()); // Set existing start date
            startDateValue = job.getStartDate();        // Initialize startDateValue
            rootForStartDate.getChildren().add(startDatePicker);
            panelForStartDate.setScene(new Scene(rootForStartDate));

            // End Date Picker
            StackPane rootForEndDate = new StackPane();
            endDatePicker = new DatePicker();
            endDatePicker.setPromptText("Select End Date");
            endDateValue = job.getEndDate(); // Initialize endDateValue
            endDatePicker.setValue(job.getEndDate());
            rootForEndDate.getChildren().add(endDatePicker);
            panelForEndDate.setScene(new Scene(rootForEndDate));

            if(job.getEndDate().equals(LocalDate.of(0001, 01, 01)))
            {
                endDatePicker.setDisable(true);
                presentDateCheckBox.setSelected(true);
                endDatePicker.setValue(null);

            }
            else
            {
                endDatePicker.setValue(job.getEndDate());
                presentDateCheckBox.setSelected(false);
                endDatePicker.setDisable(false);

            }

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
        update.setEnabled(allFieldsFilled);
    }
}
