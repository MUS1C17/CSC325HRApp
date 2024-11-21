package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SprintEvaluationPanel extends JPanel {
    private MainApplication mainApp;
    private SprintEvaluationDAO sprintDAO;
    private Employee employee;


    // Constructor for SprintEvaluationPanel with parentFrame parameter
    public SprintEvaluationPanel(MainApplication mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        try 
        {
            //Initialize the sprintDAO variable that holds SprintEvaluationDAO ojbect to handle all the 
            //database connections
            sprintDAO = new SprintEvaluationDAO();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
        initUI();
    }

    //This method initializes all the elements on the panel
    public void initUI()
    {

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("YOU CAN ADD EVERYTHING HERE"));
        add(panel, BorderLayout.CENTER);

        //Switch to AddSprintEvaluationPanel
        JButton addButton = new JButton("Add Sprint Evaluation");
        addButton.addActionListener(e -> mainApp.switchToAddSprintEvaluationPanel(employee));

        // Adding button to the bottom of the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

}
