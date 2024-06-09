import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 700; // Additional space for the restart button

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel();
    JButton restartButton = new JButton("Restart");

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("<html>Tic-Tac-Toe<br>Player X's turn</html>");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel, BorderLayout.CENTER);

        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(restartButton);
        frame.add(controlPanel, BorderLayout.SOUTH);

        restartButton.setFont(new Font("Arial", Font.BOLD, 30));
        restartButton.setFocusable(false);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().isEmpty()) {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText("<html>" + currentPlayer + "'s turn</html>");
                            }
                        }
                    }
                });
            }
        }
    }

    void checkWinner() {
        // Check horizontal, vertical, and diagonal for a winner
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().isEmpty() &&
                board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                setWinner(board[r][0], board[r][1], board[r][2]);
                return;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().isEmpty() &&
                board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                setWinner(board[0][c], board[1][c], board[2][c]);
                return;
            }
        }

        if (!board[0][0].getText().isEmpty() &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            setWinner(board[0][0], board[1][1], board[2][2]);
            return;
        }

        if (!board[0][2].getText().isEmpty() &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2], board[1][1], board[2][0]);
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            textLabel.setText("<html>Tie!<br>Do you want to play again?</html>");
            gameOver = true;
        }
    }

    void setWinner(JButton... tiles) {
        for (JButton tile : tiles) {
            tile.setForeground(Color.green);
            tile.setBackground(Color.gray);
        }
        textLabel.setText("<html>" + currentPlayer + " is the winner!<br>Do you want to play again?</html>");
        gameOver = true;
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
    }

    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.darkGray);
            }
        }
        currentPlayer = playerX;
        textLabel.setText("<html>Tic-Tac-Toe<br>Player " + currentPlayer + "'s turn</html>");
        turns = 0;
        gameOver = false;
    }
}

