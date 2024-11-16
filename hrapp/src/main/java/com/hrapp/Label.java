package com.hrapp;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Label extends JLabel { //TODO: Set up try-catch with font formatting for new font Montserrat and variants
    public Label(String text) {
        super("<html>" + text + "</html>");
        try {
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("resources\\fonts\\Montserrat\\static\\Montserrat-Bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            super.setFont(montserrat.deriveFont(Font.PLAIN, 18));
        } catch(IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
    public Label(String text, int fontSize) {
        super("<html>" + text + "</html>");
        try {
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("resources\\fonts\\Montserrat\\static\\Montserrat-Bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            super.setFont(montserrat.deriveFont(Font.PLAIN, fontSize));
        } catch(IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
    public Label(String text, int fontSize, Color color) {
        super("<html>" + text + "</html>");
        try {
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("resources\\fonts\\Montserrat\\static\\Montserrat-Bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            super.setFont(montserrat.deriveFont(Font.PLAIN, fontSize));
            super.setForeground(color);
        } catch(IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
