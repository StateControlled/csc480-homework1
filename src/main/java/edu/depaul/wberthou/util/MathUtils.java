package edu.depaul.wberthou.util;

public class MathUtils {

    /**
     * Calculates the distance between a pair of points.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return  the distance
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        double xSqr = deltaX * deltaY;
        double ySqr = deltaY * deltaY;

        return Math.sqrt(xSqr + ySqr);
    }

    private MathUtils() {

    }

}
