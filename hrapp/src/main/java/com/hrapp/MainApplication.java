package com.hrapp;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainApplication extends JFrame{
    //Properties
    private HomePanel homePanel;
    private AddEmployeePanel addEmployeePanel;
    private JPanel mainPanel;
    private EmployeeDetailPanel employeeDetailPanel;
    private UserSelectionPanel userSelectionPanel;
    private AddSurveySatisfactionPanel addSurveryPanel;
    private CardLayout cardLayout;

    private Employee currentUser;



    public MainApplication() 
    {
        setTitle("HRApp");

        //Set up the CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        boolean isManagerOrCEO = checkUserRole();

        //Create an instance of HomePanel
        //homePanel = new HomePanel(isManagerOrCEO, this);
        employeeDetailPanel = new EmployeeDetailPanel(this);
        addEmployeePanel = new AddEmployeePanel(this);
        userSelectionPanel = new UserSelectionPanel(this);
        addSurveryPanel = new AddSurveySatisfactionPanel(this);

        // Add the HomePanel to the Frame
        mainPanel.add(userSelectionPanel, "UserSelectionPanel");
        //mainPanel.add(homePanel, "HomePanel");
        mainPanel.add(employeeDetailPanel, "EmployeeDetailPanel");
        mainPanel.add(addEmployeePanel, "AddEmployeePanel");
        mainPanel.add(addSurveryPanel, "AddSurveySatisfactionPanel");
        

        //Add the mainPanel to the JFrame
        add(mainPanel);

        // Frame settings 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
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

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            MainApplication app = new MainApplication();
            app.setVisible(true);
        });
    }
}
 