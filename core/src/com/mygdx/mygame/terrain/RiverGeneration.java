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
public class RiverGeneration {
    public static final Color shallowWater = new Color(37, 174, 255);
    static SecureRandom random;
    static OpenSimplex2F n;
    static double opennoise[][];

    public static double sumOctaves(int numberOfIterations, int x, int y, double persistence, double scale) {
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
        noise = (1 - Math.abs(noise)) * 255; //Ridge noise
        return noise;
    }

    public static double[][] init(int width, int height, String name, double sc, int seed, int x, int y) {
        //random = new SecureRandom();
        n = new OpenSimplex2F(/*random.nextInt(10000)*/seed);
        opennoise = new double[width][height];
        double scale = sc;
        for (int i = 0; i < width; i++) {//sumOctaves t1[]=new sumOctaves[height];
            for (int j = 0; j < height; j++) {
                opennoise[j][i] = sumOctaves(16, i + x, j + y, 0.5, scale);
                //t1[j]=new sumOctaves(16,i+x,j+y,0.5,scale,i,j);
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
                double d = data[x][y];
                int n = (int) (d);
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
/*class sumOctaves implements Runnable {
    int numberOfIterations,x,y,i,j;
    double persistence,scale;
    static OpenSimplex2F n;
    public sumOctaves(int numberOfIterations,int x,int y,double persistence,double scale,int i,int j)
    {
        this.numberOfIterations=numberOfIterations;
        this.x=x;
        this.y=y;
        this.persistence=persistence;
        this.scale=scale;
        this.i=i;
        this.j=j;
    }
    @Override
    public void run() {
        double maxAmp=0;
        double amp=10;
        double frequency=scale;
        double noise=0;
        for(int i=0 ; i<numberOfIterations ; i++)
        {
            noise+=n.noise2(x*frequency,y*frequency)*amp;
            maxAmp+=amp;
            amp*=persistence;
            frequency*=2;
        }
        noise/=maxAmp;
        noise=(1-Math.abs(noise))*255; //Ridge noise
        opennoise[j][i]=noise;
    }
}*/
