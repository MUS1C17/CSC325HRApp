package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JTextArea;

//import javafx.scene.shape.Box;

public class JobHistoryPanel extends JPanel 
{
    private MainApplication mainApp;
    private JobDAO jobDAO;
    private ArrayList<Job> jobs;
    private int employeeID;

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

    public void setEmployeeID(int employeeID)
    {
        this.employeeID = employeeID;
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
        System.out.println("Removed all UI elements for job history.");
        getJobs();
        System.out.println("Got jobs");
        initUI();
        System.out.println("Called initUI()");
    }

    /*
        This is all temporary. I am just trying to get an idea of how
        it works.
     */

     private JPanel createJobHistoryBox(Job job) /*
        This method creates individual box objects to display the information about the given job.
     */
     {
        JPanel jobBox = new JPanel();
        jobBox.setMaximumSize(new Dimension(800,150));

        // String of all the job details, organized neatly to fit in the box.
        String jobDetails = "Job title: " + job.getJobTitle() + "\nCompany name: " + job.getCompanyName() +
        "\nCity: " + job.getCity() + " (" + job.getStartDateStringFormat() + " - " + job.getEndDateStringFormat() + ")" +
        "\n" + job.getJobDescription() + "\nTermination reason: " + job.getQuitReason();

        // JOB LABELS NOT WRAPPING OR STACKING PROPERLY!!!
        // WIP
        JTextArea jobTextArea = new JTextArea(jobDetails, 5, 50);
        jobTextArea.setAlignmentX(CENTER_ALIGNMENT);
        jobTextArea.setWrapStyleWord(true);
        jobTextArea.setLineWrap(true);

        jobBox.add(jobTextArea);

        // create delete button
        JButton deleteJobButton = new JButton("Delete");
        deleteJobButton.addActionListener(new ActionListener() { // DELETING A JOB BREAKS EVERYTHING!!
            @Override
            public void actionPerformed(ActionEvent event) {
                int confirm = JOptionPane.showConfirmDialog(JobHistoryPanel.this,
                        "Are you sure you want to delete job ID " + job.getJobID() + "?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        // Delete the job from the database
                        jobDAO.deleteJob(job.getJobID());
        
                        // Remove the job from the jobs list
                        jobs.remove(job);
                        //mainApp.switchToPanel("HomePanel"); // This is really stupid, but it would not work if switching to job history immediately.
                        //mainApp.switchToJobHistoryPanel(employeeID);
                    } catch (Exception error) {
                        JOptionPane.showMessageDialog(JobHistoryPanel.this,
                                "Error deleting job: " + error.getMessage(),
                                "Database Error", JOptionPane.ERROR_MESSAGE);
                        error.printStackTrace();
                    }
                }
            }
        });
        jobBox.add(deleteJobButton);
        
        return jobBox;
     }

    public void initUI()
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
            JLabel noJobsLabel = new JLabel("No job history");
            noJobsLabel.setAlignmentX(CENTER_ALIGNMENT);
            noJobsLabel.setAlignmentY(CENTER_ALIGNMENT);
            panel.add(noJobsLabel);
        }

        // Adds the panel to the center.
        add(panel, BorderLayout.CENTER);

        // Adds button panel to the bottom.
        JPanel buttonPanel = new JPanel();

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainApp.switchToPanel("EmployeeDetailPanel"));

        // Add job button
        JButton addJobButton = new JButton("Add New Job");
        addJobButton.addActionListener(e -> mainApp.switchToAddJobPanel(employeeID));

        // Adds buttons to button panel.
        buttonPanel.add(backButton);
        buttonPanel.add(addJobButton);

        add(buttonPanel, BorderLayout.PAGE_END);
    }
}
