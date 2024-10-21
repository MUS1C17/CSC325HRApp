package com.hrapp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SimpleGUI {
    
    public SimpleGUI()
    {
        String[] employmentStatusArray = { "Intern", "Full-time", "Part-time", "Contract" };

        //Create a JFrame object
        JFrame addUserFrame = new JFrame("Add User Window");
        addUserFrame.setLayout(null);
        addUserFrame.setSize(800,600);

        //Set background color
        addUserFrame.getContentPane().setBackground(Color.decode("#e5d07b"));

        //Set the default close to close application when windows is closed
        addUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create a JLabel object with some text
        JLabel logo = new JLabel(new ImageIcon("C:\\Users\\ethan\\Downloads\\FRONTLINE HR Color Version.jpg"));
        JLabel exampleTitle = new JLabel("Add a User");
        JLabel demoLabel = new JLabel("DEMO");
        JLabel firstNameLabel = new JLabel("First Name: ");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel employementStatusLabel = new JLabel("Employement Status:");

        logo.setBounds(30, 30, 200, 60);
        exampleTitle.setBounds(350, 30, 300, 30);
        demoLabel.setBounds(725, 30, 50, 30);
        firstNameLabel.setBounds(100, 100, 150, 30);
        lastNameLabel.setBounds(100, 150, 150, 30);
        employementStatusLabel.setBounds(100, 200, 150, 30);

        //Set the size of the window
        JButton saveButton = new JButton("Save");

        saveButton.setBounds(300, 500, 150, 30);

        // Create a JComboBox and pass the options
        JComboBox<String> employmentStatusComboBox = new JComboBox<>(employmentStatusArray);
        employmentStatusComboBox.setBounds(300, 200, 150, 30);


        //Create a Box Field for input
        JTextField firstNameTextField = new JTextField();
        JTextField lastNameTextField = new JTextField();


        firstNameTextField.setBounds(300, 100, 150, 30);
        lastNameTextField.setBounds(300, 150, 150, 30);

        
        //Save button functionality
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Get text from all fields
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String employmentStatus = (String) employmentStatusComboBox.getSelectedItem();

                System.out.println("Entered text: " + firstName);
                
                //Connect to SQL database and execute query
                SQLExecuter myExecuter = null;
                try
                {
                    myExecuter = new SQLExecuter();
                    myExecuter.setDataInDatabase("INSERT INTO Employee (FirstName, LastName, EmploymentStatus) VALUES ('" +  firstName + "', '" + lastName + "', '" + employmentStatus + "')");
                }
                catch(SQLException ex)
                {
                    ex.getMessage();
                }
                finally
                {
                    myExecuter.closeConnection();
                }

            }
        });

        //Add all objects to the gui
        addUserFrame.add(logo);
        addUserFrame.add(exampleTitle);
        addUserFrame.add(demoLabel);
        addUserFrame.add(firstNameLabel);
        addUserFrame.add(lastNameLabel);
        addUserFrame.add(employementStatusLabel);
        addUserFrame.add(saveButton);
        addUserFrame.add(firstNameTextField);
        addUserFrame.add(lastNameTextField);
        addUserFrame.add(employmentStatusComboBox);

        //Make the windows visible
        addUserFrame.setVisible(true);
    }
}
