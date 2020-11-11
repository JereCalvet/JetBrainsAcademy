package life.game.logic.observer;

import life.game.gui.ViewGameOfLife;
import life.game.logic.subject.ModelGameLogic;
import life.game.logic.subject.Subject;
import java.util.Objects;

public class Controller implements Observer {
    private final ModelGameLogic model;
    private final ViewGameOfLife view;
    private final Subject subjectModelGameLogic;
    private Thread threadModel;

    public Controller(ModelGameLogic model, ViewGameOfLife view) {
        this.model = model;

        subjectModelGameLogic = model;
        subjectModelGameLogic.registerObserver(this);

        this.view = view;
        initModel();
        initController();
    }

    private void initController() {
        view.getPlayPauseButton().addActionListener(a -> pauseContinueGame());
        view.getResetButton().addActionListener(action -> model.resetGame());
        view.getSpeedSlider().addChangeListener(action -> updateGameSpeed());
    }

    private void initModel() {
        threadModel = new Thread(model);
        threadModel.start();
    }

    private boolean isGamePaused() {
        return view.getPlayPauseButton().getText().equals("Play");
    }

    private void pauseContinueGame() {
        if (isGamePaused()) {
            //then play
            view.getPlayPauseButton().setText("Pause");
            threadModel = new Thread(model);
            threadModel.start();
        } else {
            //else pause
            view.getPlayPauseButton().setText("Play");
            threadModel.interrupt();
        }
    }

    private void updateLabelsInformation(int genNum, int liveCells) {
        view.getGenerationLabel().setText("Generation #" + genNum);
        view.getAliveLabel().setText("Alive: " + liveCells);
    }

    private void updateBoard(int size, boolean[][] board) {
        view.getBoardPanel().setBoardSize(size);
        view.getBoardPanel().setCurrentBoard(board);
        view.getBoardPanel().repaint();
    }

    private void updateGameSpeed() {
        if (!view.getSpeedSlider().getValueIsAdjusting()) {
            model.setDelayBetweenGens(1000 / view.getSpeedSlider().getValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Controller that = (Controller) o;
        return model.equals(that.model) &&
                view.equals(that.view);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, view);
    }

    @Override
    public void update() {
        ModelGameLogic data = (ModelGameLogic) subjectModelGameLogic;
        updateLabelsInformation(data.getGenerationNumber(), data.getAmountOfAliveCells());
        updateBoard(data.getSize(), data.getCurrentBoard());
    }
}
