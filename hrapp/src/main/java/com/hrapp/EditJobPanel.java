package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

    //Instance variables for input fields (this is to fix bug with Calendar dissapearing)
    private JTextField jobTitle;
    private JTextField companyName;
    private JTextField city;
    private JTextField description;
    private JTextField quitReason;


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
    public void setJobID(int jobID, Job job)
    {
        this.jobID = jobID;
        this.job = job;
        removeAll(); // Clear previous content
        initUI();
        revalidate();
        repaint();
    }

    public void initUI()
    {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Fill the Panel with labels and TextFields

        // Job title
        panel.add(new JLabel("Job Title:"));
        jobTitle = new JTextField(job.getJobTitle());
        panel.add(jobTitle);

        // Company name
        panel.add(new JLabel("Company Name:"));
        companyName = new JTextField(job.getCompanyName());
        panel.add(companyName);

        // Start date
        panel.add(new JLabel("Start Date:"));
        panelForStartDate = new JFXPanel();
        panel.add(panelForStartDate);

        // End date
        panel.add(new JLabel("End Date:"));
        panelForEndDate = new JFXPanel();
        panel.add(panelForEndDate);
        
        //Initialize the datepickers
        Platform.runLater(this::initFX);

        // city
        panel.add(new JLabel("City:"));
        city = new JTextField(job.getCity());
        panel.add(city);

        // Job description
        panel.add(new JLabel("Job Description:"));
        description = new JTextField(job.getJobDescription());
        panel.add(description);

        // Quit reason
        panel.add(new JLabel("Reason for termination:"));
        quitReason = new JTextField(job.getQuitReason());
        panel.add(quitReason);

        //Add panel to the AddJobPanel.
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();

        //Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));

        //Update job button.
        JButton update = new JButton("Update");
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

            private void updateButtonState() 
            {
                // Check if all text fields contain text
                boolean allFieldsFilled = !jobTitle.getText().trim().isEmpty() &&
                                          !companyName.getText().trim().isEmpty() &&
                                          //!dateOfBirth.getText().trim().isEmpty() &&
                                          !city.getText().trim().isEmpty() &&
                                          (!description.getText().trim().isEmpty() ||
                                          !quitReason.getText().trim().isEmpty());
                update.setEnabled(allFieldsFilled);
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
                        LocalDate endDate = endDatePicker.getValue();
            
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
            rootForStartDate.getChildren().add(startDatePicker);
            panelForStartDate.setScene(new Scene(rootForStartDate));

            // End Date Picker
            StackPane rootForEndDate = new StackPane();
            endDatePicker = new DatePicker();
            endDatePicker.setPromptText("Select End Date");
            endDatePicker.setValue(job.getEndDate()); // Set existing end date
            rootForEndDate.getChildren().add(endDatePicker);
            panelForEndDate.setScene(new Scene(rootForEndDate));
        });   
    }
}
