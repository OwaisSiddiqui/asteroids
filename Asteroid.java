package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.Random;

public class Asteroid
{
    Ship ship;
    Polygon asteroidImage;
    Main main;
    int IdentificationNumber;
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
    Line[] lineArray = new Line[6];
    Line[] newLineArray = new Line[6];
    Color[] colorArray = new Color[6];
    Text text = new Text();
    double changeInX;
    double changeInY;
    boolean isCollision = true;
    Random rand = new Random();
    AnimationTimer timer;

    Asteroid(Ship ship, Main main, int identificationNumber)
    {
        this.IdentificationNumber = identificationNumber;

        this.ship = ship;
        this.main = main;

        this.asteroidImage = new Polygon();
        // Create the asteroid polygon shape
        this.asteroidImage.getPoints().addAll(this.polygonPointsArray);
        this.asteroidImage.setFill(Color.TRANSPARENT);
        this.asteroidImage.setStroke(Color.BLACK);

        createAsteroidCourse();
        setAsteroidPosition();

        text.setLayoutX(asteroidImage.getLayoutX());
        text.setLayoutY(asteroidImage.getLayoutY());

        line.setEndX((changeInX+asteroidImage.getLayoutX())*10);
        line.setEndY((changeInY+asteroidImage.getLayoutY())*10);
        line.setStartX((changeInX+asteroidImage.getLayoutX()));
        line.setStartY((changeInY+asteroidImage.getLayoutY()));

        MoveAsteroid();

        this.initializeVectors();
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
                keepAsteroidInBounds();
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
            else if (main.asteroidArray[x] != null && main.asteroidArray[x].IdentificationNumber != this.IdentificationNumber && this.isCollision(main.asteroidArray[x]))
            {
                System.out.println("COLLISION WITH ASTEROID "+main.asteroidArray[x].IdentificationNumber+" WITH ASTEROID "+this.IdentificationNumber);
                this.asteroidImage.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                main.asteroidArray[x].asteroidImage.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                changeInX = -changeInX;
                changeInY = -changeInY;
            }
        }
    }

    public void keepAsteroidInBounds()
    {
        if (this.asteroidImage.getLayoutX() < 50 || this.asteroidImage.getLayoutX() > 650 || this.asteroidImage.getLayoutY() > 650 || this.asteroidImage.getLayoutY() < 50)
        {
            changeInX = -changeInX;
            changeInY = -changeInY;
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

    public void createAsteroidVectors()
    {
        for (int i = 0, idx = 0; i <= 10 && idx <= 5; i+=2, idx++)
        {
            this.vectorAsteroids[idx] = new VectorAsteroids(
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
            if (i <= 8)
            {
                vectorEdgesAsteroids[idx] = new VectorAsteroids(
                        asteroidImage.getPoints().get(i)+asteroidImage.getLayoutX(),
                        asteroidImage.getPoints().get(i+1)+asteroidImage.getLayoutY(),
                        asteroidImage.getPoints().get(i+2)+asteroidImage.getLayoutX(),
                        asteroidImage.getPoints().get(i+3)+asteroidImage.getLayoutY());
            }
            else if (i == 10)
            {
                this.vectorEdgesAsteroids[idx] = new VectorAsteroids(
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
            vectorChangeInX = vectorEdgesAsteroids[i].TerminalPointX - vectorEdgesAsteroids[i].InitialPointX;
            vectorChangeInY = vectorEdgesAsteroids[i].TerminalPointY - vectorEdgesAsteroids[i].InitialPointY;
            vectorAsteroidsVectorEdgeNormals[i] = new VectorAsteroids(
                    asteroidImage.getLayoutX(),
                    asteroidImage.getLayoutY(),
                    vectorChangeInY+asteroidImage.getLayoutX(),
                    -vectorChangeInX+asteroidImage.getLayoutY());
//            vectorAsteroidsVectorEdgeNormals[i].normalizeVector();
            vectorAsteroidsVectorEdgeNormals[i].moveVectorToPoint(this.asteroidImage.getLayoutX(), this.asteroidImage.getLayoutY());
            i++;
        }
    }

    public VectorAsteroids[] projectVectorOntoNormal(Asteroid asteroid1, Asteroid asteroid2, int vectorAsteroidVectorEdgeNormalsIndex)
    {
        VectorAsteroids[] projectionVectorArray = new VectorAsteroids[6];
        for (int i = 0; i <= 5; i++)
        {
            projectionVectorArray[i] = multiplyVector(asteroid2.vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex]
                    ,(getDotProduct(asteroid1.vectorAsteroids[i],
                            asteroid2.vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex])/
                            getDotProduct(asteroid2.vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex],
                                    asteroid2.vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex])));
            projectionVectorArray[i].moveVectorToPoint(asteroid1.asteroidImage.getLayoutX(), asteroid1.asteroidImage.getLayoutY());
        }
        double finalMagnitude;
        double signOfX = 1;
        double signOfY = 1;
        VectorAsteroids[] newProjectionVectors;
        VectorAsteroids[] newVectorAsteroidVectorEdgesNormals;
        VectorAsteroids[] newVectorAsteroidVectorEdges;
        VectorAsteroids newVectorEdgeNormal;
        VectorAsteroids newVectorEdge = new VectorAsteroids(0, 0, 0, 0);
        newProjectionVectors = projectionVectorArray;
        newVectorAsteroidVectorEdgesNormals = asteroid2.vectorAsteroidsVectorEdgeNormals;
        newVectorAsteroidVectorEdges = asteroid2.vectorEdgesAsteroids;
        newVectorEdgeNormal = newVectorAsteroidVectorEdgesNormals[vectorAsteroidVectorEdgeNormalsIndex];
        newVectorEdge = newVectorAsteroidVectorEdges[vectorAsteroidVectorEdgeNormalsIndex];

        newVectorEdge.normalizeVector();
        double newVectorEdgeNormalB = newVectorEdgeNormal.b;
        double newVectorProjectionSlope = newProjectionVectors[0].slope;

        finalMagnitude = Math.abs(newProjectionVectors[0].b - newVectorEdgeNormalB)/
                Math.sqrt(1+Math.pow(newVectorProjectionSlope, 2));
        newProjectionVectors[0].moveVectorToAxis(newVectorEdge, finalMagnitude, signOfX, signOfY);
        if (Math.round(Math.abs(newProjectionVectors[0].b - newVectorEdgeNormalB)/
                Math.sqrt(1+Math.pow(newVectorProjectionSlope, 2))) != 0)
        {
            newProjectionVectors[0].moveVectorToAxis(newVectorEdge, finalMagnitude, -1, -1);
            newProjectionVectors[0].moveVectorToAxis(newVectorEdge, finalMagnitude, 1, -1);
            signOfX = 1;
            signOfY = -1;
        }
        if (Math.round(Math.abs(newProjectionVectors[0].b - newVectorEdgeNormalB)/
                Math.sqrt(1+Math.pow(newVectorProjectionSlope, 2))) != 0)
        {
            newProjectionVectors[0].moveVectorToAxis(newVectorEdge, finalMagnitude, -1, 1);
            newProjectionVectors[0].moveVectorToAxis(newVectorEdge, finalMagnitude, -1, 1);
            signOfX = -1;
            signOfY = 1;

        }
        if (Math.round(Math.abs(newProjectionVectors[0].b - newVectorEdgeNormalB)/
                Math.sqrt(1+Math.pow(newVectorProjectionSlope, 2))) != 0)
        {
            newProjectionVectors[0].moveVectorToAxis(newVectorEdge, finalMagnitude, 1, -1);
            newProjectionVectors[0].moveVectorToAxis(newVectorEdge, finalMagnitude, -1, -1);

            signOfX = -1;
            signOfY = -1;
        }

        for (int j = 1; j < 6; j++)
        {
            projectionVectorArray[j].moveVectorToAxis(newVectorEdge, finalMagnitude, signOfX, signOfY);
        }

        return projectionVectorArray;
    }

    public Double[] projectVectorOntoNormalScalars(Asteroid asteroid1, Asteroid asteroid2, int vectorAsteroidVectorEdgeNormalsIndex)
    {
        int i = 0;
        Double[] projectionScalarsArray = new Double[6];
        while (i <= 5)
        {
            projectionScalarsArray[i] = getDotProduct(vectorAsteroids[i],
                    asteroid2.vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex])/
                    getDotProduct(asteroid2.vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex],
                            asteroid2.vectorAsteroidsVectorEdgeNormals[vectorAsteroidVectorEdgeNormalsIndex]);
            i++;
        }

        return projectionScalarsArray;
    }

    public double getDotProduct(VectorAsteroids vector1, VectorAsteroids vector2)
    {
        double ax = vector1.TerminalPointX-vector1.InitialPointX;
        double ay = vector1.TerminalPointY-vector1.InitialPointY;

        double bx = vector2.TerminalPointX-vector2.InitialPointX;
        double by = vector2.TerminalPointY-vector2.InitialPointY;

        return (ax*bx) + (ay*by);
    }

    public VectorAsteroids multiplyVector(VectorAsteroids vector, double magnitude)
    {
        return new VectorAsteroids(
                vector.InitialPointX*magnitude, vector.InitialPointY*magnitude,
                vector.TerminalPointX*magnitude, vector.TerminalPointY*magnitude);
    }

    public double findMax(VectorAsteroids[] thisProjectionVectors, Double[] thisProjectionScalars, Character component)
    {
        double max = thisProjectionScalars[0];
        int maxIdx = 0;
        for (int i = 0; i < 6; i++)
        {
            if (thisProjectionScalars[i] > max)
            {
                max  = thisProjectionScalars[i];
                maxIdx = i;
            }
        }
        double min = thisProjectionScalars[0];
        int minIdx = 0;
        for (int i = 0; i < 6; i++)
        {
            if (thisProjectionScalars[i] < min)
            {
                min  = thisProjectionScalars[i];
                minIdx = i;
            }
        }
        if (component.equals("x"))
        {
            if (thisProjectionVectors[maxIdx].TerminalPointX > thisProjectionVectors[minIdx].TerminalPointX)
            {
                return thisProjectionVectors[maxIdx].TerminalPointX;
            }
            else
            {
                return thisProjectionVectors[minIdx].TerminalPointX;
            }
        }
        else
        {
            if (thisProjectionVectors[maxIdx].TerminalPointY > thisProjectionVectors[minIdx].TerminalPointY)
            {
                return thisProjectionVectors[maxIdx].TerminalPointY;
            }
            else
            {
                return thisProjectionVectors[minIdx].TerminalPointY;
            }
        }
    }

    public double findMin(VectorAsteroids[] thisProjectionVectors, Double[] thisProjectionScalars, Character component)
    {
        double max = thisProjectionScalars[0];
        int maxIdx = 0;
        for (int i = 0; i < 6; i++)
        {
            if (thisProjectionScalars[i] > max)
            {
                max  = thisProjectionScalars[i];
                maxIdx = i;
            }
        }
        double min = thisProjectionScalars[0];
        int minIdx = 0;
        for (int i = 0; i < 6; i++)
        {
            if (thisProjectionScalars[i] < min)
            {
                min  = thisProjectionScalars[i];
                minIdx = i;
            }
        }
        if (component.equals("x"))
        {
            if (thisProjectionVectors[maxIdx].TerminalPointX < thisProjectionVectors[minIdx].TerminalPointX)
            {
                return thisProjectionVectors[maxIdx].TerminalPointX;
            }
            else
            {
                return thisProjectionVectors[minIdx].TerminalPointX;
            }
        }
        else
        {
            if (thisProjectionVectors[maxIdx].TerminalPointY < thisProjectionVectors[minIdx].TerminalPointY)
            {
                return thisProjectionVectors[maxIdx].TerminalPointY;
            }
            else
            {
                return thisProjectionVectors[minIdx].TerminalPointY;
            }
        }
    }

    public boolean isCollision(Asteroid asteroid)
    {
        for (int axis = 0; axis < this.vectorEdgesAsteroids.length; axis++)
        {
            VectorAsteroids[] thisProjectionVectors = this.projectVectorOntoNormal(this, this, axis);
            VectorAsteroids[] asteroidProjectionVectors = this.projectVectorOntoNormal(asteroid, this, axis);
            Double[] thisProjectionScalars = this.projectVectorOntoNormalScalars(this, this, axis);
            Double[] asteroidProjectionScalars = this.projectVectorOntoNormalScalars(asteroid, this, axis);
            double max = this.findMax(thisProjectionVectors, thisProjectionScalars, 'x');
            double min = this.findMin(thisProjectionVectors, thisProjectionScalars, 'x');
            double max2 = asteroid.findMax(asteroidProjectionVectors, asteroidProjectionScalars, 'x');
            double min2 = asteroid.findMin(asteroidProjectionVectors, asteroidProjectionScalars, 'x');
            if (max < min2)
            {
                return false;
            }
            if (max2 < min)
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