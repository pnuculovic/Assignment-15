import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TicTacToe5x5 extends Application {

    private static final int SIZE = 5; // 5x5 board
    private Button[][] buttons = new Button[SIZE][SIZE];
    private boolean playerXTurn = true;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        // Initialize the board
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button button = new Button("");
                button.setFont(Font.font(18));
                button.setPrefSize(80, 80);
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> handleMove(finalRow, finalCol));
                buttons[row][col] = button;
                gridPane.add(button, col, row);
            }
        }

        Scene scene = new Scene(gridPane, 450, 450);
        primaryStage.setTitle("TicTacToe 5x5");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMove(int row, int col) {
        if (!buttons[row][col].getText().isEmpty()) {
            return; // Cell already filled
        }

        buttons[row][col].setText(playerXTurn ? "X" : "O");
        if (checkWin(row, col)) {
            showWinner(playerXTurn ? "Player X" : "Player O");
            resetBoard();
            return;
        }

        playerXTurn = !playerXTurn; // Switch turns
    }

    private boolean checkWin(int row, int col) {
        String currentPlayer = buttons[row][col].getText();
        return checkDirection(row, col, 1, 0, currentPlayer) || // Horizontal
               checkDirection(row, col, 0, 1, currentPlayer) || // Vertical
               checkDirection(row, col, 1, 1, currentPlayer) || // Diagonal (top-left to bottom-right)
               checkDirection(row, col, 1, -1, currentPlayer);  // Diagonal (top-right to bottom-left)
    }

    private boolean checkDirection(int row, int col, int rowDelta, int colDelta, String player) {
        int count = 1; // Current position counts as 1

        // Check in the positive direction
        for (int i = 1; i < 5; i++) {
            int newRow = row + i * rowDelta;
            int newCol = col + i * colDelta;
            if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE) break;
            if (!buttons[newRow][newCol].getText().equals(player)) break;
            count++;
        }

        // Check in the negative direction
        for (int i = 1; i < 5; i++) {
            int newRow = row - i * rowDelta;
            int newCol = col - i * colDelta;
            if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE) break;
            if (!buttons[newRow][newCol].getText().equals(player)) break;
            count++;
        }

        return count >= 5; // 5 in a row needed to win
    }

    private void showWinner(String winner) {
        System.out.println(winner + " wins!");
    }

    private void resetBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setText("");
            }
        }
        playerXTurn = true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
