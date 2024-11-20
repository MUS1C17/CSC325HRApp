package com.hrapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SprintEvaluationPanel extends JPanel {
    private AddSprintEvaluationPanel addSprintEvaluationPanel;
    private MainApplication mainApp;
    private JFrame parentFrame;

    // Constructor for SprintEvaluationPanel with parentFrame parameter
    public SprintEvaluationPanel(MainApplication mainApp) {
        this.mainApp = mainApp;  // Correctly assign the passed parentFrame
        setLayout(new BorderLayout());

        // Label for displaying message
        JLabel label = new JLabel("Sprint Evaluations", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Add button to open AddSprintEvaluationPanel
        JButton addButton = new JButton("Add Sprint Evaluation");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display AddSprintEvaluationPanel when button is clicked
                showAddSprintEvaluationPanel();
            }
        });

        // Adding button to the bottom of the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Show the AddSprintEvaluationPanel
    private void showAddSprintEvaluationPanel() {
        if (addSprintEvaluationPanel == null) {
            addSprintEvaluationPanel = new AddSprintEvaluationPanel(mainApp);
        }
        
        // Switch to the AddSprintEvaluationPanel
        this.removeAll();
        this.add(addSprintEvaluationPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
