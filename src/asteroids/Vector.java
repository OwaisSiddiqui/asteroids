package src.asteroids;

class Vector {

    private final double x;
    private final double y;
    private final double magnitude;

    Vector(double x, double y) {
        this.x = x;
        this.y = y;
        magnitude = Math.sqrt(x*x + y*y);
    }

    private static double getDotProduct(Vector vector1, Vector vector2) { return (vector1.x * vector2.x) + (vector1.y * vector2.y); }

    static double projectVector(Vector vector1, Vector vector2) { return getDotProduct(vector1, getUnitVector(vector2)); }

    static Vector getNormal(Vector vector) {
        return new Vector(vector.y, -vector.x);
    }

    static Vector getUnitVector(Vector vector) {
        if (vector.magnitude != 0) { return scaleVector(vector, 1 / vector.magnitude); }
        else { return scaleVector(vector, 0); }
    }

    static public Vector[] addVectorArrays(Vector[] vectors1, Vector[] vectors2) {
        Vector[] combinedVectors = new Vector[vectors1.length + vectors2.length];
        int longerVectorArrayLength = Math.max(vectors1.length, vectors2.length);
        Vector[] longerVectorArray;
        Vector[] shorterVectorArray;
        if (longerVectorArrayLength == vectors1.length) {
            longerVectorArray = vectors1;
            shorterVectorArray = vectors2;
        } else {
            longerVectorArray = vectors2;
            shorterVectorArray = vectors1;
        }
        for (int i = 0; i < longerVectorArray.length; i++) {
            combinedVectors[i] = longerVectorArray[i];
            if (i < shorterVectorArray.length) { combinedVectors[longerVectorArray.length + i] = shorterVectorArray[i]; }
        }
        return combinedVectors;
    }

    private static Vector scaleVector(Vector vector, double magnitude) { return new Vector(vector.x*magnitude, vector.y*magnitude); }
}