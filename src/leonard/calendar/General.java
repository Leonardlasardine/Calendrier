package leonard.calendar;

import javax.swing.*;
import java.awt.*;

public class General {

    public static void main(String[] args) {
        new Calendar();
        Window window = new Window();
    }

    public static void errorMessageText(String message, boolean exit) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, message);
        if (exit) {
            System.exit(1);
        }
    }
}
