package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.GridLayout;

/*
 * This is the panel where you add job positions:
 * -name of position and soft/hard skills required for position
 * -button to reset fields if desired
 */
public class AddJobPositionPanel extends JPanel
{
    //Properties
    private MainApplication mainApp;
    private JobPositionDAO jobPositionDAO;
    private JButton addButton;

    private JTextField jobPositionName;
    private JComboBox<String> hardSkillOne;
    private JComboBox<String> hardSkillTwo;
    private JComboBox<String> softSkillOne;
    private JComboBox<String> softSkillTwo;

    //Constructor 
    public AddJobPositionPanel(MainApplication mainApp)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        initUI();

        try
        {
            jobPositionDAO = new JobPositionDAO();
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
    }

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

        topPanel.add(new Label("Add Position", 32, Color.WHITE));

        add(topPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Arrays to use for ComboBoxes
        String[] hardSkills = new String[]{null, "Java", "Python", "C#"};
        String[] softSkills = new String[]{null, "Leadership", "Teamwork", "Time Management"};

        //Fill the Panel with labels and TextFields

        //JobPositionName
        panel.add(new Label("Position Name:"));
        jobPositionName = new TextField();
        panel.add(jobPositionName);

        //Hard Skill 1
        panel.add(new Label("Main Hard Skill:"));
        hardSkillOne = new JComboBox(hardSkills);
        panel.add(hardSkillOne);
 
        //Hard Skill 2
        panel.add(new Label("Secondary Hard Skill:"));
        hardSkillTwo = new JComboBox(hardSkills);
        panel.add(hardSkillTwo);
    
        //Soft Skill 1
        panel.add(new Label("Main Soft Skill:"));
        softSkillOne = new JComboBox(softSkills);
        panel.add(softSkillOne);
    
        //Soft Skill 2
        panel.add(new Label("Secondary Soft Skill:"));
        softSkillTwo = new JComboBox(softSkills);
        panel.add(softSkillTwo);

        //Add panel to the AddEmployeePanel
        add(panel, BorderLayout.CENTER);

        //Button Panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(17, 59, 95));

        //Back button
        JButton backButton = new Button("resources\\BackButtons\\Back button (no hover).png", "resources\\BackButtons\\Back button (hover).png");
        backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));

        //Add button
        addButton = new Button("resources\\AddButtons\\Add button (no hover).png", "resources\\AddButtons\\Add button (hover).png");
        addButton.setDisabledIcon(new ImageIcon("resources\\AddButtons\\Add button (disabled).png"));
        addButton.setEnabled(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    jobPositionDAO.addJobPosition(new JobPosition(
                        jobPositionName.getText(),
                        hardSkillOne.getSelectedItem() != null ? hardSkillOne.getSelectedItem().toString() : null,             //Pass null if hard skill one status isn't specified
                        hardSkillTwo.getSelectedItem() != null ? hardSkillTwo.getSelectedItem().toString() : null,             //Pass null if hard skill two status isn't specified
                        softSkillOne.getSelectedItem() != null ? softSkillOne.getSelectedItem().toString() : null,             //Pass null if soft skill one status isn't specified
                        softSkillTwo.getSelectedItem() != null ? softSkillTwo.getSelectedItem().toString() : null            //Pass null if soft skill two status isn't specified
                    ));

                    //Refresh the employee table in HomePanel
                    mainApp.getHomePanel().refreshJobPositionTable();

                    //Switch back to HomePanel
                    mainApp.switchToPanel("HomePanel");
                    
                }
                catch(Exception error)
                {
                    JOptionPane.showMessageDialog(AddJobPositionPanel.this, 
                        "Error adding Job Position: " + error.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                        error.printStackTrace();
                }
            }
        });

        //Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(addButton);
        
        //Add Button Panel to the main panel
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    //Method to reset all the fields back to empty
    public void resetFields()
    {
        jobPositionName.setText("");
        hardSkillOne.setSelectedIndex(0);
        hardSkillTwo.setSelectedIndex(0);
        softSkillOne.setSelectedIndex(0);
        softSkillTwo.setSelectedIndex(0);
    }
}
