package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HomePanel extends JPanel
{
    private EmployeeTablePanel employeeTablePanel;
    private JTextField searchField;
    private JButton addEmployeeButton;
    private JButton addJobButton;
    private MainApplication mainApp;
    private HomePanel homePanel;
    private JPanel userInfoPanel;
    private Employee currentUser;
    private JButton jobSatisfactionButton;
    private JButton editProfileButton;

    public HomePanel(boolean isManagerOrCEO, MainApplication mainApp, Employee currentUser)
    {
        this.mainApp = mainApp;
        this.currentUser = currentUser;

        setLayout(new BorderLayout());

        // Top Panel containing search bar and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(45, 137, 216));

        // Add logo in top left
        JLabel logo = new JLabel(new ImageIcon("resources\\FRONTLINE_HR_Color_Version__1_-removebg-preview.png"));
        topPanel.add(logo);
        
        //search bar
        JLabel searchLabel = new Label("Search: ", 18, Color.WHITE);
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(searchLabel);
        topPanel.add(searchField);

        //Add Employee button
        addEmployeeButton = new JButton(new ImageIcon("resources\\AddButtons\\Add Employee button (no hover).png"));
        addEmployeeButton.setMaximumSize(new Dimension(232, 45));
        addEmployeeButton.setBorderPainted(false);
        addEmployeeButton.setContentAreaFilled(false);
        addEmployeeButton.setVisible(isManagerOrCEO); //Only visible for Managers/CEO
        topPanel.add(addEmployeeButton);

        addEmployeeButton.addMouseListener(new MouseListener() 
        {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                addEmployeeButton.setIcon(new ImageIcon("resources\\AddButtons\\Add Employee button (hover).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) 
            {
                addEmployeeButton.setIcon(new ImageIcon("resources\\AddButtons\\Add Employee button (no hover).png"));
            }
        });

        //TODO: Add Job/Skill button
        addJobButton = new JButton(new ImageIcon("resources\\AddButtons\\Add button (no hover).png"));
        addJobButton.setMaximumSize(new Dimension(100, 45)); //TODO: Change when placeholder button removed
        addJobButton.setBorderPainted(false);
        addJobButton.setContentAreaFilled(false);
        addJobButton.setVisible(isManagerOrCEO); //Only visible for Managers/CEO
        topPanel.add(addJobButton);
        //TODO: Event handlers required, particularly when implementing the job/skill add screen/DAO


        add(topPanel, BorderLayout.NORTH);

        // Initialize and add the user info panel
        userInfoPanel = createUserInfoPanel();
        add(userInfoPanel, BorderLayout.WEST);        

        //Employee Table Panel
        employeeTablePanel = new EmployeeTablePanel();
        add(employeeTablePanel, BorderLayout.CENTER);
        
        //Add Action Listener for search functionality
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) 
            {
                filter();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) 
            {
                filter();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) 
            {
                filter();
            }
            private void filter() 
            {
                String query = searchField.getText();
                employeeTablePanel.filterTable(query);
            }
        });

        //Open Detailed view panel
        employeeTablePanel.setEmployeeSelectionListener(new EmployeeTablePanel.EmployeeSelectionListener()
        {
            @Override
            public void employeeSelected(Employee employee)
            {
                // Open the detailed view panel
                mainApp.showEmployeeDetails(employee);
            }
        });

        //Open AddEmployeePanel when clicking on Add Employee Button
        addEmployeeButton.addActionListener(e -> mainApp.switchToAddEmployeePanel("AddEmployeePanel"));
    }    

    //Refresh employee table
    public void refreshEmployeeTable() throws SQLException 
    {
        employeeTablePanel.loadEmployeeData();
    }
    
    //Closes resources when the panel is no longer needed
    public void closeResources() 
    {
        employeeTablePanel.closeDAO();
    }

    //Creates a Left Panel with user information
    private JPanel createUserInfoPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(250, 0)); //Width - 200 and height adjusts automatically
        panel.setBackground(new Color(17, 59, 95));

        Employee currentUser = mainApp.getCurrentUser(); //Get current employee user

        //Add components

        // Logo/profile picture in top left
        JLabel logo = new JLabel(new ImageIcon("resources\\FRONTLINE HR App Badge (Large).png"));
        panel.add(logo);
        panel.add(Box.createVerticalStrut(35)); //Spacer

        //Welcome Message
        JLabel welcomeLabel = new Label("Welcome, " + currentUser.getFirstName() + "!", 20, Color.WHITE);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(20)); //Spacer
        
        //First and Last name
        JLabel firstAndLastName = new Label(currentUser.getFirstAndLastName(), 16, Color.WHITE);
        panel.add(firstAndLastName);

        //Email
        JLabel email = new Label(currentUser.getEmail(), 12, Color.WHITE);
        panel.add(email);

        //Phone Number
        JLabel phoneNumber = new Label("(" + currentUser.getPhoneNumber().substring(0, 3) + ")-" + currentUser.getPhoneNumber().substring(3, 6) + "-" +  currentUser.getPhoneNumber().substring(6), 12, Color.WHITE);
        panel.add(phoneNumber);

        //Job Title
        JLabel jobTitle = new Label(currentUser.getJobTitle(), 12, Color.WHITE);
        panel.add(jobTitle);


        /*
         * TODO: Add the rest of needed elements here
         */

        panel.add(Box.createVerticalStrut(50));
        // Edit Profile Button
        editProfileButton = new JButton(new ImageIcon("resources\\EditButtons\\Edit Profile button (no hover).png"));
        editProfileButton.setMaximumSize(new Dimension(166, 45));
        editProfileButton.setBorderPainted(false);
        editProfileButton.setContentAreaFilled(false);
        panel.add(editProfileButton);
        //TODO: Add event handlers to edit page and for hover behavior

        panel.add(Box.createVerticalStrut(20));
        //Job Satisfection Button
        jobSatisfactionButton = new JButton(new ImageIcon("resources\\JobSatisfactionButtons\\Job Satisfaction Reflection button (no hover).png"));
        jobSatisfactionButton.setBorderPainted(false);
        jobSatisfactionButton.setContentAreaFilled(false);

        panel.add(Box.createVerticalStrut(100));
        panel.add(jobSatisfactionButton);

        jobSatisfactionButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainApp.switchToPanel("AddSurveySatisfactionPanel");
            }
        });

        // Hover behavior for the jobSatisfactionButton
        jobSatisfactionButton.addMouseListener(new MouseListener() 
        {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                jobSatisfactionButton.setIcon(new ImageIcon("resources\\JobSatisfactionButtons\\Job Satisfaction Reflection button (hover).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) 
            {
                jobSatisfactionButton.setIcon(new ImageIcon("resources\\JobSatisfactionButtons\\Job Satisfaction Reflection button (no hover).png"));
            }
        });


        return panel;
    }
}
