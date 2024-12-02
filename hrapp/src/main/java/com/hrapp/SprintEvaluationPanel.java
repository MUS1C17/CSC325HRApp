package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SprintEvaluationPanel extends JPanel {

    //Properties
    private MainApplication mainApp;
    private SprintEvaluationDAO sprintDAO;
    private Employee employee;
    private DefaultListModel listModel;
    private JList dateList;

    private Label feelingsField;
    private Label favoriteTaskField;
    private Label proficientTaskField;
    private Label dreadTaskField;
    private Label potentialTaskField;
    private Label notesArea;

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
        try
        {
            // Clear previous content and initialize the GUI
            removeAll(); 
            initUI();
            revalidate();
            repaint();
        }
        catch(SQLException e)
        {
             JOptionPane.showMessageDialog(mainApp, "Error Occured: " + 
             e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //This method initializes all the elements on the panel
    public void initUI() throws SQLException
    {
        // Initialize the list model for the sprint evaluations
        listModel = new DefaultListModel<>();

        //List with all SprintEvaluations for a given employee
        List<SprintEvaluation> sprintEvaluations = sprintDAO.getEmployeeSprintEvaluations(employee.getEmployeeID()); 

        //Loop through all the evaluations and add them to the list
        for(SprintEvaluation sprint : sprintEvaluations)
        {
            listModel.addElement(sprint);
        }

        // Clear existing components and set the layout
        removeAll();
        setLayout(new BorderLayout());

        if (listModel.isEmpty()) 
        {
            // If there are no evaluations, display a message
            displayNoEvaluationsMessage();
        } 
        else 
        {
            // if there are sprint evaluations, display them
            displayEvaluations();
        }

         // Create a button to add a new sprint evaluation
        JButton addButton = new Button("resources\\StartNewEvaluationButtons\\Start New Evaluation button (no hover).png", "resources\\StartNewEvaluationButtons\\Start New Evaluation button (hover).png");
        addButton.setVisible(mainApp.isCurrentUserAndSelectedEmployeeSame(employee));

        //Switch to AddSprintEvaluationPanel
        addButton.addActionListener(e -> mainApp.switchToAddSprintEvaluationPanel(employee));

        // Adding button to the bottom of the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(17, 59, 95));
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Refresh the panel and display updates
        revalidate();
        repaint();
    }

    // Method to display a message when no evaluations are available
    private void displayNoEvaluationsMessage() 
    {
        //Label with the message
        Label noEvaluationsLabel = new Label("No sprint evaluations have been submitted yet.");

        // Center the text horizontally
        noEvaluationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(noEvaluationsLabel, BorderLayout.CENTER);
    }

    // Method to create the information panel showing evaluation details
    private JPanel createInformationPanel() 
    {
        //Create a panel
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize labels
        feelingsField = new Label("");
        favoriteTaskField = new Label("");
        proficientTaskField = new Label("");
        dreadTaskField = new Label("");
        potentialTaskField = new Label("");
        notesArea = new Label("");

        // Add components to the panel
        panel.add(new Label("Feelings:"));
        panel.add(feelingsField);
        panel.add(new Label("Favorite Task(s):"));
        panel.add(favoriteTaskField);
        panel.add(new Label("Proficient Task(s):"));
        panel.add(proficientTaskField);
        panel.add(new Label("Dreaded Task(s):"));
        panel.add(dreadTaskField);
        panel.add(new Label("Potential Task(s):"));
        panel.add(potentialTaskField);
        panel.add(new Label("Notes:"));
        panel.add(notesArea);

        //Returns the panel
        return panel;
    }

     // Method to display the list of evaluations and the information panel
    private void displayEvaluations() 
    {
        // Create the information panel
        JPanel informationPanel = createInformationPanel();

        // Initialize the list component with the list model
        dateList = new JList<>(listModel);

        // Set a custom cell renderer to display the date of each evaluation
        dateList.setCellRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
            {
                // Use the default rendering
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof SprintEvaluation) 
                {
                    SprintEvaluation sprint = (SprintEvaluation) value;
                    // Set the text to display the submission date
                    setText("Sprint Evaluation from " + sprint.getSubmissionDateStringFormat());
                }
                return this; // Return the component
            }
        });

        // Set font
        dateList.setFont(new Font("Arial", Font.PLAIN, 16));

        // Display all rows
        dateList.setVisibleRowCount(listModel.getSize()); 

        // Add a listener to handle selection changes
        dateList.addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e) 
            {
                if (!e.getValueIsAdjusting()) 
                {
                    // When selection changes, update the information fields
                    SprintEvaluation selectedSprint = (SprintEvaluation) dateList.getSelectedValue();
                    updateInformationFields(selectedSprint);
                }
            }
        });

        // Select the first item by default
        dateList.setSelectedIndex(0);
        SprintEvaluation firstSprint = (SprintEvaluation) dateList.getSelectedValue();
        updateInformationFields(firstSprint);

         // Create a scroll pane for the list
        JScrollPane leftScrollPane = new JScrollPane(dateList);

        // Main panel that will hold the list and information panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel that holds the list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(leftScrollPane, BorderLayout.CENTER);

        // Add list panel and information panel to the main panel
        mainPanel.add(listPanel, BorderLayout.WEST);
        mainPanel.add(informationPanel, BorderLayout.CENTER);

        // Add the main panel to the center of this panel
        add(mainPanel, BorderLayout.CENTER);
    }

    // Method to update the information fields based on the selected evaluation
    private void updateInformationFields(SprintEvaluation sprintEvaluation) 
    {
        if (sprintEvaluation != null) 
        {
            feelingsField.setText(sprintEvaluation.getFeelings());
            favoriteTaskField.setText(sprintEvaluation.getFavoriteTask());
            proficientTaskField.setText(sprintEvaluation.getProficientTask());
            dreadTaskField.setText(sprintEvaluation.getDreadTask());
            potentialTaskField.setText(sprintEvaluation.getPotentialTask());
            notesArea.setText(sprintEvaluation.getNotes());
        } 
        else 
        {
            feelingsField.setText("");
            favoriteTaskField.setText("");
            proficientTaskField.setText("");
            dreadTaskField.setText("");
            potentialTaskField.setText("");
            notesArea.setText("");
        }
    }
}