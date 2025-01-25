import java.util.Scanner;
import java.util.Random;

public class Game {

    String[][] gameBoard = new String[3][3];
    int turnCounter = 0;
    boolean leaveLoop = false;
    int firstPlayer = 0; // 0 = user first, 1 = computer first

    public Game() {
        resetGame();
    }

    public void resetGame() {
        // Initialize the board with numbers 1 to 9
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j] = Integer.toString(i * 3 + j + 1);
            }
        }
        turnCounter = 0;
        leaveLoop = false;
    }

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

    public String checkForWinner() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0].equals(gameBoard[i][1]) && gameBoard[i][1].equals(gameBoard[i][2])) {
                return gameBoard[i][0];
            }
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

    public int computerMove() {
        // Try to win the game
        for (int i = 1; i <= 9; i++) {
            if (isValidMove(i)) {
                makeMove(i, "O");
                if ("O".equals(checkForWinner())) {
                    return i;
                }
                undoMove(i);
            }
        }

        // Block the player's winning move
        for (int i = 1; i <= 9; i++) {
            if (isValidMove(i)) {
                makeMove(i, "X");
                if ("X".equals(checkForWinner())) {
                    makeMove(i, "O");
                    return i;
                }
                undoMove(i);
            }
        }

        // Choose the center if available
        if (isValidMove(5)) {
            makeMove(5, "O");
            return 5;
        }

        // Choose a corner if available
        int[] corners = {1, 3, 7, 9};
        for (int corner : corners) {
            if (isValidMove(corner)) {
                makeMove(corner, "O");
                return corner;
            }
        }

        // Choose an edge if available
        int[] edges = {2, 4, 6, 8};
        for (int edge : edges) {
            if (isValidMove(edge)) {
                makeMove(edge, "O");
                return edge;
            }
        }

        return -1; // Should never reach here
    }

    public boolean isValidMove(int move) {
        move -= 1; // Adjust for 0-based indexing
        int row = move / 3;
        int col = move % 3;
        return gameBoard[row][col].equals(Integer.toString(move + 1));
    }

    public void makeMove(int move, String symbol) {
        move -= 1; // Adjust for 0-based indexing
        int row = move / 3;
        int col = move % 3;
        gameBoard[row][col] = symbol;
    }

    public void undoMove(int move) {
        move -= 1; // Adjust for 0-based indexing
        int row = move / 3;
        int col = move % 3;
        gameBoard[row][col] = Integer.toString(move + 1);
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Tic Tac Toe!");
        System.out.print("Choose Heads or Tails (H/T): ");
        String userChoice = scanner.next().toUpperCase();
        String tossResult = new Random().nextBoolean() ? "H" : "T";

        System.out.println("Coin toss result: " + (tossResult.equals("H") ? "Heads" : "Tails"));
        if (userChoice.equals(tossResult)) {
            System.out.println("You won the toss! You will play first.");
            firstPlayer = 0;
        } else {
            System.out.println("Computer won the toss! It will play first.");
            firstPlayer = 1;
        }

        while (true) {
            resetGame();

            while (!leaveLoop) {
                printGameBoard();

                String winner = checkForWinner();
                if (winner != null) {
                    printGameBoard();
                    if ("X".equals(winner)) {
                        System.out.println("You won!");
                    } else if ("O".equals(winner)) {
                        System.out.println("Computer won!");
                    }
                    break;
                } else if (turnCounter >= 9) {
                    printGameBoard();
                    System.out.println("It's a draw!");
                    break;
                }

                if (turnCounter % 2 == firstPlayer) {
                    System.out.print("Your turn! Choose a number [1-9]: ");
                    int userMove = scanner.nextInt();
                    if (isValidMove(userMove)) {
                        makeMove(userMove, "X");
                        turnCounter++;
                    } else {
                        System.out.println("Invalid move! Try again.");
                    }
                } else {
                    System.out.println("Computer's turn...");
                    int compMove = computerMove();
                    System.out.println("Computer chose: " + compMove);
                    turnCounter++;
                }
            }

            System.out.print("Do you want to play again? (Y/N): ");
            String playAgain = scanner.next().toUpperCase();
            if (playAgain.equals("N")) {
                System.out.println("Thank you for playing! Goodbye!");
                break;
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}
