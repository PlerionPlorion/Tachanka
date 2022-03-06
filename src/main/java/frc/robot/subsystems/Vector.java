package frc.robot.subsystems;

public class Vector {
    private double x;
    private double y;
    private double angle;
    private double mag;

    /**
     * Creates a two dimensional vector
     * 
     * @param v
     *          The vector to duplicate
     */
    public Vector(Vector v) {
        x = v.x;
        y = v.y;
        angle = v.angle;
        mag = v.mag;
    }

    /**
     * Creates a two dimensional vector
     * 
     * @param X
     *          The x coordinate of the vector
     * @param Y
     *          The y coordinate of the vector
     */
    public Vector(double X, double Y) {
        x = X;
        y = Y;
        updatePolar();
    }

    /**
     * Creates a two dimensional vector
     * 
     * @param xa
     *                  The x coordinate or the angle of the vector
     * @param ym
     *                  The y coordinate or the magnitude of the vector
     * @param cartpolar
     *                  If true, uses xa and ym as an angle and a magnitude for the
     *                  vector. If false, uses xa and ym as x and y coordinates for
     *                  the vector
     */
    public Vector(double xa, double ym, Boolean cartpolar) { // If cartpolar is true, use polar coordinates; if
                                                             // cartpolar is false, use cartesian coordinates.
        if (cartpolar) {
            angle = xa;
            mag = ym;
            updateCart();
        } else {
            x = xa;
            y = ym;
            updatePolar();
        }
    }

    /**
     * Used to recalculate the x and y coordinates after changing the angle or
     * magnitude of the vector
     */
    private void updateCart() {
        x = mag * Math.cos(angle);
        y = mag * Math.sin(angle);
    }

    /**
     * Used to recalculate the angle and magnitude after changing the x or y
     * coordinates of the vector
     */
    private void updatePolar() {
        angle = Math.atan2(y, x);
        mag = Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
    }

    /**
     * @return
     *         The x coordinate of the vector
     */
    public double getX() {
        return x;
    }

    /**
     * @return
     *         The y coordinate of the vector
     */
    public double getY() {
        return y;
    }

    /**
     * @return
     *         The angle of the vector in radians
     */
    public double getAngle() {
        return angle;
    }

    /**
     * @return
     *         The angle of the vector in degrees
     */
    public double getAngleDeg() {
        return angle * 180 / Math.PI;
    }

    /**
     * @return
     *         The magnitude of the vector
     */
    public double getMag() {
        return mag;
    }

    /**
     * Sets the x coordinate of the vector
     * 
     * @param X
     *          New value for the x coordinate
     */
    public void setX(double X) {
        x = X;
        updatePolar();
    }

    /**
     * Sets the y coordinate of the vector
     * 
     * @param Y
     *          New value for the y coordinate
     */
    public void setY(double Y) {
        y = Y;
        updatePolar();
    }

    /**
     * Sets the angle of the vector
     * 
     * @param Angle
     *              New value for the angle
     */
    public void setAngle(double Angle) {
        angle = Angle;
        updateCart();
    }

    /**
     * Sets the magnitude of the vector
     * 
     * @param Mag
     *            New value for the magnitude
     */
    public void setMag(double Mag) {
        mag = Mag;
        updateCart();
    }

    /**
     * Adds a vector to this vector
     * 
     * @param v
     *          The vector to add
     * @return
     *         The sum of the vectors
     */
    public Vector addVector(Vector v) {
        Vector result = new Vector(x + v.x, y + v.y);
        return result;
    }

    /**
     * Subtracts a vector from this vector
     * 
     * @param v
     *          The vector to subtract
     * @return
     *         The difference of the vectors
     */
    public Vector subVector(Vector v) {
        Vector result = new Vector(x - v.x, y - v.y);
        return result;
    }

    /**
     * Multiplies the vector by a scalar
     * 
     * @param s
     *          The scalar to multiply by
     * @return
     *         The product vector
     */
    public Vector multScalar(double s) {
        Vector result = new Vector(x * s, y * s);
        return result;
    }

    /**
     * Divides the vector by a scalar
     * 
     * @param s
     *          The scalar to divide by
     * @return
     *         The quotient vector
     */
    public Vector divScalar(double s) {
        Vector result = new Vector(x / s, y / s);
        return result;
    }

    /**
     * 
     */
    public Vector invXY() {
        Vector result = new Vector(y, x);
        return result;
    }

    @Override
    public String toString() {
        return "Vector(" + x + ", " + y + ")";
    }
}