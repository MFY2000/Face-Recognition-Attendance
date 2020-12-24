package sample;


//import javax.swing.JDialog;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Compare_face {
    public double compare_image() throws Exception {
        BufferedImage img1 = ImageIO.read(new File("C:\\Users\\MF\\IdeaProjects\\FaceRecognition&Attendance\\src\\sample\\Images\\Capture.PNG"));
        BufferedImage img2 = ImageIO.read(new File("C:\\Users\\MF\\IdeaProjects\\FaceRecognition&Attendance\\src\\sample\\Images\\Capture.PNG"));
        int w1 = img1.getWidth();
        int w2 = img2.getWidth();
        int h1 = img1.getHeight();
        int h2 = img2.getHeight();
        double percentage = 0;
        if ((w1 != w2) || (h1 != h2)) {
            System.out.println("Both images should have same dimwnsions");
        } else {
            long diff = 0;
            for (int j = 0; j < h1; j++) {
                for (int i = 0; i < w1; i++) {
                    //Getting the RGB values of a pixel
                    int pixel1 = img1.getRGB(i, j);
                    Color color1 = new Color(pixel1, true);
                    int r1 = (int) color1.getRed();
                    int g1 = (int) color1.getGreen();
                    int b1 = (int) color1.getBlue();
                    int pixel2 = img2.getRGB(i, j);
                    Color color2 = new Color(pixel2, true);
                    int r2 = (int) color2.getRed();
                    int g2 = (int) color2.getGreen();
                    int b2 = (int) color2.getBlue();
                    //sum of differences of RGB values of the two images
                    long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                    diff = diff + data;
                }
            }
            double avg = diff / (w1 * h1 * 3);
            percentage = (avg / 255) * 100;
            System.out.println(percentage);
        }
        return percentage;
    }
}
