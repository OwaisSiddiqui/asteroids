package asteroids;

class Vector {

    double x;
    double y;
    double magnitude;
    double directionX;
    double directionY;

    Vector(double x, double y) {
        this.x = x;
        this.y = y;
        magnitude = Math.sqrt(x*x + y*y);
        if (magnitude != 0) {
            directionX = -x / magnitude;
            directionY = -y / magnitude;
        }
        else { directionX = 0; directionY = 0;}
    }

    void normalize() {
        if (magnitude != 0) {
            x /= magnitude;
            y /= magnitude;
            magnitude = 1;
        }
    }

    private static double getDotProduct(Vector vector1, Vector vector2) {
        return (vector1.x * vector2.x) + (vector1.y * vector2.y);
    }

    static double projectVector(Vector vector1, Vector vector2) {
        return getDotProduct(vector1, vector2);
    }

    static Vector getNormal(Vector vector) {
        return new Vector(vector.y, -vector.x);
    }

    static Vector getVector(double[] point) {
        return new Vector(point[0], point[1]);
    }

    static Vector getUnitVector(Vector vector) {
        if (vector.magnitude != 0) {
            return scaleVector(vector, 1 / vector.magnitude);
        }
        else {
            return scaleVector(vector, 0);
        }
    }

    private static Vector scaleVector(Vector vector, double magnitude) {
        return new Vector(vector.x*magnitude, vector.y*magnitude);
    }
}