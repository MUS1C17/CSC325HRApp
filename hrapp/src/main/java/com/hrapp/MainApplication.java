package com.hrapp;

import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainApplication extends JFrame {
    //Properties
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
    private EmployeeTablePanel employeeTablePanel;

    private Employee currentUser;



    public MainApplication() 
    {
        setTitle("FRONTLINE HR App");
        setIconImage(new ImageIcon("resources\\FRONTLINE HR App Badge (Large).png").getImage());

        //Set up the CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //boolean isManagerOrCEO = checkUserRole();

        //Create an instance of HomePanel
        //homePanel = new HomePanel(isManagerOrCEO, this);
        employeeDetailPanel = new EmployeeDetailPanel(this);
        addEmployeePanel = new AddEmployeePanel(this);
        addJobPanel = new AddJobPanel(this);
        editJobPanel = new EditJobPanel(this);
        userSelectionPanel = new UserSelectionPanel(this);
        addSurveryPanel = new AddSurveySatisfactionPanel(this);
        addJobPositionPanel = new AddJobPositionPanel(this);
        editJobPositionPanel = new EditJobPositionPanel(this);
        addSprintEvaluationPanel = new AddSprintEvaluationPanel(this);
        employeeTablePanel = new EmployeeTablePanel(this);


        // Add the HomePanel to the Frame
        mainPanel.add(userSelectionPanel, "UserSelectionPanel");
        //mainPanel.add(homePanel, "HomePanel");
        mainPanel.add(employeeDetailPanel, "EmployeeDetailPanel");
        mainPanel.add(addEmployeePanel, "AddEmployeePanel");
        mainPanel.add(addJobPanel, "AddJobPanel");
        mainPanel.add(editJobPanel, "EditJobPanel");
        mainPanel.add(addSurveryPanel, "AddSurveySatisfactionPanel");
        mainPanel.add(addJobPositionPanel, "AddJobPositionPanel");
        mainPanel.add(editJobPositionPanel, "EditJobPositionPanel");
        mainPanel.add(addSprintEvaluationPanel, "AddSprintEvaluationPanel");
        

        //Add the mainPanel to the JFrame
        add(mainPanel);

        // Frame settings 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 800);
        setLocationRelativeTo(null); //Center the window
    }

    /**
     * Closes resources when the application is closing.
     */
    @Override
    public void dispose() 
    {
        homePanel.closeResources();
        super.dispose();
    }

    public void createHomePanel()
    {
        homePanel = new HomePanel(this, this.getCurrentUser());
        mainPanel.add(homePanel, "HomePanel");

    }

    //This method is used to switch to Edit Employee Panel to edit employee
    public void switchToEditEmployeePanel(Employee employee)
    {
        editEmployeePanel = new EditEmployeePanel(this, employee);
        mainPanel.add(editEmployeePanel, "EditEmployeePanel");
        switchToPanel("EditEmployeePanel");
    }

    public void showHomePanel()
    {
        homePanel = new HomePanel(this, this.getCurrentUser());
        mainPanel.add(homePanel, "HomePanel");
        switchToPanel("HomePanel");
    }

    //This method is used to switch to the specified panel in the CardLayout 
    //using the name of the panel
    public void switchToAddEmployeePanel(String panelName)
    {
        //Clears all the previous content and opens the panel
        addEmployeePanel.resetFields();
        switchToPanel("AddEmployeePanel"); 
    }

    //Passes the selected Employee to the detail panel and then switches to it
    public void showEmployeeDetails(Employee employee) 
    {
        employeeDetailPanel.setEmployee(employee);
        employeeDetailPanel.setDetailsButtonStatus(false); //Disable Details button
        switchToPanel("EmployeeDetailPanel");
    }

    //This method can only be used when pressing back button on the EditJobPanel & AddJobPanel.
    //It will disable JobHistory button
    public void showJobHistoryDetails(Employee employee)
    {
        employeeDetailPanel.setEmployee(employee);
        employeeDetailPanel.setJobHistoryButtonStatus(false); //Disable Job History button
        switchToPanel("EmployeeDetailPanel");
    }

    //
    public void showSprintEvaluationDetails(Employee employee)
    {
        employeeDetailPanel.setEmployee(employee);
        employeeDetailPanel.setSprintEvaluationButton(false);
        switchToPanel("EmployeeDetailPanel");
    }

    //
    public void refreshSprintEvaluations(Employee employee)
    {
        employeeDetailPanel.setEmployee(employee);
    }



    // Passes employeeID to the job panel to display matching jobs.
    public void switchToAddJobPanel(Employee employee)
    {
        addJobPanel.resetFields();
        addJobPanel.setEmployee(employee);
        switchToPanel("AddJobPanel");
    }

    //Method to switch to AddJobPositionPanel
    public void switchToAddJobPositionPanel()
    {
        //Reset fields on the panel
        addJobPositionPanel.resetFields();

        //Show the panel
        switchToPanel("AddJobPositionPanel");
    }

    //Method to switch to EditJobPostionPanel
    public void switchToEditJobPostionPanel(JobPosition jobPosition)
    {
        editJobPositionPanel.setJobPosition(jobPosition);
        switchToPanel("EditJobPositionPanel");
    }

    // Passes jobID to edit panel so the panel knows which job to update in the database.
    public void switchToEditJobPanel(int jobID, Job job, Employee employee)
    {
        //editJobPanel.resetFields();
        editJobPanel.setInformation(jobID, job, employee);

        switchToPanel("EditJobPanel");
    }
    
    public void switchToJobHistoryPanel()
    {
        employeeDetailPanel.refreshJobHistory();
        switchToPanel("EmployeeDetailPanel");
    }

    //This method is used to switch to AddSprintEvaluationPanel
    public void switchToAddSprintEvaluationPanel(Employee employee)
    {

        //Set employee on the add panel to then get employee's id
        addSprintEvaluationPanel.setEmployee(employee);

        switchToPanel("AddSprintEvaluationPanel"); 

        //Reset all the text fields
        addSprintEvaluationPanel.resetFields();
    }

    public void switchToPanel(String panelName) 
    {
        cardLayout.show(mainPanel, panelName);
    }

    //Getter for HomePanel
    public HomePanel getHomePanel() 
    {
        return homePanel;
    }

    public void setCurrentUser(Employee user)
    {
        this.currentUser = user;
    }

    public Employee getCurrentUser()
    {
        return currentUser;
    }

    public int getCurrentUserID()
    {
        return currentUser.getEmployeeID();
    }

    /*
     * Check if the current user that is logged in is a manager
     * If yes, return true, otherwise return false
     */
    public boolean isCurrentUserManager() 
    {
        //Check for null at first to prevent NullPointerException
        if(currentUser != null && currentUser.getIsManager() == 1)
        {
            return true;
        }
        return false;
    }

    /*
     * Check if the current user that is logged in is CEO
     * If yes, return true, otherwise return false
     */
    public boolean isCurrentUserCEO()
    {
        //Check for null at first to prevent NullPointerException
        if(currentUser != null && currentUser.getIsCEO() == 1)
        {
            return true;
        }

        return false;
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            MainApplication app = new MainApplication();
            app.setVisible(true);
        });
    }
}
 