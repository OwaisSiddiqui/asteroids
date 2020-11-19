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
    private Polygon polygon;
    private int rotationDirection;
    private double vx;
    private double vy;
    private double rotationFactor = 0;
    private double acceleration = 0;
    private double rotation = 90;
    private double directionX = Math.cos(Math.toRadians(rotation));
    private double directionY = Math.sin(Math.toRadians(rotation));
    private Point[] positionPoints;
    private Point[] polygonPoints;
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
                setPositionPoints(polygonPoints);
                updateDecomposedPolygonsPositionPoints();
            }
        };
        AnimationTimer rotate = new AnimationTimer() {
            @Override
            public void handle(long l) {
                polygon.setRotate(polygon.getRotate() + rotationFactor * rotationDirection);
                if (rotationFactor != 0) { setRotation(); }
            }
        };
        move.start();
        rotate.start();
    }

    private void createShip() {
        polygon = new Polygon();
        polygon.setLayoutX(350);
        polygon.setLayoutY(350);
        Double[] points = new Double[]{0.0, 5.0, 12.5, 15.0, 0.0, -15.0, -12.5, 15.0};
        polygon.getPoints().addAll(points);
        polygonPoints = new Point[points.length/2];
        setPolygonPoints();
        positionPoints = new Point[points.length/2];
        setPositionPoints(polygonPoints);
        decomposedPolygonsPositionPoints = new ArrayList<>();
        decomposedPolygonsPositionPoints.add(new Point[] {positionPoints[0], positionPoints[1], positionPoints[2]});
        decomposedPolygonsPositionPoints.add(new Point[] {positionPoints[0], positionPoints[2], positionPoints[3]});
        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(Color.BLACK);
    }

    public void updateDecomposedPolygonsPositionPoints() {
        Point[] dp1PositionPoints = decomposedPolygonsPositionPoints.get(0);
        Point[] dp2PositionPoints = decomposedPolygonsPositionPoints.get(1);
        dp1PositionPoints[0].updateXY(positionPoints[0].getX(), positionPoints[0].getY());
        dp1PositionPoints[1].updateXY(positionPoints[1].getX(), positionPoints[1].getY());
        dp1PositionPoints[2].updateXY(positionPoints[2].getX(), positionPoints[2].getY());
        dp2PositionPoints[0].updateXY(positionPoints[0].getX(), positionPoints[0].getY());
        dp2PositionPoints[1].updateXY(positionPoints[2].getX(), positionPoints[2].getY());
        dp2PositionPoints[2].updateXY(positionPoints[3].getX(), positionPoints[3].getY());
    }

    public ArrayList<Point[]> getDecomposedPolygonsPositionPoints() { return decomposedPolygonsPositionPoints; }

    Polygon getImage() { return polygon; }

    private void setPolygonPoints() {
        Double[] points = polygon.getPoints().toArray(new Double[0]);
        int j = 0;
        for (int i = 0; i < points.length - 1; i+=2) {
            polygonPoints[j] = new Point(points[i], points[i + 1]);
            j += 1;
        }
    }

    private void setPositionPoints(Point[] polygonPoints) {
        for (int i = 0; i < polygonPoints.length; i++) { positionPoints[i] = new Point((polygonPoints[i].getX()) + polygon.getLayoutX(), (polygonPoints[i].getY()) + polygon.getLayoutY()); }
    }

    private void setRotation() {
        rotation += rotationFactor*-rotationDirection;
        directionX = Math.cos(Math.toRadians(rotation));
        directionY = Math.sin(Math.toRadians(rotation));
    }

    void reset() {
        polygon.setLayoutX(350);
        polygon.setLayoutY(350);
        polygon.setRotate(90);
        vx = 0;
        vy = 0;
        acceleration = 0;
        directionX = 0;
        directionY = 1;
    }

    public double getDirectionX() { return directionX; }

    public double getDirectionY() { return directionY; }

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
