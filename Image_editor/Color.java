//package edu.byu.cs.imageeditor.studentCode;

/**
 * Created by Cory on 9/6/16.
 */
public class Color {

    private int thisColor;

    public Color(int color) {
        checkColor(color);
        thisColor = color;
    }

    private void checkColor(int color) {
        if (color > 255 || color < 0) {
           System.out.println("Bad color range");
        }
    }

    public int getColor() {
        return thisColor;
    }

    public void invert() {
        thisColor = 255 - thisColor;
    }

    public void setColor(int color) {
        checkColor(color);
        thisColor = color;
    }


}
