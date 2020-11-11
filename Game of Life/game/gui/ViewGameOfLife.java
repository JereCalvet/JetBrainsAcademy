package life.game.gui;

import javax.swing.*;
import java.awt.*;

public class ViewGameOfLife extends JFrame {

    private JLabel generationLabel;
    private JLabel aliveLabel;
    private BoardPanel boardPanel;
    private JToggleButton playPauseButton;
    private JButton resetButton;
    private JSlider speedSlider;

    public ViewGameOfLife() {
        windowConfiguration();
    }

    public JToggleButton getPlayPauseButton() {
        return playPauseButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JLabel getGenerationLabel() {
        return generationLabel;
    }

    public JLabel getAliveLabel() {
        return aliveLabel;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public JSlider getSpeedSlider() {
        return speedSlider;
    }

    private void windowConfiguration() {
        setTitle("Game of Life");

        //create panels and setup layout
        boardPanel = new BoardPanel();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


        //add panel with labels and buttons, and panel with board
        mainPanel.add(createAndGetInteractivePanel(), BorderLayout.WEST);
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        //add main panel to frame
        add(mainPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setVisible(true);
    }

    private JPanel createAndGetInteractivePanel() {

        JPanel interactivePanel = new JPanel();
        BoxLayout interactiveLayout = new BoxLayout(interactivePanel, BoxLayout.Y_AXIS);
        interactivePanel.setLayout(interactiveLayout);

        interactivePanel.add(createAndGetButtonsPanel());
        interactivePanel.add(createAndGetSliderPanel());
        interactivePanel.add(createAndGetLabelsPanel());

        alignPanelsToLeft(interactiveLayout);

        return interactivePanel;
    }

    private void alignPanelsToLeft(BoxLayout interactiveLayout) {
        for (Component comp : interactiveLayout.getTarget().getComponents()) {
           JPanel panel = (JPanel) comp;
           panel.setAlignmentX(LEFT_ALIGNMENT);
        }
    }

    private JPanel createAndGetSliderPanel() {
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BorderLayout());
        createAndSetupSlider();
        sliderPanel.add(speedSlider);
        return sliderPanel;
    }

    private void createAndSetupSlider() {
        speedSlider = new JSlider(1, 10, 1);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
    }

    private JPanel createAndGetLabelsPanel() {
        JPanel labelsPanel = new JPanel();
        BoxLayout labelsLayout = new BoxLayout(labelsPanel, BoxLayout.Y_AXIS);
        labelsPanel.setLayout(labelsLayout);
        createAndSetupLabels();
        labelsPanel.add(generationLabel);
        labelsPanel.add(aliveLabel);
        return labelsPanel;
    }

    private JPanel createAndGetButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        createAndSetupButtons();
        buttonsPanel.add(playPauseButton);
        buttonsPanel.add(resetButton);
        return buttonsPanel;
    }

    private void createAndSetupButtons() {
        playPauseButton = new JToggleButton("Pause");
        playPauseButton.setName("PlayToggleButton");
        playPauseButton.setPreferredSize(new Dimension(100, 20));

        resetButton = new JButton("Reset");
        resetButton.setName("ResetButton");
        resetButton.setPreferredSize(new Dimension(100, 20));
    }

    private void createAndSetupLabels() {
        generationLabel = new JLabel("Generation #0");
        generationLabel.setName("GenerationLabel");

        aliveLabel = new JLabel("Alive: 0");
        aliveLabel.setName("AliveLabel");
    }
}
