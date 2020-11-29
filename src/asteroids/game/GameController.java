package src.asteroids.game;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GameController {

    @FXML
    private Pane gameScreen;
    @FXML
    private HBox lifeIconsContainer;
    @FXML
    private Text pointsText;

    public GameController() {}

    @FXML
    private void initialize() {}

    public Pane getBackgroundScreen() { return gameScreen; }

    public HBox getLifeIconsContainer() { return lifeIconsContainer; }

    public Text getPointsText() { return pointsText; }
}
