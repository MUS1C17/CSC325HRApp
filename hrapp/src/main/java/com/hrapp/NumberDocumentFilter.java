package com.hrapp;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * This class restricts input in a JFormattedTextField to ensure that only valid numeric 
 * input is allowed. It enforces a format for hourly rates such as:
 * - Up to 5 digits before the decimal
 * - Up to 2 digits after the decimal
 * - Examples of valid input: 0.12, 12.23, 123.45, 1234.5, 12345
 * - No other characters are allowed
 */
public class NumberDocumentFilter extends DocumentFilter {
    // Regular expression to validate the input format
    private static final Pattern PATTERN = Pattern.compile("^\\d{0,5}(\\.\\d{0,2})?$"); 

    /**
     * Handles inserting text into the document. Validates the resulting text against the regex.
     *
     * @param fb The FilterBypass object to manage the operation
     * @param offset The offset in the document to insert the text
     * @param string The string to insert
     * @param attr The attributes to associate with the inserted content
     * @throws BadLocationException If the insertion fails
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        // If the input string is null, do nothing
        if (string == null) return;

        // Build the resulting text after the insertion
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.insert(offset, string);

        // Validate the new text; only insert if it matches the pattern
        if (PATTERN.matcher(sb.toString()).matches()) {
            super.insertString(fb, offset, string, attr);
        }
    }

    /**
     * Handles replacing text in the document. Validates the resulting text against the regex.
     *
     * @param fb The FilterBypass object to manage the operation
     * @param offset The offset in the document to replace text
     * @param length The length of text to replace
     * @param text The new text to insert
     * @param attrs The attributes to associate with the new content
     * @throws BadLocationException If the replacement fails
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        // If the replacement text is null, do nothing
        if (text == null) return;

        // Build the resulting text after the replacement
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.replace(offset, offset + length, text);

        // Validate the new text; only replace if it matches the pattern
        if (PATTERN.matcher(sb.toString()).matches()) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    /**
     * Handles removing text from the document. Validates the resulting text against the regex.
     *
     * @param fb The FilterBypass object to manage the operation
     * @param offset The offset in the document where the text starts
     * @param length The length of text to remove
     * @throws BadLocationException If the removal fails
     */
    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        // Build the resulting text after the removal
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.delete(offset, offset + length);

        // Validate the new text; only remove if it matches the pattern
        if (PATTERN.matcher(sb.toString()).matches()) {
            super.remove(fb, offset, length);
        }
    }
}
