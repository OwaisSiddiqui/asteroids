package src.asteroids.gameOver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import src.asteroids.game.GameController;

public class GameOverController {

    @FXML
    public Button returnMenuButton;
    @FXML
    private Text pointsText;

    @FXML
    private Button playAgainButton;

    public GameOverController() {}

    public Button getReturnMenuButton() { return returnMenuButton; }

    public Text getPointsText() { return pointsText; }

    public Button getPlayAgainButton() { return playAgainButton; }

}
