//import org.example.game.Game;
//import org.example.entities.characters.Character;
//import org.example.entities.Account;
//import org.example.game.*;
//import org.example.exceptions.IllegalArgumentException;
//import org.example.exceptions.InvalidCommandException;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class Test {
//    public static void main(String[] args) throws InvalidCommandException, IllegalArgumentException {
//        Game game = new Game();
//        game.run();
//    }
//
////    public static void main(String[] args) throws InvalidCommandException, IllegalArgumentException {
////        Game game = new Game();
////        Scanner scanner = new Scanner(System.in);
////
////        System.out.println("Welcome to League of Warriors!");
////        Account account = authenticate(game, scanner);
////
////        if (account == null) {
////            System.out.println("Authentication failed. Exiting...");
////            return;
////        }
////
////        System.out.println("Select a character from your account:");
////        ArrayList<Character> characters = account.getCharacters();
////        for (int i = 0; i < characters.size(); i++) {
////            System.out.println((i + 1) + ". " + characters.get(i));
////        }
////
////        int characterIndex;
////        do {
////            System.out.print("Enter the number of the character you want to use: ");
////            characterIndex = scanner.nextInt() - 1;
////        } while (characterIndex < 0 || characterIndex >= characters.size());
////
////        Character character = characters.get(characterIndex);
////        game.setCurrentCharacter(character);
////
////        Grid grid = Grid.generatePredefinedGrid(character);
////        game.setGrid(grid);
////
////        System.out.println("Initial game grid:");
////        grid.displayGrid();
////
////        System.out.println("Moving 3 cells to the right:");
////        try {
////            grid.goEast();
////            grid.goEast();
////            grid.goEast();
////        } catch (Exception e) {
////            System.out.println("Error moving: " + e.getMessage());
////        }
////
////        game.handleCellInteraction();
////
////        System.out.println("Moving 1 cell to the right:");
////        try {
////            grid.goEast();
////        } catch (Exception e) {
////            System.out.println("Error moving: " + e.getMessage());
////        }
////
////        System.out.println("Moving 3 cells down:");
////        try {
////            grid.goSouth();
////            grid.goSouth();
////            grid.goSouth();
////        } catch (Exception e) {
////            System.out.println("Error moving: " + e.getMessage());
////        }
////
////        game.handleCellInteraction();
////
////        System.out.println("Moving 1 cell down:");
////        try {
////            grid.goSouth();
////        } catch (Exception e) {
////            System.out.println("Error moving: " + e.getMessage());
////        }
////
////        game.handleCellInteraction();
////
////        System.out.println("Last game's grid:");
////        grid.displayGrid();
////    }
////
////    private static Account authenticate(Game game, Scanner scanner) {
////        ArrayList<Account> accounts = game.getAccounts();
////
////        while (true) {
////            System.out.print("Enter your email: ");
////            String email = scanner.nextLine();
////            System.out.print("Enter your password: ");
////            String password = scanner.nextLine();
////
////            for (Account account : accounts) {
////                if (account.getInformation().getCredentials().validate(email, password)) {
////                    System.out.println("Authentication successful! Welcome, " + account.getInformation().getName() + "!");
////                    return account;
////                }
////            }
////
////            System.out.println("Invalid credentials. Try again or type 'exit' to quit.");
////            System.out.print("Enter 'exit' to quit or press Enter to retry: ");
////            if (scanner.nextLine().equalsIgnoreCase("exit")) {
////                return null;
////            }
////        }
////    }
//
////    public static void main(String[] args) {
////        Game game = new Game();
////        Scanner scanner = new Scanner(System.in);
////
////        System.out.println("Welcome to League of Warriors!");
////        Account account = authenticate(game, scanner);
////
////        if (account == null) {
////            System.out.println("Authentication failed. Exiting...");
////            return;
////        }
////
////        System.out.println("Select a character from your account:");
////        ArrayList<Character> characters = account.getCharacters();
////        for (int i = 0; i < characters.size(); i++) {
////            System.out.println((i + 1) + ". " + characters.get(i));
////        }
////
////        int characterIndex;
////        do {
////            System.out.print("Enter the number of the character you want to use: ");
////            characterIndex = scanner.nextInt() - 1;
////        } while (characterIndex < 0 || characterIndex >= characters.size());
////
////        Character character = characters.get(characterIndex);
////        game.setCurrentCharacter(character);
////
////        Grid grid = Grid.generatePredefinedGrid(character);
////        game.setGrid(grid);
////
////        System.out.println("Initial game grid:");
////        grid.displayGrid();
////
////        boolean running = true;
////        while (running) {
////            System.out.println("\nYour current position: " + grid.getCurrentCell());
////            System.out.println("Choose your next action:");
////            System.out.println("1. Move");
////            System.out.println("2. Show Stats");
////            System.out.println("3. Exit");
////
////            int choice = scanner.nextInt();
////            switch (choice) {
////                case 1 -> move(scanner, game, grid);
////                case 2 -> System.out.println(character);
////                case 3 -> {
////                    System.out.println("Exiting the game...");
////                    running = false;
////                }
////                default -> System.out.println("Invalid choice. Try again.");
////            }
////
////            // check if the game ends due to victory or defeat
////            if (character.getCurrentHealth() <= 0) {
////                System.out.println("Game Over! You have been defeated.");
////                running = false;
////            } else if (grid.getCurrentCell().getType() == CellEntityType.PORTAL) {
////                System.out.println("Congratulations! You reached the portal and completed the level!");
////                running = false;
////            }
////        }
////
////        System.out.println("Final game grid:");
////        grid.displayGrid();
////    }
////
////    private static void move(Scanner scanner, Game game, Grid grid) {
////        System.out.println("Choose a direction to move: ");
////        System.out.println("N - North, S - South, E - East, W - West");
////        String direction = scanner.next().toUpperCase();
////
////        try {
////            switch (direction) {
////                case "N" -> grid.goNorth();
////                case "S" -> grid.goSouth();
////                case "E" -> grid.goEast();
////                case "W" -> grid.goWest();
////                default -> System.out.println("Invalid direction. Please try again.");
////            }
////        } catch (Exception e) {
////            System.out.println("Movement error: " + e.getMessage());
////        }
////
////        try {
////            game.handleCellInteraction();
////        } catch (IllegalArgumentException e) {
////            System.out.println("Error handling cell interaction: " + e.getMessage());
////        }
////        grid.displayGrid();
////    }
////
////    private static Account authenticate(Game game, Scanner scanner) {
////        ArrayList<Account> accounts = game.getAccounts();
////
////        while (true) {
////            System.out.print("Enter your email: ");
////            String email = scanner.nextLine();
////            System.out.print("Enter your password: ");
////            String password = scanner.nextLine();
////
////            for (Account account : accounts) {
////                if (account.getInformation().getCredentials().validate(email, password)) {
////                    System.out.println("Authentication successful! Welcome, " + account.getInformation().getName() + "!");
////                    return account;
////                }
////            }
////
////            System.out.println("Invalid credentials. Try again or type 'exit' to quit.");
////            System.out.print("Enter 'exit' to quit or press Enter to retry: ");
////            if (scanner.nextLine().equalsIgnoreCase("exit")) {
////                return null;
////            }
////        }
////    }
//}
