package asteroids;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Random;

import static asteroids.Vector.getNormal;
import static asteroids.Vector.getVector;
import static asteroids.Vector.projectVector;
import static asteroids.Vector.getUnitVector;

class Asteroid {

    private Polygon polygon;
    private double dx;
    private double dy;
    private Point[] positionPoints;

    Asteroid() {
        createAsteroid();
    }

    private void createAsteroid() {
        polygon = new Polygon();
        Double[] polygonPoints = new Double[] {-40.0, -13.0, 25.0, -40.0, 50.0, 0.0, 20.0, 60.0, 0.0, 70.0, -30.0, 30.0};
        polygon.getPoints().addAll(polygonPoints);
        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(Color.BLACK);
        createAsteroidCourse();
        positionPoints = new Point[polygonPoints.length/2];
        int j = 0;
        for (int i = 0; i < polygonPoints.length - 1; i+=2) {
            positionPoints[j] = new Point(polygonPoints[i] + polygon.getLayoutX(), polygonPoints[i + 1] + polygon.getLayoutY());
            j += 1;
        }
        moveAsteroid.start();
    }

    private void createAsteroidCourse() {
        Random rand = new Random();
        dx = Math.random();
        if (rand.nextInt(2) == 0) { dx = -dx; }
        dy = Math.random();
        if (rand.nextInt(2) == 0) { dy = -dy; }
        polygon.setLayoutX(350);
        polygon.setLayoutY(350);
    }

    private void setAsteroidInBounds() {
        if (polygon.getLayoutX() < 50 || polygon.getLayoutX() > 650 || polygon.getLayoutY() > 650 || polygon.getLayoutY() < 50) {
            dx = -dx;
            dy = -dy;
        }
    }

    Polygon getAsteroidImage() {
        return polygon;
    }

    static boolean isCollision(Point[] points, Point[] points2) {
        Vector[] edgeVectors = new Vector[points.length];
        for (int i = 0; i < points.length; i++) {
            edgeVectors[i] = getVector(new double[] {points[i].x, points[i].y});
        }
        Vector[] edgeVectors2 = new Vector[points2.length];
        for (int i = 0; i < points2.length; i++) {
            edgeVectors2[i] = getVector(new double[] {points2[i].x, points2[i].y});
        }

        Vector[] axes = new Vector[edgeVectors.length + edgeVectors2.length];
        for (int i = 0; i < points.length - 1; i++) {
            axes[i] = getNormal(getVector(new double[] {points[i + 1].x - points[i].x, points[i + 1].y - points[i].y}));
        }
        axes[points.length - 1] = getNormal(getVector(new double[] {points[0].x - points[points.length - 1].x, points[0].y - points[points.length - 1].y}));
        for (int i = points.length; i < axes.length - 1; i++) {
            axes[i] = getNormal(getVector(new double[] {points2[i + 1 - edgeVectors.length].x - points2[i - edgeVectors.length].x, points2[i + 1 - edgeVectors.length].y - points2[i - edgeVectors.length].y}));
        }
        axes[axes.length - 1] = getNormal(getVector(new double[] {points2[0].x - points2[edgeVectors2.length - 1].x, points2[0].y - points2[edgeVectors2.length - 1].y}));

        for (Vector axis : axes) {
            double currentMaxX = Double.NEGATIVE_INFINITY;
            double currentMinX = Double.POSITIVE_INFINITY;
            for (Vector edgeVector : edgeVectors) {
                double currentProjection = projectVector(edgeVector, getUnitVector(axis));
                if (currentProjection >= currentMaxX) {
                    currentMaxX = currentProjection;
                }
                if (currentProjection <= currentMinX) {
                    currentMinX = currentProjection;
                }
            }
            double currentMaxX2 = Double.NEGATIVE_INFINITY;
            double currentMinX2 = Double.POSITIVE_INFINITY;
            for (Vector edgeVector : edgeVectors2) {
                double currentProjection2 = projectVector(edgeVector, getUnitVector(axis));
                if (currentProjection2 >= currentMaxX2) {
                    currentMaxX2 = currentProjection2;
                }
                if (currentProjection2 <= currentMinX2) {
                    currentMinX2 = currentProjection2;
                }
            }
            if (currentMinX > currentMaxX2 || currentMinX2 > currentMaxX) {
                return false;
            }
        }
        return true;
    }

    static Vector getMinimumTranslationVector(Point[] points, Point[] points2) {
        Vector[] edgeVectors = new Vector[points.length + points2.length];
        for (int i = 0; i < points.length; i++) {
            edgeVectors[i] = getVector(new double[]{points[i].x, points[i].y});
        }
        for (int i = points.length; i < edgeVectors.length; i++) {
            edgeVectors[i] = getVector(new double[]{points2[i - points2.length].x, points2[i - points2.length].y});
        }
        Vector[] axes = new Vector[edgeVectors.length];
        for (int i = 0; i < points.length - 1; i++) {
            axes[i] = getNormal(getVector(new double[] {points[i + 1].x - points[i].x, points[i + 1].y - points[i].y}));
            axes[i].normalize();
        }
        axes[points.length - 1] = getNormal(getVector(new double[] {points[0].x - points[points.length - 1].x, points[0].y - points[points.length - 1].y}));
        axes[points.length - 1].normalize();
        for (int i = points2.length; i < axes.length - 1; i++) {
            axes[i] = getNormal(getVector(new double[] {points2[i + 1 - points.length].x - points2[i - points.length].x, points2[i + 1 - points.length].y - points2[i - points.length].y}));
            axes[i].normalize();
        }
        axes[axes.length - 1] = getNormal(getVector(new double[] {points2[0].x - points2[points2.length - 1].x, points2[0].y - points2[points2.length - 1].y}));
        axes[axes.length - 1].normalize();

        Vector minimumTranslationVector = new Vector(axes[0].x, axes[0].y);
        minimumTranslationVector.magnitude = Double.POSITIVE_INFINITY;
        Vector currentMinimumTranslationVector = new Vector(axes[0].x, axes[0].y);
        currentMinimumTranslationVector.magnitude = Double.POSITIVE_INFINITY;

        for (Vector axis : axes) {
            double currentMaxX = Double.NEGATIVE_INFINITY;
            double currentMinX = Double.POSITIVE_INFINITY;
            for (Vector edgeVector : edgeVectors) {
                double currentProjection = projectVector(edgeVector, axis);
                if (currentProjection >= currentMaxX) {
                    currentMaxX = currentProjection;
                }
                if (currentProjection <= currentMinX) {
                    currentMinX = currentProjection;
                }
            }
            double currentMaxX2 = Double.NEGATIVE_INFINITY;
            double currentMinX2 = Double.POSITIVE_INFINITY;
            for (Vector edgeVector : edgeVectors) {
                double currentProjection2 = projectVector(edgeVector, axis);
                if (currentProjection2 >= currentMaxX2) {
                    currentMaxX2 = currentProjection2;
                }
                if (currentProjection2 <= currentMinX) {
                    currentMinX2 = currentProjection2;
                }
            }
            currentMinimumTranslationVector = axis;
            currentMinimumTranslationVector.magnitude = Math.abs(currentMinX2 - currentMaxX);
            if (currentMinimumTranslationVector.magnitude <= minimumTranslationVector.magnitude) {
                minimumTranslationVector = axis;
                minimumTranslationVector.magnitude = currentMinimumTranslationVector.magnitude;
            }
        }
        return minimumTranslationVector;
    }

    private final AnimationTimer moveAsteroid = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for (Point point: positionPoints) {
                point.x += dx;
                point.y += dy;
            }
            polygon.setLayoutX(polygon.getLayoutX()+dx);
            polygon.setLayoutY(polygon.getLayoutY()+dy);
            setAsteroidInBounds();
        }
    };

}