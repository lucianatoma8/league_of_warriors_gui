package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel{

    public MainMenu(MainFrame mainFrame) {
        // layout and panel properties
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1000, 600));
        setBackground(Color.DARK_GRAY);

        // constraints for layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(20, 0, 20, 0);

        // title label
        JLabel title = new JLabel("League of Warriors");
        title.setPreferredSize(new Dimension(1000, 90));
        title.setFont(new Font("Georgia", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.NORTH);

        // play button
        JButton playButton = new JButton("Start Game");
        playButton.setFont(new Font("Georgia", Font.BOLD, 20));
        playButton.setPreferredSize(new Dimension(150, 50));
        playButton.addActionListener((ActionEvent e) -> {
            // switch to login screen when play button is clicked
            mainFrame.switchPanel(new LoginScreen(mainFrame));
        });

        // exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Georgia", Font.BOLD, 20));
        exitButton.setPreferredSize(new Dimension(150, 50));
        exitButton.addActionListener((ActionEvent e) -> {
            // exit the game when exit button is clicked
            System.exit(0);
        });

        // image label
        JLabel imageLabel = new JLabel();
        ImageIcon image = new ImageIcon("src/org/example/gui/images/league_of_warriors.png");
        Image scaledImage = image.getImage().getScaledInstance(1000, 580, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);
        imageLabel.setIcon(image);

        // add components to the panel
        add(title, constraints);
        add(imageLabel, constraints);
        add(playButton, constraints);
        add(exitButton, constraints);
    }
}
