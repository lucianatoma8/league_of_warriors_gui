package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

import static java.util.Map.entry;

import org.example.game.Game;
import org.example.game.Grid;
import org.example.entities.Account;
import org.example.entities.characters.Character;

public class CharacterSelectionScreen extends JPanel {

    private final Map<String, String> characterImagePaths;

    public CharacterSelectionScreen(MainFrame frame, Game game, Account account) {
        // ,ap character names to image paths
        characterImagePaths = Map.ofEntries(
                entry("Odysseus Prisco", "src/org/example/gui/images/characters/odysseus_prisco.png"),
                entry("Kameron Neppl", "src/org/example/gui/images/characters/kameron_neppl.png"),
                entry("Chlarimonde Markert", "src/org/example/gui/images/characters/chlarimonde_markert.png"),
                entry("Brisco Schaab", "src/org/example/gui/images/characters/brisco_schaab.png"),
                entry("Scarlett Gardon", "src/org/example/gui/images/characters/scarlett_gardon.png"),
                entry("Miyoko Fei", "src/org/example/gui/images/characters/miyoko_fei.png"),
                entry("Fujio Takeshita", "src/org/example/gui/images/characters/fujio_takeshita.png"),
                entry("Briareus Prestia", "src/org/example/gui/images/characters/briareus_prestia.png"),
                entry("Kame Oda", "src/org/example/gui/images/characters/kame_oda.png"),
                entry("Fedele Sama", "src/org/example/gui/images/characters/fedele_sama.png"),
                entry("Jannik Wriedt", "src/org/example/gui/images/characters/jannik_wriedt.png"),
                entry("Hisa Hano", "src/org/example/gui/images/characters/hisa_hano.png"),
                entry("Rina Zanin", "src/org/example/gui/images/characters/rina_zanin.png"),
                entry("Dyana Inselman", "src/org/example/gui/images/characters/dyana_inselman.png"),
                entry("Uysal Abdallah", "src/org/example/gui/images/characters/uysal_abdallah.png"),
                entry("Silvain Spilker", "src/org/example/gui/images/characters/silvain_spilker.png"),
                entry("Thibaut Goy", "src/org/example/gui/images/characters/thibaut_goy.png"),
                entry("Eyup Uner", "src/org/example/gui/images/characters/eyup_uner.png"),
                entry("Jaiden Kimmich", "src/org/example/gui/images/characters/jaiden_kimmich.png"),
                entry("Zain Eiden", "src/org/example/gui/images/characters/zain_eiden.png"),
                entry("Crocefissa Smeriglio", "src/org/example/gui/images/characters/crocefissa_smeriglio.png"),
                entry("Fiona Broussard", "src/org/example/gui/images/characters/fiona_broussard.png"),
                entry("Fjodora Schutzman", "src/org/example/gui/images/characters/fjodora_schutzman.png"),
                entry("Shigeru Uno", "src/org/example/gui/images/characters/shigeru_uno.png")
        );

        // layout
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.DARK_GRAY);

        // title label
        JLabel title = new JLabel("Select Your Character");
        title.setFont(new Font("Georgia", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // characters panel
        JPanel charactersPanel = new JPanel();
        charactersPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));
        charactersPanel.setBackground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(charactersPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // add character cards
        for (Character character : account.getCharacters()) {
            charactersPanel.add(createCharacterCard(character, frame, game));
        }

        // back button
        JButton backButton = new JButton("Main Menu");
        backButton.setFont(new Font("Georgia", Font.BOLD, 16));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener((ActionEvent e) -> {
            frame.switchPanel(new MainMenu(frame));
        });
        add(backButton, BorderLayout.SOUTH);
    }

    private JPanel createCharacterCard(Character character, MainFrame frame, Game game) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(410, 730));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setBackground(Color.LIGHT_GRAY);

        // load character image from the mapping
        JLabel imageLabel;
        try {
            String imagePath = characterImagePaths.getOrDefault(character.getName(), "src/org/example/gui/images/default.png");
            ImageIcon icon = new ImageIcon(imagePath);

            Image scaledImage = icon.getImage().getScaledInstance(450, 500, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            imageLabel = new JLabel("No Image");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        card.add(imageLabel, BorderLayout.NORTH);

        // add character details to the card
        JPanel detailsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Name: " + character.getName());
        JLabel professionLabel = new JLabel("Profession: " + character.getProfession());
        JLabel levelLabel = new JLabel("Level: " + character.getLevel());
        JLabel experienceLabel = new JLabel("Experience: " + character.getExperience());

        Font statsFont = new Font("Georgia", Font.BOLD, 14);

        nameLabel.setFont(statsFont);
        professionLabel.setFont(statsFont);
        levelLabel.setFont(statsFont);
        experienceLabel.setFont(statsFont);

        detailsPanel.add(nameLabel);
        detailsPanel.add(professionLabel);
        detailsPanel.add(levelLabel);
        detailsPanel.add(experienceLabel);

        card.add(detailsPanel, BorderLayout.CENTER);

        // add a select button to the card
        JButton selectButton = new JButton("SELECT");
        selectButton.setFont(new Font("Georgia", Font.BOLD, 16));
        selectButton.setBackground(Color.BLACK);
        selectButton.setForeground(Color.WHITE);
        selectButton.addActionListener(e -> {
            // set the selected character and switch to the game screen
            game.setCurrentCharacter(character);
            game.setGrid(Grid.generateGrid(character));
            frame.switchPanel(new GameScreen(frame, game));
        });
        card.add(selectButton, BorderLayout.SOUTH);

        return card;
    }
}
