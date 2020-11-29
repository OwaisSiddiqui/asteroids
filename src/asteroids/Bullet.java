package src.asteroids;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.animation.AnimationTimer;

class Bullet {

    private final Circle circle;
    private double lastDirectionX;
    private double lastDirectionY;
    private double lastX;
    private double lastY;
    private boolean isRunning = false;
    private Point positionPoint;
    private Double radius;
    double distance = 0;
    final AnimationTimer move = new AnimationTimer() {
        @Override
        public void handle(long l) {
            double maxDistance = 450;
            if (isRunning && distance <= maxDistance) {
                double acceleration = 20;
                double finalX = circle.getLayoutX() + acceleration * lastDirectionX;
                double finalY = circle.getLayoutY() - acceleration * lastDirectionY;
                circle.setLayoutX(finalX);
                circle.setLayoutY(finalY);
                updatePositionPoint(finalX, finalY);
                updateDistance();
                wrap();
            } else { reset(); }
        }
    };
    private double startingX;
    private double startingY;

    Bullet() {
        circle = new Circle();
        circle.setFill(Color.TRANSPARENT);
        createBullet();
        move.start();
    }

    private void createBullet() {
        this.radius = 3.0;
        circle.setRadius(this.radius);
        circle.setLayoutX(startingX);
        circle.setLayoutY(startingY);
        this.positionPoint = new Point(startingX, startingY);
    }

    void start(double directionX, double directionY) {
        lastX = startingX;
        lastY = startingY;
        lastDirectionX = directionX;
        lastDirectionY = directionY;
        circle.setLayoutX(startingX);
        circle.setLayoutY(startingY);
        circle.setFill(Color.CADETBLUE);
        isRunning = true;
    }

    void reset() {
        isRunning = false;
        circle.setFill(Color.TRANSPARENT);
        circle.setLayoutX(startingX);
        circle.setLayoutY(startingY);
        updatePositionPoint(startingX, startingY);
        distance = 0;
    }

    protected void wrap() {
        Bounds bulletBoundsInParent = circle.getBoundsInParent();
        if (bulletBoundsInParent.getMinX() > 700) { circle.relocate(0 - bulletBoundsInParent.getWidth(), circle.getLayoutY()); }
        else if (bulletBoundsInParent.getMinY() > 700) { circle.relocate(circle.getLayoutX(), 0 - bulletBoundsInParent.getHeight()); }
        else if (bulletBoundsInParent.getMaxX() < 0) { circle.relocate(700, circle.getLayoutY()); }
        else if (bulletBoundsInParent.getMaxY() < 0) { circle.relocate(circle.getLayoutX(), 700); }
        lastX = circle.getLayoutX();
        lastY = circle.getLayoutY();
    }

    private void updateDistance() { distance += Math.sqrt(Math.pow(circle.getLayoutX() - lastX, 2) + Math.pow(circle.getLayoutY() - lastY, 2)); }

    private void updatePositionPoint(double x, double y) {
        positionPoint.setX(x);
        positionPoint.setY(y);
    }

    Point getPositionPoint() { return positionPoint; }

    Double getRadius() { return radius; }

    Circle getImage() { return circle; }

    public boolean isReadyToStart() { return distance == 0 && !isRunning; }

    public void setStartingX(double x) { startingX = x; }

    public void setStartingY(double startingY) { this.startingY = startingY; }

    boolean isRunning() { return isRunning; }
}