package src.asteroids;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Random;

class Asteroid extends AsteroidsObject {

    private double dx;
    private double dy;

    Asteroid() {
        createAsteroid();
    }

    private void createAsteroid() {
        polygon = new Polygon();
        Double[] points = new Double[] {-40.0, -13.0, 25.0, -40.0, 50.0, 0.0, 20.0, 60.0, 0.0, 70.0, -30.0, 30.0};
        polygon.getPoints().addAll(points);
        polygonPoints = new Point[points.length/2];
        setPolygonPoints();
        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(Color.BLACK);
        createCourse();
        positionPoints = new Point[polygonPoints.length];
        setPositionPoints(0);
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

    Polygon getImage() { return polygon; }

    Point[] getPositionPoints() { return positionPoints; }

    public final AnimationTimer move = new AnimationTimer() {
        @Override
        public void handle(long l) {
            polygon.setLayoutX(polygon.getLayoutX()+dx);
            polygon.setLayoutY(polygon.getLayoutY()+dy);
            wrap(polygon);
            setPositionPoints(0);
        }
    };

}