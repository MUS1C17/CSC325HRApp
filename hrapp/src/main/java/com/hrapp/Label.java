package com.hrapp;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

public class Label extends JLabel 
{
    // Constructor to create a label with text and default font size.
    public Label(String text) 
    {
        // Set the text of the label and wrap it in HTML format for multi-line support.
        super("<html>" + text + "</html>");
        
        try 
        {
            // Load the Montserrat font from the specified path.
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("resources\\fonts\\Montserrat\\static\\Montserrat-Bold.ttf"));
            
            // Get the local graphics environment to register the font.
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            
            // Set the font of the label to Montserrat with plain style and size 18.
            super.setFont(montserrat.deriveFont(Font.PLAIN, 18));
        } 
        catch(IOException | FontFormatException e) 
        {
            // Print stack trace if there's an issue loading the font.
            e.printStackTrace();
        }
    }

    // Constructor to create a label with text and a custom font size.
    public Label(String text, int fontSize) 
    {
        // Set the text of the label and wrap it in HTML format for multi-line support.
        super("<html>" + text + "</html>");
        
        try 
        {
            // Load the Montserrat font from the specified path.
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("resources\\fonts\\Montserrat\\static\\Montserrat-Bold.ttf"));
            
            // Get the local graphics environment to register the font.
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            
            // Set the font of the label to Montserrat with plain style and the specified font size.
            super.setFont(montserrat.deriveFont(Font.PLAIN, fontSize));
        } 
        catch(IOException | FontFormatException e) 
        {
            // Print stack trace if there's an issue loading the font.
            e.printStackTrace();
        }
    }

    // Constructor to create a label with text, custom font size, and color.
    public Label(String text, int fontSize, Color color) 
    {
        // Set the text of the label and wrap it in HTML format for multi-line support.
        super("<html>" + text + "</html>");
        
        try 
        {
            // Load the Montserrat font from the specified path.
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("resources\\fonts\\Montserrat\\static\\Montserrat-Bold.ttf"));
            
            // Get the local graphics environment to register the font.
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            
            // Set the font of the label to Montserrat with plain style and the specified font size.
            super.setFont(montserrat.deriveFont(Font.PLAIN, fontSize));
            
            // Set the text color of the label.
            super.setForeground(color);
        } 
        catch(IOException | FontFormatException e) 
        {
            // Print stack trace if there's an issue loading the font.
            e.printStackTrace();
        }
    }
}
