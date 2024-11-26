package com.hrapp;

import java.awt.Color;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import javafx.scene.control.DatePicker;

public class EmployeeFormValidator 
{
    //Borders for validation feedback
    private static Border defaultBorder = UIManager.getBorder("TextField.border");
    private static Border errorBorder = BorderFactory.createLineBorder(Color.RED, 2);
    /**
     * Adds a FocusListener to a JTextField to validate it when focus is lost.
     *
     * @param textField The JTextField to add the listener to.
     */
    public static void addFocusListenerToField(JTextField textField)
    {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e)
            {
                textField.setBorder(defaultBorder);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e)
            {
                validateFieldOnFocusLost(textField);
            }
        });
    }

    /**
     * Validates the JTextField when focus is lost.
     *
     * @param textField The JTextField to validate.
     */
    public static void validateFieldOnFocusLost(JTextField textField)
    {
        if(textField.getText().trim().isEmpty())
        {
            textField.setBorder(errorBorder);
        }
        else
        {
            textField.setBorder(defaultBorder);
        }
    }

    /**
     * Updates the state of the given JButton based on the validation of required fields.
     *
     * @param button       The JButton to enable or disable.
     * @param requiredFields An array of JTextFields that are required.
     */
    public static void updateButtonState(JButton button, JTextField[] requiredFields, DatePicker datePicker)
    {
        boolean allFieldsFilled = true;

        for(JTextField field : requiredFields)
        {
            if (field.getText().trim().isEmpty())
            {
                allFieldsFilled = false;
                break;
            }
        }

        //Additional checks, e.g., for phone number lenght and format
        if(allFieldsFilled)
        {
            JTextField phoneNumberField = null;
            for (JTextField field : requiredFields) 
            {
                String fieldName = field.getName();
                if ("phoneNumber".equals(fieldName)) 
                {
                    phoneNumberField = field;
                    break;
                }
            }
            if (phoneNumberField != null) 
            {
                allFieldsFilled = isFieldInteger(phoneNumberField) && phoneNumberField.getText().length() == 10;
            }
        }

        // Check if DatePicker has a value
        if (allFieldsFilled && datePicker != null) 
        {
            allFieldsFilled = datePicker.getValue() != null;
        }

        button.setEnabled(allFieldsFilled);
    }

    /**
     * Checks if the field contains an integer value.
     *
     * @param field The JTextField to check.
     * @return true if the field contains an integer, false otherwise.
     */
    public static boolean isFieldInteger(JTextField field) 
    {
        try 
        {
            Long.parseLong(field.getText());
            return true;
        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
    }

    /**
     * LimitedPlainDocument class for character limit.
     */
    public static class LimitedPlainDocument extends PlainDocument 
    {
        private final int limit;

        public LimitedPlainDocument(int limit)
        {
            if (limit <= 0) 
            {
                throw new IllegalArgumentException("Limit must be positive.");
            }
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException 
        {
            if (str == null)
                return;

            if ((getLength() + str.length()) <= limit) 
            {
                super.insertString(offset, str, attr);
            }
        }
    }

    /**
     * NumberDocumentFilter class for numeric fields.
     */
    public static class NumberDocumentFilter extends DocumentFilter 
    {
        private static final String DIGITS = "\\d*\\.?\\d{0,2}";
        private final Pattern pattern = Pattern.compile(DIGITS);

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException 
        {
            String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
            if (pattern.matcher(newText).matches()) 
            {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException 
        {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (pattern.matcher(newText).matches()) 
            {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
