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
    double vx;
    private double vy;
    private double rotationFactor = 0;
    private double acceleration = 0;
    private double rotation = 90;
    private double directionX = Math.cos(Math.toRadians(rotation));
    private double directionY = Math.sin(Math.toRadians(rotation));
    private ArrayList<Point[]> decomposedPolygonsPositionPoints;
    private final Launcher launcher;
    boolean isResetting;
    private long lastUpdate;
    AnimationTimer move = new AnimationTimer() {
        @Override
        public void handle(long l) {
            double speed = Math.hypot(vx, vy);
            double maxSpeed = 10;
            if (acceleration > 0 && speed <= maxSpeed) {
                vx += directionX * acceleration * 0.004;
                vy += directionY * acceleration * 0.004;
            } else if (vx != 0 || vy != 0) {
                double correctionFactor = Math.max((speed - 0.03) / speed, 0);
                vx *= correctionFactor;
                vy *= correctionFactor;
            }
            polygon.setLayoutX(polygon.getLayoutX() + vx);
            polygon.setLayoutY(polygon.getLayoutY() - vy);
            setPositionPoints(-rotation);
            setDecomposedPolygonsPositionPoints();
            if (speed > 0) { wrap(polygon); }
        }
    };

    Ship(int numberOfBullets) {
        createShip();
        launcher = new Launcher(this, createBullets(numberOfBullets));
        AnimationTimer rotate = new AnimationTimer() {
            @Override
            public void handle(long l) { setRotation(); }
        };
        move.start();
        AnimationTimer setIsResetting = new AnimationTimer() {
            @Override
            public void handle(long l) {
                long resetDelay = 3000;
                if (isResetting) {
                    if (System.currentTimeMillis() - lastUpdate >= resetDelay) {
                        isResetting = false;
                    }
                }
            }
        };
        setIsResetting.start();
        rotate.start();
    }

    private void createShip() {
        polygon = new Polygon();
        Double[] points = new Double[]{15.0, 0.0, -15.0, -12.5, -5.0, 0.0, -15.0, 12.5};
        polygon.getPoints().addAll(points);
        polygon.setLayoutX(350);
        polygon.setLayoutY(350);
        polygonPoints = new Point[points.length/2];
        setPolygonPoints();
        positionPoints = new Point[points.length/2];
        setPositionPoints(0);
        setDecomposedPolygonsPositionPoints();
        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(Color.BLACK);
    }

    private ArrayList<Bullet> createBullets(int numberOfBullets) {
        ArrayList<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < numberOfBullets; i++) { bullets.add(new Bullet()); }
        return bullets;
    }

    void reset() {
        isResetting = true;
        vx = 0;
        vy = 0;
        acceleration = 0;
        rotation = 90;
        directionX = 0;
        directionY = 1;
        polygon.setLayoutX(350);
        polygon.setLayoutY(350);
        lastUpdate = System.currentTimeMillis();
    }

    private void setDecomposedPolygonsPositionPoints() {
        decomposedPolygonsPositionPoints = new ArrayList<>();
        decomposedPolygonsPositionPoints.add(new Point[] {positionPoints[0], positionPoints[1], positionPoints[2]});
        decomposedPolygonsPositionPoints.add(new Point[] {positionPoints[0], positionPoints[2], positionPoints[3]});
    }

    public ArrayList<Point[]> getDecomposedPolygonsPositionPoints() { return decomposedPolygonsPositionPoints; }

    Polygon getImage() { return polygon; }

    public Launcher getLauncher() { return launcher; }

    public double getDirectionX() { return directionX; }

    public double getDirectionY() { return directionY; }

    private void setRotation() {
        double newRotation = rotation + rotationFactor * -rotationDirection;
        rotation = newRotation % 360;
        if (rotation < 0) rotation += 360;
        directionX = Math.cos(Math.toRadians(rotation));
        directionY = Math.sin(Math.toRadians(rotation));
        polygon.setRotate(-rotation);
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
        } else if (keyEventPR == KeyEvent.KEY_RELEASED) { return keyEvent -> { if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT) { rotationFactor = 0; } }; } else { return keyEvent -> {}; }
    }

    EventHandler<KeyEvent> move(EventType<KeyEvent> keyEventPR) { if (keyEventPR == KeyEvent.KEY_PRESSED) { return keyEvent -> { if (keyEvent.getCode() == KeyCode.UP) { acceleration = 17; } }; } else { return keyEvent -> { if (keyEvent.getCode() == KeyCode.UP) { acceleration = 0; } }; } }

}
