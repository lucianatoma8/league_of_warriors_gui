package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel currentPanel; // holds the current panel being displayed

    public MainFrame() {
        setTitle("League of Warriors");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        showMainMenu();
    }

    public void showMainMenu() {
        switchPanel(new MainMenu(this));
    }

    public void switchPanel(JPanel panel) {
        if (currentPanel != null) {
            remove(currentPanel);
        }

        currentPanel = panel;
        add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
