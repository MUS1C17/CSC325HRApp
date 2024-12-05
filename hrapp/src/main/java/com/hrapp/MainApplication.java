package com.hrapp;

import java.awt.CardLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * MainApplication class serves as the entry point and main frame for the HR application.
 * It manages different panels and uses a CardLayout for seamless navigation between them.
 */
public class MainApplication extends JFrame {
    // Properties to hold various application panels
    private HomePanel homePanel;
    private AddEmployeePanel addEmployeePanel;
    private AddJobPanel addJobPanel;
    private JPanel mainPanel;
    private EmployeeDetailPanel employeeDetailPanel;
    private UserSelectionPanel userSelectionPanel;
    private AddSurveySatisfactionPanel addSurveryPanel;
    private AddJobPositionPanel addJobPositionPanel;
    private EditEmployeePanel editEmployeePanel;
    private CardLayout cardLayout;
    private JobHistoryPanel jobHistoryPanel;
    private EditJobPanel editJobPanel;
    private EditJobPositionPanel editJobPositionPanel;
    private AddSprintEvaluationPanel addSprintEvaluationPanel;

    private Employee currentUser; // Current user of the application

    /**
     * Constructor to initialize the main application window.
     */
    public MainApplication() {
        // Set application title and icon
        setTitle("FRONTLINE HR App");
        setIconImage(new ImageIcon("resources\\FRONTLINE HR App Badge (Large).png").getImage());

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Dummy user role check
        boolean isManagerOrCEO = checkUserRole();

        // Initialize various panels
        employeeDetailPanel = new EmployeeDetailPanel(this);
        addEmployeePanel = new AddEmployeePanel(this);
        addJobPanel = new AddJobPanel(this);
        editJobPanel = new EditJobPanel(this);
        userSelectionPanel = new UserSelectionPanel(this);
        addSurveryPanel = new AddSurveySatisfactionPanel(this);
        addJobPositionPanel = new AddJobPositionPanel(this);
        editJobPositionPanel = new EditJobPositionPanel(this);
        addSprintEvaluationPanel = new AddSprintEvaluationPanel(this);

        // Add panels to the CardLayout
        mainPanel.add(userSelectionPanel, "UserSelectionPanel");
        mainPanel.add(employeeDetailPanel, "EmployeeDetailPanel");
        mainPanel.add(addEmployeePanel, "AddEmployeePanel");
        mainPanel.add(addJobPanel, "AddJobPanel");
        mainPanel.add(editJobPanel, "EditJobPanel");
        mainPanel.add(addSurveryPanel, "AddSurveySatisfactionPanel");
        mainPanel.add(addJobPositionPanel, "AddJobPositionPanel");
        mainPanel.add(editJobPositionPanel, "EditJobPositionPanel");
        mainPanel.add(addSprintEvaluationPanel, "AddSprintEvaluationPanel");

        // Add the main panel to the frame
        add(mainPanel);

        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 800);
        setLocationRelativeTo(null); // Center the window
    }

    /**
     * Dummy method to simulate user role checking. Replace with actual authentication logic.
     *
     * @return true if the user is a manager or CEO, false otherwise.
     */
    public boolean checkUserRole() {
        return true; // For demonstration purposes, always return true
    }

    /**
     * Override dispose to close resources when the application is shutting down.
     */
    @Override
    public void dispose() {
        homePanel.closeResources();
        super.dispose();
    }

    /**
     * Creates the HomePanel dynamically.
     *
     * @param isManagerOrCEO Indicates if the current user is a manager or CEO.
     */
    public void createHomePanel(boolean isManagerOrCEO) {
        homePanel = new HomePanel(isManagerOrCEO, this, this.getCurrentUser());
        mainPanel.add(homePanel, "HomePanel");
    }

    /**
     * Switch to EditEmployeePanel with a specific employee to edit.
     *
     * @param employee The employee to edit.
     */
    public void switchToEditEmployeePanel(Employee employee) {
        editEmployeePanel = new EditEmployeePanel(this, employee);
        mainPanel.add(editEmployeePanel, "EditEmployeePanel");
        switchToPanel("EditEmployeePanel");
    }

    /**
     * Shows the HomePanel.
     */
    public void showHomePanel() {
        homePanel = new HomePanel(true, this, this.getCurrentUser());
        mainPanel.add(homePanel, "HomePanel");
        switchToPanel("HomePanel");
    }

    /**
     * Switch to AddEmployeePanel and reset its fields.
     */
    public void switchToAddEmployeePanel(String panelName) {
        addEmployeePanel.resetFields();
        switchToPanel("AddEmployeePanel");
    }

    /**
     * Displays the details of a specific employee in EmployeeDetailPanel.
     *
     * @param employee The employee to display.
     */
    public void showEmployeeDetails(Employee employee) {
        employeeDetailPanel.setEmployee(employee);
        employeeDetailPanel.setDetailsButtonStatus(false); // Disable Details button
        switchToPanel("EmployeeDetailPanel");
    }

    // More methods to switch between panels and update specific panels
    public void showJobHistoryDetails(Employee employee) {
        employeeDetailPanel.setEmployee(employee);
        employeeDetailPanel.setJobHistoryButtonStatus(false);
        switchToPanel("EmployeeDetailPanel");
    }

    public void showSprintEvaluationDetails(Employee employee) {
        employeeDetailPanel.setEmployee(employee);
        employeeDetailPanel.setSprintEvaluationButton(false);
        switchToPanel("EmployeeDetailPanel");
    }

    public void refreshSprintEvaluations(Employee employee) {
        employeeDetailPanel.setEmployee(employee);
    }

    public void switchToAddJobPanel(Employee employee) {
        addJobPanel.resetFields();
        addJobPanel.setEmployee(employee);
        switchToPanel("AddJobPanel");
    }

    public void switchToAddJobPositionPanel() {
        addJobPositionPanel.resetFields();
        switchToPanel("AddJobPositionPanel");
    }

    public void switchToEditJobPostionPanel(JobPosition jobPosition) {
        editJobPositionPanel.setJobPosition(jobPosition);
        switchToPanel("EditJobPositionPanel");
    }

    public void switchToEditJobPanel(int jobID, Job job, Employee employee) {
        editJobPanel.setInformation(jobID, job, employee);
        switchToPanel("EditJobPanel");
    }

    public void switchToJobHistoryPanel() {
        employeeDetailPanel.refreshJobHistory();
        switchToPanel("EmployeeDetailPanel");
    }

    public void switchToAddSprintEvaluationPanel(Employee employee) {
        addSprintEvaluationPanel.setEmployee(employee);
        switchToPanel("AddSprintEvaluationPanel");
        addSprintEvaluationPanel.resetFields();
    }

    /**
     * Switches to a specified panel by its name.
     *
     * @param panelName The name of the panel to switch to.
     */
    public void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    // Getters and setters for HomePanel and current user
    public HomePanel getHomePanel() {
        return homePanel;
    }

    public void setCurrentUser(Employee user) {
        currentUser = user;
    }

    public Employee getCurrentUser() {
        return currentUser;
    }

    public int getCurrentUserID() {
        return currentUser.getEmployeeID();
    }

    /**
     * The main method to start the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApplication app = new MainApplication();
            app.setVisible(true);
        });
    }
}
