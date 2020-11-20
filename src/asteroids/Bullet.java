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
    private double shipLastDirectionX;
    private double shipLastDirectionY;
    private double lastX = 0;
    private double lastY = 0;
    private boolean isRunning = false;
    int numberOfTriggers = 0;
    private Point positionPoint;
    private Double radius;
    final AnimationTimer move = new AnimationTimer() {
        @Override
        public void handle(long l) {
            double maxBulletDistance = 250;
            if (isRunning && getDistance() < maxBulletDistance) {
                numberOfTriggers = 1;
                double acceleration = 7;
                double finalX = circle.getLayoutX() + acceleration * shipLastDirectionX;
                double finalY = circle.getLayoutY() - acceleration * shipLastDirectionY;
                circle.setLayoutX(finalX);
                circle.setLayoutY(finalY);
                positionPoint = new Point(finalX, finalY);
            } else { reset(); }
        }
    };
    private boolean readyToFire = false;
    int id;

    Bullet(Ship ship, int id) {
        circle = new Circle();
        circle.setFill(Color.TRANSPARENT);
        this.ship = ship;
        this.id = id;
        createBullet();
        move.start();
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

    void reset() {
        circle.setLayoutX(ship.getImage().getLayoutX());
        circle.setLayoutY(ship.getImage().getLayoutY());
        circle.setFill(Color.TRANSPARENT);
        isRunning = false;
        numberOfTriggers = 0;
    }

    private void start() {
        shipLastDirectionX = ship.getDirectionX();
        shipLastDirectionY = ship.getDirectionY();
        lastX = circle.getLayoutX();
        lastY = circle.getLayoutY();
        circle.setFill(Color.BLACK);
        isRunning = true;
    }

    private double getDistance() { return Math.sqrt(Math.pow(circle.getLayoutX() - lastX, 2) + Math.pow(circle.getLayoutY() - lastY, 2)); }

    Circle getImage() { return circle; }

    Point getPositionPoint() { return positionPoint; }

    Double getRadius() { return radius; }

    boolean isRunning() { return isRunning; }

    void setReadyToFire(boolean status) { readyToFire = status; }

    boolean isReadyToFire() { return readyToFire; }

    EventHandler<KeyEvent> move() { return keyEvent -> { if (keyEvent.getCode() == KeyCode.SPACE && numberOfTriggers == 0 && readyToFire) { start(); } }; }
}
