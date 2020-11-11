package life.game.gui;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private int boardSize;
    private boolean[][] currentBoard;

    public BoardPanel() {
        super.setBackground(Color.WHITE);
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setCurrentBoard(boolean[][] currentBoard) {
        this.currentBoard = currentBoard;
    }

    public void drawBoard(Graphics graphics) {
        int width = this.getWidth();
        int height = this.getHeight();

        int squareWidth = width / boardSize;
        int squareHeight = height / boardSize;

        graphics.setColor(Color.BLACK);
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                graphics.drawRect(x * squareWidth, y * squareHeight, squareWidth, squareHeight);
                if (currentBoard[y][x]) {
                    graphics.fillRect(x * squareWidth, y * squareHeight, squareWidth, squareHeight);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (boardSize > 0) { //if main thread have started
            drawBoard(graphics);
        }
    }
}
