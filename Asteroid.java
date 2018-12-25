package sample;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.Random;


public class Asteroid
{
    Ship ship;
    Polygon asteroidImage;
    Random rand = new Random();
    AnimationTimer timer;
    double changeInX;
    double changeInY;
    boolean isCollision = true;
    Main main;
    int IdentificationNumber;
    Text text = new Text();
    Line line = new Line();
    VectorAsteroids[] vectorAsteroids = new VectorAsteroids[5];

    Asteroid(Ship ship, Main main, int identificationNumber)
    {
        IdentificationNumber = identificationNumber;
        text.setText(String.valueOf(IdentificationNumber));
        this.ship = ship;
        this.main = main;
        asteroidImage = new Polygon();
        Double[] array = new Double[]
                {
                        0.0, 30.0,
                        20.0, 60.0,
                        50.0, 0.0,
                        25.0, -40.0,
                        -40.0, -13.0,
                        -30.0, 30.0
                };
        this.asteroidImage.getPoints().addAll(array);
        this.asteroidImage.setFill(Color.TRANSPARENT);
        this.asteroidImage.setStroke(Color.BLACK);

        text.setFill(Color.PURPLE);
        text.setStyle("-fx-font: 24 arial;");

        createAsteroidCourse();
        setAsteroidPosition();

        text.setLayoutX(asteroidImage.getLayoutX());
        text.setLayoutY(asteroidImage.getLayoutY());

        line.setEndX((changeInX+asteroidImage.getLayoutX())*10);
        line.setEndY((changeInY+asteroidImage.getLayoutY())*10);
        line.setStartX((changeInX+asteroidImage.getLayoutX()));
        line.setStartY((changeInY+asteroidImage.getLayoutY()));

        MoveAsteroid();
    }

    public void MoveAsteroid()
    {
        timer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                text.setLayoutX(asteroidImage.getLayoutX());
                text.setLayoutY(asteroidImage.getLayoutY());
                asteroidImage.setLayoutX(asteroidImage.getLayoutX()+changeInX);
                asteroidImage.setLayoutY(asteroidImage.getLayoutY()+changeInY);
                DetectBulletCollision(ship);
                DetectShipCollision(ship);
                bumpAsteroid();
                text.setLayoutX(asteroidImage.getLayoutX());
                text.setLayoutY(asteroidImage.getLayoutY());
            }
        };
        timer.start();
    }

    public void bumpAsteroid()
    {
        for (int x = 0; x < 5; x++)
        {
            if (main.asteroidArray[x] != null && main.asteroidArray[x].IdentificationNumber == this.IdentificationNumber && isCollision)
            {
                isCollision = false;
            }
            else if (main.asteroidArray[x] != null && main.asteroidArray[x].IdentificationNumber != this.IdentificationNumber && this.asteroidImage.getBoundsInParent().intersects(main.asteroidArray[x].asteroidImage.getBoundsInParent()))
            {
                System.out.println("COLLISION WITH ASTEROID "+main.asteroidArray[x].IdentificationNumber+" WITH ASTEROID "+this.IdentificationNumber);
                this.asteroidImage.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                main.asteroidArray[x].asteroidImage.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                changeInX = -changeInX;
                changeInY = -changeInY;
            }
        }
    }

    public void DetectBulletCollision(Ship ship)
    {
        if(ship.bulletZero.isBulletFired() && asteroidImage.getBoundsInParent().intersects(ship.bulletZero.bulletImage.getBoundsInParent()) && ship.bulletZero.isBulletFired())
        {
            asteroidImage.setFill(Color.RED);
            ship.bulletZero.stopBullet();
        }
        else if (ship.bulletOne.isBulletFired() && asteroidImage.getBoundsInParent().intersects(ship.bulletOne.bulletImage.getBoundsInParent()) && ship.bulletOne.isBulletFired())
        {
            asteroidImage.setFill(Color.RED);
            ship.bulletOne.stopBullet();
        }
        else if (ship.bulletTwo.isBulletFired() && asteroidImage.getBoundsInParent().intersects(ship.bulletTwo.bulletImage.getBoundsInParent()) && ship.bulletTwo.isBulletFired())
        {
            asteroidImage.setFill(Color.RED);
            ship.bulletTwo.stopBullet();
        }
        else if (ship.bulletThree.isBulletFired() && asteroidImage.getBoundsInParent().intersects(ship.bulletThree.bulletImage.getBoundsInParent()))
        {
            asteroidImage.setFill(Color.RED);
            ship.bulletThree.stopBullet();
        }
        else if (ship.bulletFour.isBulletFired() && asteroidImage.getBoundsInParent().intersects(ship.bulletFour.bulletImage.getBoundsInParent()))
        {
            asteroidImage.setFill(Color.RED);
            ship.bulletFour.stopBullet();
        }
    }

    public void DetectShipCollision(Ship ship)
    {
        if (ship.getVelocity() > 0 && asteroidImage.getBoundsInParent().intersects(ship.shipImage.getBoundsInParent()))
        {
            asteroidImage.setFill(Color.RED);
        }
    }

    public void createAsteroidCourse()
    {
        changeInX = Math.random();
        if (rand.nextInt(2) == 0)
        {
            changeInX = -changeInX;
        }
        changeInY = Math.random();
        if (rand.nextInt(2) == 0)
        {
            changeInY = -changeInY;
        }
    }

    public void setAsteroidPosition()
    {
        this.asteroidImage.setLayoutX(rand.nextInt(698)+1);
        this.asteroidImage.setLayoutY(rand.nextInt(698)+1);
    }



}