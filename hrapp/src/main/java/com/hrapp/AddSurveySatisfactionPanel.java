package com.hrapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class AddSurveySatisfactionPanel extends JPanel
{
    //Properties 
    private MainApplication mainApp;
    private SurveySatisfactionDAO surveyDAO;
    private JButton addButton;

    private JTextArea favoriteAspect;
    private JTextArea additionalComments;
    private JComboBox<String> growthOpportunites;
    private JSlider communicationRating;
    private JSlider satisfactionLevel;

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

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Fill the Panel with label and TextFields

        //Favorite Aspect
        panel.add(new JLabel("What do you like most about working here?"));
        favoriteAspect = new JTextArea();
        //favoriteAspect.setDocument(new LimitedPlainDocument(250)); - THis is to limit the input. Uncomment in future
        panel.add(favoriteAspect);

        //Growth Opportunities
        panel.add(new JLabel("Do you feel you have opportunities for professional growth?"));
        growthOpportunites = new JComboBox<>(new String[]{"Yes", "No"});
        panel.add(growthOpportunites);

        // Communication Rating
        panel.add(new JLabel("How would you rate the communication within the company?"));
        communicationRating = new JSlider(-5, 5, 0);
        communicationRating.setPaintTrack(true); //Make the line of the slider visible
        communicationRating.setPaintTicks(true); //Make small lines above numbers visible
        communicationRating.setPaintLabels(true); //Make all the numbers visible (1-10)
        communicationRating.setMajorTickSpacing(1); //Make a line above each number by 1
        panel.add(communicationRating);

        //Satisfaction Level
        panel.add(new JLabel("How satisfied are you with your current role?"));
        satisfactionLevel = new JSlider(-5, 5, 0);
        satisfactionLevel.setPaintTrack(true); //Make the line of the slider visible
        satisfactionLevel.setPaintTicks(true); //Make small lines above numbers visible
        satisfactionLevel.setPaintLabels(true); //Make all the numbers visible (1-10)
        satisfactionLevel.setMajorTickSpacing(1); //Make a line above each number by 1
        panel.add(satisfactionLevel);

        //Additional Commments 
        panel.add(new JLabel("Any additional comments or suggestions?"));
        additionalComments = new JTextArea();
        //additionalComments.setDocument(new LimitedPlainDocument(250)); - THis is to limit the input. Uncomment in future
        panel.add(additionalComments);
        
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

        //Save information into database when Save button is clicked
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    //Create a new Survey Satisfaction object and add it to the database
                    surveyDAO.addSurveySatisfaction(new SurveySatisfaction
                    (
                        mainApp.getCurrentUserID(),   //Get employee ID
                        LocalDate.now(),            //Get Submission Date
                        satisfactionLevel.getValue(),
                        growthOpportunites.getSelectedItem().toString(),
                        favoriteAspect.getText(),
                        communicationRating.getValue(),
                        additionalComments.getText()
                    ));

                    //Swtich back to HomePanel
                    mainApp.switchToPanel("HomePanel");
                }
                catch(Exception error)
                {
                    // If error occured, show the pop up indicating the error message
                    JOptionPane.showMessageDialog(AddSurveySatisfactionPanel.this, 
                    "Error adding Satisfaction survey: " + error.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(addButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
