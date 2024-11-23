package com.hrapp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
    private JButton backButton;

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
        jobHistoryPanel.setEmployeeID(employee.getEmployeeID(), employee);
        //jobHistoryPanel.showJobs(employee.getEmployeeID());
    }

    private void initUI() 
    {
        // Top Panel containing logo and page title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 137, 216));

        // Add logo and page title
        JLabel logo = new JLabel(new ImageIcon("resources\\FRONTLINE_HR_Color_Version__1_-removebg-preview.png"));
        topPanel.add(logo);
        topPanel.add(Box.createHorizontalStrut(50));

        topPanel.add(new Label("Employee Info", 32, Color.WHITE)); // TODO: Replace with logic to get name of employee

        // Left navigation panel
        navigationPanel = new JPanel();

        // Use BoxLayout with vertical alignment
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Optional padding
        navigationPanel.setBackground(new Color(17, 59, 95));
        
        //Initialize buttons
        detailsButton = new JButton(new ImageIcon("resources\\DescriptionToggles\\DetailsButtons\\Details button (no hover) (1).png"));
        jobHistoryButton = new JButton(new ImageIcon("resources\\DescriptionToggles\\JobHistoryButtons\\Job History button (no hover) (1).png"));
        sprintEvaluationButton = new JButton(new ImageIcon("resources\\DescriptionToggles\\SprintEvaluationButtons\\Sprint Evaluations button (no hover) (1).png"));
        backButton = new JButton(new ImageIcon("resources\\BackButtons\\Back button (no hover).png"));

        //Set toggled look for each button
        detailsButton.setDisabledIcon(new ImageIcon("resources\\DescriptionToggles\\DetailsButtons\\Details button (toggled) (1).png"));
        jobHistoryButton.setDisabledIcon(new ImageIcon("resources\\DescriptionToggles\\JobHistoryButtons\\Job History button (toggled) (1).png"));
        sprintEvaluationButton.setDisabledIcon(new ImageIcon("resources\\DescriptionToggles\\SprintEvaluationButtons\\Sprint Evaluations button (toggled) (1).png"));

        //Set hover behavior for each button
        detailsButton.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                detailsButton.setIcon(new ImageIcon("resources\\DescriptionToggles\\DetailsButtons\\Details button (hover) (1).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                detailsButton.setIcon(new ImageIcon("resources\\DescriptionToggles\\DetailsButtons\\Details button (no hover) (1).png"));
            }
        });
        jobHistoryButton.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                jobHistoryButton.setIcon(new ImageIcon("resources\\DescriptionToggles\\JobHistoryButtons\\Job History button (hover) (1).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jobHistoryButton.setIcon(new ImageIcon("resources\\DescriptionToggles\\JobHistoryButtons\\Job History button (no hover) (1).png"));
            }
        });
        sprintEvaluationButton.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                sprintEvaluationButton.setIcon(new ImageIcon("resources\\DescriptionToggles\\SprintEvaluationButtons\\Sprint Evaluations button (hover) (1).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                sprintEvaluationButton.setIcon(new ImageIcon("resources\\DescriptionToggles\\SprintEvaluationButtons\\Sprint Evaluations button (no hover) (1).png"));
            }
        });

        // Create a vertical separator
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(1, 0)); // 1 pixel wide, height adjusts automatically

       // Container panel to hold navigationPanel and separator
       JPanel leftPanelContainer = new JPanel(new BorderLayout());
       leftPanelContainer.add(navigationPanel, BorderLayout.WEST);
       leftPanelContainer.add(separator, BorderLayout.EAST);

        //Dimenstion for the buttons
        Dimension buttonSize = new Dimension(200, 76);
        detailsButton.setMaximumSize(buttonSize);
        jobHistoryButton.setMaximumSize(buttonSize);
        sprintEvaluationButton.setMaximumSize(buttonSize);

        //No border or background on the buttons
        detailsButton.setBorderPainted(false);
        detailsButton.setContentAreaFilled(false);
        jobHistoryButton.setBorderPainted(false);
        jobHistoryButton.setContentAreaFilled(false);
        sprintEvaluationButton.setBorderPainted(false);
        sprintEvaluationButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);

        navigationPanel.add(detailsButton);
        navigationPanel.add(jobHistoryButton);
        navigationPanel.add(sprintEvaluationButton);


        // Add vertical glue to push the backButton to the bottom
        navigationPanel.add(Box.createVerticalGlue());
        
        // Add the backButton at the bottom
        navigationPanel.add(backButton);

        // Content panel with CardLayout
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);

        // Initialize sub-panels
        detailsPanel = new DetailsPanel(mainApp);
        jobHistoryPanel = new JobHistoryPanel(mainApp);
        sprintEvaluationPanel = new SprintEvaluationPanel();

        
        // Add sub-panels to content panel
        contentPanel.add(detailsPanel, "DetailsPanel");
        contentPanel.add(jobHistoryPanel, "JobHistoryPanel");
        contentPanel.add(sprintEvaluationPanel, "SprintEvaluationPanel");

        //Action of Details button:
            //Switch to Deatils Panel
            //Enable Job History and Sprint Evaluation button
            //Disable Details button
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

        //Action of the Job History button:
            //Switch to Job History Panel
            //Enable Details and Sprint Evaluation button
            //Disable Job History button
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

        //Action of the Sprint Evaluation button
            //Switch to Sprint Evaluation Panel
            //Enable Details and Job History button
            //Disable Sprint Evaluation button
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

        //Action of the Back button:
            //Swith to detail panel to force opening details panel in future
            //Switch to HomePanel
            //Enable Details, Job History, Sprint Evaluations buttons
            //Enable buttons to prevent bugs with disabled buttons when constantly switching between panels
        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentCardLayout.show(contentPanel, "DetailsPanel");
                mainApp.switchToPanel("HomePanel");
                detailsButton.setEnabled(true);
                jobHistoryButton.setEnabled(true);
                sprintEvaluationButton.setEnabled(true);
            }
        });
        //Hover behavior for Back button
        backButton.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setIcon(new ImageIcon("resources\\BackButtons\\Back button (hover).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setIcon(new ImageIcon("resources\\\\BackButtons\\\\Back button (no hover).png"));
            }
        });
    
        // Add components to EmployeeDetailPanel
        add(topPanel, BorderLayout.NORTH);
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

    public void setJobHistoryButtonStatus(boolean status)
    {
        jobHistoryButton.setEnabled(status);
    }

    // Refresh job history.
    public void refreshJobHistory()
    {
        jobHistoryPanel.setEmployeeID(employee.getEmployeeID(), employee);
    }

}
