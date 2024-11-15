package com.hrapp;

public EmployeeDetailPanel(MainApplication mainApp) {
    this.mainApp = mainApp;
    setLayout(new BorderLayout());

    try {
        employeeDAO = new EmployeeDAO();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    initUI();
}

public void setEmployee(Employee employee) {
    this.employee = employee;
    detailsPanel.setEmployee(employee);
    // Pass employee data to other panels
    jobHistoryPanel.setEmployee(employee);
    
    // Check if employee is null before passing it to sprintEvaluationPanel
    if (employee != null) {
        sprintEvaluationPanel.setEmployee(employee);
    }
}

private void initUI() {
    // Left navigation panel
    navigationPanel = new JPanel();
    navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
    navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Optional padding

    // Initialize buttons
    detailsButton = new JButton(new ImageIcon("resources\\DescriptionToggles\\DetailsButtons\\Details button (no hover) (1).png"));
    jobHistoryButton = new JButton(new ImageIcon("resources\\DescriptionToggles\\JobHistoryButtons\\Job History button (no hover) (1).png"));
    sprintEvaluationButton = new JButton(new ImageIcon("resources\\DescriptionToggles\\SprintEvaluationButtons\\Sprint Evaluations button (no hover) (1).png"));
    backButton = new JButton(new ImageIcon("resources\\BackButtons\\Back button (no hover).png"));

    // Set toggled look for each button
    detailsButton.setDisabledIcon(new ImageIcon("resources\\DescriptionToggles\\DetailsButtons\\Details button (toggled) (1).png"));
    jobHistoryButton.setDisabledIcon(new ImageIcon("resources\\DescriptionToggles\\JobHistoryButtons\\Job History button (toggled) (1).png"));
    sprintEvaluationButton.setDisabledIcon(new ImageIcon("resources\\DescriptionToggles\\SprintEvaluationButtons\\Sprint Evaluations button (toggled) (1).png"));

    // Create a vertical separator
    JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
    separator.setPreferredSize(new Dimension(1, 0)); // 1 pixel wide, height adjusts automatically

    // Container panel to hold navigationPanel and separator
    JPanel leftPanelContainer = new JPanel(new BorderLayout());
    leftPanelContainer.add(navigationPanel, BorderLayout.WEST);
    leftPanelContainer.add(separator, BorderLayout.EAST);

    // Dimension for the buttons
    Dimension buttonSize = new Dimension(200, 76);
    detailsButton.setMaximumSize(buttonSize);
    jobHistoryButton.setMaximumSize(buttonSize);
    sprintEvaluationButton.setMaximumSize(buttonSize);

    // No border or background on the buttons
    detailsButton.setBorderPainted(false);
    detailsButton.setContentAreaFilled(false);
    jobHistoryButton.setBorderPainted(false);
    jobHistoryButton.setContentAreaFilled(false);
    sprintEvaluationButton.setBorderPainted(false);
    sprintEvaluationButton.setContentAreaFilled(false);
    backButton.setBorderPainted(false);
    backButton.setContentAreaFilled(false);

    navigationPanel.add(detailsButton);
    navigationPanel.add(jobHistoryButton);
    navigationPanel.add(sprintEvaluationButton);

    // Add vertical glue to push the backButton to the bottom
    navigationPanel.add(Box.createVerticalGlue());

    // Add the backButton at the bottom
    navigationPanel.add(backButton);

    // Content panel with CardLayout
    contentCardLayout = new CardLayout();
    contentPanel = new JPanel(contentCardLayout);

    // Initialize sub-panels
    detailsPanel = new DetailsPanel(mainApp);
    jobHistoryPanel = new JobHistoryPanel();
    sprintEvaluationPanel = new SprintEvaluationPanel();  // Initialize the panel without passing employeeID initially

    // Add sub-panels to content panel
    contentPanel.add(detailsPanel, "DetailsPanel");
    contentPanel.add(jobHistoryPanel, "JobHistoryPanel");
    contentPanel.add(sprintEvaluationPanel, "SprintEvaluationPanel");

    // Action of Details button
    detailsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            contentCardLayout.show(contentPanel, "DetailsPanel");
            detailsButton.setEnabled(false);            // Disable details button
            jobHistoryButton.setEnabled(true);          // Enable Job History button
            sprintEvaluationButton.setEnabled(true);    // Enable Sprint Evaluation button
        }
    });

    // Action of Job History button
    jobHistoryButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            contentCardLayout.show(contentPanel, "JobHistoryPanel");
            detailsButton.setEnabled(true);             // Enable details button
            jobHistoryButton.setEnabled(false);         // Disable job history button
            sprintEvaluationButton.setEnabled(true);    // Enable sprint evaluation button
        }
    });

    // Action of Sprint Evaluation button
    sprintEvaluationButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            contentCardLayout.show(contentPanel, "SprintEvaluationPanel");
            detailsButton.setEnabled(true);             // Enable details button
            jobHistoryButton.setEnabled(true);          // Enable job history button
            sprintEvaluationButton.setEnabled(false);   // Disable sprint evaluation button
        }
    });

    // Action of Back button
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            contentCardLayout.show(contentPanel, "DetailsPanel");
            mainApp.switchToPanel("HomePanel");
            detailsButton.setEnabled(true);
            jobHistoryButton.setEnabled(true);
            sprintEvaluationButton.setEnabled(true);
        }
    });

    // Add components to EmployeeDetailPanel
    add(leftPanelContainer, BorderLayout.WEST);
    add(contentPanel, BorderLayout.CENTER);

    // Show the details panel by default
    contentCardLayout.show(contentPanel, "DetailsPanel");
}
