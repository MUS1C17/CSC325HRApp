package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private MainApplication mainApp;
    private HomePanel homePanel;
    private JPanel userInfoPanel;
    private Employee currentUser;
    private JButton jobSatisfactionButton;

    public HomePanel(boolean isManagerOrCEO, MainApplication mainApp, Employee currentUser)
    {
        this.mainApp = mainApp;
        this.currentUser = currentUser;

        setLayout(new BorderLayout());

        // Top Panel containing search bar and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Logo in top left
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("resources\\FRONTLINE_HR_Color_Version__1_-removebg-preview.png"));
        topPanel.add(logo);
        
        //search bar
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(20);
        topPanel.add(searchLabel);
        topPanel.add(searchField);

        //Add Employee button
        addEmployeeButton = new JButton(new ImageIcon("resources\\AddButtons\\Add button (no hover).png"));
        addEmployeeButton.setMaximumSize(new Dimension(100, 45));
        addEmployeeButton.setBorderPainted(false);
        addEmployeeButton.setContentAreaFilled(false);
        addEmployeeButton.setVisible(isManagerOrCEO); //Only visible for Managers/CEO
        topPanel.add(addEmployeeButton);

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

        Employee currentUser = mainApp.getCurrentUser(); //Get current employee user

        //Add components

        //Welcome Message
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getFirstName() + "!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(20)); //Spacer
        
        //First and Last name
        JLabel firstAndLastName = new JLabel(currentUser.getFirstAndLastName());
        panel.add(firstAndLastName);

        //Email
        JLabel email = new JLabel(currentUser.getEmail());
        panel.add(email);

        //Phone Number
        JLabel phoneNumber = new JLabel("(" + currentUser.getPhoneNumber().substring(0, 3) + ")-" + currentUser.getPhoneNumber().substring(3, 6) + "-" +  currentUser.getPhoneNumber().substring(6));
        panel.add(phoneNumber);

        //Job Title
        JLabel jobTitle = new JLabel(currentUser.getJobTitle());
        panel.add(jobTitle);


        /*
         * TODO: Add the rest of needed elements here
         */

        //Job Satisfection Button
        jobSatisfactionButton = new JButton("Job Satisfaction Reflection");
        panel.add(Box.createVerticalStrut(300));
        panel.add(jobSatisfactionButton);

        jobSatisfactionButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainApp.switchToPanel("AddSurveySatisfactionPanel");
            }
        });


        return panel;
    }
}
