package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * This panel allows you to create new sprint evaluations:
 * -allows user to create a new sprint evaluation and submit it
 */
public class AddSprintEvaluationPanel extends JPanel {

    //Properties
    private JTextField feelingsField;
    private JTextField favoriteTaskField;
    private JTextField proficientTaskField;
    private JTextField dreadTaskField;
    private JTextField potentialTaskField;
    private JTextField notesArea;
    private SprintEvaluationPanel sprintEvaluationPanel;
    private MainApplication mainApp;
    private SprintEvaluationDAO sprintDAO;
    private Employee employee;

    // Constructor with parentFrame and SprintEvaluationPanel passed in
    public AddSprintEvaluationPanel(MainApplication mainApp) 
    {
        this.mainApp = mainApp;  // Set parentFrame reference
        setLayout(new BorderLayout());
        initUI();

        try
        {
            sprintDAO = new SprintEvaluationDAO();
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
    }

    //Method to initialize all the UI elements
    public void initUI()
    {
        // Top Panel containing logo and page title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 137, 216));

        // Add logo and page title
        JLabel logo = new JLabel(new ImageIcon("resources\\FRONTLINE_HR_Color_Version__1_-removebg-preview.png"));
        topPanel.add(logo);
        topPanel.add(Box.createHorizontalStrut(50));

        topPanel.add(new Label("New Sprint Evaluation", 32, Color.WHITE));

        add(topPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Create labels and text fields for each question

        //Feelings
        panel.add(new Label("Over the last two weeks, did you notice any feelings of positivity or negativity while performing specific job tasks, and if so, how would you describe those feelings?"));
        feelingsField = new TextField();
        panel.add(feelingsField);

        //Favorite Task
        panel.add(new Label("If you could do one task at work all day, which task would you choose, and why?"));
        favoriteTaskField = new TextField();
        panel.add(favoriteTaskField);

         //Proficient Task
        panel.add(new Label("Are there any tasks you perform in your job that you feel you are really good at, and if so, what are they?"));
        proficientTaskField = new TextField();
        panel.add(proficientTaskField);

        //Dread Task
        panel.add(new Label("Are there any tasks in your job you dread having to do, and if so, what are they and what about them makes you dread them?"));
        dreadTaskField = new TextField();
        panel.add(dreadTaskField);

        //Potential Task
        panel.add(new Label("Are there any tasks in your job you look forward to doing, and if so, what are they and why do you look forward to them?"));
        potentialTaskField = new TextField();
        panel.add(potentialTaskField);

        //Notes Area
        panel.add(new Label("Recommendations/notes on current job satisfaction, proficiency, and efficiency"));
        notesArea = new TextField();
        panel.add(notesArea);

        //Add panel to the AddSprintEvaluationPanel
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(17, 59, 95));

        // Submit button
        JButton submitButton = new Button("resources\\SaveButtons\\Save button (no hover).png", "resources\\SaveButtons\\Save button (hover).png");
        submitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();

                //Switch back to Sprint Evaluations page
                mainApp.switchToPanel("EmployeeDetailPanel");

                //Refresh Evaluations
                mainApp.refreshSprintEvaluations(employee);
            }
        });

        // Cancel button to return to SprintEvaluationPanel
        JButton cancelButton = new Button("resources\\CancelButtons\\Cancel button (no hover).png", "resources\\CancelButtons\\Cancel button (hover).png");
        cancelButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //Switch back to the sprint evaluation panel
                mainApp.switchToPanel("EmployeeDetailPanel");
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        //Add Button Panel to the main panel
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    //Setter method for Employee to get EmployeeID
    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    // Handle form submission
    private void handleSubmit() 
    {
        try 
        {
            // Save SprintEvaluation using addSprintEvaluation method from SprintEvaluationDAO class
            sprintDAO.addSprintEvaluation(new SprintEvaluation
            (
                employee.getEmployeeID(),
                feelingsField.getText(),
                favoriteTaskField.getText(),
                proficientTaskField.getText(),
                dreadTaskField.getText(),
                potentialTaskField.getText(),
                notesArea.getText(),
                LocalDate.now()
            )); 

            // Confirmation dialog and return to SprintEvaluationPanel
            JOptionPane.showMessageDialog(mainApp, "Sprint Evaluation submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } 
        catch (Exception ex) 
        {
            //Show Error if an error happens
            JOptionPane.showMessageDialog(mainApp, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Method to make all the text fields empty
    public void resetFields()
    {
        feelingsField.setText("");
        favoriteTaskField.setText("");
        proficientTaskField.setText("");
        dreadTaskField.setText("");
        potentialTaskField.setText("");
        notesArea.setText("");
    }
}