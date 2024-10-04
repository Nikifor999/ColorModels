import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//main functionality of GUI elements

public class Main implements ChangeListener, ActionListener {
    private JFrame jf= null;

    private JPanel image, compImage;
    private JLabel imageLabel, compImageLabel;



    private JSlider RSlider= new JSlider(JSlider.VERTICAL, 0, 25500, 0);
    private JSlider GSlider= new JSlider(JSlider.VERTICAL, 0, 25500, 25500);
    private JSlider BSlider= new JSlider(JSlider.VERTICAL, 0, 25500, 0);

    /** CMYK sliders. */
    private JSlider CSlider= new JSlider(JSlider.VERTICAL, 0, 10000, 10000);
    private JSlider MSlider= new JSlider(JSlider.VERTICAL, 0, 10000, 0);
    private JSlider YSlider= new JSlider(JSlider.VERTICAL, 0, 10000, 10000);
    private JSlider KSlider= new JSlider(JSlider.VERTICAL, 0, 10000, 0);

    /** HSV sliders. */
    private JSlider HSlider= new JSlider(JSlider.VERTICAL, 0, 35999, 12000);
    private JSlider SSlider= new JSlider(JSlider.VERTICAL, 0, 100, 100);
    private JSlider VSlider= new JSlider(JSlider.VERTICAL, 0, 100, 100);

    /** The fields for the components of the colors and
     the input buttons */
    private JButton inputrgb=  new JButton("RGB");
    private JTextField rfield= new JTextField();
    private JTextField gfield= new JTextField();
    private JTextField bfield= new JTextField();
    private JButton inputcmyk=  new JButton("CMYK");
    private JTextField cfield= new JTextField();
    private JTextField mfield= new JTextField();
    private JTextField yfield= new JTextField();
    private JTextField kfield= new JTextField();
    private JButton inputhsv= new JButton("HSV");
    private JTextField hfield= new JTextField();
    private JTextField sfield= new JTextField();
    private JTextField vfield= new JTextField();


    public static void main(String[] args) {
        Main a4 = new Main();
    }

    public void init() {
         Main a4= new Main();
    }


    public Main() {
        jf= new JFrame("Color");
        jf.setSize(800, 800);
        jf.setResizable(false);

        // Create color red in three models.
        Color rgb= new Color(0, 255, 0);
        CMYK cmyk= ConvertMethods.convertRGBtoCMYK(rgb);
        HSV hsv= ConvertMethods.convertRGBtoHSV(rgb);

        // Create panel image to contain the color.
        image= new JPanel();
        image.setSize(230, 200);
        image.setPreferredSize(new Dimension(230, 200));
        imageLabel= new JLabel("");
        imageLabel.setPreferredSize(new Dimension(230, 160));
        imageLabel.setVerticalAlignment(JLabel.TOP);
        image.add(imageLabel);

        // Create panel compImage to contain the complementary color.
        compImage= new JPanel();
        compImage.setSize(230, 200);
        compImage.setPreferredSize(new Dimension(230, 200));
        compImageLabel= new JLabel("");
        compImageLabel.setPreferredSize(new Dimension(230, 160));
        compImageLabel.setVerticalAlignment(JLabel.TOP);
        compImage.add(compImageLabel);

        Box sliders= new Box(BoxLayout.X_AXIS);

        fixSlider(sliders, RSlider, 'R');
        fixSlider(sliders, GSlider, 'G');
        fixSlider(sliders, BSlider, 'B');

        sliders.add(Box.createRigidArea(new Dimension(20,0)));

        fixSlider(sliders, CSlider, 'C');
        fixSlider(sliders, MSlider, 'M');
        fixSlider(sliders, YSlider, 'Y');
        fixSlider(sliders, KSlider, 'K');

        sliders.add(Box.createRigidArea(new Dimension(20,0)));

        fixSlider(sliders, HSlider, 'H');
        fixSlider(sliders, SSlider, 'S');
        fixSlider(sliders, VSlider, 'V');

        Box GUI= new Box(BoxLayout.X_AXIS);
        GUI.add(image);
        GUI.add(compImage);
        GUI.add(sliders);

        setSliders(rgb, hsv, cmyk);

        setColorPanels(rgb, hsv, cmyk);

        Box fields= createFields();
        Box finalBox= new Box(BoxLayout.Y_AXIS);
        finalBox.add(GUI);
        finalBox.add(fields);

        jf.getContentPane().add(finalBox);
        jf.pack();
        jf.setVisible(true);
    }

    public Box createFields() {
        Box fields = new Box(BoxLayout.X_AXIS); // Main vertical box

        // RGB input fields
        Box rgbBox = new Box(BoxLayout.Y_AXIS);
        rgbBox.setBorder(BorderFactory.createTitledBorder("RGB Inputs"));
        addLabeledField(rgbBox, "R", rfield);
        addLabeledField(rgbBox, "G", gfield);
        addLabeledField(rgbBox, "B", bfield);
        inputrgb.addActionListener(this);
        rgbBox.add(inputrgb);

        // CMYK input fields
        Box cmykBox = new Box(BoxLayout.Y_AXIS);
        cmykBox.setBorder(BorderFactory.createTitledBorder("CMYK Inputs"));
        addLabeledField(cmykBox, "C", cfield);
        addLabeledField(cmykBox, "M", mfield);
        addLabeledField(cmykBox, "Y", yfield);
        addLabeledField(cmykBox, "K", kfield);
        inputcmyk.addActionListener(this);
        cmykBox.add(inputcmyk);

        // HSV input fields
        Box hsvBox = new Box(BoxLayout.Y_AXIS);
        hsvBox.setBorder(BorderFactory.createTitledBorder("HSV Inputs"));
        addLabeledField(hsvBox, "Hue", hfield);
        addLabeledField(hsvBox, "Sat", sfield);
        addLabeledField(hsvBox, "Val", vfield);
        inputhsv.addActionListener(this);
        hsvBox.add(inputhsv);
        // Add the individual color boxes to the main box
        fields.add(rgbBox);
        fields.add(cmykBox);
        fields.add(hsvBox);

        return fields;
    }

    private void addLabeledField(Box box, String labelText, JTextField textField) {
        textField.setPreferredSize(new Dimension(80, 25));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        box.add(panel); //
    }
    public void fixSlider(Box sliders, JSlider s, char c) {
        Box b= new Box(BoxLayout.Y_AXIS);
        b.add(new JLabel("" + c));
        b.add(s);
        sliders.add(b);
        s.addChangeListener(this);
    }

    public void fixListeners(boolean b) {
        if (b) {
            RSlider.addChangeListener(this);
            GSlider.addChangeListener(this);
            BSlider.addChangeListener(this);
            HSlider.addChangeListener(this);
            SSlider.addChangeListener(this);
            VSlider.addChangeListener(this);
            CSlider.addChangeListener(this);
            MSlider.addChangeListener(this);
            YSlider.addChangeListener(this);
            KSlider.addChangeListener(this);

        } else {
            RSlider.removeChangeListener(this);
            GSlider.removeChangeListener(this);
            BSlider.removeChangeListener(this);
            CSlider.removeChangeListener(this);
            MSlider.removeChangeListener(this);
            YSlider.removeChangeListener(this);
            KSlider.removeChangeListener(this);
            HSlider.removeChangeListener(this);
            SSlider.removeChangeListener(this);
            VSlider.removeChangeListener(this);
        }
    }

    /** Process a movement in one of the sliders */
    public void stateChanged(ChangeEvent e) {
        Color rgb;
        CMYK cmyk;
        HSV hsv;

        fixListeners(false);

        JSlider source= (JSlider)e.getSource();

        if (source.equals(HSlider) || source.equals(SSlider) ||
                source.equals(VSlider)) {
            double H= (double)HSlider.getValue() / 100.0;
            double S= (double)SSlider.getValue() / 100.0;
            double V= (double)VSlider.getValue() / 100.0;
            hsv= new HSV(H, S, V);
            rgb= ConvertMethods.convertHSVtoRGB(hsv);
            cmyk= ConvertMethods.convertRGBtoCMYK(rgb);
        }
        else if (source.equals(RSlider) || source.equals(GSlider) ||
                source.equals(BSlider)) {
            int R= (int) Math.round(RSlider.getValue() / 100.0);
            int G= (int) Math.round(GSlider.getValue() / 100.0);
            int B= (int) Math.round(BSlider.getValue() / 100.0);
            rgb= new Color(R, G, B);
            hsv= ConvertMethods.convertRGBtoHSV(rgb);
            cmyk= ConvertMethods.convertRGBtoCMYK(rgb);
        }
        else if (source.equals(CSlider) || source.equals(MSlider) ||
                source.equals(YSlider) || source.equals(KSlider)) {
            double C= CSlider.getValue() / 100.0;
            double M= MSlider.getValue() / 100.0;
            double Y= YSlider.getValue() / 100.0;
            double K= KSlider.getValue() / 100.0;
            cmyk= new CMYK(C, M, Y, K);
            rgb= ConvertMethods.convertCMYKtoRGB(cmyk);
            hsv= ConvertMethods.convertRGBtoHSV(rgb);
        }
        else return;  // event was not a change in a slider

        setSliders(rgb, hsv, cmyk);
        setColorPanels(rgb, hsv, cmyk);

        // Add the listeners for the sliders
        fixListeners(true);
    }

    /** Set the RGB, HSV, CMYK sliders based on rgb, hsv, and cmyk. */
    public void setSliders(Color rgb, HSV hsv, CMYK cmyk) {
        RSlider.setValue(rgb.getRed() * 100);
        GSlider.setValue(rgb.getGreen() * 100);
        BSlider.setValue(rgb.getBlue() * 100);

        HSlider.setValue((int)(hsv.getH() * 100.0));
        SSlider.setValue((int)(hsv.getS() * 100.0));
        VSlider.setValue((int)(hsv.getV() * 100.0));

        CSlider.setValue((int)(cmyk.cyan() * 100.0));
        MSlider.setValue((int)(cmyk.magenta() * 100.0));
        YSlider.setValue((int)(cmyk.yellow() * 100.0));
        KSlider.setValue((int)(cmyk.black() * 100.0));
    }

    public void setColorPanels(Color rgb, HSV hsv, CMYK cmyk) {
        image.setBackground(rgb);
        Color compRgb= ConvertMethods.complementRGB(rgb);
        compImage.setBackground(compRgb);

        imageLabel.setForeground(compRgb);
        imageLabel.setText("<html>&nbsp;Color<br>&nbsp;RGB:&nbsp;&nbsp;&nbsp;&nbsp;" + ConvertMethods.toString(rgb) +
                "<br>&nbsp;CMYK:&nbsp;" + ConvertMethods.toString(cmyk) +
                "<br>&nbsp;HSV:&nbsp;&nbsp;&nbsp;&nbsp;" + ConvertMethods.toString(hsv) + "<br><br>" +
                "&nbsp;R,G,B sliders in: 0..255<br>" +
                "&nbsp;C,M,Y,K sliders: 0 to 100%<br>" +
                "&nbsp;H slider: 0 &lt;= H &lt; 360 degrees<br>" +
                "&nbsp;S,V sliders: 0 &lt;= S,V &lt;= 1" + "</html>" );
        compImageLabel.setForeground(rgb);
        HSV compHsv= ConvertMethods.convertRGBtoHSV(compRgb);
        CMYK compCmyk= ConvertMethods.convertRGBtoCMYK(compRgb);
        compImageLabel.setText("<html>&nbsp;Complementary Color<br>&nbsp;RGB:&nbsp;&nbsp;&nbsp;&nbsp;" +
                ConvertMethods.toString(compRgb) +
                "<br>&nbsp;CMYK:&nbsp;" + ConvertMethods.toString(compCmyk) +
                "<br>&nbsp;HSV:&nbsp;&nbsp;&nbsp;&nbsp;" + ConvertMethods.toString(compHsv) + "<br><br>" +
                "&nbsp;R,G,B sliders in: 0..255<br>" +
                "&nbsp;C,M,Y,K sliders: 0 to 100%<br>" +
                "&nbsp;H slider: 0 &lt;= H &lt; 360 degrees<br>" +
                "&nbsp;S,V sliders: 0 &lt;= S,V &lt;= 1" +
                "</html>");
        jf.setTitle("Color RGB: " + ConvertMethods.toString(rgb));
    }

    public void actionPerformed(ActionEvent e) {
        fixListeners(false);

        Color rgb;
        HSV hsv;
        CMYK cmyk;
        if (e.getSource() == inputrgb) {
            int r= getInt(rfield.getText());
            int g= getInt(gfield.getText());
            int b= getInt(bfield.getText());

            rfield.setText("" + r);
            gfield.setText("" + g);
            bfield.setText("" + b);

            rgb= new Color(r, g, b);
            hsv= ConvertMethods.convertRGBtoHSV(rgb);
            cmyk= ConvertMethods.convertRGBtoCMYK(rgb);

        } else if (e.getSource() == inputcmyk) {
            double c= getDouble100(cfield.getText());
            double m= getDouble100(mfield.getText());
            double y= getDouble100(yfield.getText());
            double k= getDouble100(kfield.getText());

            cfield.setText(ConvertMethods.roundTo5(c));
            mfield.setText(ConvertMethods.roundTo5(m));
            yfield.setText(ConvertMethods.roundTo5(y));
            kfield.setText(ConvertMethods.roundTo5(k));

            cmyk= new CMYK(c, m, y, k);
            rgb= ConvertMethods.convertCMYKtoRGB(cmyk);
            hsv= ConvertMethods.convertRGBtoHSV(rgb);

        } else {
            double h= getDouble360(hfield.getText());
            double s= getDouble(sfield.getText());
            double v= getDouble(vfield.getText());
            hsv= new HSV(h, s, v);
            rgb= ConvertMethods.convertHSVtoRGB(hsv);
            cmyk= ConvertMethods.convertRGBtoCMYK(rgb);

            hfield.setText(ConvertMethods.roundTo5(h));
            sfield.setText(ConvertMethods.roundTo5(s));
            vfield.setText(ConvertMethods.roundTo5(v));

        }

        setColorPanels(rgb, hsv, cmyk);
        setSliders(rgb, hsv, cmyk);

        fixListeners(true);
    }

    public static int getInt(String s) {
        try {
            int i= Integer.parseInt(s.trim());
            return Math.max(0, Math.min(255, i));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double getDouble(String s) {
        try {
            double d= Double.parseDouble(s.trim());
            return Math.max(0.0, Math.min(1.0, d));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static double getDouble100(String s) {
        try {
            double d= Double.parseDouble(s.trim());
            return Math.max(0.0, Math.min(100.0, d));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static double getDouble360(String s) {
        try {
            double d= Double.parseDouble(s.trim());
            return Math.max(0.0, Math.min(359.9, d));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}