package src.asteroids;

import static src.asteroids.Vector.*;

public class Collision {
    Object object1;
    Object object2;

    Collision(Object object1, Object object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    public boolean isCollision() {
        if (this.object1 instanceof Asteroid && this.object2 instanceof Asteroid) {
            Point[] points = ((Asteroid) this.object1).getPositionPoints();
            Point[] points2 = ((Asteroid) this.object2).getPositionPoints();
            Vector[] edgeVectors = new Vector[points.length];
            setEdgeVectors(edgeVectors, points);
            Vector[] edgeVectors2 = new Vector[points2.length];
            setEdgeVectors(edgeVectors2, points2);
            Vector[] axes = new Vector[edgeVectors.length + edgeVectors2.length];
            setAxes(points, axes, 0, points.length - 1, 0);
            axes[points.length - 1] = getNormal(new Vector(points[0].getX() - points[points.length - 1].getX(), points[0].getY() - points[points.length - 1].getY()));
            setAxes(points2, axes, points.length, axes.length - 1, edgeVectors.length);
            axes[axes.length - 1] = getNormal(new Vector(points2[0].getX() - points2[edgeVectors2.length - 1].getX(), points2[0].getY() - points2[edgeVectors2.length - 1].getY()));
            return isOverlap(edgeVectors, edgeVectors2, axes);
        } else if (this.object1 instanceof Ship && this.object2 instanceof Asteroid) {
            Point[] points = ((Ship) this.object1).getDecomposedPolygonsPositionPoints().get(1);
            Point[] points2 = ((Asteroid) this.object2).getPositionPoints();
            Vector[] edgeVectors = new Vector[points.length];
            setEdgeVectors(edgeVectors, points);
            Vector[] edgeVectors2 = new Vector[points2.length];
            setEdgeVectors(edgeVectors2, points2);
            Vector[] axes = new Vector[edgeVectors.length + edgeVectors2.length];
            setAxes(points, axes, 0, points.length - 1, 0);
            axes[points.length - 1] = getNormal(new Vector(points[0].getX() - points[points.length - 1].getX(), points[0].getY() - points[points.length - 1].getY()));
            setAxes(points2, axes, points.length, axes.length - 1, edgeVectors.length);
            axes[axes.length - 1] = getNormal(new Vector(points2[0].getX() - points2[edgeVectors2.length - 1].getX(), points2[0].getY() - points2[edgeVectors2.length - 1].getY()));
            Point[] points3 = ((Ship) this.object1).getDecomposedPolygonsPositionPoints().get(0);
            Vector[] edgeVectors3 = new Vector[points3.length];
            setEdgeVectors(edgeVectors3, points3);
            Vector[] axes3 = new Vector[edgeVectors3.length + edgeVectors2.length];
            setAxes(points3, axes3, 0, points3.length - 1, 0);
            axes3[points3.length - 1] = getNormal(new Vector(points3[0].getX() - points3[points3.length - 1].getX(), points3[0].getY() - points3[points3.length - 1].getY()));
            setAxes(points2, axes3, points3.length, axes3.length - 1, edgeVectors3.length);
            axes3[axes3.length - 1] = getNormal(new Vector(points2[0].getX() - points2[edgeVectors2.length - 1].getX(), points2[0].getY() - points2[edgeVectors2.length - 1].getY()));
            return isOverlap(edgeVectors, edgeVectors2, axes) || isOverlap(edgeVectors3, edgeVectors2, axes3);
        } else if (this.object1 instanceof Bullet && this.object2 instanceof Asteroid) {
            Point bulletPositionPoint = ((Bullet) this.object1).getPositionPoint();
            Point[] asteroidPositionPoints = ((Asteroid) this.object2).getPositionPoints();
            Point closestPoint = getClosestPoint(bulletPositionPoint, asteroidPositionPoints);
            Vector[] asteroidImageEdgeVectors = new Vector[asteroidPositionPoints.length];
            setEdgeVectors(asteroidImageEdgeVectors, asteroidPositionPoints);
            Vector[] axes = new Vector[asteroidPositionPoints.length + 1];
            setAxes(asteroidPositionPoints, axes, 0, asteroidPositionPoints.length - 1, 0);
            axes[asteroidPositionPoints.length - 1] = getNormal(new Vector(asteroidPositionPoints[0].getX() - asteroidPositionPoints[asteroidPositionPoints.length - 1].getX(), asteroidPositionPoints[0].getY() - asteroidPositionPoints[asteroidPositionPoints.length - 1].getY()));
            axes[asteroidPositionPoints.length] = new Vector(bulletPositionPoint.getX() - closestPoint.getX(), bulletPositionPoint.getY() - closestPoint.getY());
            for (Vector axis : axes) {
                double[] currentMaxMin = getEdgeVectorsMaxMin(axis, asteroidImageEdgeVectors);
                double currentMaxX = currentMaxMin[0];
                double currentMinX = currentMaxMin[1];
                double currentCircleProjection = projectVector(new Vector(bulletPositionPoint.getX(), bulletPositionPoint.getY()), axis);
                double currentCircleMaxX = currentCircleProjection + ((Bullet) this.object1).getRadius();
                double currentCircleMinX = currentCircleProjection - ((Bullet) this.object1).getRadius();
                if (currentMinX > currentCircleMaxX || currentCircleMinX > currentMaxX) { return false; }
            }
            return true;
        }
        return false;
    }

    private boolean isOverlap(Vector[] edgeVectors1, Vector[] edgeVectors2, Vector[] axes) {
        for (Vector axis: axes) {
            double[] currentMaxMin = getEdgeVectorsMaxMin(axis, edgeVectors1);
            double currentMaxX = currentMaxMin[0];
            double currentMinX = currentMaxMin[1];
            double[] currentMaxMin2 = getEdgeVectorsMaxMin(axis, edgeVectors2);
            double currentMaxX2 = currentMaxMin2[0];
            double currentMinX2 = currentMaxMin2[1];
            if (currentMinX > currentMaxX2 || currentMinX2 > currentMaxX) { return false; }
        }
        return true;
    }

    private void setEdgeVectors(Vector[] edgeVectors, Point[] points) {
        for (int i = 0; i < points.length; i++) {
            edgeVectors[i] = new Vector(points[i].getX(), points[i].getY());
        }
    }

    private void setAxes(Point[] points, Vector[] axes, int startingIndexAxes, int endingIndexAxes, int indexFactor) {
        for (int i = startingIndexAxes; i < endingIndexAxes; i++) {
            axes[i] = getNormal(new Vector(points[i + 1 - indexFactor].getX() - points[i - indexFactor].getX(), points[i + 1 - indexFactor].getY() - points[i - indexFactor].getY()));
        }
    }

    private double[] getEdgeVectorsMaxMin(Vector axis, Vector[] edgeVectors) {
        double currentMaxX = Double.NEGATIVE_INFINITY;
        double currentMinX = Double.POSITIVE_INFINITY;
        for (Vector edgeVector : edgeVectors) {
            double currentProjection = projectVector(edgeVector, getUnitVector(axis));
            if (currentProjection >= currentMaxX) { currentMaxX = currentProjection; }
            if (currentProjection <= currentMinX) { currentMinX = currentProjection; }
        }
        return new double[] {currentMaxX, currentMinX};
    }

    private Point getClosestPoint(Point circlePoint, Point[] polygonPositionPoints) {
        double closestDistance = Double.POSITIVE_INFINITY;
        Point closestPoint = null;
        for (Point positionPoint: polygonPositionPoints) {
            double distance = Math.pow((circlePoint.getX() - positionPoint.getX()), 2) + Math.pow((circlePoint.getY() - positionPoint.getY()), 2);
            if (distance <= closestDistance) {
                closestDistance = distance;
                closestPoint = positionPoint;
            }
        }
        return closestPoint;
    }
}
