package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/*
 * This panel is the first panel to show up in the app. 
 * Here you can chooose employee that you want to view the app from.
 * Depending on if employee is manager or CEO some buttons will be disabled/enabled for employee
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

        // Top Panel containing logo and page title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 137, 216));

        // Add logo and page title
        JLabel logo = new JLabel(new ImageIcon("resources\\FRONTLINE_HR_Color_Version__1_-removebg-preview.png"));
        topPanel.add(logo);
        topPanel.add(Box.createHorizontalStrut(50));

        topPanel.add(new Label("Welcome! Please sign in.", 32, Color.WHITE));

        // Create a JPanel within the UserSelectionPanel to house the center components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // Create a title text for the list
        Label label = new Label("Select from the list of users below.", 24);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(label, BorderLayout.NORTH);

        // To center the JList vertically, use a BoxLayout with vertical glue
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // Add vertical glue before the list to push it to the center
        listPanel.add(Box.createVerticalGlue());
        listPanel.add(leftScrollPane);
        listPanel.add(Box.createVerticalGlue());

        //Create login button
        loginButton = new Button("resources\\LoginButtons\\Login button (no hover).png", "resources\\LoginButtons\\Login button (hover).png");

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
        buttonPanel.setBackground(new Color(17, 59, 95));
        buttonPanel.add(loginButton);
        
        //Right panel that will have password field
        JPanel loginInformationPanel = new JPanel();
        passwordField = new JPasswordField(15);
        
        //Label to indicate current password
        JLabel passwordLabel = new Label("Password is: 12345");

        //Add password to the login Information Panel
        loginInformationPanel.add(passwordLabel);
        loginInformationPanel.add(passwordField);


        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        centerPanel.add(listPanel, BorderLayout.CENTER);
        centerPanel.add(loginInformationPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
    }
}
