package sample;

import java.util.Vector;

public class VectorAsteroids {
    double magnitude;
    double direction;
    double slope;
    double b;
    double InitialPointX;
    double InitialPointY;
    double TerminalPointX;
    double TerminalPointY;

    VectorAsteroids(double InitialPointX, double InitialPointY, double TerminalPointX, double TerminalPointY)
    {
        this.InitialPointX = InitialPointX;
        this.InitialPointY = InitialPointY;
        this.TerminalPointX = TerminalPointX;
        this.TerminalPointY = TerminalPointY;
        this.magnitude = Math.sqrt(Math.pow((TerminalPointX - InitialPointX), 2) + Math.pow((TerminalPointY - InitialPointY), 2));
        double direction = -(Math.toDegrees(Math.atan((TerminalPointY - InitialPointY) / (TerminalPointX - InitialPointX))));
        this.slope = (TerminalPointY-InitialPointY)/(TerminalPointX-InitialPointX);
        this.b = InitialPointY-(this.slope*InitialPointX);
        if (direction < 0) {
            if (InitialPointX < TerminalPointX) {
                this.direction = 360 - Math.abs(direction);
            } else {
                this.direction = 180 - Math.abs(direction);
            }
        } else {
            if (InitialPointX > TerminalPointX) {
                this.direction = 180 + direction;
            } else {
                this.direction = direction;
            }
        }
    }

    public void printVectorAsteroids()
    {
        System.out.println("VectorAsteroids with magnitude: " + this.magnitude + " and direction: " + this.direction + "°");
        System.out.println("Coordinates: (" + this.InitialPointX + ", " + this.InitialPointY + ") | (" + this.TerminalPointX + ", " + this.TerminalPointY + ")");
        System.out.println("Equation: y = "+this.slope+"x + "+this.b);
        System.out.println();
    }

    public void normalizeVector()
    {
        this.InitialPointX = this.InitialPointX / this.magnitude;
        this.InitialPointY = this.InitialPointY / this.magnitude;
        this.TerminalPointX = this.TerminalPointX / this.magnitude;
        this.TerminalPointY = this.TerminalPointY / this.magnitude;
        this.magnitude = Math.sqrt(Math.pow((TerminalPointX - InitialPointX), 2) + Math.pow((TerminalPointY - InitialPointY), 2));
        this.b = TerminalPointY-(this.slope*TerminalPointX);
    }

    public void moveVectorToAsteroid(Asteroid asteroid)
    {
        double changeInX = this.InitialPointX - asteroid.asteroidImage.getLayoutX();
        double changeInY = this.InitialPointY - asteroid.asteroidImage.getLayoutY();
        this.InitialPointX = asteroid.asteroidImage.getLayoutX();
        this.InitialPointY = asteroid.asteroidImage.getLayoutY();
        this.TerminalPointX = this.TerminalPointX - changeInX;
        this.TerminalPointY = this.TerminalPointY - changeInY;
        this.b = InitialPointY-(this.slope*InitialPointX);
    }

    public void moveVectorToPoint(double x, double y)
    {
        double changeInX = this.InitialPointX - x;
        double changeInY = this.InitialPointY - y;
        this.InitialPointX = x;
        this.InitialPointY = y;
        this.TerminalPointX = this.TerminalPointX - changeInX;
        this.TerminalPointY = this.TerminalPointY - changeInY;
        this.b = TerminalPointY-(this.slope*TerminalPointX);
    }

    public void moveVectorToAxis(VectorAsteroids axis, Double magnitudeToMove, double signX, double signY)
    {
        double changeInX = Math.abs(axis.TerminalPointX - axis.InitialPointX)*magnitudeToMove*signX;
        double changeInY = Math.abs(axis.TerminalPointY - axis.InitialPointY)*magnitudeToMove*signY;
        this.InitialPointX = changeInX + this.InitialPointX;
        this.InitialPointY = changeInY + this.InitialPointY;
        this.TerminalPointX = changeInX + this.TerminalPointX;
        this.TerminalPointY = changeInY + this.TerminalPointY;
        this.b = this.InitialPointY-(this.slope*this.InitialPointX);
    }
}