public class CMYK {
    private double c; /** Color cyan. 0 <= c <= 100. */
    private double m; /** Color magenta. 0 <= m <= 100. */
    private double y; /** Color yellow. 0 <= y <= 100. */
    private double k; /** Color black. 0 <= c <= 100. */
    
    public CMYK(double c, double m, double y, double k) {
        this.c= c;
        this.m= m;
        this.y= y;
        this.k= k;
    }
    
    public String toString() {
        return ConvertMethods.toString(this);
    }
    
    public double cyan() {
        return c;
    }
    
    public double magenta() {
        return m;
    }
    
    public double yellow() {
        return y;
    }
    
    public double black() {
        return k;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }
}
