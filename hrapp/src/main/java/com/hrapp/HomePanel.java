package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;

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

    public HomePanel(boolean isManagerOrCEO, MainApplication mainApp)
    {
        this.mainApp = mainApp;

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
}
