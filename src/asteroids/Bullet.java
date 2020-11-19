package src.asteroids;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.animation.AnimationTimer;

class Bullet extends AsteroidsObject {
    private final Circle circle;
    private final Ship ship;
    private final double acceleration = 7;
    private double shipLastDirectionX;
    private double shipLastDirectionY;
    private double lastX = 0;
    private double lastY = 0;
    private boolean isBulletRunning = false;
    private int numberOfTriggers = 0;
    private Point positionPoint;
    private Double radius;

    Bullet(Ship ship) {
        circle = new Circle();
        circle.setFill(Color.TRANSPARENT);
        this.ship = ship;
        createBullet();
        AnimationTimer moveBulletAnimation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double maxBulletDistance = 250;
                if (isBulletRunning && getDistance() < maxBulletDistance) {
                    numberOfTriggers = 1;
                    double finalX = circle.getLayoutX() + acceleration * shipLastDirectionX;
                    double finalY = circle.getLayoutY() - acceleration * shipLastDirectionY;
                    circle.setLayoutX(finalX);
                    circle.setLayoutY(finalY);
                    positionPoint = new Point(finalX, finalY);
                } else { resetBullet(); }
            }
        };
        moveBulletAnimation.start();
    }

    private void createBullet() {
        this.radius = 3.0;
        circle.setRadius(this.radius);
        double initialX = ship.getImage().getLayoutX();
        double initialY = ship.getImage().getLayoutY();
        circle.setLayoutX(initialX);
        circle.setLayoutY(initialY);
        this.positionPoint = new Point(initialX, initialY);
    }

    private void resetBullet() {
        circle.setLayoutX(ship.getImage().getLayoutX());
        circle.setLayoutY(ship.getImage().getLayoutY());
        circle.setFill(Color.TRANSPARENT);
        isBulletRunning = false;
        numberOfTriggers = 0;
    }

    private void startBullet() {
        shipLastDirectionX = ship.getDirectionX();
        shipLastDirectionY = ship.getDirectionY();
        lastX = circle.getLayoutX();
        lastY = circle.getLayoutY();
        circle.setFill(Color.BLACK);
        isBulletRunning = true;
    }

    private double getDistance() { return Math.sqrt(Math.pow(circle.getLayoutX() - lastX, 2) + Math.pow(circle.getLayoutY() - lastY, 2)); }

    Circle getImage() { return circle; }

    Point getPositionPoint() { return positionPoint; }

    Double getRadius() { return radius; }

    EventHandler<KeyEvent> moveBullet() { return keyEvent -> { if (keyEvent.getCode() == KeyCode.SPACE && numberOfTriggers == 0) { startBullet(); } }; }
}
