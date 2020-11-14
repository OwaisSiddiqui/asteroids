package asteroids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private Ship ship = new Ship();
    private Asteroid[] asteroids = new Asteroid[]{new Asteroid(), new Asteroid()};
    private Bullet[] bullets = new Bullet[] {new Bullet(ship), new Bullet(ship), new Bullet(ship), new Bullet(ship), new Bullet(ship)};

    @Override
    public void start(Stage stage) {
        Group group = new Group(ship.getShipImage());
        Scene scene = new Scene(group, 700, 700);
        for (Asteroid asteroid: asteroids) {
            group.getChildren().add(asteroid.getAsteroidImage());
        }
        for (Bullet bullet: bullets) {
            scene.addEventHandler(KeyEvent.KEY_PRESSED, bullet.moveBullet());
            group.getChildren().add(bullet.circle);
        }

        scene.addEventHandler(KeyEvent.KEY_PRESSED, ship.rotateShip(KeyEvent.KEY_PRESSED));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, ship.rotateShip(KeyEvent.KEY_RELEASED));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ship.moveShip(KeyEvent.KEY_PRESSED));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, ship.moveShip(KeyEvent.KEY_RELEASED));
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void detectShipCollision() {

    }

}
