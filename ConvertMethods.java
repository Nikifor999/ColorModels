import java.awt.*;

public class ConvertMethods {
    
    public static Color complementRGB(Color rgb) {
        int r = 255 - rgb.getRed();
        int g = 255 - rgb.getGreen();
        int b = 255 - rgb.getBlue();
        return new Color(r, g, b);
    }
    
    
    public static String truncateTo5(double d) {
        assert 0 <= d && d <= 999;

        if (d < 0.001) {
            d = 0.0;
        }

        String s = String.format("%.4f", d); // Start with 4 decimal places

        return s.length() > 5 ? s.substring(0, 5) : s;
    }
    
    public static String roundTo5(double d) {
        // Round the value depending on the size of the number.
        if (d >= 100) {
            d = Math.round(d * 10.0) / 10.0;  // Round to one decimal place
        } else if (d >= 10) {
            d = Math.round(d * 100.0) / 100.0;  // Round to two decimal places
        } else {
            d = Math.round(d * 1000.0) / 1000.0;  // Round to three decimal places
        }

        return truncateTo5(d);
    }
    
    public static String toString(Color rgb) {
        return String.format("(%d, %d, %d)", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }
    
    public static String toString(CMYK cmyk) {
        String c = roundTo5(cmyk.getC());
        String m = roundTo5(cmyk.getM());
        String y = roundTo5(cmyk.getY());
        String k = roundTo5(cmyk.getK());
        return String.format("(%s, %s, %s, %s)", c, m, y, k);
    }
    
    public static String toString(HSV hsv) {
        String h = roundTo5(hsv.getH());
        String s = roundTo5(hsv.getS());
        String v = roundTo5(hsv.getV());
        return String.format("(%s, %s, %s)", h, s, v);
    }
    
    public static CMYK convertRGBtoCMYK(Color rgb) {
        double r = rgb.getRed() / 255.0;
        double g = rgb.getGreen() / 255.0;
        double b = rgb.getBlue() / 255.0;

        double k = 1 - Math.max(r, Math.max(g, b));
        if (k == 1) {
            return new CMYK(0, 0, 0, 100);  // Pure black
        }

        double c = (1 - r - k) / (1 - k);
        double m = (1 - g - k) / (1 - k);
        double y = (1 - b - k) / (1 - k);

        return new CMYK(c * 100, m * 100, y * 100, k * 100);
    }
    
    public static Color convertCMYKtoRGB(CMYK cmyk) {
        double c = cmyk.getC() / 100.0;
        double m = cmyk.getM() / 100.0;
        double y = cmyk.getY() / 100.0;
        double k = cmyk.getK() / 100.0;

        int r = (int) Math.round((1 - c) * (1 - k) * 255);
        int g = (int) Math.round((1 - m) * (1 - k) * 255);
        int b = (int) Math.round((1 - y) * (1 - k) * 255);

        return new Color(r, g, b);
    }
    
    public static HSV convertRGBtoHSV(Color rgb) {
        double r = rgb.getRed() / 255.0;
        double g = rgb.getGreen() / 255.0;
        double b = rgb.getBlue() / 255.0;

        double max = Math.max(r, Math.max(g, b));
        double min = Math.min(r, Math.min(g, b));
        double delta = max - min;

        double h = 0;
        if (delta != 0) {
            if (max == r) {
                h = 60 * (((g - b) / delta) % 6);
            } else if (max == g) {
                h = 60 * (((b - r) / delta) + 2);
            } else {
                h = 60 * (((r - g) / delta) + 4);
            }
        }

        if (h < 0) h += 360;

        double s = (max == 0) ? 0 : delta / max;
        double v = max;

        return new HSV(h, s, v);
    }
    
    public static Color convertHSVtoRGB(HSV hsv) {
        double h = hsv.getH();
        double s = hsv.getS();
        double v = hsv.getV();

        double c = v * s;
        double x = c * (1 - Math.abs((h / 60) % 2 - 1));
        double m = v - c;

        double rPrime = 0, gPrime = 0, bPrime = 0;
        if (0 <= h && h < 60) {
            rPrime = c;
            gPrime = x;
        } else if (60 <= h && h < 120) {
            rPrime = x;
            gPrime = c;
        } else if (120 <= h && h < 180) {
            gPrime = c;
            bPrime = x;
        } else if (180 <= h && h < 240) {
            gPrime = x;
            bPrime = c;
        } else if (240 <= h && h < 300) {
            rPrime = x;
            bPrime = c;
        } else if (300 <= h && h < 360) {
            rPrime = c;
            bPrime = x;
        }

        int r = (int) Math.round((rPrime + m) * 255);
        int g = (int) Math.round((gPrime + m) * 255);
        int b = (int) Math.round((bPrime + m) * 255);

        return new Color(r, g, b);
    }
    
}
