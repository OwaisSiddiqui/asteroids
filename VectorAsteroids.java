package sample;

public class VectorAsteroids
{
    double magnitude;
    double direction;
    double InitialPointX;
    double InitialPointY;
    double TerminalPointX;
    double TerminalPointY;

    VectorAsteroids(double InitialPointX, double InitialPointY, double TerminalPointX, double TerminalPointY)
    {
        this.magnitude = Math.sqrt(Math.pow((TerminalPointX-InitialPointX), 2)+Math.pow((TerminalPointY-InitialPointY), 2));
        double direction = -(Math.toDegrees(Math.atan((TerminalPointY-InitialPointY)/(TerminalPointX-InitialPointX))));
        if (direction < 0)
        {
            if (InitialPointX < TerminalPointX)
            {
                this.direction = 360-Math.abs(direction);
            }
            else
            {
                this.direction = 180-Math.abs(direction);
            }
        }
        else
        {
            if (InitialPointX > TerminalPointX)
            {
                this.direction = 180+direction;
            }
            else
            {
                this.direction = direction;
            }
        }

        this.InitialPointX = InitialPointX;
        this.InitialPointY = InitialPointY;
        this.TerminalPointX = TerminalPointX;
        this.TerminalPointY = TerminalPointY;
    }

    public void moveVectorToPoint(Asteroid asteroid)
    {
        double changeInX = this.InitialPointX - asteroid.asteroidImage.getLayoutX();
        double changeInY = this.InitialPointY - asteroid.asteroidImage.getLayoutY();
        this.InitialPointX = asteroid.asteroidImage.getLayoutX();
        this.InitialPointY = asteroid.asteroidImage.getLayoutY();
        this.TerminalPointX = this.TerminalPointX - changeInX;
        this.TerminalPointY = this.TerminalPointY - changeInY;
    }

    public void printVectorAsteroids()
    {
        System.out.println();
        System.out.println("VectorAsteroids with magnitude: "+this.magnitude+" and direction: "+this.direction+"°");
        System.out.println("Coordinates: ("+InitialPointX+", "+InitialPointY+") | ("+TerminalPointX+", "+TerminalPointY+")");
    }

    public void normalizeVector()
    {
        this.InitialPointX = this.InitialPointX/this.magnitude;
        this.InitialPointY = this.InitialPointY/this.magnitude;
        this.TerminalPointX = this.TerminalPointX/this.magnitude;
        this.TerminalPointY = this.TerminalPointY/this.magnitude;
        this.magnitude = 1;
    }

}