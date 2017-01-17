//package edu.byu.cs.imageeditor.studentCode;

import java.io.*;

/**
 * Created by Cory on 9/8/16.
 */
public class ImageEditor {

    public Image myImage;

    public void load(BufferedReader bufferedReader) {

        myImage = new Image(bufferedReader);
    }
    public void invert(String outputFileName) throws IOException {
        myImage.invert();
        myImage.writeToFile(outputFileName);
    }
    public void grayscale(String outputFileName) throws IOException {
        myImage.grayScale();
        myImage.writeToFile(outputFileName);
    }
    public void emboss(String outputFileName) throws IOException {
        myImage.emboss();
        myImage.writeToFile(outputFileName);
    }
    public void motionblur(String outputFileName, int blurLength) throws IOException {
        myImage.motionblur(blurLength);
        myImage.writeToFile(outputFileName);
    }

    public static void main(String args[]) {
        System.out.println("Hello there");
        String inputFileName = args[0];
        String outputFileName = args[1];
        String function = args[2];
        int blurVal = 0;
        System.out.println("function is: " + function);
        if (function.equals("motionblur")) {
            blurVal = Integer.parseInt(args[3]);
        }
        ImageEditor ie = new ImageEditor();
        try {
            FileReader fr = new FileReader(inputFileName);
            BufferedReader br = new BufferedReader(fr);
            ie.load(br);
            System.out.println("Done loading file: " + inputFileName);
            switch (function) {
                case "invert": ie.invert(outputFileName);
                    break;
                case "grayscale": ie.grayscale(outputFileName);
                    break;
                case "emboss": ie.emboss(outputFileName);
                    break;
                case "motionblur":
                    System.out.println("blur value is: " + blurVal);
                    ie.motionblur(outputFileName, blurVal);
                    break;
                default:
                    System.out.println("Bad method name");
                    break;
            }


        } catch (FileNotFoundException e) {
            System.out.println("Input file not found\n");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding\n");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // load

    }
}