package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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
        System.out.println("VectorAsteroids with magnitude: "+this.magnitude+" and direction: "+this.direction+"°");
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

}