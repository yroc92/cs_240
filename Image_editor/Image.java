//package edu.byu.cs.imageeditor.studentCode;

import java.io.*;

public class Image {
    public Pixel[][] pixels;
    public Pixel[][] pixelsTemp;
    public int width;
    public int height;

    public Image (BufferedReader br) {
        try {
            br.readLine();               // Pass over file type
            String maybeComment = br.readLine();
            String[] sizes;
            if (maybeComment.contains("#")) {
                sizes = br.readLine().split(" ");
            } else {
                sizes = maybeComment.split(" ");
            }
//            br.readLine();               // Pass over comment

            width = Integer.parseInt(sizes[0]);
            height = Integer.parseInt(sizes[1]);
            br.readLine();              // Pass over max color val
            pixels = new Pixel[height][width];
            // You might need to convert things to string first

            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    int red = Integer.parseInt(br.readLine());
                    int green = Integer.parseInt(br.readLine());
                    int blue = Integer.parseInt(br.readLine());
//                    int red = 5;
//                    int green = 10;
//                    int blue = 15;
                    pixels[h][w] = new Pixel(red, green, blue);
                }
            }
            pixelsTemp = new Pixel[height][width];
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void invert()  {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                pixelsTemp[h][w] = new Pixel(pixels[h][w].red.getColor(),
                        pixels[h][w].green.getColor(), pixels[h][w].blue.getColor());
                pixelsTemp[h][w].invert();
            }
        }
    }

    public void grayScale() {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                int avg = pixels[h][w].averageColor();
                pixelsTemp[h][w] = new Pixel(avg, avg, avg);
            }
        }
    }

    public void emboss() {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                int val;

                if (h == 0 || w == 0) {
                    val = 128;
                } else {
                    int redDiff = pixels[h][w].red.getColor() - pixels[h-1][w-1].red.getColor();
                    int greenDiff = pixels[h][w].green.getColor() - pixels[h-1][w-1].green.getColor();
                    int blueDiff = pixels[h][w].blue.getColor() - pixels[h-1][w-1].blue.getColor();

                    int [] colors = {redDiff, greenDiff, blueDiff};
                    int max = maxDiff(colors);
                    val = 128 + max;
                }


                if (val < 0) val = 0;
                if (val > 255) val = 255;

                pixelsTemp[h][w] = new Pixel(val, val, val);

            }
        }

    }
    private int maxDiff(int [] colors) {
        int max = colors[0];
        for (int i = 1; i < colors.length; i++) {
            if (Math.abs(colors[i]) > Math.abs(max)) {
                max = colors[i];
            }
        }
        return max;
    }

    public void motionblur(int blurLength) {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                pixelsTemp[h][w] = blurAvg(h, w, blurLength);
            }
        }
    }

    private Pixel blurAvg(int h, int w, int blurLength) {
        int blur = blurLength + w;
        if (blur < 1) blur = 1;

        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        int d = 0;
        if (blur >= width) {
            blur = width;
        }
        for (int n = w; n < blur; n++) {
            redSum += pixels[h][n].red.getColor();
            greenSum += pixels[h][n].green.getColor();
            blueSum += pixels[h][n].blue.getColor();
            d++;
        }

        return new Pixel(redSum/(d), greenSum/(d), blueSum/(d));
    }

    public void writeToFile(String filename) throws IOException {
        System.out.println("Writing to file: " + filename);
        StringBuilder out = new StringBuilder("P3\n");
        out.append(width).append(" ").append(height).append("\n").append("255");
        out.append("\n");
        for (int h = 0; h < height; h++) {

            for (int w = 0; w < width; w++) {
                out.append(pixelsTemp[h][w].toString());
            }
        }
        FileWriter fw = new FileWriter((new File(filename)));
        fw.write(out.toString());
        fw.close();
    }



}
