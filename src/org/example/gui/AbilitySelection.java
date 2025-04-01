package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

import org.example.entities.Spell;

public class AbilitySelection extends JFrame {

    public AbilitySelection(List<Spell> abilities, Consumer<Spell> onAbilitySelected) {
        // frame title and dimensions
        setTitle("Select an Ability");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // title label for the ability selection frame
        JLabel title = new JLabel("Choose Your Ability");
        title.setFont(new Font("Georgia", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // panel to display all abilities
        JPanel abilitiesPanel = new JPanel();
        abilitiesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JScrollPane scrollPane = new JScrollPane(abilitiesPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // add cards for each ability
        for (Spell ability : abilities) {
            abilitiesPanel.add(createAbilityCard(ability, () -> {
                onAbilitySelected.accept(ability);
                dispose(); // close the frame after selection
            }));
        }

        // close button at the bottom of the frame
        JButton closeButton = new JButton("CANCEL");
        closeButton.addActionListener(e -> dispose());
        closeButton.setFont(new Font("Georgia", Font.BOLD, 16));
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createAbilityCard(Spell ability, Runnable onSelect) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 295));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        card.setBackground(Color.LIGHT_GRAY);

        // add an image based on the ability type
        String iconPath = switch (ability.getSpellType()) {
            case "Fire" -> "src/org/example/gui/images/Annie_Disintegrate.png";
            case "Ice" -> "src/org/example/gui/images/Lissandra_Iceborn_Subjugation.png";
            case "Earth" -> "src/org/example/gui/images/Taliyah_Unraveled_Earth.png";
            default -> null;
        };

        JLabel imageLabel;
        if (iconPath != null) {
            try {
                ImageIcon icon = new ImageIcon(iconPath);
                imageLabel = new JLabel(icon);
            } catch (Exception e) {
                imageLabel = new JLabel("Error Loading Image");
                System.err.println("Error loading image for " + ability.getSpellType() + ": " + e.getMessage());
            }
        } else {
            imageLabel = new JLabel("No Image Available");
        }

        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(imageLabel, BorderLayout.NORTH);

        // add details of the ability
        JPanel detailsPanel = new JPanel(new GridLayout(3, 2));
        detailsPanel.setBackground(Color.WHITE);

        detailsPanel.add(new JLabel("Name:"));
        detailsPanel.add(new JLabel(ability.getSpellType()));

        detailsPanel.add(new JLabel("Mana Cost:"));
        detailsPanel.add(new JLabel(String.valueOf(ability.getManaCost())));

        detailsPanel.add(new JLabel("Damage:"));
        detailsPanel.add(new JLabel(String.valueOf(ability.getDamage())));

        card.add(detailsPanel, BorderLayout.CENTER);

        // add a select button at the bottom of the card
        JButton selectButton = new JButton("SELECT");
        selectButton.addActionListener(e -> onSelect.run());
        selectButton.setFont(new Font("Georgia", Font.BOLD, 16));
        selectButton.setBackground(Color.BLACK);
        selectButton.setForeground(Color.WHITE);
        card.add(selectButton, BorderLayout.SOUTH);

        return card;
    }
}
