package sample;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.Arrays;
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
    VectorAsteroids[] vectorAsteroidsVectorEdgeNormals = new VectorAsteroids[6];
    VectorAsteroids[] projectionVectors = new VectorAsteroids[6];

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
        createAsteroidVectorEdgeNormals();
        projectVectorOntoNormal();

//        MoveAsteroid();

        line.setEndX(projectionVectors[0].Point1X);
        line.setEndY(projectionVectors[0].Point1Y);
        line.setStartX(projectionVectors[0].Point2X);
        line.setStartY(projectionVectors[0].Point2Y);

        line.setStroke(Color.RED);

        line1.setEndX(projectionVectors[1].Point1X);
        line1.setEndY(projectionVectors[1].Point1Y);
        line1.setStartX(projectionVectors[1].Point2X);
        line1.setStartY(projectionVectors[1].Point2Y);

        line1.setStroke(Color.LIMEGREEN);

        line2.setEndX(projectionVectors[2].Point1X);
        line2.setEndY(projectionVectors[2].Point1Y);
        line2.setStartX(projectionVectors[2].Point2X);
        line2.setStartY(projectionVectors[2].Point2Y);

        line2.setStroke(Color.BLUE);

        line3.setEndX(projectionVectors[3].Point1X);
        line3.setEndY(projectionVectors[3].Point1Y);
        line3.setStartX(projectionVectors[3].Point2X);
        line3.setStartY(projectionVectors[3].Point2Y);

        line3.setStroke(Color.GREEN);

        line4.setEndX(projectionVectors[4].Point1X);
        line4.setEndY(projectionVectors[4].Point1Y);
        line4.setStartX(projectionVectors[4].Point2X);
        line4.setStartY(projectionVectors[4].Point2Y);

        line4.setStroke(Color.ORANGE);

        line5.setEndX(projectionVectors[5].Point1X);
        line5.setEndY(projectionVectors[5].Point1Y);
        line5.setStartX(projectionVectors[5].Point2X);
        line5.setStartY(projectionVectors[5].Point2Y);

        line5.setStroke(Color.CYAN);
//
//        VectorAsteroids vector1 = new VectorAsteroids(-5, 10, -5, 1);
//        VectorAsteroids vector2 = new VectorAsteroids(4, 3, 10,3);
//        System.out.println(Arrays.toString(projectionVectors));

//        System.out.println();
//        System.out.println("EXACT: ");
//        getDotProduct(vectorAsteroidsVectorEdgeNormals[0], vectorAsteroidsVectorEdgeNormals[0], 2);
//        System.out.println("EXACT SAME?: "+Math.pow(vectorAsteroidsVectorEdgeNormals[0].magnitude, 2));
////        System.out.println("This dot product should be: "+getDotProduct(vector1, vector2));


        for (int i = 0; i <= 5; i++)
        {
            projectionVectors[i].printVectorAsteroids();
        }

        System.out.println("ORIGINAL: ");
        vectorAsteroidsVectorEdgeNormals[0].printVectorAsteroids();

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
        for (int i = 0, idx = 0; i <= 10 && idx <= 5; i+=2, idx++)
        {
            vectorAsteroids[idx] = new VectorAsteroids(
                    asteroidImage.getLayoutX(),
                    asteroidImage.getLayoutY(),
                    asteroidImage.getPoints().get(i)+asteroidImage.getLayoutX(),
                    asteroidImage.getPoints().get(i+1)+asteroidImage.getLayoutY());
        }
    }

    public void createAsteroidEdgeVectors()
    {
        for (int i = 0, idx = 0; i <= 10 && idx <= 5; i+=2, idx++)
        {
//            System.out.println(idx);
           if (i <= 8)
           {
               vectorEdgesAsteroids[idx] = new VectorAsteroids(
                       asteroidImage.getPoints().get(i)+asteroidImage.getLayoutX(),
                       asteroidImage.getPoints().get(i+1)+asteroidImage.getLayoutY(),
                       asteroidImage.getPoints().get(i+2)+asteroidImage.getLayoutX(),
                       asteroidImage.getPoints().get(i+3)+asteroidImage.getLayoutY());
           }
           // need an else statement because for the last one the last point needs to go the the very beginning
           // and also loop will go over 10
           else if (i == 10)
           {
               vectorEdgesAsteroids[idx] = new VectorAsteroids(
                       asteroidImage.getPoints().get(i)+asteroidImage.getLayoutX(),
                       asteroidImage.getPoints().get(i+1)+asteroidImage.getLayoutY(),
                       asteroidImage.getPoints().get(0)+asteroidImage.getLayoutX(),
                       asteroidImage.getPoints().get(1)+asteroidImage.getLayoutY());
           }
        }
    }

    public void createAsteroidVectorEdgeNormals()
    {
        double vectorChangeInX;
        double vectorChangeInY;
        int i = 0;
        while (i <= 5)
        {
            vectorChangeInX = vectorEdgesAsteroids[i].Point2X - vectorEdgesAsteroids[i].Point1X;
            vectorChangeInY = vectorEdgesAsteroids[i].Point2Y - vectorEdgesAsteroids[i].Point1Y;
            // Formula to get normal vector for cartesian coordinates found on StackOverflow
            vectorAsteroidsVectorEdgeNormals[i] = new VectorAsteroids(
                    -vectorChangeInY+asteroidImage.getLayoutX(),
                    vectorChangeInX+asteroidImage.getLayoutY(),
                    vectorChangeInY+asteroidImage.getLayoutX(),
                    -vectorChangeInX+asteroidImage.getLayoutY());
            if (i == 0)
            {
//                System.out.println("VERY ORIGINAL VECTOR: ");
//                vectorAsteroidsVectorEdgeNormals[i].printVectorAsteroids();
            }
            i++;
        }
    }

    public void projectVectorOntoNormal()
    {
        int i = 0;
        while (i <= 5)
        {
//            System.out.println();
//            System.out.print("ORIGINAL (PROJECTION): ");
//            vectorAsteroidsVectorEdgeNormals[0].printVectorAsteroids();
//             The projection is a scalar, although can be a vector, but is not needed as that just tells the direction
//             but the magnitude it only needed (more on Paint)
            projectionVectors[i] = multiplyVector(vectorAsteroidsVectorEdgeNormals[0], (getDotProduct(vectorAsteroids[i],
                    vectorAsteroidsVectorEdgeNormals[0], 1)/
                    getDotProduct(vectorAsteroidsVectorEdgeNormals[0], vectorAsteroidsVectorEdgeNormals[0], 2)), i);
            System.out.println("Magnitude squared: "+getDotProduct(vectorAsteroids[i],
                    vectorAsteroidsVectorEdgeNormals[0], 1)/
                    getDotProduct(vectorAsteroidsVectorEdgeNormals[0], vectorAsteroidsVectorEdgeNormals[0], 2));
            i++;
        }
    }

    public double getDotProduct(VectorAsteroids vector1, VectorAsteroids vector2, int i)
    {
//        System.out.println();
//        System.out.println("*** DOT PRODUCT ***");
//        vector1.printVectorAsteroids();
//        vector2.printVectorAsteroids();
//        if (i == 1)
//        {
//            System.out.println("With new vector: ");
//        }
//        else
//        {
//            System.out.println("All same: ");
//        }
        double ax = vector1.Point2X-vector1.Point1X;
        double ay = vector1.Point2Y-vector1.Point1Y;

        double bx = vector2.Point2X-vector2.Point1X;
        double by = vector2.Point2Y-vector2.Point1Y;

//        System.out.println(vector1.Point2X-vector1.Point1X);

//        System.out.println("Ax: "+ax+" Ay: "+ay+" Bx: "+bx+" By: "+by);

        double dotProduct = (ax*bx) + (ay*by);
//            System.out.println("THE DOT PRODUCT: "+dotProduct);
        return dotProduct;
    }

    public VectorAsteroids multiplyVector(VectorAsteroids vector, double magnitude, int i)
    {
//        System.out.println();
//        System.out.println("MULTIPLY VECTOR: ");
//        System.out.println("MAGNITUDE: "+magnitude);
//        System.out.println("BEFORE: ("+vectorAsteroids[i].Point1X+", "+
//                vectorAsteroids[i].Point1Y+") | ("+vectorAsteroids[i].Point2X+", "+
//                vectorAsteroids[i].Point2Y+")");
//        vector.Point1X = vector.Point1X*magnitude;
//        System.out.print("NEW: ("+vector.Point1X+", ");
//        vector.Point1Y = vector.Point1Y*magnitude;
//        System.out.print(vector.Point1Y+") ");
//        vector.Point2X = vector.Point2X*magnitude;
//        System.out.print("| ("+vector.Point2X+",  ");
//        vector.Point2Y = vector.Point2Y*magnitude;
//        System.out.println(vector.Point2Y+")");

        return new VectorAsteroids(vector.Point1X*magnitude, vector.Point1Y*magnitude, vector.Point2X*magnitude, vector.Point2Y*magnitude);
    }

}