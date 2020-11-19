package src.asteroids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    final private Ship ship = new Ship();
    final private Asteroid[] asteroids = new Asteroid[]{new Asteroid(), new Asteroid(), new Asteroid(), new Asteroid(), new Asteroid(), new Asteroid(), new Asteroid()};
    final private Bullet[] bullets = new Bullet[] {new Bullet(ship), new Bullet(ship), new Bullet(ship), new Bullet(ship), new Bullet(ship)};

    @Override
    public void start(Stage stage) {
        Group group = new Group(ship.getImage());
        Scene scene = new Scene(group, 700, 700);
        for (Asteroid asteroid: asteroids) {
            group.getChildren().add(asteroid.getImage());
        }
        for (Bullet bullet: bullets) {
            scene.addEventHandler(KeyEvent.KEY_PRESSED, bullet.moveBullet());
            group.getChildren().add(bullet.getImage());
        }
        collisionDetection.start();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ship.rotate(KeyEvent.KEY_PRESSED));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, ship.rotate(KeyEvent.KEY_RELEASED));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ship.move(KeyEvent.KEY_PRESSED));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, ship.move(KeyEvent.KEY_RELEASED));
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void resetRound() {
        ship.reset();
    }

    private void detectShipCollision() {
        for (Asteroid asteroid: asteroids) {
            Collision collision = new Collision(ship, asteroid);
            if (collision.isCollision()) {
                asteroid.move.stop();
            }
        }
    }

    private void detectBulletCollision() {
        for (Bullet bullet: bullets) {
            for (Asteroid asteroid: asteroids) {
                Collision collision = new Collision(bullet, asteroid);
                if (collision.isCollision()) {
                    asteroid.getImage().setFill(Color.RED);
                }
            }
        }
    }

    private void detectAsteroidCollision() {
        for (Asteroid asteroid1: asteroids) {
            for (Asteroid asteroid2: asteroids) {
                if (asteroid1 != asteroid2) {
                    Collision collision = new Collision(asteroid1, asteroid2);
                    if (collision.isCollision()) {
                        asteroid1.move.stop();
                        asteroid2.move.stop();
                    }
                }
            }
        }
    }

    private final AnimationTimer collisionDetection = new AnimationTimer() {
        @Override
        public void handle(long l) {
//            detectShipCollision();
//            detectBulletCollision();
            detectAsteroidCollision();
        }
    };
}
