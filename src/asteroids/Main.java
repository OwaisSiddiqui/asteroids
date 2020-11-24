package src.asteroids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    Group group = new Group();
    Scene scene = new Scene(group, 700, 700);
    final private Ship ship = new Ship(20);
    private final ArrayList<Asteroid> asteroids = new ArrayList<>();
    private int round = 1;
    private int lives = 3;
    private int livesPoints = 0;
    private final ArrayList<Polygon> lifeIcons = new ArrayList<>();
    private int numberOfAsteroids = 5;
    private int points = 0;
    private final Text pointsText = new Text();

    @Override
    public void start(Stage stage) {
        setShip();
        setPointsText();
        setLifeIcons();
        setAsteroids();
        setBullets();
        collisionDetection.start();
        updateRound.start();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ship.rotate(KeyEvent.KEY_PRESSED));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, ship.rotate(KeyEvent.KEY_RELEASED));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ship.move(KeyEvent.KEY_PRESSED));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, ship.move(KeyEvent.KEY_RELEASED));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ship.getLauncher().detectLaunch(KeyEvent.KEY_PRESSED));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, ship.getLauncher().detectLaunch(KeyEvent.KEY_RELEASED));
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setLifeIcons() {
        for (int i = 0; i < lives; i++) { addLifeIcon(); }
    }

    private void removeLifeIcon() {
        Polygon lifeIconToRemove = lifeIcons.get(lifeIcons.size() - 1);
        lifeIcons.remove(lifeIconToRemove);
        group.getChildren().remove(lifeIconToRemove);
    }

    private void addLifeIcon() {
        double x = 200;
        double y = 50;
        if (lifeIcons.size() > 0) {
            Polygon lastLifeIcon = lifeIcons.get(lifeIcons.size() - 1);
            x = lastLifeIcon.getLayoutX() + 30;
            y = lastLifeIcon.getLayoutY();
        }
        Polygon newLifeIcon = getLifeIcon(x, y);
        lifeIcons.add(newLifeIcon);
        group.getChildren().add(newLifeIcon);
    }

    private void updateLives(String action) {
        if (action.equals("remove")) {
            lives--;
            removeLifeIcon();
        } else {
            lives++;
            addLifeIcon();
        }
        if (lives == 0) { System.out.println("Game over!"); System.exit(0); }
    }

    private Polygon getLifeIcon(double x, double y) {
        Polygon lifeIcon = new Polygon();
        ObservableList<Double> shipPoints = ship.getImage().getPoints();
        Double[] points = new Double[shipPoints.size()];
        for (int i = 0; i < shipPoints.size(); i++) {
            points[i] = shipPoints.get(i);
        }
        lifeIcon.getPoints().addAll(points);
        lifeIcon.setRotate(-90);
        lifeIcon.setFill(Color.TRANSPARENT);
        lifeIcon.setStroke(Color.BLACK);
        lifeIcon.setScaleX(0.7);
        lifeIcon.setScaleY(0.7);
        lifeIcon.setLayoutX(x);
        lifeIcon.setLayoutY(y);
        return lifeIcon;
    }

    private void setBullets() {
        for (Bullet bullet: ship.getLauncher().getBullets()) {
            group.getChildren().add(bullet.getImage());
        }
    }

    private void setAsteroids() {
        Random rand = new Random();
        for (int i = 0; i < numberOfAsteroids; i++) {
            asteroids.add(new Asteroid("big", rand.nextInt(700), rand.nextInt(700)));
            group.getChildren().add(asteroids.get(i).getImage());
        }
    }

    private void setPointsText() {
        pointsText.setLayoutX(50);
        pointsText.setLayoutY(50);
        pointsText.setFont(new Font(20));
        group.getChildren().add(pointsText);
        updatePointsText();
    }

    private void setShip() {
        group.getChildren().add(ship.getImage());
    }

    private void updatePointsText() {
        pointsText.setText(String.valueOf(points));
    }

    private void detectShipCollision() {
        for (Asteroid asteroid: asteroids) {
            Collision collision = new Collision(ship, asteroid);
            if (!ship.isResetting && collision.isCollision()) {
                updateLives("remove");
                ship.reset();
            }
        }
    }

    private void detectBulletCollision() {
        for (Bullet bullet: ship.getLauncher().getBullets()) {
            for (Asteroid asteroid: asteroids) {
                Collision collision = new Collision(bullet, asteroid);
                if (bullet.isRunning() && collision.isCollision()) {
                    updatePoints(asteroid.getSize());
                    ArrayList<Asteroid> resultingAsteroids = asteroid.crumble(asteroid.getImage().getLayoutX(), asteroid.getImage().getLayoutY());
                    asteroids.remove(asteroid);
                    group.getChildren().remove(asteroid.getImage());
                    if (resultingAsteroids.size() != 0) {
                        asteroids.addAll(resultingAsteroids);
                        for (Asteroid asteroidInLoop: resultingAsteroids) {
                            group.getChildren().add(asteroidInLoop.getImage());
                        }
                    }
                    bullet.reset();
                    break;
                }
            }
        }
    }

    private void updatePoints(String asteroidSize) {
        int pointsToAdd = 0;
        switch (asteroidSize) {
            case "big" -> pointsToAdd = 100;
            case "medium" -> pointsToAdd = 500;
            case "small" -> pointsToAdd = 1000;
        }
        livesPoints += pointsToAdd;
        points += pointsToAdd;
        pointsText.setText(String.valueOf(points));
        if (livesPoints >= 10000) {
            livesPoints -= 10000;
            updateLives("add");
        }
    }

    private final AnimationTimer collisionDetection = new AnimationTimer() {
        @Override
        public void handle(long l) {
            detectShipCollision();
            detectBulletCollision();
        }
    };

    private final AnimationTimer updateRound = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (asteroids.size() == 0) {
                Random rand = new Random();
                round++;
                numberOfAsteroids += 3;
                ship.reset();
                for (int i = 0; i < numberOfAsteroids; i++) {
                    asteroids.add(new Asteroid("big", rand.nextInt(700), rand.nextInt(700)));
                    group.getChildren().add(asteroids.get(i).getImage());
                }
            }
        }
    };
}
