package src.asteroids.stats;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class StatsController {

    @FXML
    private Button backToMenu;
    @FXML
    private Text highScoreText;
    @FXML
    private Text highLevelText;
    @FXML
    private Text totalAsteroidsText;
    @FXML
    private Text totalPointsText;
    @FXML
    private Text totalGamesPlayedText;

    public Text getHighScoreText() {
        return highScoreText;
    }

    public Text getHighLevelText() {
        return highLevelText;
    }

    public Text getTotalAsteroidsText() {
        return totalAsteroidsText;
    }

    public Text getTotalPointsText() {
        return totalPointsText;
    }

    public Text getTotalCollisionsText() {
        return totalCollisionsText;
    }

    public Text getTotalGamesPlayedText() {
        return totalGamesPlayedText;
    }

    @FXML
    private Text totalCollisionsText;

    public StatsController() {}

    @FXML
    private void initialize() {}

    public Button getBackToMenuButton() { return backToMenu; }
}
