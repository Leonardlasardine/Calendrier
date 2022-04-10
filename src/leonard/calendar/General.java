package leonard.calendar;

import javax.swing.*;
import java.awt.*;

public class General {

    public static void main(String[] args) {
        Window window = new Window();
    }

    public static void errorMessageText(String message) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, message);

    }

    public static void errorMessageText(String message, int exitCode) {
        errorMessageText(message);
        System.exit(exitCode);
    }
}
