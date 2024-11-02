package com.hrapp;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

public class UserSelectionPanel extends JPanel
{
    //Properties
    private MainApplication mainApp;
    private EmployeeDAO employeeDAO;


    private JList userList; 
    private JButton loginButton;
    private JPasswordField passwordField;
    private DefaultListModel<String> listModel;
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
            e.getMessage();
        }

        
    }

    public void initUI() throws SQLException
    {
        
        listModel = new DefaultListModel<>();

        List<Employee> employees = employeeDAO.getAllEmployees(); //get all employees into the list of Employee objects
        for(Employee emp : employees)
        {
            listModel.addElement(emp.getFirstAndLastName());
        }

        // Create a JList and set its model to the DefaultListModel
        userList = new JList<>(listModel);
        
        // Add the JList to a JScrollPane to allow for scrolling
        JScrollPane scrollPane = new JScrollPane(userList);

        JPanel panel = new JPanel();
        panel.add(scrollPane);

        add(panel, BorderLayout.CENTER);
    }


}
