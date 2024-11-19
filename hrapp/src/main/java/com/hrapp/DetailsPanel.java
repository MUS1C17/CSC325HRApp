package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
        panel.add(new Label("Employee ID:"));
        panel.add(new Label(String.valueOf(employee.getEmployeeID())));

        panel.add(new Label("First Name:"));
        panel.add(new Label(employee.getFirstName()));

        panel.add(new Label("Last Name:"));
        panel.add(new Label(employee.getLastName()));

        panel.add(new Label("Date of Birth:"));
        panel.add(new Label(employee.getDateOfBirth() != null ? employee.getDateOfBirthStringFormat() : "N/A"));

        panel.add(new Label("Job Title:"));
        panel.add(new Label(employee.getJobTitle() != null ? employee.getJobTitle() : "N/A"));

        panel.add(new Label("Department:"));
        panel.add(new Label(employee.getDepartment() != null ? employee.getDepartment() : "N/A"));

        panel.add(new Label("Work Location:"));
        panel.add(new Label(employee.getWorkLocation() != null ? employee.getWorkLocation() : "N/A"));

        panel.add(new Label("Employment Status:"));
        panel.add(new Label(employee.getEmploymentStatus() != null ? employee.getEmploymentStatus() : "N/A"));

        panel.add(new Label("Email:"));
        panel.add(new Label(employee.getEmail()));

        panel.add(new Label("Phone Number:"));
        JLabel phoneNumberLabel = new JLabel("<html><a href =''>" + (employee.getPhoneNumber() != null && employee.getPhoneNumber().length() == 10 ? String.format("(%s)-%s-%s",
            employee.getPhoneNumber().substring(0, 3),
            employee.getPhoneNumber().substring(3, 6),
            employee.getPhoneNumber().substring(6, 10))
        : "N/A") + "</a></html>"); //This outputs phone number in the format (123)-456-7890

        phoneNumberLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); //Cursor becomes a hand

        //Add mouse click listener to the label
        phoneNumberLabel.addMouseListener( new MouseAdapter() 
        {
            @Override
                public void mouseClicked(MouseEvent e)
                {
                    String phoneNumber = employee.getPhoneNumber();
                    try
                    {
                        //Create a URI with the tel: scheme
                        URI uri = new URI("tel:" + phoneNumber);
                        Desktop desktop = Desktop.getDesktop();
                        
                        if(desktop.isSupported(Desktop.Action.BROWSE))
                        {
                            desktop.browse(uri);
                        }
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(mainApp, "Error opening phone application");
                    }
                }       
        });
        panel.add(phoneNumberLabel);

        panel.add(new Label("Hourly Rate:"));
        panel.add(new Label(employee.getHourlyrate() != null ? employee.getHourlyrate().toString() : "N/A"));

        panel.add(new Label("Notes:"));
        panel.add(new Label(employee.getNotes() != null ? employee.getNotes() : "N/A"));

        panel.add(new Label("Hard Skill 1:"));
        panel.add(new Label(employee.getHardSkill1() != null ? employee.getHardSkill1() : "N/A"));

        panel.add(new Label("Hard Skill 2:"));
        panel.add(new Label(employee.getHardSkill2() != null ? employee.getHardSkill2() : "N/A"));

        panel.add(new Label("Soft Skill 1:"));
        panel.add(new Label(employee.getSoftSkill1() != null ? employee.getSoftSkill1() : "N/A"));

        panel.add(new Label("Soft Skill 2:"));
        panel.add(new Label(employee.getSoftSkill2() != null ? employee.getSoftSkill2() : "N/A"));

        panel.add(new Label("Is Manager:"));
        panel.add(new Label(employee.getIsManager() == 1 ? "Yes" : "No"));

        panel.add(new Label("Is CEO:"));
        panel.add(new Label(employee.getIsCEO() == 1 ? "Yes" : "No"));
    
        // Add the panel to the center
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();

        //Back button
        JButton backButton = new JButton(new ImageIcon("resources\\BackButtons\\Back button (no hover).png"));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));
        
        //Delete Employee button
        JButton deleteEmployeeButton = new JButton(new ImageIcon("resources\\DeleteButtons\\Delete button (no hover).png"));
        deleteEmployeeButton.setBorderPainted(false);
        deleteEmployeeButton.setContentAreaFilled(false);
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

        // Delete button hover behavior
        deleteEmployeeButton.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteEmployeeButton.setIcon(new ImageIcon("resources\\DeleteButtons\\Delete button (hover).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                deleteEmployeeButton.setIcon(new ImageIcon("resources\\DeleteButtons\\Delete button (no hover).png"));
            }
        });

        //Add button to the button panel
        buttonPanel.add(deleteEmployeeButton);




        add(buttonPanel, BorderLayout.PAGE_END);
    }
}
