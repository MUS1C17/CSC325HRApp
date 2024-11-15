package com.hrapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SprintEvaluationPanel extends JPanel {

    private JList<String> evaluationList;
    private JTextArea feelingsTextArea;
    private JTextArea favoriteTaskTextArea;
    private JTextArea proficientTaskTextArea;
    private JTextArea dreadTaskTextArea;
    private JTextArea potentialTaskTextArea;
    private JTextArea notesTextArea;
    private JButton addEvaluationButton;
    private SprintEvaluationDAO sprintEvaluationDAO;
    private int employeeID;

    public SprintEvaluationPanel(int employeeID) {
        this.employeeID = employeeID;
        setLayout(new BorderLayout());

        try {
            sprintEvaluationDAO = new SprintEvaluationDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Left panel with JList of evaluation dates
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        JLabel listLabel = new JLabel("Sprint Evaluations:");
        leftPanel.add(listLabel, BorderLayout.NORTH);

        evaluationList = new JList<>();
        evaluationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        evaluationList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedDate = evaluationList.getSelectedValue();
                loadEvaluationDetails(selectedDate);
            }
        });
        leftPanel.add(new JScrollPane(evaluationList), BorderLayout.CENTER);

        // Right panel with the details of the selected evaluation
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(6, 2));

        feelingsTextArea = new JTextArea(5, 20);
        favoriteTaskTextArea = new JTextArea(5, 20);
        proficientTaskTextArea = new JTextArea(5, 20);
        dreadTaskTextArea = new JTextArea(5, 20);
        potentialTaskTextArea = new JTextArea(5, 20);
        notesTextArea = new JTextArea(5, 20);

        rightPanel.add(new JLabel("Feelings:"));
        rightPanel.add(new JScrollPane(feelingsTextArea));
        rightPanel.add(new JLabel("Favorite Task:"));
        rightPanel.add(new JScrollPane(favoriteTaskTextArea));
        rightPanel.add(new JLabel("Proficient Task:"));
        rightPanel.add(new JScrollPane(proficientTaskTextArea));
        rightPanel.add(new JLabel("Dread Task:"));
        rightPanel.add(new JScrollPane(dreadTaskTextArea));
        rightPanel.add(new JLabel("Potential Task:"));
        rightPanel.add(new JScrollPane(potentialTaskTextArea));
        rightPanel.add(new JLabel("Notes:"));
        rightPanel.add(new JScrollPane(notesTextArea));

        addEvaluationButton = new JButton("Add Evaluation");
        addEvaluationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add the logic for submitting an evaluation
                addSprintEvaluation();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addEvaluationButton);

        // Adding left and right panels to the main panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.3);

        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadEvaluations();
    }

    private void loadEvaluations() {
        try {
            List<String> evaluations = sprintEvaluationDAO.getEvaluationsForEmployee(employeeID);
            evaluationList.setListData(evaluations.toArray(new String[0]));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadEvaluationDetails(String selectedDate) {
        // Load the details of the selected evaluation
        try {
            SprintEvaluation evaluation = sprintEvaluationDAO.getEvaluationByDate(employeeID, selectedDate);
            if (evaluation != null) {
                feelingsTextArea.setText(evaluation.getFeelings());
                favoriteTaskTextArea.setText(evaluation.getFavoriteTask());
                proficientTaskTextArea.setText(evaluation.getProficientTask());
                dreadTaskTextArea.setText(evaluation.getDreadTask());
                potentialTaskTextArea.setText(evaluation.getPotentialTask());
                notesTextArea.setText(evaluation.getNotes());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addSprintEvaluation() {
        // Logic to add a new evaluation
        String feelings = feelingsTextArea.getText();
        String favoriteTask = favoriteTaskTextArea.getText();
        String proficientTask = proficientTaskTextArea.getText();
        String dreadTask = dreadTaskTextArea.getText();
        String potentialTask = potentialTaskTextArea.getText();
        String notes = notesTextArea.getText();

        SprintEvaluation newEvaluation = new SprintEvaluation(employeeID, feelings, favoriteTask, proficientTask,
                dreadTask, potentialTask, notes);
        
        try {
            sprintEvaluationDAO.addSprintEvaluation(newEvaluation);
            loadEvaluations(); // Refresh the evaluation list
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
