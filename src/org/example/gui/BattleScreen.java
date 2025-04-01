package org.example.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;

import org.example.game.Game;
import org.example.entities.Enemy;
import org.example.entities.characters.Character;

public class BattleScreen extends JPanel {

    private final Character player;
    private final Enemy enemy;
    private JLabel playerHealthLabel, playerManaLabel, enemyHealthLabel, enemyManaLabel;
    private JProgressBar playerHealthBar, playerManaBar, enemyHealthBar, enemyManaBar;
    private final MainFrame frame;

    public BattleScreen(MainFrame frame, Character player, Enemy enemy) {
        this.frame = frame;
        this.player = player;
        this.enemy = enemy;

        // layout and background
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.DARK_GRAY);

        // add title to the top of the screen
        JLabel title = new JLabel("Fight!");
        title.setFont(new Font("Georgia", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // main battle panel
        JPanel battlePanel = new JPanel(new BorderLayout());
        battlePanel.setBackground(Color.DARK_GRAY);

        // player and enemy panels
        JPanel playerPanel = createPlayerPanel();
        JPanel enemyPanel = createEnemyPanel();

        JPanel playerContainer = new JPanel(new BorderLayout());
        playerContainer.add(playerPanel, BorderLayout.CENTER);
        playerContainer.setBackground(Color.DARK_GRAY);
        playerContainer.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 20));

        JPanel enemyContainer = new JPanel(new BorderLayout());
        enemyContainer.add(enemyPanel, BorderLayout.CENTER);
        enemyContainer.setBackground(Color.DARK_GRAY);
        enemyContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 150));

        battlePanel.add(playerContainer, BorderLayout.WEST);
        battlePanel.add(enemyContainer, BorderLayout.EAST);

        // action buttons panel in the center
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(Color.DARK_GRAY);
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        // attack button
        JButton attackButton = new JButton("ATTACK");
        attackButton.addActionListener(e -> performAttack());
        attackButton.setFont(new Font("Georgia", Font.BOLD, 18));
        attackButton.setBackground(Color.BLACK);
        attackButton.setForeground(Color.WHITE);
        attackButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // use ability button
        JButton abilityButton = new JButton("USE ABILITY");
        abilityButton.addActionListener(e -> useAbility());
        abilityButton.setFont(new Font("Georgia", Font.BOLD, 18));
        abilityButton.setBackground(Color.BLACK);
        abilityButton.setForeground(Color.WHITE);
        abilityButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add buttons to the action panel
        actionPanel.add(Box.createVerticalGlue());
        actionPanel.add(attackButton);
        actionPanel.add(Box.createVerticalStrut(40));
        actionPanel.add(abilityButton);
        actionPanel.add(Box.createVerticalGlue());

        battlePanel.add(actionPanel, BorderLayout.CENTER);

        // add battle panel to the main panel
        add(battlePanel, BorderLayout.CENTER);
    }

    private JPanel createPlayerPanel() {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBackground(Color.DARK_GRAY);

        // player health bar
        playerHealthBar = new JProgressBar(0, player.getMaxHealth());
        playerHealthBar.setValue(player.getCurrentHealth());
        playerHealthBar.setStringPainted(true);
        playerHealthBar.setForeground(Color.BLUE);
        playerHealthBar.setBackground(Color.BLACK);
        playerHealthBar.setMaximumSize(new Dimension(400, 15));

        // player mana bar
        playerManaBar = new JProgressBar(0, player.getMaxMana());
        playerManaBar.setValue(player.getCurrentMana());
        playerManaBar.setStringPainted(true);
        playerManaBar.setForeground(Color.GREEN);
        playerManaBar.setBackground(Color.BLACK);
        playerManaBar.setMaximumSize(new Dimension(400, 15));

        // player stats are under the picture
        playerHealthLabel = new JLabel("Player Health: " + player.getCurrentHealth());
        playerManaLabel = new JLabel("Player Mana: " + player.getCurrentMana());
        playerHealthLabel.setForeground(Color.WHITE);
        playerManaLabel.setForeground(Color.WHITE);
        playerHealthLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        playerManaLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        playerHealthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerManaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // player image
        JLabel playerImage = new JLabel();
        try {
            String imagePath = "src/org/example/gui/images/characters/" + player.getName().replace(" ", "_").toLowerCase() + ".png";
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(400, 600, Image.SCALE_SMOOTH);
            playerImage.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            playerImage.setText("No Image");
            playerImage.setHorizontalAlignment(SwingConstants.CENTER);
        }

        Border greyBorder = BorderFactory.createLineBorder(Color.GRAY, 3);
        playerImage.setBorder(greyBorder);
        playerImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add components to player panel
        playerPanel.add(Box.createVerticalStrut(30));
        playerPanel.add(playerHealthBar);
        playerPanel.add(Box.createVerticalStrut(5));
        playerPanel.add(playerManaBar);
        playerPanel.add(Box.createVerticalStrut(10));
        playerPanel.add(playerImage);
        playerPanel.add(Box.createVerticalStrut(30));
        playerPanel.add(playerHealthLabel);
        playerPanel.add(Box.createVerticalStrut(5));
        playerPanel.add(playerManaLabel);

        return playerPanel;
    }

    private JPanel createEnemyPanel() {
        JPanel enemyPanel = new JPanel();
        enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));
        enemyPanel.setBackground(Color.DARK_GRAY);

        // enemy health bar
        enemyHealthBar = new JProgressBar(0, enemy.getMaxHealth());
        enemyHealthBar.setValue(enemy.getCurrentHealth());
        enemyHealthBar.setStringPainted(true);
        enemyHealthBar.setForeground(Color.RED);
        enemyHealthBar.setBackground(Color.BLACK);
        enemyHealthBar.setMaximumSize(new Dimension(400, 15));

        // enemy mana bar
        enemyManaBar = new JProgressBar(0, player.getMaxMana());
        enemyManaBar.setValue(player.getCurrentMana());
        enemyManaBar.setStringPainted(true);
        enemyManaBar.setForeground(Color.GREEN);
        enemyManaBar.setBackground(Color.BLACK);
        enemyManaBar.setMaximumSize(new Dimension(400, 15));

        // enemy stats are under the picture
        enemyHealthLabel = new JLabel("Enemy Health: " + enemy.getCurrentHealth());
        enemyManaLabel = new JLabel("Enemy Mana: " + enemy.getCurrentMana());
        enemyHealthLabel.setForeground(Color.WHITE);
        enemyManaLabel.setForeground(Color.WHITE);
        enemyHealthLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        enemyManaLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        enemyHealthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemyManaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // enemy image
        JLabel enemyImage = new JLabel();
        try {
            // images are of type "enemy_1 enemy_2 etc until 24, make random decision
            int random = new Random().nextInt(24) + 1;
            String imagePath = "src/org/example/gui/images/enemies/enemy_" + random + ".png";
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(400, 600, Image.SCALE_SMOOTH);
            enemyImage.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            enemyImage.setText("No Image");
            enemyImage.setHorizontalAlignment(SwingConstants.CENTER);
        }

        Border greyBorder = BorderFactory.createLineBorder(Color.GRAY, 3);
        enemyImage.setBorder(greyBorder);
        enemyImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add components to enemy panel
        enemyPanel.add(Box.createVerticalStrut(30));
        enemyPanel.add(enemyHealthBar);
        enemyPanel.add(Box.createVerticalStrut(5));
        enemyPanel.add(enemyManaBar);
        enemyPanel.add(Box.createVerticalStrut(10));
        enemyPanel.add(enemyImage);
        enemyPanel.add(Box.createVerticalStrut(30));
        enemyPanel.add(enemyHealthLabel);
        enemyPanel.add(Box.createVerticalStrut(5));
        enemyPanel.add(enemyManaLabel);

        return enemyPanel;
    }

    private void performAttack() {
        int damage = player.getDamage();

        // if player is a warrior, double the damage
        if (player.getProfession().equals("Warrior")) {
            damage *= 2;
            MessageUtils.showMessage("Warrior's strength caused double damage taken!", "Strength");
        }

        enemy.receiveDamage(damage);
        updateEnemyStats();

        if (enemy.getCurrentHealth() <= 0) {
            endBattle(true);
            return;
        }

        enemyTurn();
    }

    private void useAbility() {
        if (player.getAbilities().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No abilities available!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new AbilitySelection(player.getAbilities(), selectedAbility -> {
            player.useAbility(selectedAbility, enemy);
            updateEnemyStats();

            if (enemy.getCurrentHealth() <= 0) {
                endBattle(true);
                return;
            }

            enemyTurn();
            updatePlayerStats();
        });
    }

    private void enemyTurn() {
        if (enemy.getAbilities().isEmpty()) {
            MessageUtils.showMessage("Enemy has no abilities left!", "Enemy Ability");
            return;
        }

        if (player.getProfession().equals("Mage") && Math.random() < 0.5) {
            int damage = enemy.performAttack(player);
            player.setCurrentHealth(player.getCurrentHealth() + damage);
            damage /= 2;
            MessageUtils.showMessage("Mage's charisma reduced damage by 50%!", "Charisma");
            player.receiveDamage(damage);
        } else if (player.getProfession().equals("Rogue") && Math.random() < 0.5) {
            int damage = 0;
            MessageUtils.showMessage("Rogue's dexterity dodged the attack!", "Dexterity");
            player.receiveDamage(damage);
        } else {
            enemy.performAttack(player);
        }

        updatePlayerStats();
        updateEnemyStats();

        if (player.getCurrentHealth() <= 0) {
            endBattle(false);
        }

        checkPlayerDefeat();
    }

    private void updatePlayerStats() {
        playerHealthBar.setValue(player.getCurrentHealth());
        playerManaBar.setValue(player.getCurrentMana());
        playerHealthLabel.setText("Player Health: " + player.getCurrentHealth());
        playerManaLabel.setText("Player Mana: " + player.getCurrentMana());
    }

    private void updateEnemyStats() {
        enemyHealthBar.setValue(enemy.getCurrentHealth());
        enemyManaBar.setValue(enemy.getCurrentMana());
        enemyHealthLabel.setText("Enemy Health: " + enemy.getCurrentHealth());
        enemyManaLabel.setText("Enemy Mana: " + enemy.getCurrentMana());
    }

    private void endBattle(boolean playerWon) {
        if (playerWon) {
            int gainedExperience = 10 + new Random().nextInt(21);
            JOptionPane.showMessageDialog(this, "Enemy defeated! You gained " + gainedExperience + " experience.");
            player.addExperience(gainedExperience);

            if (player.getExperience() >= player.getLevelUpThresHold()) {
                player.levelUp();
                JOptionPane.showMessageDialog(this, "Congratulations! " + player.getName() + " leveled up to level " + player.getLevel() + "!");
            }

            player.increaseKilledEnemies();

            MessageUtils.showMessage("Your health has been doubled!", "Health Regeneration");
            player.regenerateHealth(player.getCurrentHealth()); // Heal after battle
            MessageUtils.showMessage("Your mana has been fully recharged!", "Mana Regeneration");
            player.regenerateMana(player.getMaxMana());
        } else {
            JOptionPane.showMessageDialog(this, "You have been defeated!", "Game Over", JOptionPane.ERROR_MESSAGE);
        }

        frame.switchPanel(new GameScreen(frame, Game.getInstance()));
    }

    private void checkPlayerDefeat() {
        if (player.getCurrentHealth() <= 0) {
            frame.switchPanel(new DefeatScreen(frame));
        }
    }
}
