package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

/*
 * This panel is the first panel to show up in the app. 
 * Here you can chooose employee that you want to view the app from.
 * Depending on if employee is manager or ceo some buttons will be disabled/enabled for employee
 */

public class UserSelectionPanel extends JPanel
{
    //Properties
    private MainApplication mainApp;
    private EmployeeDAO employeeDAO;


    private JList<Employee> userList; 
    private JButton loginButton;
    private JPasswordField passwordField;
    private DefaultListModel<Employee> listModel;
    private List<String> listOfNames;


    //Constructor
    public UserSelectionPanel(MainApplication mainApp)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        //initUI();

        try
        {
            employeeDAO = new EmployeeDAO();
            initUI();
        }
        catch(SQLException e)
        {
             JOptionPane.showMessageDialog(this, "Error Occured: " + 
             e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        
    }

    public void initUI() throws SQLException
    {
        
        listModel = new DefaultListModel<>();

        List<Employee> employees = employeeDAO.getAllEmployees(); //get all employees into the list of Employee objects
        for(Employee emp : employees)
        {
            listModel.addElement(emp);
        }

        // Create a JList and set its model to the DefaultListModel
        userList = new JList<>(listModel);

        // Implement a custom cell renderer to display the employee's name
        userList.setCellRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
            {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Employee) 
                {
                    Employee emp = (Employee) value;
                    setText(emp.getFirstAndLastName());
                }
                return this;
            }
        });

        // Set the visible row count to display all elements
        userList.setVisibleRowCount(listModel.getSize());

        // Increase the font size for better visibility
        userList.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Add the JList to a JScrollPane to allow for scrolling
        JScrollPane leftScrollPane = new JScrollPane(userList);

        // To center the JList vertically, use a BoxLayout with vertical glue
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Add vertical glue before the list to push it to the center
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(leftScrollPane);
        leftPanel.add(Box.createVerticalGlue());

        //Create login button
        loginButton = new JButton("Log in");

        //Open HomePanel after login button is clicked
        loginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Employee selectedEmployee = userList.getSelectedValue();
                if(selectedEmployee == null)
                {
                    JOptionPane.showMessageDialog(UserSelectionPanel.this, "Please select an employee.",
                    "No Employee Selected", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String enteredPassword = new String(passwordField.getPassword());
                if(enteredPassword.equals("12345"))
                {
                    mainApp.setCurrentUser(selectedEmployee);
                    mainApp.createHomePanel(true); //Creates home panel based on what user was chosen
                    mainApp.switchToPanel("HomePanel");
                }
                else
                {
                    JOptionPane.showMessageDialog(UserSelectionPanel.this, "Incorrect password. Please try again.", 
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        
        //Button panel to hold login button at the bottom of the screen
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        
        //Right panel that will have password field
        JPanel loginInformationPanel = new JPanel();
        passwordField = new JPasswordField(15);
        
        //Label to indicate current password
        JLabel passwordLabel = new JLabel("Password is: 12345");

        //Add password to the login Information Panel
        loginInformationPanel.add(passwordLabel);
        loginInformationPanel.add(passwordField);


        add(leftPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);
        add(loginInformationPanel, BorderLayout.CENTER);
    }


}
