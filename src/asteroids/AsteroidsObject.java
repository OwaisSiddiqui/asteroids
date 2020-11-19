package src.asteroids;

import javafx.geometry.Bounds;
import javafx.scene.shape.Polygon;

public class AsteroidsObject {

    static void wrap(Polygon polygon) {
        Bounds shipBoundsInParent = polygon.getBoundsInParent();
        if (shipBoundsInParent.getMinX() > 700) { polygon.relocate(0 - shipBoundsInParent.getWidth(), polygon.getLayoutY()); }
        else if (shipBoundsInParent.getMinY() > 700) { polygon.relocate(polygon.getLayoutX(), 0 - shipBoundsInParent.getHeight()); }
        else if (shipBoundsInParent.getMaxX() < 0) { polygon.relocate(700, polygon.getLayoutY()); }
        else if (shipBoundsInParent.getMaxY() < 0) { polygon.relocate(polygon.getLayoutX(), 700); }
    }
}
