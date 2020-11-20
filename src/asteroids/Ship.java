package src.asteroids;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

class Ship extends AsteroidsObject {

    private int rotationDirection;
    private double vx;
    private double vy;
    private double rotationFactor = 0;
    private double acceleration = 0;
    private double rotation = 90;
    private double directionX = Math.cos(Math.toRadians(rotation));
    private double directionY = Math.sin(Math.toRadians(rotation));
    private ArrayList<Point[]> decomposedPolygonsPositionPoints;

    Ship() {
        createShip();
        AnimationTimer move = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (acceleration > 0) {
                    vx += directionX * acceleration * 0.004;
                    vy += directionY * acceleration * 0.004;
                } else if (vx != 0 || vy != 0) {
                    double speed = Math.hypot(vx, vy);
                    double correctionFactor = Math.max((speed - 0.03) / speed, 0);
                    vx *= correctionFactor;
                    vy *= correctionFactor;
                }
                polygon.setLayoutX(polygon.getLayoutX() + vx);
                polygon.setLayoutY(polygon.getLayoutY() - vy);
                wrap(polygon);
                setPositionPoints(-rotation);
                setDecomposedPolygonsPositionPoints();
            }
        };
        AnimationTimer rotate = new AnimationTimer() {
            @Override
            public void handle(long l) {
                setRotation();
            }
        };
        move.start();
        rotate.start();
    }

    private void createShip() {
        polygon = new Polygon();
        polygon.setLayoutX(350);
        polygon.setLayoutY(350);
        Double[] points = new Double[]{15.0, 0.0, -15.0, -12.5, -5.0, 0.0, -15.0, 12.5};
        polygon.getPoints().addAll(points);
        polygonPoints = new Point[points.length/2];
        setPolygonPoints();
        positionPoints = new Point[points.length/2];
        setPositionPoints(0);
        setDecomposedPolygonsPositionPoints();
        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(Color.BLACK);
    }

    private void setDecomposedPolygonsPositionPoints() {
        decomposedPolygonsPositionPoints = new ArrayList<>();
        decomposedPolygonsPositionPoints.add(new Point[] {positionPoints[0], positionPoints[1], positionPoints[2]});
        decomposedPolygonsPositionPoints.add(new Point[] {positionPoints[0], positionPoints[2], positionPoints[3]});
    }

    public ArrayList<Point[]> getDecomposedPolygonsPositionPoints() { return decomposedPolygonsPositionPoints; }

    Polygon getImage() { return polygon; }

    public double getDirectionX() { return directionX; }

    public double getDirectionY() { return directionY; }

    private void setRotation() {
        double newRotation = rotation + rotationFactor * -rotationDirection;
        rotation = newRotation % 360;
        if (rotation < 0) rotation += 360;
        polygon.setRotate(-rotation);
        directionX = Math.cos(Math.toRadians(rotation));
        directionY = Math.sin(Math.toRadians(rotation));
    }

    EventHandler<KeyEvent> rotate(EventType<KeyEvent> keyEventPR) {
        if (keyEventPR == KeyEvent.KEY_PRESSED) {
            return keyEvent -> {
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    rotationFactor = 5;
                    rotationDirection = 1;
                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                    rotationFactor = 5;
                    rotationDirection = -1;
                }
            };
        } else { return keyEvent -> { if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT) { rotationFactor = 0; } }; }
    }

    EventHandler<KeyEvent> move(EventType<KeyEvent> keyEventPR) { if (keyEventPR == KeyEvent.KEY_PRESSED) { return keyEvent -> { if (keyEvent.getCode() == KeyCode.UP) { acceleration = 17; } }; } else { return keyEvent -> { if (keyEvent.getCode() == KeyCode.UP) { acceleration = 0; } }; } }

}
