package com.hrapp;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainApplication extends JFrame{
    private HomePanel homePanel;

    public MainApplication() 
    {
        setTitle("HRApp");

        boolean isManagerOrCEO = checkUserRole();

        //Create an instance of HomePanel
        homePanel = new HomePanel(isManagerOrCEO);

        // Add the HomePanel to the Frame
        add(homePanel);

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

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            MainApplication app = new MainApplication();
            app.setVisible(true);
        });
    }
}
 