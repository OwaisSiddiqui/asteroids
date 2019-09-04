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
    Asteroid asteroidThree = new Asteroid(ship, this, 3, 480, 100);
    Asteroid asteroidFour = new Asteroid(ship, this, 4, 400, 100);
    Line line = new Line();
    Line line1 = new Line();
    Line line2 = new Line();
    Line line3 = new Line();
    Line line4 = new Line();
    Line line5 = new Line();
    Line line6 = new Line();
    Line line7 = new Line();
    Line line8 = new Line();
    Line line9 = new Line();
    Line line10 = new Line();
    Line line11 = new Line();
    Group group = new Group(asteroidThree.asteroidImage, asteroidFour.asteroidImage,
                            asteroidThree.line, asteroidThree.line1, asteroidThree.line2, asteroidThree.line3, asteroidThree.line4, asteroidThree.line5,
                            asteroidThree.line6, asteroidThree.line7, asteroidThree.line8, asteroidThree.line9, asteroidThree.line10, asteroidThree.line11,
                            asteroidFour.line, asteroidFour.line1, asteroidFour.line2, asteroidFour.line3, asteroidFour.line4, asteroidFour.line5,
                            asteroidFour.line6, asteroidFour.line7, asteroidFour.line8, asteroidFour.line9, asteroidFour.line10, asteroidFour.line11,
                            line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11);
    Scene scene = new Scene(group, 700, 700);

    @Override
    public void start(Stage primaryStage)
    {
        VectorAsteroids[] vectors = asteroidThree.projectVectorOntoNormal(asteroidFour, asteroidFour, 3);
        VectorAsteroids[] vectors2 = asteroidThree.projectVectorOntoNormal(asteroidThree, asteroidFour, 3);
        lineArray[0] = line;
        lineArray[1] = line1;
        lineArray[2] = line2;
        lineArray[3] = line3;
        lineArray[4] = line4;
        lineArray[5] = line5;
        newLineArray[0] = line6;
        newLineArray[1] = line7;
        newLineArray[2] = line8;
        newLineArray[3] = line9;
        newLineArray[4] = line10;
        newLineArray[5] = line11;
        colorArray[0] = Color.RED;
        colorArray[1] = Color.LIMEGREEN;
        colorArray[2] = Color.BLUE;
        colorArray[3] = Color.GREEN;
        colorArray[4] = Color.ORANGE;
        colorArray[5] = Color.CYAN;
        for (int i = 0; i < 6; i++)
        {
            newLineArray[i].setEndX(vectors[i].InitialPointX);
            newLineArray[i].setEndY(vectors[i].InitialPointY);
            newLineArray[i].setStartX(vectors[i].TerminalPointX);
            newLineArray[i].setStartY(vectors[i].TerminalPointY);
            newLineArray[i].setStroke(colorArray[i]);
        }
        for (int i = 0; i < 6; i++)
        {
            lineArray[i].setEndX(vectors2[i].InitialPointX);
            lineArray[i].setEndY(vectors2[i].InitialPointY);
            lineArray[i].setStartX(vectors2[i].TerminalPointX);
            lineArray[i].setStartY(vectors2[i].TerminalPointY);

            lineArray[i].setStroke(colorArray[i]);
        }
        asteroidThree.isCollision(asteroidFour);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Asteroids");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}