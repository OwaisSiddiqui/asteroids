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
    Line line1 = new Line();
    Line line2 = new Line();
    Line line3 = new Line();
    Line line4 = new Line();
    Line line5 = new Line();
    // This starts at the very bottom in the middle to make a polygon with an array. Think of it as the very first point
    // on the negative x-axis and negative y-axis like on the bottom of an addition sign then goes to the right
    // (counter clock-wise (left to right))
    Double[] polygonPointsArray = new Double[]
            {
                    0.0, 70.0,
                    20.0, 60.0,
                    50.0, 0.0,
                    25.0, -40.0,
                    -40.0, -13.0,
                    -30.0, 30.0
            };
    VectorAsteroids[] vectorAsteroids = new VectorAsteroids[6];
    VectorAsteroids[] vectorEdgesAsteroids = new VectorAsteroids[6];
    VectorAsteroids[] vectorAsteroidsVectorNormals = new VectorAsteroids[6];
    double[] projectionVectors = new double[6];

    Asteroid(Ship ship, Main main, int identificationNumber)
    {
        IdentificationNumber = identificationNumber;
        text.setText(String.valueOf(IdentificationNumber));
        this.ship = ship;
        this.main = main;
        asteroidImage = new Polygon();
        this.asteroidImage.getPoints().addAll(polygonPointsArray);
        this.asteroidImage.setFill(Color.TRANSPARENT);
        this.asteroidImage.setStroke(Color.BLACK);

        text.setFill(Color.PURPLE);
        text.setStyle("-fx-font: 24 arial;");

//        createAsteroidCourse();
        setAsteroidPosition();

        text.setLayoutX(asteroidImage.getLayoutX());
        text.setLayoutY(asteroidImage.getLayoutY());

        createAsteroidVectors();
        createAsteroidEdgeVectors();
//        createAsteroidVectorEdgeNormals();
//        projectVectorOntoNormal();

//        MoveAsteroid();

        line.setEndX(vectorAsteroids[0].Point1X);
        line.setEndY(vectorAsteroids[0].Point1Y);
        line.setStartX(vectorAsteroids[0].Point2X);
        line.setStartY(vectorAsteroids[0].Point2Y);

        line.setStroke(Color.RED);

        line1.setEndX(vectorAsteroids[1].Point1X);
        line1.setEndY(vectorAsteroids[1].Point1Y);
        line1.setStartX(vectorAsteroids[1].Point2X);
        line1.setStartY(vectorAsteroids[1].Point2Y);

        line1.setStroke(Color.LIMEGREEN);

        line2.setEndX(vectorAsteroids[2].Point1X);
        line2.setEndY(vectorAsteroids[2].Point1Y);
        line2.setStartX(vectorAsteroids[2].Point2X);
        line2.setStartY(vectorAsteroids[2].Point2Y);

        line2.setStroke(Color.BLUE);

        line3.setEndX(vectorAsteroids[3].Point1X);
        line3.setEndY(vectorAsteroids[3].Point1Y);
        line3.setStartX(vectorAsteroids[3].Point2X);
        line3.setStartY(vectorAsteroids[3].Point2Y);

        line3.setStroke(Color.GREEN);

        line4.setEndX(vectorAsteroids[4].Point1X);
        line4.setEndY(vectorAsteroids[4].Point1Y);
        line4.setStartX(vectorAsteroids[4].Point2X);
        line4.setStartY(vectorAsteroids[4].Point2Y);

        line4.setStroke(Color.ORANGE);

        line5.setEndX(vectorAsteroids[5].Point1X);
        line5.setEndY(vectorAsteroids[5].Point1Y);
        line5.setStartX(vectorAsteroids[5].Point2X);
        line5.setStartY(vectorAsteroids[5].Point2Y);

        line5.setStroke(Color.CYAN);

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
//                DetectBulletCollision(ship);
//                DetectShipCollision(ship);
//                bumpAsteroid();
                text.setLayoutX(asteroidImage.getLayoutX());
                text.setLayoutY(asteroidImage.getLayoutY());
            }
        };
        timer.start();
    }

//    public void bumpAsteroid()
//    {
//        for (int x = 0; x < 5; x++)
//        {
//            if (main.asteroidArray[x] != null && main.asteroidArray[x].IdentificationNumber == this.IdentificationNumber && isCollision)
//            {
//                isCollision = false;
//            }
//            else if (main.asteroidArray[x] != null && main.asteroidArray[x].IdentificationNumber != this.IdentificationNumber && this.asteroidImage.getBoundsInParent().intersects(main.asteroidArray[x].asteroidImage.getBoundsInParent()))
//            {
//                System.out.println("COLLISION WITH ASTEROID "+main.asteroidArray[x].IdentificationNumber+" WITH ASTEROID "+this.IdentificationNumber);
//                this.asteroidImage.setFill(Color.color(Math.random(), Math.random(), Math.random()));
//                main.asteroidArray[x].asteroidImage.setFill(Color.color(Math.random(), Math.random(), Math.random()));
//                changeInX = -changeInX;
//                changeInY = -changeInY;
//            }
//        }
//    }
//
//    public void DetectBulletCollision(Ship ship)
//    {
//        if(ship.bulletZero.isBulletFired() && asteroidImage.getBoundsInParent().intersects(ship.bulletZero.bulletImage.getBoundsInParent()) && ship.bulletZero.isBulletFired())
//        {
//            asteroidImage.setFill(Color.RED);
//            ship.bulletZero.stopBullet();
//        }
//        else if (ship.bulletOne.isBulletFired() && asteroidImage.getBoundsInParent().intersects(ship.bulletOne.bulletImage.getBoundsInParent()) && ship.bulletOne.isBulletFired())
//        {
//            asteroidImage.setFill(Color.RED);
//            ship.bulletOne.stopBullet();
//        }
//        else if (ship.bulletTwo.isBulletFired() && asteroidImage.getBoundsInParent().intersects(ship.bulletTwo.bulletImage.getBoundsInParent()) && ship.bulletTwo.isBulletFired())
//        {
//            asteroidImage.setFill(Color.RED);
//            ship.bulletTwo.stopBullet();
//        }
//        else if (ship.bulletThree.isBulletFired() && asteroidImage.getBoundsInParent().intersects(ship.bulletThree.bulletImage.getBoundsInParent()))
//        {
//            asteroidImage.setFill(Color.RED);
//            ship.bulletThree.stopBullet();
//        }
//        else if (ship.bulletFour.isBulletFired() && asteroidImage.getBoundsInParent().intersects(ship.bulletFour.bulletImage.getBoundsInParent()))
//        {
//            asteroidImage.setFill(Color.RED);
//            ship.bulletFour.stopBullet();
//        }
//    }
//
//    public void DetectShipCollision(Ship ship)
//    {
//        if (ship.getVelocity() > 0 && asteroidImage.getBoundsInParent().intersects(ship.shipImage.getBoundsInParent()))
//        {
//            asteroidImage.setFill(Color.RED);
//        }
//    }
//
//    public void createAsteroidCourse()
//    {
//        changeInX = Math.random();
//        if (rand.nextInt(2) == 0)
//        {
//            changeInX = -changeInX;
//        }
//        changeInY = Math.random();
//        if (rand.nextInt(2) == 0)
//        {
//            changeInY = -changeInY;
//        }
//    }
//
    public void setAsteroidPosition()
    {
        this.asteroidImage.setLayoutX(350);
        this.asteroidImage.setLayoutY(350);
    }

    //Separating Axis Theorem

    public void createAsteroidVectors()
    {
        for (int i = 0, idx = 0; i < 11 && idx <= 6; i+=2, idx++)
        {
            vectorAsteroids[idx] = new VectorAsteroids(
                    asteroidImage.getLayoutX(),
                    asteroidImage.getLayoutY(),
                    asteroidImage.getPoints().get(i)+asteroidImage.getLayoutX(),
                    asteroidImage.getPoints().get(i+1)+asteroidImage.getLayoutY());
            System.out.print(vectorAsteroids[idx].Point2X-asteroidImage.getLayoutX()+" ");
            System.out.println(vectorAsteroids[idx].Point2Y-asteroidImage.getLayoutY());
        }
    }

    public void createAsteroidEdgeVectors()
    {
        for (int i = 0, idx = 0; i < 9 && idx < 6; i+=4, idx++)
        {
            vectorEdgesAsteroids[i] = new VectorAsteroids(asteroidImage.getPoints().get(i), asteroidImage.getPoints().get(i+1),
                    asteroidImage.getPoints().get(i+2), asteroidImage.getPoints().get(i+3));
            i++;
        }
    }
//
//    public void createAsteroidVectorEdgeNormals()
//    {
//        double vectorChangeInX = 0;
//        double vectorChangeInY = 0;
//        int i = 0;
//        while (i < vectorAsteroidsVectorNormals.length)
//        {
//            vectorChangeInX = vectorEdgesAsteroids[i].Point2X - vectorEdgesAsteroids[i].Point1X;
//            vectorChangeInY = vectorEdgesAsteroids[i].Point2Y - vectorEdgesAsteroids[i].Point1Y;
//            vectorAsteroidsVectorNormals[i] = new VectorAsteroids(-vectorChangeInX, vectorChangeInX, vectorChangeInY, -vectorChangeInY);
//        }
//    }
//
//    public void projectVectorOntoNormal()
//    {
//        int i = 0;
//        while (i < vectorEdgesAsteroids.length)
//        {
//            projectionVectors[i] = getDotProduct(vectorAsteroids[i], vectorAsteroidsVectorNormals[i]);
//        }
//    }
//
//    public double getDotProduct(VectorAsteroids vector1, VectorAsteroids vector2)
//    {
//        return vector1.Point1X*vector1.Point2X + vector2.Point1Y*vector2.Point2Y;
//    }

}