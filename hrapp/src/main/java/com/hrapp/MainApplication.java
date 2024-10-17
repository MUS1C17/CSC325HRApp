package com.hrapp;

import java.awt.CardLayout;

import javax.swing.*;

public class MainApplication extends JFrame{
    private HomePanel homePanel;
    private JPanel mainPanel;
    private EmployeeDetailPanel employeeDetailPanel;
    private CardLayout cardLayout;


    public MainApplication() 
    {
        setTitle("HRApp");

        //Set up the CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        boolean isManagerOrCEO = checkUserRole();

        //Create an instance of HomePanel
        homePanel = new HomePanel(isManagerOrCEO, this);
        employeeDetailPanel = new EmployeeDetailPanel(this);

        // Add the HomePanel to the Frame
        mainPanel.add(homePanel, "HomePanel");
        mainPanel.add(employeeDetailPanel, "EmployeeDetailPanel");

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

    //This method is used to switch to the specified panel in the CardLayout 
    //using the name of the panel
    public void switchToPanel(String panelName) 
    {
        cardLayout.show(mainPanel, panelName);
    }

    //Passes the selected Employee to the detail panel and then switches to it
    public void showEmployeeDetails(Employee employee) 
    {
        employeeDetailPanel.setEmployee(employee);
        switchToPanel("EmployeeDetailPanel");
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
 