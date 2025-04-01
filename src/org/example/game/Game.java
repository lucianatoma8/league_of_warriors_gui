package org.example.game;

import org.example.entities.Account;
import org.example.entities.Enemy;
import org.example.entities.JsonInput;
import org.example.entities.characters.Character;
import org.example.exceptions.ImpossibleMove;
import org.example.exceptions.InvalidCommandException;
import org.example.factories.CharacterFactory;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    // singleton instance
    private static Game instance;

    private final ArrayList<Account> accounts;
    private Grid grid;
    private Character currentCharacter;

    // private constructor to prevent instantiation
    private Game() {
        accounts = JsonInput.deserializeAccounts();
    }

    // public static method to get the singleton instance
    public static Game getInstance() {
        // lazy initialization
        if (instance == null) {
            instance = new Game(); // create a new instance only if it doesn't exist
        }
        return instance;
    }

    public void setCurrentCharacter(Character character) {
        currentCharacter = character;
    }

    public Character getCurrentCharacter() {
        return currentCharacter;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Grid getGrid() {
        return grid;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Choose an option: ");
                System.out.println("1. Start New Game");
                System.out.println("2. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> {
                        System.out.println("Welcome to League of Warriors! Starting a new game...");
                        startNewGame(scanner);
                    }
                    case 2 -> {
                        System.out.println("Thank you for playing. Goodbye!");
                        return;
                    }
                    default -> throw new InvalidCommandException("Invalid option. Please choose 1 or 2.");
                }
            } catch (InvalidCommandException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void startNewGame(Scanner scanner) {
        Account account = authenticate(scanner);
        if (account == null) {
            return;
        }

        System.out.println("Welcome, " + account.getInformation().getName() + "!");
        currentCharacter = selectCharacter(account, scanner);

        // reset the player's health and mana to full before starting a new game
        currentCharacter.setCurrentHealth(currentCharacter.getMaxHealth());
        currentCharacter.setCurrentMana(currentCharacter.getMaxMana());
        System.out.println("Your health and mana have been fully recharged!");

        grid = Grid.generateGrid(currentCharacter);
        System.out.println("Game grid generated!");
        grid.displayGrid();

        while (true) {
            try {
                System.out.println("What would you like to do?");
                System.out.println("1. Move");
                System.out.println("2. Show Stats");
                System.out.println("3. Exit to Main Menu");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> move(scanner);
                    case 2 -> System.out.println(currentCharacter);
                    case 3 -> {
                        System.out.println("Returning to the main menu...");
                        return;
                    }
                    default -> throw new InvalidCommandException("Invalid option. Please choose 1, 2, or 3.");
                }

                handleCellInteraction();

                // end the game if the character dies
                if (currentCharacter.getCurrentHealth() <= 0) {
                    System.out.println("Game Over! You have been defeated.");
                    break;
                }

                // end the game if the player reaches the portal
                if (grid.getCurrentCell().getType() == CellEntityType.PORTAL) {
                    System.out.println("Congratulations! You completed the level!");
                    break;
                }
            } catch (InvalidCommandException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private Account authenticate(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter your email: ");
                String email = scanner.next();
                System.out.print("Enter your password: ");
                String password = scanner.next();

                for (Account account : accounts) {
                    if (account.getInformation().getCredentials().validate(email, password)) {
                        return account;
                    }
                }
                System.out.println("Invalid credentials. Try again.");
            } catch (Exception e) {
                System.out.println("Authentication error: " + e.getMessage());
            }
        }
    }

    private Character selectCharacter(Account account, Scanner scanner) {
        ArrayList<Character> characters = account.getCharacters();
        int choice;
        while (true) {
            try {
                System.out.println("Select a character:");
                for (int i = 0; i < characters.size(); i++) {
                    System.out.println((i + 1) + ". " + characters.get(i));
                }
                choice = scanner.nextInt();
                if (choice < 1 || choice > characters.size()) {
                    throw new InvalidCommandException("Invalid character choice. Try again.");
                }
                Character selected = characters.get(choice - 1);
                return CharacterFactory.createCharacter(
                        selected.getProfession(), selected.getName(), selected.getExperience(), selected.getLevel()
                );
            } catch (InvalidCommandException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error while selecting character: " + e.getMessage());
            }
        }
    }

    private void move(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Choose direction: N (North), S (South), E (East), W (West)");
                String direction = scanner.next().toUpperCase();

                switch (direction) {
                    case "N" -> grid.goNorth();
                    case "S" -> grid.goSouth();
                    case "E" -> grid.goEast();
                    case "W" -> grid.goWest();
                    default -> throw new InvalidCommandException("Invalid direction. Please choose N, S, E, or W.");
                }
                grid.displayGrid();
                return;
            } catch (ImpossibleMove e) {
                System.out.println("Movement error: " + e.getMessage());
            } catch (InvalidCommandException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void handleCellInteraction() {
        Cell currentCell = grid.getCurrentCell();
        switch (currentCell.getType()) {
            case ENEMY -> {
                startBattle(new Enemy(30, 50));
                currentCell.setType(CellEntityType.VOID);
                currentCell.setVisited();
            }
            case SANCTUARY -> {
                Random random = new Random();
                int healthBoost = 20 + random.nextInt(31); // random health boost between 20 and 50
                int manaBoost = 10 + random.nextInt(21); // random mana boost between 10 and 30

                System.out.println("You found a sanctuary. Restoring health and mana...");
                currentCharacter.regenerateHealth(healthBoost);
                currentCharacter.regenerateMana(manaBoost);

                currentCell.setType(CellEntityType.VOID);
                currentCell.setVisited();
            }
            case PORTAL -> {
                System.out.println("You found a portal. Moving to the next level...");
                int experienceBonus = currentCharacter.getLevel() * 5;
                currentCharacter.addExperience(experienceBonus);
                System.out.println("You gained " + experienceBonus + " experience for completing level " + currentCharacter.getLevel() + "!");

                int currentExperience = currentCharacter.getExperience();
                int experienceNeededForLevelUp = currentCharacter.getLevelUpThresHold();

                if (currentExperience < experienceNeededForLevelUp) {
                    int additionalExperience = experienceNeededForLevelUp - currentExperience;
                    currentCharacter.addExperience(additionalExperience);
                    System.out.println("An additional " + additionalExperience + " experience was added to level up!");
                }

                grid = Grid.generateGrid(currentCharacter);
                System.out.println("New game grid generated!");
                grid.displayGrid();
            }
            case VOID -> System.out.println("This cell is unexplored.");
            default -> System.out.println("This cell is the player's current position.");
        }
        currentCell.setVisited();
    }

    private void startBattle(Enemy enemy) {
        System.out.println("You encountered an enemy!");
        System.out.println("Enemy stats: " + enemy);

        Scanner scanner = new Scanner(System.in);
        while (currentCharacter.getCurrentHealth() > 0 && enemy.getCurrentHealth() > 0) {
            try {
                System.out.println("Your turn. Choose an action:");
                System.out.println("1. Normal Attack");
                System.out.println("2. Use Ability");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        int damage = currentCharacter.getDamage();
                        enemy.receiveDamage(damage);
                    }
                    case 2 -> {
                        if (currentCharacter.getAbilities().isEmpty()) {
                            System.out.println("No abilities available.");
                        } else {
                            System.out.println("Choose an ability:");
                            for (int i = 0; i < currentCharacter.getAbilities().size(); i++) {
                                System.out.println((i + 1) + ". " + currentCharacter.getAbilities().get(i));
                            }
                            int abilityChoice = scanner.nextInt() - 1;
                            if (abilityChoice >= 0 && abilityChoice < currentCharacter.getAbilities().size()) {
                                currentCharacter.useAbility(currentCharacter.getAbilities().get(abilityChoice), enemy);
                            } else {
                                throw new InvalidCommandException("Invalid ability choice.");
                            }
                        }
                    }
                    default -> throw new InvalidCommandException("Invalid action. Choose 1 or 2.");
                }

                // enemy's turn
                if (enemy.getCurrentHealth() > 0) {
                    System.out.println("Enemy's turn!");
                    enemy.performAttack(currentCharacter);
                }

                // check if the player is defeated
                if (currentCharacter.getCurrentHealth() <= 0) {
                    return;
                }
            } catch (InvalidCommandException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        int gainedExperience = 10 + new Random().nextInt(21); // random experience between 10 and 30
        System.out.println("Enemy defeated! You gained " + gainedExperience + " experience!");
        currentCharacter.addExperience(gainedExperience);
        System.out.println("Current experience: " + currentCharacter.getExperience());

        if (currentCharacter.getExperience() >= currentCharacter.getLevel() * 100) {
            currentCharacter.levelUp();
            System.out.println("Congratulations! You leveled up to level " + currentCharacter.getLevel() + "!");
        }

        System.out.println("Your health has been doubled!");
        int healthBonus = currentCharacter.getCurrentHealth();
        currentCharacter.regenerateHealth(healthBonus);

        System.out.println("Your mana has been fully recharged!");
        int manaBonus = currentCharacter.getMaxMana() - currentCharacter.getCurrentMana();
        currentCharacter.regenerateMana(manaBonus);

        grid.displayGrid();
    }

    public Account authenticate(String email, String password) {
        for (Account account : accounts) {
            if (account.getInformation().getCredentials().validate(email, password)) {
                return account;
            }
        }
        return null;
    }

    public void generateNewGrid() {
        this.grid = Grid.generateGrid(currentCharacter);
    }
}


