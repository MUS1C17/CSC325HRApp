package com.hrapp;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.awt.GridLayout;

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
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Arrays to use for ComboBoxes
        String[] hardSkills = new String[]{null, "Java", "Python", "C#"};
        String[] softSkills = new String[]{null, "Leadership", "Teamwork", "Time Management"};

        //Fill the Panel with labels and TextFields

        //JobPositionName
        panel.add(new Label("Position Name:"));
        jobPositionName = new JTextField();
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

        //Back button
        JButton backButton = new JButton(new ImageIcon("resources\\BackButtons\\Back button (no hover).png"));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));

        //Hover behavior for Back button
        backButton.addMouseListener(new MouseListener() 
        {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setIcon(new ImageIcon("resources\\BackButtons\\Back button (hover).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setIcon(new ImageIcon("resources\\BackButtons\\Back button (no hover).png"));
            }
        });

        //Add Employee button
        addButton = new JButton(new ImageIcon("resources\\AddButtons\\Add button (no hover).png"));
        addButton.setDisabledIcon(new ImageIcon("resources\\AddButtons\\Add button (disabled).png"));
        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);
        addButton.setEnabled(true);

        // Hover behavior for Add Employee button
        addButton.addMouseListener(new MouseListener() 
        {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                addButton.setIcon(new ImageIcon("resources\\AddButtons\\Add button (hover).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) 
            {
                addButton.setIcon(new ImageIcon("resources\\AddButtons\\Add button (no hover).png"));
            }
        });

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
}
