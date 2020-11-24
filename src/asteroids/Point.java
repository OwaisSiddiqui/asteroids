package src.asteroids;

class Point {

    private double x;
    private double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() { return this.x; }

    double getY() { return this.y; }

    void setX(double x) { this.x = x; }

    void setY(double y) { this.y = y; }
}
