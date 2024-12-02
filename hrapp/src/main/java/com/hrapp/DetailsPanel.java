package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

    private Label emailLabel;
    private Label phoneNumberLabel;

    // Get the desktop environment to perform desktop-related actions
    private Desktop desktop = Desktop.getDesktop();

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
        emailLabel = new Label("<a href =''>" + (employee.getEmail()) + "</a>");

        //Cursor becomes a hand
        emailLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Add mouse click listener to the email label
        emailLabel.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                try
                {
                    int choice = JOptionPane.showConfirmDialog(mainApp,
                    "Would you like to email " + employee.getFirstAndLastName() + "?",
                    "Send Email",
                    JOptionPane.YES_NO_OPTION);

                    //If yes option is clicked
                    if(choice == JOptionPane.YES_OPTION)
                    {
                        // Create a URI for the mailto action using the employee's email
                        URI emailURI = new URI("mailto:" + employee.getEmail());

                        // Check if the desktop environment supports the "browse" action
                        if(desktop.isSupported(Desktop.Action.BROWSE))
                        {
                            desktop.browse(emailURI);
                        }
                    }
                    //No code needed for no option since it will automatically close the JOptionPane

                } 
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainApp, "Error opening phone application: " + ex.getMessage());
                }
            }

        });

        panel.add(emailLabel);

        panel.add(new Label("Phone Number:"));
        phoneNumberLabel = new Label("<a href =''>" + (employee.getPhoneNumber() != null && employee.getPhoneNumber().length() == 10 ? String.format("(%s)-%s-%s",
            employee.getPhoneNumber().substring(0, 3),
            employee.getPhoneNumber().substring(3, 6),
            employee.getPhoneNumber().substring(6, 10))
        : "N/A") + "</a>"); //This outputs phone number in the format (123)-456-7890

        //Cursor becomes a hand
        phoneNumberLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 

        //Add mouse click listener to the phoneNubmerLabel
        phoneNumberLabel.addMouseListener( new MouseAdapter() 
        {
            @Override
                public void mouseClicked(MouseEvent e)
                {
                    String[] contactOptions = {"Call", "Message", "Cancel",};
                    try
                    {
                        int choice = JOptionPane.showOptionDialog(mainApp, 
                        "How would you like to contact " + employee.getFirstAndLastName() + "?", 
                        "Choose Contact Option",
                        JOptionPane.DEFAULT_OPTION,   // Option type
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        contactOptions,
                        contactOptions[0]);
                        //Desktop desktop = Desktop.getDesktop();

                        switch (choice) 
                        {
                            //If call button is clicked, then open Windows Phone Call
                            case 0:     
                                // Create a URI for the tel: action using the employee's phonenumber
                                URI callURI = new URI("tel:" + employee.getPhoneNumber());
                                
                                // Check if the desktop environment supports the "browse" action
                                if(desktop.isSupported(Desktop.Action.BROWSE))
                                {
                                    desktop.browse(callURI);
                                }
                                break;
                            
                            //If Message button is clicked, then open Windows Phone Message
                            case 1:

                                // Create a URI for the sms: action using the employee's phoneNumber
                                URI smsURI = new URI("sms:" + employee.getPhoneNumber());

                                // Check if the desktop environment supports the "browse" action
                                if(desktop.isSupported(Desktop.Action.BROWSE))
                                {
                                    desktop.browse(smsURI);
                                }
                                break;
                            
                            //No need for switch case for Cancel button since it will close the OptionPane anyway 
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

        //If manager/ceo/same employee show hourly rate and notes
        if(mainApp.isCurrentUserCEO() || mainApp.isCurrentUserManager() ||mainApp.isCurrentUserAndSelectedEmployeeSame(employee))
        {
            //Hourly rate: Set visible only for Managers/CEO
            panel.add(new Label("Hourly Rate:"));
            panel.add(new Label(employee.getHourlyrate() != null ? employee.getHourlyrate().toString() : "N/A"));

            //Notes: set visible only for managers/CEO
            panel.add(new Label("Notes:"));
            panel.add(new Label(employee.getNotes() != null ? employee.getNotes() : "N/A"));
        }

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
        buttonPanel.setBackground(new Color(17, 59, 95));

        //Delete Employee button
        JButton deleteEmployeeButton = new Button("resources\\DeleteButtons\\Delete button (no hover).png", "resources\\DeleteButtons\\Delete button (hover).png");

        //Set visible to CEO or Manager unless manager is viewing his own details
        deleteEmployeeButton.setVisible(mainApp.isCurrentUserCEO() || mainApp.isCurrentUserManager());
        deleteEmployeeButton.addActionListener(new ActionListener() 
        {
           @Override
           public void actionPerformed(ActionEvent event)
           {

                //If current user is CEO AND is trying to delete themself
                //THEN show pop up message indicating it is impossible
                if(mainApp.isCurrentUserCEO() && mainApp.getCurrentUser().getEmployeeID() == employee.getEmployeeID())
                {
                    JOptionPane.showMessageDialog(mainApp, "You cannot delete yourself from the company since you are the CEO of the company.",
                                            "Validation Issue", JOptionPane.WARNING_MESSAGE);
                }

                //If current user is manager AND is trying to delete themself
                //THEN show pop up message indicating they can't delete themself
                else if(mainApp.isCurrentUserManager() && mainApp.getCurrentUser().getEmployeeID() == employee.getEmployeeID())
                {
                    JOptionPane.showMessageDialog(mainApp, "You cannot delete yourself from the company",
                                            "Validation Issue", JOptionPane.WARNING_MESSAGE);
                }

                 /*
                 *  if currentUser.isCEO OR (currentUser.department == employee.department AND currentUser.isManager AND employee.notCEO) 
                 *      can delete employee
                 *  else
                 *       show message indicating permission issue
                 */

                else if(mainApp.isCurrentUserCEO() ||
                    (mainApp.getCurrentUser().getDepartment().equals(employee.getDepartment()) && mainApp.isCurrentUserManager() && employee.getIsCEO() == 0))
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
                
                            // Switch back to HomePanel                                .
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
                else
                {
                    JOptionPane.showMessageDialog(mainApp, "You don't have rights to delete " + employee.getFirstAndLastName() + ".",
                                            "Permission Issue", JOptionPane.WARNING_MESSAGE);
                }
            } 
        });

        JButton editEmployeeButton = new Button("resources\\EditButtons\\Edit Profile button (no hover).png", "resources\\EditButtons\\Edit Profile button (hover).png");
        editEmployeeButton.setVisible(mainApp.isCurrentUserCEO() || mainApp.isCurrentUserManager() || mainApp.isCurrentUserAndSelectedEmployeeSame(employee));
        editEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                /*
                 *  if currentUser.isCEO OR currentUser.isEmployee OR (currentUser.department == employee.department AND currentUser.isManager AND employee.notCEO) 
                 *      can edit employee info
                 *  else
                 *       show message indicating departments are different so employee cannot
                 *       editied
                 */
                if(mainApp.isCurrentUserCEO() || mainApp.isCurrentUserAndSelectedEmployeeSame(employee) ||
                    (mainApp.getCurrentUser().getDepartment().equals(employee.getDepartment()) && mainApp.isCurrentUserManager() && employee.getIsCEO() == 0))
                    {
                        mainApp.switchToEditEmployeePanel(employee);
                    }
                else
                {
                    JOptionPane.showMessageDialog(mainApp, "You don't have rights to edit information for " + employee.getFirstAndLastName() + ".",
                                            "Permission Issue", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //Add button to the button panel
        buttonPanel.add(editEmployeeButton);
        buttonPanel.add(deleteEmployeeButton);


        add(buttonPanel, BorderLayout.PAGE_END);
    }
}
