package src.asteroids;

import javafx.geometry.Bounds;
import javafx.scene.shape.Polygon;

public class AsteroidsObject {

    protected Polygon polygon;
    protected Point[] polygonPoints;
    protected Point[] positionPoints;

    protected void wrap(Polygon polygon) {
        Bounds shipBoundsInParent = polygon.getBoundsInParent();
        if (shipBoundsInParent.getMinX() > 700) { polygon.relocate(0 - shipBoundsInParent.getWidth(), polygon.getLayoutY()); }
        else if (shipBoundsInParent.getMinY() > 700) { polygon.relocate(polygon.getLayoutX(), 0 - shipBoundsInParent.getHeight()); }
        else if (shipBoundsInParent.getMaxX() < 0) { polygon.relocate(700, polygon.getLayoutY()); }
        else if (shipBoundsInParent.getMaxY() < 0) { polygon.relocate(polygon.getLayoutX(), 700); }
    }

    protected void setPolygonPoints() {
        Double[] points = polygon.getPoints().toArray(new Double[0]);
        int j = 0;
        for (int i = 0; i < points.length - 1; i+=2) {
            polygonPoints[j] = new Point(points[i], points[i + 1]);
            j += 1;
        }
    }

    protected void setPositionPoints() { for (int i = 0; i < polygonPoints.length; i++) { positionPoints[i] = new Point(polygonPoints[i].getX() + polygon.getLayoutX(), polygonPoints[i].getY() + polygon.getLayoutY()); } }
}
