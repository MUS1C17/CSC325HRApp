package com.hrapp;

import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainApplication extends JFrame{
    //Properties
    private HomePanel homePanel;
    private AddEmployeePanel addEmployeePanel;
    private AddJobPanel addJobPanel;
    private JPanel mainPanel;
    private EmployeeDetailPanel employeeDetailPanel;
    private UserSelectionPanel userSelectionPanel;
    private AddSurveySatisfactionPanel addSurveryPanel;
    private CardLayout cardLayout;
    private JobHistoryPanel jobHistoryPanel;
    private EditJobPanel editJobPanel;

    private Employee currentUser;



    public MainApplication() 
    {
        setTitle("FRONTLINE HR App");
        setIconImage(new ImageIcon("resources\\FRONTLINE HR App Badge (Large).png").getImage());

        //Set up the CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        boolean isManagerOrCEO = checkUserRole();

        //Create an instance of HomePanel
        //homePanel = new HomePanel(isManagerOrCEO, this);
        employeeDetailPanel = new EmployeeDetailPanel(this);
        addEmployeePanel = new AddEmployeePanel(this);
        addJobPanel = new AddJobPanel(this);
        editJobPanel = new EditJobPanel(this);
        userSelectionPanel = new UserSelectionPanel(this);
        addSurveryPanel = new AddSurveySatisfactionPanel(this);


        // Add the HomePanel to the Frame
        mainPanel.add(userSelectionPanel, "UserSelectionPanel");
        //mainPanel.add(homePanel, "HomePanel");
        mainPanel.add(employeeDetailPanel, "EmployeeDetailPanel");
        mainPanel.add(addEmployeePanel, "AddEmployeePanel");
        mainPanel.add(addJobPanel, "AddJobPanel");
        mainPanel.add(editJobPanel, "EditJobPanel");
        mainPanel.add(addSurveryPanel, "AddSurveySatisfactionPanel");

        

        //Add the mainPanel to the JFrame
        add(mainPanel);

        // Frame settings 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 800);
        setLocationRelativeTo(null); //Center the window
    }

    /**
     * Dummy method to simulate role checking.
     * Replace this with actual authentication logic.
     */
    private boolean checkUserRole() 
    {
        // For demonstration, return true
        return true;
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

    public void createHomePanel(boolean isManagerOrCEO)
    {
        homePanel = new HomePanel(isManagerOrCEO, this, this.getCurrentUser());
        mainPanel.add(homePanel, "HomePanel");
    }

    public void showHomePanel()
    {
        homePanel = new HomePanel(true, this, this.getCurrentUser());
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

    // Passes employeeID to the job panel to display matching jobs.
    public void switchToAddJobPanel(Employee employee)
    {
        addJobPanel.resetFields();
        addJobPanel.setEmployee(employee);
        switchToPanel("AddJobPanel");
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
        currentUser = user;
    }

    public Employee getCurrentUser()
    {
        return currentUser;
    }

    public int getCurrentUserID()
    {
        return currentUser.getEmployeeID();
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
 