package asteroids;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

class Ship
{
    Polygon polygon;
    private int rotationDirection;
    private double vx;
    private double vy;
    private double rotationFactor = 0;
    private double acceleration = 0;
    private double rotation = 90;
    double directionX = Math.cos(Math.toRadians(rotation));
    double directionY = Math.sin(Math.toRadians(rotation));

    Ship() {
        createShip();
        AnimationTimer moveShipAnimation = new AnimationTimer() {
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
                wrapShip();
            }
        };
        AnimationTimer rotateShipAnimation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                polygon.setRotate(polygon.getRotate() + rotationFactor * rotationDirection);
                if (rotationFactor != 0) { setRotation(); }
            }
        };
        moveShipAnimation.start();
        rotateShipAnimation.start();
    }

    private void createShip() {
        polygon = new Polygon();
        Double[] polygonPoints = new Double[]{0.0, 5.0, 12.5, 15.0, 0.0, -15.0, -12.5, 15.0, 0.0, 5.0};
        polygon.getPoints().addAll(polygonPoints);
        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(Color.BLACK);
        polygon.setLayoutX(350);
        polygon.setLayoutY(350);
    }

    Polygon getShipImage() {
        return polygon;
    }

    private void setRotation() {
        rotation += rotationFactor*-rotationDirection;
        directionX = Math.cos(Math.toRadians(rotation));
        directionY = Math.sin(Math.toRadians(rotation));
    }

    private void wrapShip() {
        if (polygon.getLayoutX() > 700) { polygon.setLayoutX(1); }
        else if (polygon.getLayoutY() > 700) { polygon.setLayoutY(1); }
        else if (polygon.getLayoutX() < 0) { polygon.setLayoutX(699); }
        else if (polygon.getLayoutY() < 0) { polygon.setLayoutY(699); }
    }

    EventHandler<KeyEvent> rotateShip(EventType<KeyEvent> keyEventPR) {
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
        } else { return keyEvent -> { if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT) { rotationFactor = 0; } };
        }
    }

    EventHandler<KeyEvent> moveShip(EventType<KeyEvent> keyEventPR) {
        if (keyEventPR == KeyEvent.KEY_PRESSED) { return keyEvent -> { if (keyEvent.getCode() == KeyCode.UP) { acceleration = 17; } }; }
        else { return keyEvent -> { if (keyEvent.getCode() == KeyCode.UP) { acceleration = 0; } }; }
    }

}
