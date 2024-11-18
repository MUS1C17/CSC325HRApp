package com.hrapp;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/*
 * This class is made for restricting input in the JFormattedTextField for the Hourly rate
 * Since hourly rate can have different ways of input such as:
 * 0.12, 12.23, 123.45, 1234.5, 12345 and so on. We need a way how to handle them and yet not allow 
 * ussers to put in any other characters in the field. Use this if you need to
 */
public class NumberDocumentFilter extends DocumentFilter 
{
    private static final Pattern PATTERN = Pattern.compile("^\\d{0,5}(\\.\\d{0,2})?$"); //regular expression

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException 
    {
        if (string == null) return;

        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.insert(offset, string);

        if (PATTERN.matcher(sb.toString()).matches()) 
        {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException 
    {
        if (text == null) return;


        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.replace(offset, offset + length, text);

        if (PATTERN.matcher(sb.toString()).matches()) 
        {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException 
    {
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.delete(offset, offset + length);

        if (PATTERN.matcher(sb.toString()).matches()) 
        {
            super.remove(fb, offset, length);
        }
    }
}