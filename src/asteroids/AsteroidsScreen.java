package src.asteroids;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class AsteroidsScreen {

    private final Pane screen;

    AsteroidsScreen(String type) {
        if (type.equals("HBox")) {
            screen = new HBox();
        } else if (type.equals("StackPane")) {
            screen = new StackPane();
        } else {
            screen = new Pane();
        }
    }

    void add(Node e) {
        screen.getChildren().add(e);
    }

    void remove(Node e) { screen.getChildren().remove(e); }

    Pane getScreen() { return screen; }

}
