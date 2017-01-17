//package edu.byu.cs.imageeditor.studentCode;

/**
 * Created by Cory on 9/6/16.
 */
public class Pixel {
    Color red;
    Color green;
    Color blue;

//    public Pixel(String rgbVals) {
////        String [] values = rgbVals.split("\\\\W+");
////        red = new Color(Integer.parseInt(values[0]));
////        green = new Color(Integer.parseInt(values[1]));
////        blue = new Color (Integer.parseInt(values[2]));
////        System.out.println("*****************");
//    }

    public Pixel(int r, int g, int b) {
        red = new Color(r);
        green = new Color(g);
        blue = new Color(b);
    }

    public void invert() {
        red.invert();
        green.invert();
        blue.invert();
    }

    public int averageColor() {
        return ((red.getColor() + green.getColor() + blue.getColor()) / 3);

    }

    // Do i need this function?
    public void setColors(int r, int g, int b) {
        red.setColor(r);
        green.setColor(g);
        blue.setColor(b);
    }

    public String toString() {
        return Integer.toString(red.getColor()) + "\n" + Integer.toString(green.getColor())
                + "\n" + Integer.toString(blue.getColor()) + "\n";
    }
}
