package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddSurveySatisfactionPanel extends JPanel
{
    //Properties 
    private MainApplication mainApp;
    private SurveySatisfactionDAO surveyDAO;
    private JButton addButton;

    private JTextField favoriteAspect;
    private JComboBox<String> growthOpportunites;

    //Constructor 
    public AddSurveySatisfactionPanel(MainApplication mainApp)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        initUI();

        try
        {
            surveyDAO = new SurveySatisfactionDAO();
        }
        catch(SQLException error)
        {
            error.getMessage();
        }
    }

    public void initUI()
    {
        String[] yesOrNo = new String[]{"Yes", "No"};

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Fill the Panel with label and TextFields

        //Favorite Aspect
        panel.add(new JLabel("What do you like most about working here?"));
        favoriteAspect = new JTextField();
        //favoriteAspect.setDocument(new LimitedPlainDocument(250))    - Limit the input
        panel.add(favoriteAspect);

        //Growth Opportunities
        panel.add(new JLabel("Do you feel you have opportunities for professional growth?"));
        growthOpportunites = new JComboBox<>(yesOrNo);
        panel.add(growthOpportunites);

        //Add panel to the AddSurveySatisfactionPanel
        add(panel, BorderLayout.CENTER);

        //Button panel at the bottom
        JPanel buttonPanel = new JPanel();

         //Back button
         JButton backButton = new JButton("Back");
         backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));

         //Add Button
         addButton = new JButton("Add");
         addButton.setEnabled(true);

         buttonPanel.add(backButton);
         buttonPanel.add(addButton);

         add(buttonPanel, BorderLayout.SOUTH);

    }
}
