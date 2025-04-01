package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import org.example.game.*;
import org.example.entities.Enemy;

public class GameScreen extends JPanel {

    private final Game game;
    private JButton[][] gridButtons;
    private final JLabel healthLabel;
    private final JLabel manaLabel;
    private final JLabel levelLabel;
    private final JLabel xpLabel;

    // icons for each cell type
    private final ImageIcon playerIcon = new ImageIcon("src/org/example/gui/images/player.png");
    private final ImageIcon enemyIcon = new ImageIcon("src/org/example/gui/images/enemy.png");
    private final ImageIcon sanctuaryIcon = new ImageIcon("src/org/example/gui/images/sanctuary.png");
    private final ImageIcon portalIcon = new ImageIcon("src/org/example/gui/images/portal.png");
    private final ImageIcon voidIcon = new ImageIcon("src/org/example/gui/images/question-circle.png");

    public GameScreen(MainFrame frame, Game game) {
        this.game = game;

        // initialize stats labels
        healthLabel = new JLabel("Health: " + game.getCurrentCharacter().getCurrentHealth());
        healthLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        manaLabel = new JLabel("Mana: " + game.getCurrentCharacter().getCurrentMana());
        manaLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        levelLabel = new JLabel("Level: " + game.getCurrentCharacter().getLevel());
        levelLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        xpLabel = new JLabel("Experience: " + game.getCurrentCharacter().getExperience());
        xpLabel.setFont(new Font("Georgia", Font.BOLD, 14));

        // main layout
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.DARK_GRAY);

        // add panels to the screen
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createLeftPanel(), BorderLayout.WEST);
        add(createGridPanel(), BorderLayout.CENTER);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.DARK_GRAY);

        JLabel titleLabel = new JLabel("League of Warriors");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(200, 600));
        leftPanel.setBackground(Color.DARK_GRAY);

        // add direction buttons and stats panel
        leftPanel.add(createDirectionPanel(), BorderLayout.NORTH);
        leftPanel.add(createStatsPanel(), BorderLayout.SOUTH);

        return leftPanel;
    }

    private JPanel createDirectionPanel() {
        JPanel directionPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        directionPanel.setBackground(Color.DARK_GRAY);
        directionPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        directionPanel.add(createDirectionButton("NORTH", "N"));
        directionPanel.add(createDirectionButton("SOUTH", "S"));
        directionPanel.add(createDirectionButton("EAST", "E"));
        directionPanel.add(createDirectionButton("WEST", "W"));

        return directionPanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 20, 10));
        statsPanel.setBackground(Color.DARK_GRAY);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        statsPanel.add(levelLabel);
        statsPanel.add(xpLabel);
        statsPanel.add(healthLabel);
        statsPanel.add(manaLabel);

        levelLabel.setForeground(Color.WHITE);
        xpLabel.setForeground(Color.WHITE);
        healthLabel.setForeground(Color.WHITE);
        manaLabel.setForeground(Color.WHITE);

        return statsPanel;
    }

    private JPanel createGridPanel() {
        Grid grid = game.getGrid();
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(grid.getHeight(), grid.getWidth()));
        gridPanel.setBackground(Color.LIGHT_GRAY);

        // create buttons for each cell in the grid
        gridButtons = new JButton[grid.getHeight()][grid.getWidth()];
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                JButton button = getjButton(grid, i, j);
                gridButtons[i][j] = button;
                gridPanel.add(button);
            }
        }

        return gridPanel;
    }

    private JButton getjButton(Grid grid, int i, int j) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));

        // determine the color and icon for the button based on the cell type
        if (grid.get(i).get(j) == grid.getCurrentCell()) {
            button.setBackground(Color.BLUE);
            button.setIcon(playerIcon);
        } else if (!grid.get(i).get(j).isVisited()) {
            button.setBackground(Color.GRAY);
            button.setIcon(voidIcon);
        } else {
            switch (grid.get(i).get(j).getType()) {
                case ENEMY -> {
                    button.setBackground(Color.RED);
                    button.setIcon(enemyIcon);
                }
                case SANCTUARY -> {
                    button.setBackground(Color.GREEN);
                    button.setIcon(sanctuaryIcon);
                }
                case PORTAL -> {
                    button.setBackground(Color.YELLOW);
                    button.setIcon(portalIcon);
                }
                default -> button.setBackground(Color.BLACK);
            }
        }

        button.setEnabled(false);
        return button;
    }

    private JButton createDirectionButton(String label, String direction) {
        JButton button = new JButton(label);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Georgia", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.addActionListener(e -> movePlayer(direction));
        return button;
    }

    private void movePlayer(String direction) {
        try {
            switch (direction) {
                case "N" -> game.getGrid().goNorth();
                case "S" -> game.getGrid().goSouth();
                case "E" -> game.getGrid().goEast();
                case "W" -> game.getGrid().goWest();
            }
            handleCellInteraction();
            updateGrid();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCellInteraction() {
        Cell currentCell = game.getGrid().getCurrentCell();

        switch (currentCell.getType()) {
            case ENEMY -> {
                JButton button = gridButtons[currentCell.getY()][currentCell.getX()];
                button.setBackground(Color.RED);
                button.setIcon(enemyIcon);

                JOptionPane.showMessageDialog(this, "You encountered an enemy! Prepare for battle...", "Enemy", JOptionPane.INFORMATION_MESSAGE);
                startBattle(new Enemy(30 + game.getCurrentCharacter().getLevel() * 10,
                        50 + game.getCurrentCharacter().getLevel() * 5));
                currentCell.setType(CellEntityType.VOID);
                currentCell.setVisited();
            }
            case SANCTUARY -> {
                JButton button = gridButtons[currentCell.getY()][currentCell.getX()];
                button.setBackground(Color.GREEN);
                button.setIcon(sanctuaryIcon);

                Random random = new Random();
                int healthBoost = 20 + random.nextInt(31); // random health boost between 20 and 50
                int manaBoost = 10 + random.nextInt(21); // random mana boost between 10 and 30

                JOptionPane.showMessageDialog(this, "You found a sanctuary. Restoring health and mana...", "Sanctuary", JOptionPane.INFORMATION_MESSAGE);
                game.getCurrentCharacter().regenerateHealth(healthBoost);
                game.getCurrentCharacter().regenerateMana(manaBoost);

                JOptionPane.showMessageDialog(this,
                        "Health regenerated by " + healthBoost + "!" +
                                "\nMana regenerated by " + manaBoost + "!",
                        "Sanctuary", JOptionPane.INFORMATION_MESSAGE);

                currentCell.setType(CellEntityType.VOID);
                currentCell.setVisited();
            }
            case PORTAL -> {
                JButton button = gridButtons[currentCell.getY()][currentCell.getX()];
                button.setBackground(Color.YELLOW);
                button.setIcon(portalIcon);

                JOptionPane.showMessageDialog(this, "You found a portal. Moving to the next level...", "Portal", JOptionPane.INFORMATION_MESSAGE);
                int experienceBonus = game.getCurrentCharacter().getLevel() * 5;
                game.getCurrentCharacter().addExperience(experienceBonus);
                JOptionPane.showMessageDialog(this, "You gained " + experienceBonus +
                        " experience for completing level " + game.getCurrentCharacter().getLevel() + "!", "Portal", JOptionPane.INFORMATION_MESSAGE);

                int currentExperience = game.getCurrentCharacter().getExperience();
                int experienceNeededForLevelUp = game.getCurrentCharacter().getLevelUpThresHold();

                if (currentExperience < experienceNeededForLevelUp) {
                    // add the remaining experience needed for level up
                    int additionalExperience = experienceNeededForLevelUp - currentExperience;
                    game.getCurrentCharacter().addExperience(additionalExperience);
                    JOptionPane.showMessageDialog(this, "An additional " + additionalExperience +
                            " experience was added to level up!");
                }

                currentCell.setType(CellEntityType.VOID);
                currentCell.setVisited();

                // generate a new grid for the next level
                Grid newGrid = Grid.generateGrid(game.getCurrentCharacter());
                for (int i = 0; i < newGrid.getHeight(); i++) {
                    for (int j = 0; j < newGrid.getWidth(); j++) {
                        Cell cell = newGrid.get(i).get(j);
                        cell.setUnvisited();
                    }
                }
                game.setGrid(newGrid);
                recreateGrid(newGrid.getHeight(), newGrid.getWidth());

                JOptionPane.showMessageDialog(this, "New game grid generated! Prepare for the next level.");
            }
            case VOID -> {
                return;
            }
            default -> JOptionPane.showMessageDialog(this, "This cell is the player's current position.");
        }

        updateGrid();
    }

    private void startBattle(Enemy enemy) {
        MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
        frame.switchPanel(new BattleScreen(frame, game.getCurrentCharacter(), enemy));
    }

    private void updateGrid() {
        Grid grid = game.getGrid();
        int height = grid.getHeight();
        int width = grid.getWidth();

        if (gridButtons.length != height || gridButtons[0].length != width) {
            recreateGrid(height, width);
            return;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JButton button = gridButtons[i][j];
                Cell cell = grid.get(i).get(j);

                if (cell == grid.getCurrentCell()) {
                    button.setBackground(Color.BLUE);
                    button.setIcon(playerIcon);
                } else if (cell.isVisited()) {
                    switch (cell.getType()) {
                        case ENEMY -> {
                            button.setBackground(Color.RED);
                            button.setIcon(enemyIcon);
                        }
                        case SANCTUARY -> {
                            button.setBackground(Color.GREEN);
                            button.setIcon(sanctuaryIcon);
                        }
                        case PORTAL -> {
                            button.setBackground(Color.YELLOW);
                            button.setIcon(portalIcon);
                        }
                        default -> {
                            button.setBackground(Color.BLACK);
                            button.setIcon(null);
                        }
                    }
                } else {
                    button.setBackground(Color.GRAY);
                    button.setIcon(voidIcon);
                }
            }
        }

        // updated stats
        healthLabel.setText("Health: " + game.getCurrentCharacter().getCurrentHealth());
        healthLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        manaLabel.setText("Mana: " + game.getCurrentCharacter().getCurrentMana());
        manaLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        levelLabel.setText("Level: " + game.getCurrentCharacter().getLevel());
        levelLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        xpLabel.setText("Experience: " + game.getCurrentCharacter().getExperience());
        xpLabel.setFont(new Font("Georgia", Font.BOLD, 14));

        revalidate();
        repaint();
    }

    private void recreateGrid(int newHeight, int newWidth) {
        removeAll();
        setLayout(new BorderLayout());

        // re-add panels
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createLeftPanel(), BorderLayout.WEST);
        add(createGridPanel(), BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
