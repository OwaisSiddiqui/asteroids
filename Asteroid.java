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
    Main main;
    int IdentificationNumber;
    Text text = new Text();
    Line line = new Line();
    Line line1 = new Line();
    Line line2 = new Line();
    Line line3 = new Line();
    Line line4 = new Line();
    Line line5 = new Line();
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
    double[] projectionScalars = new double[6];

    Asteroid(Ship ship, Main main, int identificationNumber, int x, int y)
    {
        this.IdentificationNumber = identificationNumber;
        main.asteroidArray[this.IdentificationNumber] = this;

        this.ship = ship;
        this.main = main;

        this.asteroidImage = new Polygon();
        this.asteroidImage.getPoints().addAll(polygonPointsArray);
        this.asteroidImage.setFill(Color.TRANSPARENT);
        this.asteroidImage.setStroke(Color.BLACK);

        this.createAsteroidCourse();
        this.setAsteroidPosition(x, y);
        this.initializeVectors();

        this.bumpAsteroid();
    }

    public void bumpAsteroid()
    {
        for (int x = 3; x <= 4; x++)
        {
            if (main.asteroidArray[x] != null && main.asteroidArray[x].IdentificationNumber != this.IdentificationNumber
            && isCollision(main.asteroidArray[x]))
            {
                this.asteroidImage.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            }
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

    public void setAsteroidPosition(int x, int y)
    {
        this.asteroidImage.setLayoutX(x);
        this.asteroidImage.setLayoutY(y);
    }

    public void createAsteroidVectors()
    {
        for (int i = 0, idx = 0; i <= 10 && idx <= 5; i+=2, idx++)
        {
            this.vectorAsteroids[idx] = new VectorAsteroids(
                    this.asteroidImage.getLayoutX(),
                    this.asteroidImage.getLayoutY(),
                    this.asteroidImage.getPoints().get(i)+this.asteroidImage.getLayoutX(),
                    this.asteroidImage.getPoints().get(i+1)+this.asteroidImage.getLayoutY());
        }
    }

    public void createAsteroidEdgeVectors()
    {
        for (int i = 0, idx = 0; i <= 10 && idx <= 5; i+=2, idx++)
        {
           if (i <= 8)
           {
               this.vectorEdgesAsteroids[idx] = new VectorAsteroids(
                       this.asteroidImage.getPoints().get(i)+this.asteroidImage.getLayoutX(),
                       this.asteroidImage.getPoints().get(i+1)+this.asteroidImage.getLayoutY(),
                       this.asteroidImage.getPoints().get(i+2)+this.asteroidImage.getLayoutX(),
                       this.asteroidImage.getPoints().get(i+3)+this.asteroidImage.getLayoutY());
           }
           else if (i == 10)
           {
               this.vectorEdgesAsteroids[idx] = new VectorAsteroids(
                       this.asteroidImage.getPoints().get(i)+this.asteroidImage.getLayoutX(),
                       this.asteroidImage.getPoints().get(i+1)+this.asteroidImage.getLayoutY(),
                       this.asteroidImage.getPoints().get(0)+this.asteroidImage.getLayoutX(),
                       this.asteroidImage.getPoints().get(1)+this.asteroidImage.getLayoutY());
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
            vectorChangeInX = this.vectorEdgesAsteroids[i].Point2X - this.vectorEdgesAsteroids[i].Point1X;
            vectorChangeInY = this.vectorEdgesAsteroids[i].Point2Y - this.vectorEdgesAsteroids[i].Point1Y;
            this.vectorAsteroidsVectorEdgeNormals[i] = new VectorAsteroids(
                    -vectorChangeInY+this.asteroidImage.getLayoutX(),
                    vectorChangeInX+this.asteroidImage.getLayoutY(),
                    vectorChangeInY+this.asteroidImage.getLayoutX(),
                    -vectorChangeInX+this.asteroidImage.getLayoutY());
            i++;
        }
    }

    public void projectVectorOntoNormal(int vectorAsteroidVectorEdgeNormalsIndex)
    {
        int i = 0;
        while (i <= 5)
        {
            projectionVectors[i] = multiplyVector(vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex]
                    ,(getDotProduct(vectorAsteroids[i],
                    vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex])/
                    getDotProduct(vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex],
                            vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex])));
            projectionScalars[i] = getDotProduct(vectorAsteroids[i],
                    vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex])/
                    getDotProduct(vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex],
                            vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex]);
            i++;
        }
    }

    public double getDotProduct(VectorAsteroids vector1, VectorAsteroids vector2)
    {
        double ax = vector1.Point2X-vector1.Point1X;
        double ay = vector1.Point2Y-vector1.Point1Y;

        double bx = vector2.Point2X-vector2.Point1X;
        double by = vector2.Point2Y-vector2.Point1Y;

        return (ax*bx) + (ay*by);
    }

    public VectorAsteroids multiplyVector(VectorAsteroids vector, double magnitude)
    {
        return new VectorAsteroids(
                vector.Point1X*magnitude, vector.Point1Y*magnitude,
                vector.Point2X*magnitude, vector.Point2Y*magnitude);
    }

    public VectorAsteroids findMax(double[] projectionScalarsArray)
    {
        double max = projectionScalarsArray[0];
        int maxIdx = 0;
        for (int i = 1; i < 6; i++)
        {
            if (projectionScalarsArray[i] > max)
            {
                max = projectionScalarsArray[i];
                maxIdx = i;
            }
        }
        return this.projectionVectors[maxIdx];
    }

    public VectorAsteroids findMin(double[] projectionScalarsArray)
    {
        double min = projectionScalarsArray[0];
        int minIdx = 0;
        for (int i = 1; i < 6; i++)
        {
            if (projectionScalarsArray[i] < min)
            {
                min = projectionScalarsArray[i];
                minIdx = i;
            }
        }
        return this.projectionVectors[minIdx];
    }

    public boolean isSeparatingAxis(Asteroid asteroid1, Asteroid asteroid2)
    {
        VectorAsteroids vector1Max = findMax(asteroid1.projectionScalars);
        VectorAsteroids vector1Min = findMin(asteroid1.projectionScalars);
        VectorAsteroids vector2Max = findMax(asteroid2.projectionScalars);
        VectorAsteroids vector2Min = findMin(asteroid2.projectionScalars);

        for (int i = 0; i < 6; i++)
        {
            vectorAsteroidsVectorEdgeNormals[i].moveVectorToPoint(asteroid1);
            vectorAsteroidsVectorEdgeNormals[i].normalizeVector();
            vectorAsteroidsVectorEdgeNormals[i].moveVectorToPoint(asteroid1);
            projectionVectors[i].moveVectorToPoint(asteroid1);
        }

        if (!(vector1Max.Point1X > vector2Max.Point1X) || !(vector1Max.Point2X > vector2Max.Point2X))
        {
            return true;
        }
        else if (!(vector1Max.Point1Y > vector2Max.Point1Y) || !(vector1Max.Point2Y > vector2Max.Point2Y))
        {
            return true;
        }
        return false;

    }

    public boolean isCollision(Asteroid asteroid)
    {
        for (int x = 0; x < 6; x++)
        {
            projectVectorOntoNormal(x);
            if (isSeparatingAxis(this, asteroid))
            {
                return false;
            }
        }
        return true;
    }

    public void initializeVectors()
    {
        this.createAsteroidVectors();
        this.createAsteroidEdgeVectors();
        this.createAsteroidVectorEdgeNormals();
    }

}
