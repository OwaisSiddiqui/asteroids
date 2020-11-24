package src.asteroids;

import javafx.geometry.Bounds;
import javafx.scene.shape.Polygon;

public class AsteroidsObject {

    protected Polygon polygon;
    protected Point[] polygonPoints;
    Point[] positionPoints;

    protected void wrap(Polygon polygon) {
        Bounds shipBoundsInParent = polygon.getBoundsInParent();
        if (shipBoundsInParent.getMinX() > 700) {
            polygon.setLayoutX(0 - Math.abs(shipBoundsInParent.getMaxX() - polygon.getLayoutX()));
            polygon.setLayoutY(polygon.getLayoutY());
        }
        else if (shipBoundsInParent.getMinY() > 700) {
            polygon.setLayoutX(polygon.getLayoutX());
            polygon.setLayoutY(0 - Math.abs(shipBoundsInParent.getMaxY() - polygon.getLayoutY()));
        }
        else if (shipBoundsInParent.getMaxX() < 0) {
            polygon.setLayoutX(700 + Math.abs(shipBoundsInParent.getMinX() - polygon.getLayoutX()));
            polygon.setLayoutY(polygon.getLayoutY());
        }
        else if (shipBoundsInParent.getMaxY() < 0) {
            polygon.setLayoutX(polygon.getLayoutX());
            polygon.setLayoutY(700 + Math.abs(shipBoundsInParent.getMinY() - polygon.getLayoutY()));
        }
    }

    protected void setPolygonPoints() {
        Double[] points = polygon.getPoints().toArray(new Double[0]);
        int j = 0;
        for (int i = 0; i < points.length - 1; i+=2) {
            polygonPoints[j] = new Point(points[i], points[i + 1]);
            j += 1;
        }
    }

    protected void setPositionPoints(double rotation) { for (int i = 0; i < polygonPoints.length; i++) { positionPoints[i] = new Point(polygonPoints[i].getX()*Math.cos(Math.toRadians(rotation)) - polygonPoints[i].getY()*Math.sin(Math.toRadians(rotation)) + polygon.getLayoutX(), polygonPoints[i].getX()*Math.sin(Math.toRadians(rotation)) + polygonPoints[i].getY()*Math.cos(Math.toRadians(rotation)) + polygon.getLayoutY()); } }
}
