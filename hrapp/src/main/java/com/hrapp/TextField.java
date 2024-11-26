package com.hrapp;
import java.awt.Font;

import javax.swing.JTextField;

public class TextField extends JTextField 
{
    public TextField() 
    {
        super();
        super.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    public TextField(String preFilledText) 
    {
        super(preFilledText);
        super.setFont(new Font("Arial", Font.PLAIN, 16));
    }
}
