package src.asteroids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import src.asteroids.game.GameController;
import src.asteroids.gameOver.GameOverController;
import src.asteroids.menu.MenuController;
import src.asteroids.stats.StatsController;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main extends Application {

    Group group = new Group();
    Scene scene = new Scene(group,700, 700);
    final private Ship ship = new Ship(20);
    private final ArrayList<Asteroid> asteroids = new ArrayList<>();
    private int lives = 3;
    private int livesPoints = 0;
    private int numberOfAsteroids = 5;
    private int points = 0;
    private int numberOfAsteroidsDestroyed = 0;
    private int level = 1;
    private int totalCollisions = 0;
    Parent menuScreen;
    Parent gameScreen;
    Parent gameOverScreen;
    Parent statsScreen;
    GameController gameController;
    GameOverController gameOverController;
    StatsController statsController;

    @Override
    public void start(Stage stage) throws IOException {
        initializeGameData();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu/menu.fxml"));
        menuScreen = menuLoader.load();
        MenuController menuController = menuLoader.getController();
        menuController.getPlayButton().setOnAction(actionEvent -> startGame());
        menuController.getStatsButton().setOnAction(actionEvent -> {
            try {
                switchToStatsScreen();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        setBackgroundMenuScreen(menuController.getBackgroundScreen());
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("game/game.fxml"));
        gameScreen = gameLoader.load();
        gameController = gameLoader.getController();
        FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("gameOver/gameOver.fxml"));
        gameOverScreen = gameOverLoader.load();
        gameOverController = gameOverLoader.getController();
        gameOverController.getReturnMenuButton().setOnAction(actionEvent -> backToMenuScreen());
        gameOverController.getPlayAgainButton().setOnAction(actionEvent -> startGame());
        FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("stats/stats.fxml"));
        statsScreen = statsLoader.load();
        statsController = statsLoader.getController();
        statsController.getBackToMenuButton().setOnAction(actionEvent -> backToMenuScreen());
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

    private void switchToStatsScreen() throws IOException, ClassNotFoundException {
        removeAllScreens();
        HashMap<String, String> data = getDataFromFile();
        statsController.getHighScoreText().setText(data.get("High Score"));
        statsController.getHighLevelText().setText(data.get("Highest Level"));
        statsController.getTotalCollisionsText().setText(data.get("Total Collisions"));
        statsController.getTotalAsteroidsText().setText(data.get("Total Asteroids Destroyed"));
        statsController.getTotalPointsText().setText(data.get("Total Points"));
        statsController.getTotalGamesPlayedText().setText(data.get("Total Games Played"));
        addScreen(statsScreen);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void removeScreen(Node e) {
        group.getChildren().remove(e);
    }

    private void backToMenuScreen() {
        removeAllScreens();
        addScreen(menuScreen);
    }

    private void removeAllScreens() {
        group.getChildren().clear();
    }

    public void setBackgroundMenuScreen(Pane backgroundMenuScreen) {
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            Asteroid asteroid = new Asteroid("big", rand.nextInt((int) scene.getWidth()), rand.nextInt((int) scene.getHeight()));
            backgroundMenuScreen.getChildren().add(asteroid.getImage());
            asteroid.move.start();
        }
        for (int i = 0; i < 2; i++) {
            Asteroid asteroid = new Asteroid("medium", rand.nextInt((int) scene.getWidth()), rand.nextInt((int) scene.getHeight()));
            backgroundMenuScreen.getChildren().add(asteroid.getImage());
            asteroid.move.start();
        }
        for (int i = 0; i < 5; i++) {
            Asteroid asteroid = new Asteroid("small", rand.nextInt((int) scene.getWidth()), rand.nextInt((int) scene.getHeight()));
            backgroundMenuScreen.getChildren().add(asteroid.getImage());
            asteroid.move.start();
        }
    }

    private void startMovingAsteroids()  {
        for (Asteroid asteroid: asteroids) {
            asteroid.move.start();
        }
    }

    private void addScreen(Node e) {
        group.getChildren().add(e);
    }

    private void addLifeIcon() {
        gameController.getLifeIconsContainer().getChildren().add(getLifeIcon());
    }

    private void setPointsText() {
        gameController.getPointsText().setText(String.valueOf(points));
    }

    private void setAsteroids() {
        Random rand = new Random();
        for (int i = 0; i < numberOfAsteroids; i++) {
            asteroids.add(new Asteroid("big", rand.nextInt((int) scene.getWidth()), rand.nextInt((int) scene.getHeight())));
            gameController.getBackgroundScreen().getChildren().add(asteroids.get(i).getImage());
        }
    }

    private void setBullets() {
        for (Bullet bullet: ship.getLauncher().getBullets()) {
            gameController.getBackgroundScreen().getChildren().add(bullet.getImage());
        }
    }

    private void startGame() {
        group.getChildren().clear();
        addScreen(gameScreen);
        resetGame();
        gameController.getBackgroundScreen().getChildren().add(ship.getImage());
        for (int i = 0; i < lives; i++) { addLifeIcon(); }
        setAsteroids();
        setPointsText();
        setBullets();
        startMovingAsteroids();
        collisionDetection.start();
        updateRound.start();
        ship.reset();
    }

    private void removeLifeIcon() {
        gameController.getLifeIconsContainer().getChildren().remove(gameController.getLifeIconsContainer().getChildren().size() - 1);
    }

    private void resetGame() {
        points = 0;
        lives = 3;
        livesPoints = 0;
        asteroids.clear();
        numberOfAsteroids = 5;
        numberOfAsteroidsDestroyed = 0;
        level = 1;
        totalCollisions = 0;
    }

    private void stopAsteroidsMove() {
        for (Asteroid asteroid : asteroids) { asteroid.move.stop(); }
    }

    private void saveGameData() {
        try {
            HashMap<String, String> data = null;
            FileInputStream fin = new FileInputStream("./src/asteroids/data/data.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fin);
            data = (HashMap<String, String>) objectIn.readObject();
            HashMap<String, String> newData = new HashMap<>();
            int currentHighScore = Integer.parseInt(data.get("High Score"));
            if (currentHighScore < points) {
                newData.put("High Score", String.valueOf(points));
            } else {
                newData.put("High Score", data.get("High Score"));
            }
            int currentHighestLevel = Integer.parseInt(data.get("Highest Level"));
            if (currentHighestLevel < level) {
                newData.put("Highest Level", String.valueOf(level));
            } else {
                newData.put("Highest Level", data.get("Highest Level"));
            }
            newData.put("Total Collisions", String.valueOf(Integer.parseInt(data.get("Total Collisions")) + totalCollisions));
            newData.put("Total Asteroids Destroyed", String.valueOf(Integer.parseInt(data.get("Total Asteroids Destroyed")) + numberOfAsteroidsDestroyed));
            newData.put("Total Points", String.valueOf(Integer.parseInt(data.get("Total Points")) + points));
            newData.put("Total Games Played", String.valueOf(Integer.parseInt(data.get("Total Games Played")) + 1));
            FileOutputStream fout = new FileOutputStream("./src/asteroids/data/data.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(newData);
            oos.close();
            fout.close();
            objectIn.close();
            fin.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> getDataFromFile() throws IOException, ClassNotFoundException {
        HashMap<String, String> data = null;
        FileInputStream fin = new FileInputStream("./src/asteroids/data/data.ser");
        ObjectInputStream objectIn = new ObjectInputStream(fin);
        data = (HashMap<String, String>) objectIn.readObject();
        objectIn.close();
        fin.close();
        return data;
    }

    private void initializeGameData() {
        File file = new File("src/asteroids/data/data.ser");
        try {
            if (file.createNewFile()) {
                HashMap<String, String> data = new HashMap<>();
                data.put("High Score", String.valueOf(0));
                data.put("Highest Level", String.valueOf(0));
                data.put("Total Collisions", String.valueOf(0));
                data.put("Total Asteroids Destroyed", String.valueOf(0));
                data.put("Total Points", String.valueOf(0));
                data.put("Total Games Played", String.valueOf(0));
                FileOutputStream fout = new FileOutputStream("src/asteroids/data/data.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(data);
                oos.close();
                fout.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endGame() {
        collisionDetection.stop();
        updateRound.stop();
        stopAsteroidsMove();
        saveGameData();
        group.getChildren().clear();
        gameController.getBackgroundScreen().getChildren().removeIf(o -> o instanceof Polygon || o instanceof Circle);
        gameOverController.getPointsText().setText(String.valueOf(points));
        addScreen(gameOverScreen);
    }

    private void updateLives(String action) {
        if (action.equals("remove")) {
            lives--;
            removeLifeIcon();
        } else {
            lives++;
            addLifeIcon();
        }
        if (lives == 0) {
            endGame();
        }
    }

    private Polygon getLifeIcon() {
        Polygon lifeIcon = new Polygon();
        ObservableList<Double> shipPoints = ship.getImage().getPoints();
        Double[] points = new Double[shipPoints.size()];
        for (int i = 0; i < shipPoints.size(); i++) {
            points[i] = shipPoints.get(i);
        }
        lifeIcon.getPoints().addAll(points);
        lifeIcon.setRotate(-90);
        lifeIcon.setFill(Color.TRANSPARENT);
        lifeIcon.setStroke(Color.LAWNGREEN);
        lifeIcon.setScaleX(0.7);
        lifeIcon.setScaleY(0.7);
        return lifeIcon;
    }

    private void detectShipCollision() {
        for (Asteroid asteroid: asteroids) {
            Collision collision = new Collision(ship, asteroid);
            if (!ship.isResetting && collision.isCollision()) {
                totalCollisions++;
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
                    numberOfAsteroidsDestroyed++;
                    updatePoints(asteroid.getSize());
                    ArrayList<Asteroid> resultingAsteroids = asteroid.crumble(asteroid.getImage().getLayoutX(), asteroid.getImage().getLayoutY());
                    asteroids.remove(asteroid);
                    gameController.getBackgroundScreen().getChildren().remove(asteroid.getImage());
                    if (resultingAsteroids.size() != 0) {
                        asteroids.addAll(resultingAsteroids);
                        for (Asteroid asteroidInLoop: resultingAsteroids) {
                            asteroidInLoop.move.start();
                            gameController.getBackgroundScreen().getChildren().add(asteroidInLoop.getImage());
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
            case "big" -> pointsToAdd = 25;
            case "medium" -> pointsToAdd = 50;
            case "small" -> pointsToAdd = 100;
        }
        livesPoints += pointsToAdd;
        points += pointsToAdd;
        setPointsText();
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
                level++;
                Random rand = new Random();
                numberOfAsteroids += 3;
                ship.reset();
                for (int i = 0; i < numberOfAsteroids; i++) {
                    asteroids.add(new Asteroid("big", rand.nextInt((int) scene.getWidth()), rand.nextInt((int) scene.getHeight())));
                    gameController.getBackgroundScreen().getChildren().add(asteroids.get(i).getImage());
                    asteroids.get(i).move.start();
                }
            }
        }
    };
}
