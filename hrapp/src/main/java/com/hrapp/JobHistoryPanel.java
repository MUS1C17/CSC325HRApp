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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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

        // String of all the job details, organized neatly to fit in the box.
        String jobDetails = "Job title: " + job.getJobTitle() + "\nCompany name: " + job.getCompanyName() +
        "\nCity: " + job.getCity() + " (" + job.getStartDateStringFormat() + " - " + job.getEndDateStringFormat() + ")" +
        "\n" + job.getJobDescription() + "\nTermination reason: " + job.getQuitReason();

        JTextArea jobTextArea = new JTextArea(jobDetails, 5, 50);
        jobTextArea.setAlignmentX(CENTER_ALIGNMENT);
        jobTextArea.setWrapStyleWord(true);
        jobTextArea.setLineWrap(true);
        jobTextArea.setEditable(false);

        jobBox.add(jobTextArea);

        // create edit button
        JButton editButton = new JButton(new ImageIcon("resources\\EditButtons\\Edit button (no hover).png"));
        editButton.setBorderPainted(false);
        editButton.setContentAreaFilled(false);
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editButton.addActionListener(e -> mainApp.switchToEditJobPanel(job.getJobID(),job, employee));

        // Hover behavior for Edit button
        editButton.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                editButton.setIcon(new ImageIcon("resources\\EditButtons\\Edit button (hover).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                editButton.setIcon(new ImageIcon("resources\\\\EditButtons\\\\Edit button (no hover).png"));
            }
        });

        // create delete button
        JButton deleteJobButton = new JButton(new ImageIcon("resources\\\\DeleteButtons\\\\Delete button (no hover).png"));
        deleteJobButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteJobButton.setBorderPainted(false);
        deleteJobButton.setContentAreaFilled(false);
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

        // Hover behavior for Delete button
        deleteJobButton.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteJobButton.setIcon(new ImageIcon("resources\\DeleteButtons\\Delete button (hover).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                deleteJobButton.setIcon(new ImageIcon("resources\\DeleteButtons\\Delete button (no hover).png"));
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

        // Add job button
        JButton addJobButton = new JButton(new ImageIcon("resources\\AddButtons\\Add New Job button (no hover).png"));
        addJobButton.setBorderPainted(false);
        addJobButton.setContentAreaFilled(false);
        addJobButton.addActionListener(e -> mainApp.switchToAddJobPanel(employee));

        // Hover behavior
        addJobButton.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                addJobButton.setIcon(new ImageIcon("resources\\AddButtons\\Add New Job button (hover).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addJobButton.setIcon(new ImageIcon("resources\\AddButtons\\Add New Job button (no hover).png"));
            }
        });

        // Adds buttons to button panel.
        buttonPanel.add(addJobButton);

        add(buttonPanel, BorderLayout.PAGE_END);

    }
}
