package com.hrapp;

import java.awt.Font;
import javax.swing.JTextField;

/**
 * Custom TextField class extending JTextField.
 * Provides pre-configured text fields with a consistent font style and size.
 */
public class TextField extends JTextField 
{
    /**
     * Default constructor.
     * Creates an empty text field and sets its font to Arial, plain, size 16.
     */
    public TextField() 
    {
        super(); // Call to the parent JTextField constructor
        super.setFont(new Font("Arial", Font.PLAIN, 16)); // Set the font style and size
    }

    /**
     * Constructor with pre-filled text.
     * Creates a text field with the specified initial text and sets its font to Arial, plain, size 16.
     * 
     * @param preFilledText The initial text to display in the text field.
     */
    public TextField(String preFilledText) 
    {
        super(preFilledText); // Call to the parent JTextField constructor with initial text
        super.setFont(new Font("Arial", Font.PLAIN, 16)); // Set the font style and size
    }
}
