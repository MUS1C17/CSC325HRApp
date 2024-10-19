package com.hrapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;

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

        //search bar
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(20);
        topPanel.add(searchLabel);
        topPanel.add(searchField);

        //Add Employee button
        addEmployeeButton = new JButton("Add Employee");
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

        //Open AddEmployeePanel when clicking on Add Employee Button
        addEmployeeButton.addActionListener(e -> mainApp.switchToAddEmployeePanel("AddEmployeePanel"));

        /* 
        //Add Action Listener for Add Employee button
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Open the AddEmployeeDialog
                AddEmployeeDialog dialog = new AddEmployeeDialog(SwingUtilities.getWindowAncestor(HomePanel.this));
                dialog.setVisible(true);

                if (dialog.isSucceeded())
                {
                    //Retrive the new Employee data
                    Object[] newEmployeeData = dialog.getEmployeeData();

                    //Create an Employee object
                    Employee newEmployee = new Employee(
                        (String) newEmployeeData[1], // firstName
                        (String) newEmployeeData[2], // lastName
                        null, // dateOfBirth (to be set via additional dialogs or defaults)
                        null, // jobTitle
                        null, // department
                        null, // workLocation
                        null, // employmentStatus
                        (String) newEmployeeData[3], // email
                        null, // phoneNumber
                        null, // hourlyRate
                        null, // notes
                        null, // hardSkill1
                        null, // hardSkill2
                        null, // softSkill1
                        null, // softSkill2
                        0,    // isManager
                        0     // isCEO 
                    );

                    // Add the new employee to the table and database
                    employeeTablePanel.addEmployee(newEmployee);
                }
            }
        });*/
        
        // Add List Selection Listener to handle row selection for detailed view
        employeeTablePanel.getTable().getSelectionModel().addListSelectionListener(event -> 
        {
            if (!event.getValueIsAdjusting() && employeeTablePanel.getTable().getSelectedRow() != -1) 
            {
                Employee selectedEmployee = null;
                try
                {
                    selectedEmployee = employeeTablePanel.getSelectedEmployee();
                }
                catch(SQLException error)
                {
                    error.getMessage();
                }
                
                if (selectedEmployee != null) 
                {
                    // Open the detailed view panel
                    mainApp.showEmployeeDetails(selectedEmployee);
                }
            }
        });
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
