package src.asteroids;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Random;

class Asteroid extends AsteroidsObject {

    private double dx;
    private double dy;
    private final String size;

    Asteroid(String size, double x, double y) {
        this.size = size;
        if (size.equals("small")) { createSmallAsteroid(x, y); } else if (size.equals("medium")) { createMediumAsteroid(x, y); } else { createBigAsteroid(x, y); }
    }

    public AnimationTimer move = new AnimationTimer() {
        @Override
        public void handle(long l) {
        polygon.setLayoutX(polygon.getLayoutX() + dx);
        polygon.setLayoutY(polygon.getLayoutY() + dy);
        wrap(polygon);
        setPositionPoints(0);
        }
    };

    private void createBigAsteroid(double x, double y) {
        polygon = new Polygon();
        Double[] points = new Double[] {-40.0, -13.0, 25.0, -40.0, 50.0, 0.0, 20.0, 60.0, 0.0, 70.0, -30.0, 30.0};
        setPoints(points);
        createCourse(x, y);
    }

    private void createMediumAsteroid(double x, double y) {
        polygon = new Polygon();
        Double[] points = new Double[] {-30.0, 0.0, 10.0, -20.0, 20.0, -10.0, 25.0, 25.0, 0.0, 35.0, -20.0, 15.0};
        setPoints(points);
        createCourse(x, y);
    }

    private void createSmallAsteroid(double x, double y) {
        polygon = new Polygon();
        Double[] points = new Double[] {-20.0, 0.0, -10.0, -20.0, 12.0, -10.0, 7.0, 7.0, -5.0, 10.0};
        setPoints(points);
        createCourse(x, y);
    }

    private void setPoints(Double[] points) {
        polygon.getPoints().addAll(points);
        polygonPoints = new Point[points.length/2];
        setPolygonPoints();
        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(Color.BLACK);
        positionPoints = new Point[polygonPoints.length];
        setPositionPoints(0);
    }

    private void createCourse(double startingX, double startingY) {
        Random rand = new Random();
        dx = Math.random() * 2;
        if (rand.nextInt(2) == 0) { dx = -dx; }
        dy = Math.random() * 2;
        if (rand.nextInt(2) == 0) { dy = -dy; }
        polygon.setLayoutX(startingX);
        polygon.setLayoutY(startingY);
    }

    public ArrayList<Asteroid> crumble(double x, double y) {
        ArrayList<Asteroid> resultingAsteroids = new ArrayList<>();
        if (size.equals("big")) {
            Asteroid asteroid1 = new Asteroid("medium", x, y);
            Asteroid asteroid2 = new Asteroid("medium", x, y);
            resultingAsteroids.add(asteroid1);
            resultingAsteroids.add(asteroid2);
            return resultingAsteroids;
        } else if (size.equals("medium")) {
            Asteroid asteroid1 = new Asteroid("small", x, y);
            Asteroid asteroid2 = new Asteroid("small", x, y);
            resultingAsteroids.add(asteroid1);
            resultingAsteroids.add(asteroid2);
            return resultingAsteroids;
        }
        return resultingAsteroids;
    }

    Polygon getImage() { return polygon; }

    String getSize() { return size; }

    Point[] getPositionPoints() { return positionPoints; }

}