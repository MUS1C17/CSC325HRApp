package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
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
    private JButton saveButton;

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
        // Top Panel containing logo and page title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 137, 216));

        // Add logo and page title
        JLabel logo = new JLabel(new ImageIcon("resources\\FRONTLINE_HR_Color_Version__1_-removebg-preview.png"));
        topPanel.add(logo);
        topPanel.add(Box.createHorizontalStrut(50));

        topPanel.add(new Label("Job Satisfaction Reflection", 32, Color.WHITE));

        add(topPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Fill the Panel with label and TextFields

        //Favorite Aspect
        panel.add(new Label("What do you like most about working here?"));
        favoriteAspect = new JTextArea();
        //favoriteAspect.setDocument(new LimitedPlainDocument(250)); - THis is to limit the input. Uncomment in future
        panel.add(favoriteAspect);

        //Growth Opportunities
        panel.add(new Label("Do you feel you have opportunities for professional growth?"));
        growthOpportunites = new JComboBox<>(new String[]{"Yes", "No"});
        panel.add(growthOpportunites);

        // Communication Rating
        panel.add(new Label("How would you rate the communication within the company?"));
        communicationRating = new JSlider(-5, 5, 0);
        communicationRating.setPaintTrack(true); //Make the line of the slider visible
        communicationRating.setPaintTicks(true); //Make small lines above numbers visible
        communicationRating.setPaintLabels(true); //Make all the numbers visible (1-10)
        communicationRating.setMajorTickSpacing(1); //Make a line above each number by 1
        panel.add(communicationRating);

        //Satisfaction Level
        panel.add(new Label("How satisfied are you with your current role?"));
        satisfactionLevel = new JSlider(-5, 5, 0);
        satisfactionLevel.setPaintTrack(true); //Make the line of the slider visible
        satisfactionLevel.setPaintTicks(true); //Make small lines above numbers visible
        satisfactionLevel.setPaintLabels(true); //Make all the numbers visible (1-10)
        satisfactionLevel.setMajorTickSpacing(1); //Make a line above each number by 1
        panel.add(satisfactionLevel);

        //Additional Commments 
        panel.add(new Label("Any additional comments or suggestions?"));
        additionalComments = new JTextArea();
        //additionalComments.setDocument(new LimitedPlainDocument(250)); - THis is to limit the input. Uncomment in future
        panel.add(additionalComments);
        
        //Add panel to the AddSurveySatisfactionPanel
        add(panel, BorderLayout.CENTER);

        //Button panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(17, 59, 95));

        //Back button
        JButton backButton = new JButton(new ImageIcon("resources\\BackButtons\\Back button (no hover).png"));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> mainApp.switchToPanel("HomePanel"));

        backButton.addMouseListener(new MouseListener() {
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
                backButton.setIcon(new ImageIcon("resources\\\\BackButtons\\\\Back button (no hover).png"));
            }
        });

        //Add Button
        saveButton = new JButton(new ImageIcon("resources\\SaveButtons\\Save button (no hover).png"));
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setEnabled(true);

        saveButton.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setIcon(new ImageIcon("resources\\SaveButtons\\Save button (hover).png"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setIcon(new ImageIcon("resources\\SaveButtons\\Save button (no hover).png"));
            }
        });

        //Save information into database when Save button is clicked
        saveButton.addActionListener(new ActionListener(){
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
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
