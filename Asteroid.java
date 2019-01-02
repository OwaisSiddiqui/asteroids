package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

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

    Asteroid(Ship ship, Main main, int identificationNumber, int x, int y)
    {
        this.IdentificationNumber = identificationNumber;

        this.ship = ship;
        this.main = main;

        this.asteroidImage = new Polygon();
        this.asteroidImage.getPoints().addAll(polygonPointsArray);
        this.asteroidImage.setFill(Color.TRANSPARENT);
        this.asteroidImage.setStroke(Color.BLACK);

        setAsteroidPosition(x, y);
        this.initializeVectors();

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
           newLineArray[i].setEndX(vectorAsteroidsVectorEdgeNormals[i].InitialPointX);
           newLineArray[i].setEndY(vectorAsteroidsVectorEdgeNormals[i].InitialPointY);
           newLineArray[i].setStartX(vectorAsteroidsVectorEdgeNormals[i].TerminalPointX);
           newLineArray[i].setStartY(vectorAsteroidsVectorEdgeNormals[i].TerminalPointY);

           newLineArray[i].setStroke(colorArray[i]);

           lineArray[i].setEndX(getXComponent(vectorAsteroidsVectorEdgeNormals[i]).InitialPointX);
           lineArray[i].setEndY(getXComponent(vectorAsteroidsVectorEdgeNormals[i]).InitialPointY);
           lineArray[i].setStartX(getXComponent(vectorAsteroidsVectorEdgeNormals[i]).TerminalPointX);
           lineArray[i].setStartY(getXComponent(vectorAsteroidsVectorEdgeNormals[i]).TerminalPointY);
           lineArray[i].setStroke(colorArray[i]);
       }

    }

    public void moveAsteroid()
    {
        bumpAsteroid();
    }

    public void bumpAsteroid()
    {
        for (int x = 3; x <= 4; x++)
        {
            System.out.println(IdentificationNumber);
            System.out.println(main.asteroidArray[x]);
            if (main.asteroidArray[x] != null && main.asteroidArray[x].IdentificationNumber != IdentificationNumber
            && this.isCollision(main.asteroidArray[x]))
            {
//                asteroidImage.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            }
        }
    }

    public void setAsteroidPosition(int x, int y)
    {
        asteroidImage.setLayoutX(x);
        asteroidImage.setLayoutY(y);
    }

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
//            vectorAsteroidsVectorEdgeNormals[i].moveVectorToPoint(this);
            vectorAsteroidsVectorEdgeNormals[i].printVectorAsteroids();
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
            projectionVectors[i].moveVectorToPoint(this);
            i++;
        }
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

//    public VectorAsteroids findMaxMagnitudeVector()
//    {
//        double max = this.projectionScalars[0];
//        int maxIdx = 0;
//        for (int i = 1; i < 6; i++)
//        {
//            if (this.projectionScalars[i] > max)
//            {
//                max = this.projectionScalars[i];
//                maxIdx = i;
//            }
//        }
//        if (projectionVectors[maxIdx].TerminalPointX > this.findMinMagnitudeVector().TerminalPointX)
//        {
////            return
//        }
//    }

    public VectorAsteroids findMinMagnitudeVector()
    {
        double min = this.projectionScalars[0];
        int minIdx = 0;
        for (int i = 1; i < 6; i++)
        {
            if (this.projectionScalars[i] < min)
            {
                min = this.projectionScalars[i];
                minIdx = i;
            }
        }
        return this.projectionVectors[minIdx];
    }

    public VectorAsteroids getXComponent(VectorAsteroids vector)
    {
        return new VectorAsteroids(
                vector.InitialPointX,
                vector.InitialPointY,
                vector.TerminalPointX,
                vector.InitialPointY);
    }

    public double getXComponentPoint(VectorAsteroids vector)
    {
        return vector.TerminalPointX;
    }

    public boolean isSeparatingAxis(Asteroid asteroid, int axisNumber)
    {
        return false;
    }

    public boolean isCollision(Asteroid asteroid)
    {
        for (int axisNumber = 0; axisNumber < 6; axisNumber++)
        {
            this.projectVectorOntoNormal(axisNumber);
            if (this.isSeparatingAxis(asteroid, axisNumber))
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
