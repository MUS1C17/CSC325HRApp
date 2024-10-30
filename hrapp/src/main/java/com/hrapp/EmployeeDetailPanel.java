package com.hrapp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;


public class EmployeeDetailPanel extends JPanel 
{

    // Properties
    private MainApplication mainApp;
    private Employee employee;
    private EmployeeDAO employeeDAO;

    // Navigation and content panels
    private JPanel navigationPanel;
    private JPanel contentPanel;
    private CardLayout contentCardLayout;

    // Sub-panels
    private DetailsPanel detailsPanel;
    private JobHistoryPanel jobHistoryPanel;
    private SprintEvaluationPanel sprintEvaluationPanel;

    //Buttons
    private JButton detailsButton;
    private JButton jobHistoryButton;
    private JButton sprintEvaluationButton;

    public EmployeeDetailPanel(MainApplication mainApp) 
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        try 
        {
            employeeDAO = new EmployeeDAO();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        initUI();
    }

    public void setEmployee(Employee employee) 
    {
        this.employee = employee;
        detailsPanel.setEmployee(employee);
        // If JobHistoryPanel and SprintEvaluationPanel need employee data, pass it to them here
    }

    private void initUI() 
    {
        // Left navigation panel
        navigationPanel = new JPanel();

        // Use BoxLayout with vertical alignment
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Optional padding
        
        //Initialize buttons
        detailsButton = new JButton("Details");
        jobHistoryButton = new JButton("Job History");
        sprintEvaluationButton = new JButton("Sprint Evaluations");

        // Create a vertical separator
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(1, 0)); // 1 pixel wide, height adjusts automatically

       // Container panel to hold navigationPanel and separator
       JPanel leftPanelContainer = new JPanel(new BorderLayout());
       leftPanelContainer.add(navigationPanel, BorderLayout.WEST);
       leftPanelContainer.add(separator, BorderLayout.EAST);

        //Dimenstion for the buttons
        Dimension buttonSize = new Dimension(200, 35);
        detailsButton.setMaximumSize(buttonSize);
        jobHistoryButton.setMaximumSize(buttonSize);
        sprintEvaluationButton.setMaximumSize(buttonSize);


        navigationPanel.add(detailsButton);
        navigationPanel.add(jobHistoryButton);
        navigationPanel.add(sprintEvaluationButton);

        // Content panel with CardLayout
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);

        // Initialize sub-panels
        detailsPanel = new DetailsPanel(mainApp);
        jobHistoryPanel = new JobHistoryPanel();
        sprintEvaluationPanel = new SprintEvaluationPanel();

        
        // Add sub-panels to content panel
        contentPanel.add(detailsPanel, "DetailsPanel");
        contentPanel.add(jobHistoryPanel, "JobHistoryPanel");
        contentPanel.add(sprintEvaluationPanel, "SprintEvaluationPanel");

        // Add action listeners to buttons
        detailsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentCardLayout.show(contentPanel, "DetailsPanel");
                detailsButton.setEnabled(false);            //Disable details button
                jobHistoryButton.setEnabled(true);          //Enable Job History button
                sprintEvaluationButton.setEnabled(true);    //Enable Sprint Evaluation button
            }
        });

        jobHistoryButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentCardLayout.show(contentPanel, "JobHistoryPanel");
                detailsButton.setEnabled(true);             //Enable details button
                jobHistoryButton.setEnabled(false);         //Disable job history button
                sprintEvaluationButton.setEnabled(true);    //Enable sprint evaluation button
            }
        });

        sprintEvaluationButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentCardLayout.show(contentPanel, "SprintEvaluationPanel");
                detailsButton.setEnabled(true);             //Enable details button
                jobHistoryButton.setEnabled(true);          //Enable job hisotry button
                sprintEvaluationButton.setEnabled(false);   //Disable sprint evaluation button
            }
        });
    
        // Add components to EmployeeDetailPanel
        add(leftPanelContainer, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Show the details panel by default
        contentCardLayout.show(contentPanel, "DetailsPanel");
    }

    //Method to set details button status
        //Note currently this method is used to set details button to disabled
        //when details panel is open
    public void setDetailsButtonStatus(boolean status)
    {
        detailsButton.setEnabled(status);
    }
}
