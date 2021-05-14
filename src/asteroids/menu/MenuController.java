package src.asteroids.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MenuController {

    @FXML
    private Button playButton;
    @FXML
    private Pane backgroundScreen;
    @FXML
    private Button statsButton;

    public MenuController() {}

    @FXML
    private void initialize() {}

    public Button getPlayButton() { return playButton; }

    @FXML
    private void exitButtonAction() { System.exit(0); }

    public Button getStatsButton() { return statsButton; }

    public Pane getBackgroundScreen() { return backgroundScreen; }
}
