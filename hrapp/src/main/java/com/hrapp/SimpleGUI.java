package com.hrapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SimpleGUI {
    
    public SimpleGUI()
    {
        //Create a JFRame object
        
        JFrame jobHistoryWindow = new JFrame("Job History Window");
        jobHistoryWindow.setLayout(null);
        jobHistoryWindow.setSize(800,600);

        //Set the default close to close application when windows is closed
        jobHistoryWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create a JLabel object with some text
        JLabel label = new JLabel("First Name: ");
        label.setBounds(200, 100, 150, 30);

        //Set the size of the window
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(300, 500, 150, 30);

        //Create a Box Field for input
        JTextField textField = new JTextField();
        textField.setBounds(300, 100, 150, 30);

        

        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String enteredText = textField.getText();
                System.out.println("Entered text: " + enteredText);
                
                SQLExecuter myExecuter = null;
                try
                {
                    myExecuter = new SQLExecuter();
                    myExecuter.setDataInDatabase("INSERT INTO Employee (FirstName) VALUES ('" +  enteredText+ "')");
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

        jobHistoryWindow.add(label);
        jobHistoryWindow.add(saveButton);
        jobHistoryWindow.add(textField);

        //Make the windows visible
        jobHistoryWindow.setVisible(true);

    }
}
