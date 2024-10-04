public class HSV {

    private double h; /* The hue. 0 <= h < 360. */
    private double s; /* The saturation. 0 <= s <= 1.*/
    private double v; /* The value, or brightness. 0 <= v <= 1. */

    public HSV(double h, double s, double v) {
        this.h= h;
        this.s= s;
        this.v= v;
    }

    public double getH() {
        return h;
    }

    public double getS() {
        return s;
    }

    public double getV() {
        return v;
    }

    /** A representation of this HSV color. */
    public String toString() {
        return ConvertMethods.toString(this);
    }


}
