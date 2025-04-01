package org.example.gui;

import javax.swing.*;
import java.awt.*;

import org.example.game.Game;
import org.example.game.Grid;
import org.example.entities.characters.Character;

public class DefeatScreen extends JPanel {

    public DefeatScreen(MainFrame frame) {
        // layout and background
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        // add a huge "YOU LOST!" message
        JLabel defeatLabel = new JLabel("YOU LOST!");
        defeatLabel.setFont(new Font("Georgia", Font.BOLD, 72));
        defeatLabel.setForeground(Color.RED);
        defeatLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(defeatLabel, BorderLayout.NORTH);

        // add the player's stats and image
        JPanel statsPanel = createStatsPanel();
        add(statsPanel, BorderLayout.CENTER);

        // add buttons for replay and exit
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 20));
        buttonPanel.setBackground(Color.DARK_GRAY);

        JButton playAgainButton = createPlayAgainButton(frame);
        JButton exitButton = createExitButton();

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBackground(Color.DARK_GRAY);

        Character player = Game.getInstance().getCurrentCharacter();

        // add the player's image
        JLabel playerImage;
        try {
            String imagePath = "src/org/example/gui/images/characters/" + player.getName().replace(" ", "_").toLowerCase() + "_lost.png";
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(800, 530, Image.SCALE_SMOOTH);
            playerImage = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            playerImage = new JLabel("No Image Available");
            playerImage.setHorizontalAlignment(SwingConstants.CENTER);
        }
        playerImage.setHorizontalAlignment(SwingConstants.CENTER);
        playerImage.setVerticalAlignment(SwingConstants.CENTER);
        statsPanel.add(playerImage, BorderLayout.NORTH);

        // character stats
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.DARK_GRAY);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Name: " + player.getName());
        JLabel professionLabel = new JLabel("Profession: " + player.getProfession());
        JLabel levelLabel = new JLabel("Level: " + player.getLevel());
        JLabel experienceLabel = new JLabel("Experience: " + player.getExperience());
        JLabel killedEnemiesLabel = new JLabel("Killed Enemies: " + player.getKilledEnemies());

        // add all stats to the details panel
        for (JLabel label : new JLabel[]{nameLabel, professionLabel, levelLabel, experienceLabel, killedEnemiesLabel}) {
            label.setFont(new Font("Georgia", Font.BOLD, 18));
            label.setForeground(Color.PINK);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            detailsPanel.add(label);
            detailsPanel.add(Box.createVerticalStrut(10));
        }

        statsPanel.add(detailsPanel, BorderLayout.CENTER);

        return statsPanel;
    }

    private JButton createPlayAgainButton(MainFrame frame) {
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Georgia", Font.BOLD, 24));
        playAgainButton.setBackground(Color.BLACK);
        playAgainButton.setForeground(Color.WHITE);

        playAgainButton.addActionListener(e -> {
            // preserve the current character stats
            Game game = Game.getInstance();
            Character currentCharacter = game.getCurrentCharacter();

            // reset only character's health and mana
            currentCharacter.setCurrentHealth(currentCharacter.getMaxHealth());
            currentCharacter.setCurrentMana(currentCharacter.getMaxMana());

            // reset character's killed enemies
            currentCharacter.resetKilledEnemies();

            // reset the character's abilities if all were used
            if (currentCharacter.getAbilities().isEmpty()) {
                currentCharacter.updateAbilities();
            }

            // reset the character's abilities if there are less than 3 remaining
            if (currentCharacter.getAbilities().size() < 3) {
                currentCharacter.updateAbilities();
            }

            // generate a new grid for the character
            Grid newGrid = Grid.generateGrid(currentCharacter);
            game.setGrid(newGrid);

            // switch to the game screen with the updated game state
            frame.switchPanel(new GameScreen(frame, game));
        });

        return playAgainButton;
    }

    private JButton createExitButton() {
        JButton exitButton = new JButton("Exit Game");
        exitButton.setFont(new Font("Georgia", Font.BOLD, 24));
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.WHITE);

        exitButton.addActionListener(e -> System.exit(0)); // exit the game
        return exitButton;
    }
}
