package src.asteroids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    Group group = new Group();
    Scene scene = new Scene(group, 700, 700);
    final private Ship ship = new Ship();
    private final int numberOfAsteroids = 7;
    private final int numberOfBullets = 7;
    private int numberOfBulletsFired = 0;
    private final ArrayList<Asteroid> asteroids = new ArrayList<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    EventHandler <KeyEvent> setBulletToFire() {
        return keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                bullets.get(numberOfBulletsFired).setReadyToFire(false);
                if (numberOfBulletsFired == numberOfBullets - 1) { numberOfBulletsFired = -1; }
                bullets.get(numberOfBulletsFired + 1).setReadyToFire(true);
                numberOfBulletsFired++;
            }
        };
    }

    @Override
    public void start(Stage stage) {
        group.getChildren().add(ship.getImage());
        for (int i = 0; i < numberOfAsteroids; i++) {
            asteroids.add(new Asteroid());
            group.getChildren().add(asteroids.get(i).getImage());
        }
        for (int i = 0; i < numberOfBullets; i++) {
            bullets.add(new Bullet(ship, i));
            group.getChildren().add(bullets.get(i).getImage());
            bullets.get(i).setReadyToFire(i == 0);
            scene.addEventHandler(KeyEvent.KEY_PRESSED, bullets.get(i).move());
        }
        collisionDetection.start();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ship.rotate(KeyEvent.KEY_PRESSED));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, ship.rotate(KeyEvent.KEY_RELEASED));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ship.move(KeyEvent.KEY_PRESSED));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, ship.move(KeyEvent.KEY_RELEASED));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, setBulletToFire());
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void detectShipCollision() {
        for (Asteroid asteroid: asteroids) {
            Collision collision = new Collision(ship, asteroid);
            if (collision.isCollision()) {
                ship.reset();
            }
        }
    }

    private void detectBulletCollision() {
        for (Bullet bullet: bullets) {
            for (Asteroid asteroid: asteroids) {
                Collision collision = new Collision(bullet, asteroid);
                if (bullet.isRunning() && collision.isCollision()) {
                    asteroid.getImage().setFill(Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
                    bullet.reset();
                }
            }
        }
    }

    private final AnimationTimer collisionDetection = new AnimationTimer() {
        @Override
        public void handle(long l) {
            detectShipCollision();
            detectBulletCollision();
        }
    };
}
