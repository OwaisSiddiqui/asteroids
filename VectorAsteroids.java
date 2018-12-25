package sample;

public class VectorAsteroids
{
    double magnitude;
    double direction;

    public static void main(String args[])
    {
        VectorAsteroids vector1 = new VectorAsteroids(0, 0, 10, 10);
        vector1.displayVectorAsteroids();
    }

    VectorAsteroids(double Point1X, double Point1Y, double Point2X, double Point2Y)
    {
        this.magnitude = Math.sqrt(Math.pow((Point1X-Point2X), 2)+Math.pow((Point1Y-Point2Y), 2));
        this.direction = Math.toDegrees(Math.atan((Point1Y-Point2Y)/(Point1X-Point2X)));
    }

    public void displayVectorAsteroids()
    {
        System.out.println("VectorAsteroids with magnitude: "+this.magnitude+" and direction: "+this.direction+"°");
    }
}