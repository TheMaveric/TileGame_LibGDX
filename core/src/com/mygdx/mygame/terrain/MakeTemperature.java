package com.mygdx.mygame.terrain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

/*Different noise functions for each value as
during multithreading seeds were overwritten
by the first file to call the function*/
class MakeTemperature {
    static SecureRandom random;
    static OpenSimplex2F n;

    public static double sumOctaves(int numberOfIterations, int x, int y, double persistence, double scale, int low, int high) {
        double maxAmp = 0;
        double amp = 10;
        double frequency = scale;
        double noise = 0;
        for (int i = 0; i < numberOfIterations; i++) {
            noise += n.noise2(x * frequency, y * frequency) * amp;
            maxAmp += amp;
            amp *= persistence;
            frequency *= 2;
        }
        noise /= maxAmp;
        noise = noise * (high - low) / 2 + (high + low) / 2;
        return noise;
    }

    public static double[][] init(int width, int height, String name, double sc, int low, int high, int seed, int x, int y) {
        //random = new SecureRandom();
        n = new OpenSimplex2F(/*random.nextInt(10000)*/seed);
        double opennoise[][] = new double[width][height];
        double scale = sc;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                opennoise[j][i] = sumOctaves(16, i + x, j + y, 0.5, scale, low, high);
            }
        }
        //greyWriteImage(opennoise,name);
        return opennoise;
    }

    public static void main(String[] args) {
    }

    public static void greyWriteImage(double[][] data, String name) {
        BufferedImage image = new BufferedImage(data.length, data[0].length, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < data[0].length; y++) {
            for (int x = 0; x < data.length; x++) {
                int n = (int) (data[x][y]);
                Color col = new Color(n, n, n);
                image.setRGB(x, y, col.getRGB());
            }
        }
        try {
            ImageIO.write(image, "jpg", new File("D:\\Map\\Output\\" + name + ".jpg"));
        } catch (IOException e) {
            //o no! Blank catches are bad
            throw new RuntimeException("I didn't handle this very well");
        }
    }
}