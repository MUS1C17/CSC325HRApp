package com.hrapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class HomePanel extends JPanel
{
    private EmployeeTablePanel employeeTablePanel;
    private JTextField searchField;
    private JButton addEmployeeButton;
    private JButton deleteEmployeeButton;

    public HomePanel(boolean isManagerOrCEO)
    {
        setLayout(new BorderLayout());

        // Top Panel containing search bar and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        //search bar
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(20);
        topPanel.add(searchLabel);
        topPanel.add(searchField);

        //Add Employee button
        addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.setVisible(isManagerOrCEO); //Only visible for Managers/CEO
        topPanel.add(addEmployeeButton);

        //Delete Employee button
        deleteEmployeeButton = new JButton("Delete Employee");
        deleteEmployeeButton.setVisible(isManagerOrCEO); //Only visible for Managers/CEO
        topPanel.add(deleteEmployeeButton);

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

        //Add Action Listener for Delete Employee button
        deleteEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //This try statement is made in order to handle SQLException
                Employee selectedEmployee = null;
                try
                {
                    //Get the selected employee
                    selectedEmployee = employeeTablePanel.getSelectedEmployee();
                }
                catch(SQLException error)
                {
                    error.getMessage();
                }

                if (selectedEmployee != null)
                {
                    int confirm = JOptionPane.showConfirmDialog(HomePanel.this,
                    "Are you sure you want to delete employee ID " + selectedEmployee.getEmployeeID() + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                    if(confirm == JOptionPane.YES_OPTION)
                    {
                        //Remove the employee from the table and database
                        int selectedRow = employeeTablePanel.getTable().getSelectedRow();
                        employeeTablePanel.removeEmployee(selectedRow);
                        JOptionPane.showMessageDialog(HomePanel.this, "Employee deleted successfully.",
                        "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(HomePanel.this, "Please select an employee to delete.",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    }
                }
                
            }
        });
        
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
                    // Open the detailed view dialog
                    EmployeeDetailDialog detailDialog = new EmployeeDetailDialog(
                            SwingUtilities.getWindowAncestor(HomePanel.this), selectedEmployee);
                    detailDialog.setVisible(true);
                }
            }
        });
    }    
    
    //Closes resources when the panel is no longer needed
    public void closeResources() 
    {
        employeeTablePanel.closeDAO();
    }
}
