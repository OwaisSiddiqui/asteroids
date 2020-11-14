package asteroids;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.animation.AnimationTimer;

class Bullet
{
    Circle circle;
    private Ship ship;
    private double acceleration = 7;
    private double shipLastDirectionX;
    private double shipLastDirectionY;
    private double lastX = 0;
    private double lastY = 0;
    private boolean isBulletRunning = false;
    private int numberOfTriggers = 0;

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
                    circle.setLayoutX(circle.getLayoutX() + acceleration * shipLastDirectionX);
                    circle.setLayoutY(circle.getLayoutY() - acceleration * shipLastDirectionY);
                } else {
                    resetBullet();
                }
            }
        };
        moveBulletAnimation.start();
    }

    private void createBullet() {
        circle.setRadius(3);
        circle.setLayoutX(ship.polygon.getLayoutX());
        circle.setLayoutY(ship.polygon.getLayoutY());
    }

    private void resetBullet() {
        circle.setLayoutX(ship.getShipImage().getLayoutX());
        circle.setLayoutY(ship.getShipImage().getLayoutY());
        circle.setFill(Color.TRANSPARENT);
        isBulletRunning = false;
        numberOfTriggers = 0;
    }

    private void startBullet() {
        shipLastDirectionX = ship.directionX;
        shipLastDirectionY = ship.directionY;
        lastX = circle.getLayoutX();
        lastY = circle.getLayoutY();
        circle.setFill(Color.BLACK);
        isBulletRunning = true;
    }

    private double getDistance() {
        return Math.sqrt(Math.pow(circle.getLayoutX() - lastX, 2) + Math.pow(circle.getLayoutY() - lastY, 2));
    }

    EventHandler<KeyEvent> moveBullet() {
        return keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE && numberOfTriggers == 0) {
                startBullet();
            }
        };
    }
}
