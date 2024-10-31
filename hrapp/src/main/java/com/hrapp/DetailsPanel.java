package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A Panel  to display detailed information about an employee.
 */
public class DetailsPanel extends JPanel 
{

    //Properties
    private MainApplication mainApp;
    private Employee employee;
    private EmployeeDAO employeeDAO;

    public DetailsPanel(MainApplication mainApp)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        try
        {
            employeeDAO = new EmployeeDAO();
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
    }

    public void setEmployee(Employee employee) 
    {
        this.employee = employee;
        removeAll(); // Clear previous content
        initUI();
        revalidate();
        repaint();
    }


    public void initUI()
    {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Populate the panel with employee details
        panel.add(new JLabel("Employee ID:"));
        panel.add(new JLabel(String.valueOf(employee.getEmployeeID())));

        panel.add(new JLabel("First Name:"));
        panel.add(new JLabel(employee.getFirstName()));

        panel.add(new JLabel("Last Name:"));
        panel.add(new JLabel(employee.getLastName()));

        panel.add(new JLabel("Date of Birth:"));
        panel.add(new JLabel(employee.getDateOfBirth() != null ? employee.getDateOfBirthStringFormat() : "N/A"));

        panel.add(new JLabel("Job Title:"));
        panel.add(new JLabel(employee.getJobTitle() != null ? employee.getJobTitle() : "N/A"));

        panel.add(new JLabel("Department:"));
        panel.add(new JLabel(employee.getDepartment() != null ? employee.getDepartment() : "N/A"));

        panel.add(new JLabel("Work Location:"));
        panel.add(new JLabel(employee.getWorkLocation() != null ? employee.getWorkLocation() : "N/A"));

        panel.add(new JLabel("Employment Status:"));
        panel.add(new JLabel(employee.getEmploymentStatus() != null ? employee.getEmploymentStatus() : "N/A"));

        panel.add(new JLabel("Email:"));
        panel.add(new JLabel(employee.getEmail()));

        panel.add(new JLabel("Phone Number:"));
        panel.add(new JLabel(employee.getPhoneNumber() != null && employee.getPhoneNumber().length() == 10 ? String.format("(%s)-%s-%s",
            employee.getPhoneNumber().substring(0, 3),
            employee.getPhoneNumber().substring(3, 6),
            employee.getPhoneNumber().substring(6, 10))
        : "N/A")); //This outputs phone number in the format (123)-456-7890

        panel.add(new JLabel("Hourly Rate:"));
        panel.add(new JLabel(employee.getHourlyrate() != null ? employee.getHourlyrate().toString() : "N/A"));

        panel.add(new JLabel("Notes:"));
        panel.add(new JLabel(employee.getNotes() != null ? employee.getNotes() : "N/A"));

        panel.add(new JLabel("Hard Skill 1:"));
        panel.add(new JLabel(employee.getHardSkill1() != null ? employee.getHardSkill1() : "N/A"));

        panel.add(new JLabel("Hard Skill 2:"));
        panel.add(new JLabel(employee.getHardSkill2() != null ? employee.getHardSkill2() : "N/A"));

        panel.add(new JLabel("Soft Skill 1:"));
        panel.add(new JLabel(employee.getSoftSkill1() != null ? employee.getSoftSkill1() : "N/A"));

        panel.add(new JLabel("Soft Skill 2:"));
        panel.add(new JLabel(employee.getSoftSkill2() != null ? employee.getSoftSkill2() : "N/A"));

        panel.add(new JLabel("Is Manager:"));
        panel.add(new JLabel(employee.getIsManager() == 1 ? "Yes" : "No"));

        panel.add(new JLabel("Is CEO:"));
        panel.add(new JLabel(employee.getIsCEO() == 1 ? "Yes" : "No"));
    
        // Add the panel to the center
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();

        //Delete Employee button
        JButton deleteEmployeeButton = new JButton("Delete Employee");
        deleteEmployeeButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent event)
           {
        
            int confirm = JOptionPane.showConfirmDialog(DetailsPanel.this,
                    "Are you sure you want to delete employee ID " + employee.getEmployeeID() + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if(confirm == JOptionPane.YES_OPTION)
                {
                    try
                    {
                        employeeDAO.deleteEmployee(employee.getEmployeeID()); // Delete employee in database
        
                        // Refresh the employee table in HomePanel
                        mainApp.getHomePanel().refreshEmployeeTable();
        
                        // Switch back to HomePanel
                        mainApp.switchToPanel("HomePanel");
                    }
                    catch(Exception error)
                    {
                        JOptionPane.showMessageDialog(DetailsPanel.this, 
                        "Error deleting employee: " + error.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                        error.printStackTrace();
                    }    
                }
           } 
        });

        //Add button to the button panel
        buttonPanel.add(deleteEmployeeButton);




        add(buttonPanel, BorderLayout.PAGE_END);
    }
}
