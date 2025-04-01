package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import org.example.game.Game;
import org.example.entities.Account;

public class LoginScreen extends JPanel {

    private final Game game;

    public LoginScreen(MainFrame frame) {
        game = Game.getInstance(); // get the singleton instance of the game

        // layout and background
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.DARK_GRAY);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15, 15, 15, 15);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // email label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(emailLabel, constraints);

        // email text field
        JTextField emailField = new JTextField(25);
        emailField.setForeground(Color.WHITE);
        emailField.setBackground(Color.BLACK);
        emailField.setFont(new Font("Georgia", Font.PLAIN, 20));
        constraints.gridx = 1;
        add(emailField, constraints);

        // password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(passwordLabel, constraints);

        // password field
        JPasswordField passwordField = new JPasswordField(25);
        passwordField.setForeground(Color.WHITE);
        passwordField.setBackground(Color.BLACK);
        passwordField.setFont(new Font("Georgia", Font.PLAIN, 20));
        constraints.gridx = 1;
        add(passwordField, constraints);

        // login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Georgia", Font.BOLD, 20));
        loginButton.setPreferredSize(new Dimension(20, 50));
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(loginButton, constraints);

        loginButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText(); // get the email from the text field
            String password = new String(passwordField.getPassword()); // get the password from the password field

            // authenticate user credentials
            Account account = game.authenticate(email, password);
            if (account != null) {
                // successful login
                JOptionPane.showMessageDialog(
                        null,
                        "Welcome, " + account.getInformation().getName() + "!",
                        "Login Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );
                // switch to the character selection screen
                frame.switchPanel(new CharacterSelectionScreen(frame, game, account));
            } else {
                // failed login
                JOptionPane.showMessageDialog(
                        null,
                        "Invalid email or password",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
