package Calendar;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JLabel;

public class Label extends JLabel {
    private Color background;

    private static final long serialVersionUID = 1L;

    public Label(String text, Color background, Color foreground, boolean btn) { //Function to make labels of days of week
        this.background = background;
        setText(text);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("Roboto", Font.BOLD, 20));
        setOpaque(true);
        setBackground(background);
        setForeground(foreground);
        if (btn) setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

}