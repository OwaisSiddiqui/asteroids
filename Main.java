package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application
{
    Line[] lineArray = new Line[6];
    Line[] newLineArray = new Line[6];
    Color[] colorArray = new Color[6];
    Ship ship = new Ship();
    Asteroid[] asteroidArray = new Asteroid[5];
    Asteroid asteroidZero = new Asteroid(ship, this, 0);
    Asteroid asteroidOne = new Asteroid(ship, this, 1);
    Asteroid asteroidTwo = new Asteroid(ship, this, 2);
    Asteroid asteroidThree = new Asteroid(ship, this, 3);
    Asteroid asteroidFour = new Asteroid(ship, this, 4);
    Group group = new Group(asteroidZero.asteroidImage, asteroidOne.asteroidImage, asteroidTwo.asteroidImage,
            asteroidZero.text, asteroidOne.text, asteroidTwo.text,asteroidThree.text, asteroidFour.text, asteroidThree.asteroidImage, asteroidFour.asteroidImage);
    Scene scene = new Scene(group, 700, 700);

    @Override
    public void start(Stage primaryStage)
    {
        asteroidArray[0] = asteroidZero;
        asteroidArray[1] = asteroidOne;
        asteroidArray[2] = asteroidTwo;
        asteroidArray[3] = asteroidThree;
        asteroidArray[4] = asteroidFour;

        areAsteroidsSeparate();

        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Asteroids");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void areAsteroidsSeparate()
    {
        int n = 0;
        for (int i = 0; i < 5; i++)
        {
            for (int x = 0; x < 5; x++)
            {
                if (i == 4 && n == 0)
                {
                    n++;
                }
                if (i == x)
                {
                    if (i == 4 && n == 0)
                    {
                        x = 0;
                    }
                    else
                    {
                        x += 1;
                    }
                }
                else if (asteroidArray[i].asteroidImage.getBoundsInParent().intersects(asteroidArray[x].asteroidImage.getBoundsInParent()))
                {
                    asteroidArray[i].setAsteroidPosition();
                }
            }
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}