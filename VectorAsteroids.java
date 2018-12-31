package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Vector;

public class VectorAsteroids
{
    double magnitude;
    double direction;
    double Point1X;
    double Point1Y;
    double Point2X;
    double Point2Y;
    Line line = new Line();

    public static void main(String args[])
    {
        VectorAsteroids vector1 = new VectorAsteroids(0, 0, 10, 10);
    }

    VectorAsteroids(double Point1X, double Point1Y, double Point2X, double Point2Y)
    {
        this.magnitude = Math.sqrt(Math.pow((Point1X-Point2X), 2)+Math.pow((Point1Y-Point2Y), 2));
        this.direction = Math.toDegrees(Math.atan((Point1Y-Point2Y)/(Point1X-Point2X)));
        this.Point1X = Point1X;
        this.Point1Y = Point1Y;
        this.Point2X = Point2X;
        this.Point2Y = Point2Y;
    }

    public void printVectorAsteroids()
    {
        System.out.println();
//        System.out.println("VectorAsteroids with magnitude: "+this.magnitude+" and direction: "+this.direction+"°");
        System.out.println("Coordinates: ("+Point1X+", "+Point1Y+") | ("+Point2X+", "+Point2Y+")");
    }

    public void displayVectorAsteroids(double Point1X, double Point1Y, double Point2X, double Point2Y, int i)
    {
        line.setStartX(Point1X);
        line.setStartY(Point1Y);
        line.setEndX(Point2X);
        line.setEndY(Point2Y);
        line.setFill(Color.RED);
        line.setStrokeWidth(10);
    }

    public void moveVectorToPoint(Asteroid asteroid)
    {
        double changeInX = this.Point1X - asteroid.asteroidImage.getLayoutX();
        double changeInY = this.Point1Y - asteroid.asteroidImage.getLayoutY();
        this.Point1X = asteroid.asteroidImage.getLayoutX();
        this.Point1Y = asteroid.asteroidImage.getLayoutY();
        this.Point2X = this.Point2X - changeInX;
        this.Point2Y = this.Point2Y - changeInY;
    }

    public void normalizeVector()
    {
//        System.out.print("Before: ");
//        this.printVectorAsteroids();
//        System.out.println();
        this.Point1X = this.Point1X/this.magnitude;
        this.Point1Y = this.Point1Y/this.magnitude;
        this.Point2X = this.Point2X/this.magnitude;
        this.Point2Y = this.Point2Y/this.magnitude;
//        System.out.print("After: ");
//        this.printVectorAsteroids();
        this.magnitude = 1;
//        System.out.println();
    }

    public void scaleVector(int scale)
    {
//        System.out.print("Before: ");
//        this.printVectorAsteroids();
//        System.out.println();
        this.Point1X = this.Point1X*scale;
        this.Point1Y = this.Point1Y*scale;
        this.Point2X = this.Point2X*scale;
        this.Point2Y = this.Point2Y*scale;
//        System.out.print("After: ");
//        this.printVectorAsteroids();
//        System.out.println();
    }

}