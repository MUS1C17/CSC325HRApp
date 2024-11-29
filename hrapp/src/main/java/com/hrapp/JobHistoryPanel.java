package com.hrapp;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

//import javafx.scene.shape.Box;

public class JobHistoryPanel extends JPanel 
{
    private MainApplication mainApp;
    private JobDAO jobDAO;
    private ArrayList<Job> jobs;
    private int employeeID;
    private Job job;
    private Employee employee;

    public JobHistoryPanel(MainApplication mainApp)
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

    public void setEmployeeID(int employeeID, Employee employee)
    {
        this.employeeID = employeeID;
        this.employee = employee;
        showJobs(employeeID);
    }

    public void getJobs() // This method should use the jobDAO object to populate the jobs parameter with an arraylist of all jobs related to the employeeID.
    {
        try
        {
            jobs = jobDAO.getJobs(employeeID);
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
    }

    public void showJobs(int employeeID) // This is supposed to actually display the jobs and initialize the UI.
    {
        removeAll();
        getJobs();
        initUI();
    }

     private JPanel createJobHistoryBox(Job job) /*
        This method creates individual box objects to display the information about the given job.
     */
     {
        JPanel jobBox = new JPanel();
        jobBox.setLayout(new BoxLayout(jobBox, BoxLayout.Y_AXIS));
        jobBox.setMaximumSize(new Dimension(800,150));
        jobBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        // Content for a jobBox, in the order: Title and company; City and time of employment; description; quit reason.
        JLabel jobTitleLabel = new Label(job.getJobTitle() + " at " + job.getCompanyName(), 18, Color.WHITE);
        jobTitleLabel.setOpaque(true);
        jobTitleLabel.setBackground(new Color(17, 59, 95));
        jobTitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        jobBox.add(jobTitleLabel);

        JLabel jobCityAndTimeLabel = new Label(job.getCity() + " | " + job.getStartDateStringFormat() + " to " + job.getEndDateStringFormat(), 14, Color.WHITE);
        jobCityAndTimeLabel.setOpaque(true);
        jobCityAndTimeLabel.setBackground(new Color(45, 137, 216));
        jobCityAndTimeLabel.setAlignmentX(CENTER_ALIGNMENT);
        jobBox.add(jobCityAndTimeLabel);

        JLabel jobDescriptionLabel = new Label(job.getJobDescription(), 14);
        jobDescriptionLabel.setOpaque(true);
        jobDescriptionLabel.setBackground(Color.WHITE);
        jobDescriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
        jobBox.add(jobDescriptionLabel);

        //Termination part of the box should be only visible to Managers/CEO
        JLabel terminationHeader = new Label("REASON FOR TERMINATION: " + job.getQuitReason(), 12, Color.BLACK);
        terminationHeader.setOpaque(true);
        terminationHeader.setBackground(Color.WHITE);
        terminationHeader.setAlignmentX(CENTER_ALIGNMENT);
        setVisible(mainApp.isCurrentUserCEO() || mainApp.isCurrentUserManager());
        jobBox.add(terminationHeader);

        // create edit button and make it visible only ofr Manager/CEO
        JButton editButton = new Button("resources\\EditButtons\\Edit button (no hover).png", "resources\\EditButtons\\Edit button (hover).png");
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editButton.addActionListener(e -> mainApp.switchToEditJobPanel(job.getJobID(),job, employee));
        editButton.setVisible(mainApp.isCurrentUserCEO() || mainApp.isCurrentUserManager());

        // create delete button and make it visible only for Manager/CEO
        JButton deleteJobButton = new Button("resources\\DeleteButtons\\Delete button (no hover).png", "resources\\DeleteButtons\\Delete button (hover).png");
        deleteJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteJobButton.setVisible(mainApp.isCurrentUserCEO() || mainApp.isCurrentUserManager());
        deleteJobButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                int confirm = JOptionPane.showConfirmDialog(JobHistoryPanel.this,
                        "Are you sure you want to delete " + job.getJobTitle() + " job?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
                if (confirm == JOptionPane.YES_OPTION) 
                {
                    try 
                    {
                        // Delete the job from the database
                        jobDAO.deleteJob(job.getJobID());
        
                        // Remove the job from the jobs list
                        jobs.remove(job);
                        mainApp.switchToPanel("HomePanel");
                        mainApp.switchToJobHistoryPanel();
                    } 
                    catch (Exception error) 
                    {
                        JOptionPane.showMessageDialog(JobHistoryPanel.this,
                                "Error deleting job: " + error.getMessage(),
                                "Database Error", JOptionPane.ERROR_MESSAGE);
                        error.getMessage();
                    }
                }
            }
        });

        // button panel for buttons.

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(editButton);
        buttonPanel.add(deleteJobButton);

        jobBox.add(buttonPanel);
        
        return jobBox;
     }

    private void initUI()
    {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 1, 1, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (jobs.size() != 0)
        {
            for (int i = 0; i < jobs.size(); i++) // Loops through all jobs in arraylist jobs.
            {
                Job currentJob = jobs.get(i);
                panel.add(createJobHistoryBox(currentJob));
                panel.add(Box.createVerticalStrut(1));
            }
        }
        else
        {
            JLabel noJobsLabel = new Label("No job history");
            noJobsLabel.setAlignmentX(CENTER_ALIGNMENT);
            noJobsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noJobsLabel.setAlignmentY(CENTER_ALIGNMENT);
            panel.add(noJobsLabel);
        }

        // Scroll pane wrapper
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Adds the panel to the center.
        add(scrollPane, BorderLayout.CENTER);

        // Adds button panel to the bottom.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(17, 59, 95));

        // Add job button and make it visible only for CEO/Manager
        JButton addJobButton = new Button("resources\\AddButtons\\Add New Job button (no hover).png", "resources\\AddButtons\\Add New Job button (hover).png");
        addJobButton.addActionListener(e -> mainApp.switchToAddJobPanel(employee));
        addJobButton.setVisible(mainApp.isCurrentUserCEO() || mainApp.isCurrentUserManager());

        // Adds buttons to button panel.
        buttonPanel.add(addJobButton);

        add(buttonPanel, BorderLayout.PAGE_END);

    }
}
