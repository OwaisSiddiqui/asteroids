package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application
{
    Ship ship = new Ship();
    Asteroid[] asteroidArray = new Asteroid[5];
    Asteroid asteroidThree = new Asteroid(ship, this, 3, 300, 100);
    Asteroid asteroidFour = new Asteroid(ship, this, 4, 320, 100);
    Group group = new Group(asteroidThree.asteroidImage, asteroidFour.asteroidImage,
                            asteroidThree.line, asteroidThree.line1, asteroidThree.line2, asteroidThree.line3, asteroidThree.line4, asteroidThree.line5,
                            asteroidThree.line6, asteroidThree.line7, asteroidThree.line8, asteroidThree.line9, asteroidThree.line10, asteroidThree.line11,
                            asteroidFour.line, asteroidFour.line1, asteroidFour.line2, asteroidFour.line3, asteroidFour.line4, asteroidFour.line5,
                            asteroidFour.line6, asteroidFour.line7, asteroidFour.line8, asteroidFour.line9, asteroidFour.line10, asteroidFour.line11);
    Scene scene = new Scene(group, 700, 700);

    @Override
    public void start(Stage primaryStage)
    {
        asteroidArray[3] = asteroidThree;
        asteroidArray[4] = asteroidFour;

        runAsteroids();

        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Asteroids");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void runAsteroids()
    {
        asteroidThree.moveAsteroid();
        asteroidFour.moveAsteroid();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}