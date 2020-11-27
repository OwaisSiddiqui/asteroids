package src.asteroids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    Group group = new Group();
    Scene scene = new Scene(group,700, 700);
    final private Ship ship = new Ship(20);
    private final ArrayList<Asteroid> asteroids = new ArrayList<>();
    private int lives = 3;
    private int livesPoints = 0;
    private final ArrayList<Polygon> lifeIcons = new ArrayList<>();
    private int numberOfAsteroids = 5;
    private int points = 0;
    private final Text pointsText = new Text();
    Parent menuScreen;
    AsteroidsScreen gameScreen = new AsteroidsScreen("Pane");
    AsteroidsScreen gameOverScreen = new AsteroidsScreen("StackPane");

    @Override
    public void start(Stage stage) throws Exception {
        menuScreen = FXMLLoader.load(getClass().getResource("menu/menu.fxml"));
        group.getChildren().add(menuScreen);
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

    private void exitGame() {
        System.exit(0);
    }

    private void createGameOverScreen() {
        gameOverScreen.getScreen().getChildren().clear();
        VBox middle = new VBox();
        VBox titleBox = new VBox();
        Text finalPointsText = new Text();
        finalPointsText.setFont(new Font(30));
        finalPointsText.setText("Points: " + points);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.setPadding(new Insets(100, 0, 0, 0));
        VBox buttons = new VBox();
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);
        Text title = new Text("Game Over");
        title.setFont(new Font("Helvetica Neue", 70));
        Button playButton = new Button("Play Again");
        playButton.setFocusTraversable(false);
        playButton.setFont(new Font("Helvetica Neue", 30));
        playButton.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-style: solid; -fx-border-radius: 4px");
        playButton.setOnAction(actionEvent -> startGame());
        Button exitButton = new Button("Back to Menu");
        exitButton.setFocusTraversable(false);
        exitButton.setFont(new Font("Helvetica Neue", 30));
        exitButton.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-style: solid; -fx-border-radius: 4px");
        exitButton.setOnAction(actionEvent -> backToMenuScreen());
        titleBox.getChildren().add(title);
        titleBox.getChildren().add(finalPointsText);
        buttons.getChildren().add(playButton);
        buttons.getChildren().add(exitButton);
        middle.getChildren().add(titleBox);
        middle.getChildren().add(buttons);
        middle.setAlignment(Pos.TOP_CENTER);
        middle.setSpacing(30);
        gameOverScreen.add(middle);
        gameOverScreen.getScreen().setStyle("-fx-border-color: yellow; -fx-border-style: solid");
        gameOverScreen.getScreen().setPrefWidth(scene.getWidth());
        gameOverScreen.getScreen().setPrefHeight(scene.getHeight());
        ((StackPane) gameOverScreen.getScreen()).setAlignment(Pos.CENTER);
    }

//    private void createBackgroundMenuScreen() {
//        Random rand = new Random();
//        for (int i = 0; i < 3; i++) {
//            Asteroid asteroid = new Asteroid("big", rand.nextInt((int) scene.getWidth()), rand.nextInt((int) scene.getHeight()));
//            backgroundMenuScreen.add(asteroid.getImage());
//            asteroid.move.start();
//        }
//        for (int i = 0; i < 2; i++) {
//            Asteroid asteroid = new Asteroid("medium", rand.nextInt((int) scene.getWidth()), rand.nextInt((int) scene.getHeight()));
//            backgroundMenuScreen.add(asteroid.getImage());
//            asteroid.move.start();
//        }
//        for (int i = 0; i < 5; i++) {
//            Asteroid asteroid = new Asteroid("small", rand.nextInt((int) scene.getWidth()), rand.nextInt((int) scene.getHeight()));
//            backgroundMenuScreen.add(asteroid.getImage());
//            asteroid.move.start();
//        }
//    }

//    private void createMenuScreen() {
//        VBox middle = new VBox();
//        VBox titleBox = new VBox();
//        titleBox.setAlignment(Pos.TOP_CENTER);
//        titleBox.setPadding(new Insets(100, 0, 0, 0));
//        VBox buttons = new VBox();
//        buttons.setSpacing(20);
//        buttons.setAlignment(Pos.CENTER);
//        Text title = new Text("Asteroids");
//        title.setFont(new Font("Helvetica Neue", 70));
//        Button playButton = new Button("Play");
//        playButton.setFocusTraversable(false);
//        playButton.setFont(new Font("Helvetica Neue", 30));
//        playButton.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-style: solid; -fx-border-radius: 4px");
//        playButton.setOnAction(actionEvent -> startGame());
//        Button exitButton = new Button("Exit");
//        exitButton.setFocusTraversable(false);
//        exitButton.setFont(new Font("Helvetica Neue", 30));
//        exitButton.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-style: solid; -fx-border-radius: 4px");
//        exitButton.setOnAction(actionEvent -> exitGame());
//        titleBox.getChildren().add(title);
//        buttons.getChildren().add(playButton);
//        buttons.getChildren().add(exitButton);
//        middle.getChildren().add(titleBox);
//        middle.getChildren().add(buttons);
//        middle.setAlignment(Pos.TOP_CENTER);
//        middle.setSpacing(30);
//        menuScreen.add(middle);
//        menuScreen.getScreen().setStyle("-fx-border-color: yellow; -fx-border-style: solid");
//        menuScreen.getScreen().setPrefWidth(scene.getWidth());
//        menuScreen.getScreen().setPrefHeight(scene.getHeight());
//        ((StackPane) menuScreen.getScreen()).setAlignment(Pos.CENTER);
//    }

    private void addScreen(AsteroidsScreen screen) {
        group.getChildren().add(screen.getScreen());
    }

    private void removeScreen(AsteroidsScreen screen) {
        group.getChildren().remove(screen.getScreen());
    }

    private void startMovingAsteroids()  {
        for (Asteroid asteroid: asteroids) {
            asteroid.move.start();
        }
    }

    private void startGame() {
        group.getChildren().clear();
//        removeScreen(backgroundMenuScreen);
//        removeScreen(menuScreen);
        addScreen(gameScreen);
        resetGame();
        gameScreen.add(ship.getImage());
        for (int i = 0; i < lives; i++) { addLifeIcon(gameScreen); }
        setAsteroids(gameScreen);
        setPointsText(gameScreen);
        setBullets(gameScreen);
        startMovingAsteroids();
        collisionDetection.start();
        updateRound.start();
        ship.reset();
    }

    private void setPointsText(AsteroidsScreen screen) {
        pointsText.setLayoutX(50);
        pointsText.setLayoutY(50);
        pointsText.setFont(new Font(20));
        screen.getScreen().getChildren().add(pointsText);
        updatePointsText();
    }

    private void removeLifeIcon() {
        Polygon lifeIconToRemove = lifeIcons.get(lifeIcons.size() - 1);
        lifeIcons.remove(lifeIconToRemove);
        gameScreen.remove(lifeIconToRemove);
    }

    private void addLifeIcon(AsteroidsScreen screen) {
        double x = 200;
        double y = 50;
        if (lifeIcons.size() > 0) {
            Polygon lastLifeIcon = lifeIcons.get(lifeIcons.size() - 1);
            x = lastLifeIcon.getLayoutX() + 30;
            y = lastLifeIcon.getLayoutY();
        }
        Polygon newLifeIcon = getLifeIcon(x, y);
        lifeIcons.add(newLifeIcon);
        screen.getScreen().getChildren().add(newLifeIcon);
    }

    private void resetGame() {
        points = 0;
        lifeIcons.clear();
        lives = 3;
        livesPoints = 0;
        asteroids.clear();
        numberOfAsteroids = 5;
    }

    private void backToMenuScreen() {
        removeScreen(gameOverScreen);
//        addScreen(backgroundMenuScreen);
//        addScreen(menuScreen);
    }

    private void stopAsteroidsMove() {
        System.out.println("Stopping asteroids!");
        for (Asteroid asteroid : asteroids) {
            asteroid.move.stop();
        }
    }

    private void endGame() {
        collisionDetection.stop();
        updateRound.stop();
        stopAsteroidsMove();
        gameScreen.getScreen().getChildren().clear();
        removeScreen(gameScreen);
        createGameOverScreen();
        addScreen(gameOverScreen);
    }

    private void updateLives(String action) {
        if (action.equals("remove")) {
            lives--;
            removeLifeIcon();
        } else {
            lives++;
            addLifeIcon(gameScreen);
        }
        if (lives == 0) {
            endGame();
        }
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

    private void setBullets(AsteroidsScreen screen) {
        for (Bullet bullet: ship.getLauncher().getBullets()) {
            screen.getScreen().getChildren().add(bullet.getImage());
        }
    }

    private void setAsteroids(AsteroidsScreen screen) {
        Random rand = new Random();
        for (int i = 0; i < numberOfAsteroids; i++) {
            asteroids.add(new Asteroid("big", rand.nextInt((int) scene.getWidth()), rand.nextInt((int) scene.getHeight())));
            screen.getScreen().getChildren().add(asteroids.get(i).getImage());
        }
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
                    gameScreen.remove(asteroid.getImage());
                    if (resultingAsteroids.size() != 0) {
                        asteroids.addAll(resultingAsteroids);
                        for (Asteroid asteroidInLoop: resultingAsteroids) {
                            gameScreen.add(asteroidInLoop.getImage());
                            asteroidInLoop.move.start();
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
                numberOfAsteroids += 3;
                ship.reset();
                for (int i = 0; i < numberOfAsteroids; i++) {
                    asteroids.add(new Asteroid("big", rand.nextInt((int) scene.getWidth()), rand.nextInt((int) scene.getHeight())));
                    gameScreen.add(asteroids.get(i).getImage());
                    asteroids.get(i).move.start();
                }
            }
        }
    };
}
