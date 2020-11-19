package src.asteroids;

class Point {

    private double x;
    private double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    void updateX(double x) { this.x = x; }

    void updateY(double y) { this.y = y; }

    void updateXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() { return this.x; }

    double getY() { return this.y; }

    double[] getXY() { return new double[] {this.x, this.y}; }
}
