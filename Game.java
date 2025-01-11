package TicTacToe;

import java.util.*;

public class Game {

    // Variables to represent the game state
    String[][] gameBoard = new String[3][3];
    int turnCounter = 0;
    boolean leaveLoop = false;
    int firstPlayer = 0; // 0 = user first, 1 = computer first

    // Constructor to initialize the game
    public Game() {
        resetGame();
    }

    // Reset the game board and variables for a new game
    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j] = Integer.toString(i * 3 + j + 1); // Initialize board with numbers 1-9
            }
        }
        turnCounter = 0;
        leaveLoop = false;
    }

    // Print the game board
    public void printGameBoard() {
        System.out.println("\n+---+---+---+");
        for (int i = 0; i < 3; i++) {
            System.out.print("|");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + gameBoard[i][j] + " |");
            }
            System.out.println("\n+---+---+---+");
        }
    }

    // Check if a player has won
    public String checkForWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0].equals(gameBoard[i][1]) && gameBoard[i][1].equals(gameBoard[i][2])) {
                return gameBoard[i][0];
            }
        }
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (gameBoard[0][i].equals(gameBoard[1][i]) && gameBoard[1][i].equals(gameBoard[2][i])) {
                return gameBoard[0][i];
            }
        }
        // Check diagonals
        if (gameBoard[0][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][2])) {
            return gameBoard[0][0];
        }
        if (gameBoard[0][2].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][0])) {
            return gameBoard[0][2];
        }
        return null;
    }

    // Make the computer's move (randomly selects a number)
    public int computerMove() {
        Random random = new Random();
        return random.nextInt(9) + 1; // Select a number between 1-9
    }

    // Main game loop
    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        // Coin toss to determine who plays first
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.print("Choose Heads or Tails (H/T): ");
        String userChoice = scanner.next().toUpperCase();
        String tossResult = new Random().nextBoolean() ? "H" : "T";

        System.out.println("Coin toss result: " + (tossResult.equals("H") ? "Heads" : "Tails"));
        if (userChoice.equals(tossResult)) {
            System.out.println("You won the toss! You will play first.");
            firstPlayer = 0; // Player goes first
        } else {
            System.out.println("Computer won the toss! It will play first.");
            firstPlayer = 1; // Computer goes first
        }

        while (true) {
            resetGame(); // Reset game board for a new game

            while (!leaveLoop) {
                printGameBoard();

                // Check for winner
                String winner = checkForWinner();
                if (winner != null) {
                    printGameBoard();
                    if (winner.equals("X")) {
                        System.out.println("You won!");
                    } else if (winner.equals("O")) {
                        System.out.println("Computer won!");
                    }
                    break;
                } else if (turnCounter >= 9) {
                    printGameBoard();
                    System.out.println("It's a draw!");
                    break;
                }

                // Player's turn
                if (turnCounter % 2 == firstPlayer) {
                    System.out.print("\nYour turn! Choose a number [1-9]: ");
                    int userMove = scanner.nextInt();
                    if (userMove >= 1 && userMove <= 9 && gameBoard[(userMove - 1) / 3][(userMove - 1) % 3].equals(Integer.toString(userMove))) {
                        gameBoard[(userMove - 1) / 3][(userMove - 1) % 3] = "X";
                        turnCounter++;
                    } else {
                        System.out.println("Invalid move! Try again.");
                    }
                } else { // Computer's turn
                    System.out.println("\nComputer's turn...");
                    int computerMove = computerMove();
                    while (!gameBoard[(computerMove - 1) / 3][(computerMove - 1) % 3].equals(Integer.toString(computerMove))) {
                        computerMove = computerMove(); // Retry if the spot is already taken
                    }
                    gameBoard[(computerMove - 1) / 3][(computerMove - 1) % 3] = "O";
                    turnCounter++;
                }
            }

            // Ask the user if they want to play again
            System.out.print("\nDo you want to play again? (Y/N): ");
            String playAgain = scanner.next().toUpperCase();
            if (playAgain.equals("Y")) {
                leaveLoop = false;
            } else {
                System.out.println("Thank you for playing! Goodbye!");
                break;
            }
        }

        scanner.close();
    }

    // Main method to run the game
    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}
