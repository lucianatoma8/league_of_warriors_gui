package org.example.gui;

import javax.swing.*;

public class MessageUtils {
    public static void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
