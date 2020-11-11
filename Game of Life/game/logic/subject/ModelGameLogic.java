package life.game.logic.subject;

import life.game.logic.Neighbours;
import life.game.logic.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModelGameLogic implements Runnable, Subject {
    private boolean[][] currentBoard;
    private final List<Observer> observers;
    private int delayBetweenGens;
    private final int size;
    private int generationNumber;

    public ModelGameLogic(int size) {
        this.size = size;
        this.observers = new ArrayList<>();
        delayBetweenGens = 1000;
    }

    public void setDelayBetweenGens(int delayBetweenGens) {
        this.delayBetweenGens = delayBetweenGens;
    }

    public int getAmountOfAliveCells(){
        int amount = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (isCellAlive(col,row)) {
                    amount++;
                }
            }
        }
        return amount;
    }

    public boolean[][] getCurrentBoard() {
        return currentBoard;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    private long getRandomSeed() {
        Random random = new Random();
        return random.nextLong();
    }

    public int getSize() {
        return size;
    }

    private void firstGen() {
        Random random = new Random(getRandomSeed());
        currentBoard = new boolean[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                currentBoard[row][col] = random.nextBoolean();
            }
        }
        generationNumber++;
    }

    private boolean isCellAlive(int posX, int posY) {
        return currentBoard[posY][posX];
    }

    private boolean doesTheValueNeedTransformation(int pos) {
        int superiorLimit = size - 1;
        return pos > superiorLimit || pos < 0;
    }

    private int transformValue(int value) {
        int length = size - 1;
        if (value > length) {
            return value - size;
        }
        return value + size;
    }

    private int getSurroundingLiveNeighbours(int posX, int posY) {
        int liveNeighbours = 0;
        for (Neighbours neighbour : Neighbours.values()) {
            int posXToCheck = posX + neighbour.getX();
            int posYToCheck = posY + neighbour.getY();
            if (doesTheValueNeedTransformation(posXToCheck)) {
                posXToCheck = transformValue(posXToCheck);
            }
            if (doesTheValueNeedTransformation(posYToCheck)) {
                posYToCheck = transformValue(posYToCheck);
            }
            if (isCellAlive(posXToCheck, posYToCheck)) {
                liveNeighbours++;
            }
        }
        return liveNeighbours;
    }

    private void generation() {
        if (currentBoard == null) {
            firstGen();
        } else {
            defaultGeneration();
        }
    }

    private void defaultGeneration() {
        boolean[][] nextBoard = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int liveNeighbours = getSurroundingLiveNeighbours(col, row);
                if (isCellAlive(col, row)) {
                    if (liveNeighbours < 2 || liveNeighbours > 3) {
                        nextBoard[row][col] = false;
                    } else {
                        nextBoard[row][col] = true;
                    }
                } else if (liveNeighbours == 3) {
                    nextBoard[row][col] = true;
                }
            }
        }
        currentBoard = nextBoard;
        generationNumber++;
    }

    public void resetGame() {
        currentBoard = null;
        generationNumber = 0;
    }

    @Override
    public void run() {
        boolean gameIsRunning = true;
        while (gameIsRunning) {
            try {
                generation();
                notifyObservers();
                Thread.sleep(delayBetweenGens);
            } catch (InterruptedException e) {
                gameIsRunning = false;
            }
        }
    }

    @Override
    public void registerObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
