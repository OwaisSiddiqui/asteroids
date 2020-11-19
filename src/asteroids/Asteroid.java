package src.asteroids;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Random;

class Asteroid extends AsteroidsObject {

    private Polygon polygon;
    private double dx;
    private double dy;
    private Point[] positionPoints;
    private Double[] polygonPoints;

    Asteroid() {
        createAsteroid();
    }

    private void createAsteroid() {
        polygon = new Polygon();
        polygonPoints = new Double[] {-40.0, -13.0, 25.0, -40.0, 50.0, 0.0, 20.0, 60.0, 0.0, 70.0, -30.0, 30.0};
        polygon.getPoints().addAll(polygonPoints);
        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(Color.BLACK);
        createCourse();
        positionPoints = new Point[polygonPoints.length/2];
        setPositionPoints(polygonPoints);
        move.start();
    }

    private void createCourse() {
        Random rand = new Random();
        dx = Math.random() * 2;
        if (rand.nextInt(2) == 0) { dx = -dx; }
        dy = Math.random() * 2;
        if (rand.nextInt(2) == 0) { dy = -dy; }
        polygon.setLayoutX(rand.nextInt(700));
        polygon.setLayoutY(rand.nextInt(700));
    }

    private void setPositionPoints(Double[] polygonPoints) {
        int j = 0;
        for (int i = 0; i < polygonPoints.length - 1; i+=2) {
            positionPoints[j] = new Point(polygonPoints[i] + polygon.getLayoutX(), polygonPoints[i + 1] + polygon.getLayoutY());
            j += 1;
        }
    }

    Polygon getImage() { return polygon; }

    Point[] getPositionPoints() { return positionPoints; }

    public final AnimationTimer move = new AnimationTimer() {
        @Override
        public void handle(long l) {
            polygon.setLayoutX(polygon.getLayoutX()+dx);
            polygon.setLayoutY(polygon.getLayoutY()+dy);
            wrap(polygon);
            setPositionPoints(polygonPoints);
        }
    };

}