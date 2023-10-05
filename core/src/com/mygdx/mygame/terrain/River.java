package com.mygdx.mygame.terrain;

import java.awt.*;
import java.awt.image.BufferedImage;

public class River {

    public static final Color cloudIn = new Color(255, 255, 255);
    public static final Color cloudOut = new Color(221, 221, 221);
    public static final Color deepWater = new Color(20, 91, 134);
    public static final Color shallowWater = new Color(37, 174, 255);
    public static final Color notCloud = new Color(0, 0, 0, 0);

    public static BufferedImage river(double[][] river) {
        BufferedImage imagePixelated = new BufferedImage(river.length, river[0].length, BufferedImage.TYPE_INT_RGB);
        for (int k = 0; k < river[0].length; k++) {
            for (int j = 0; j < river.length; j++) {
                int A = (int) river[j][k];
                if (A > 248) {
                    Color newColor1 = deepWater;
                    imagePixelated.setRGB(j, k, newColor1.getRGB());
                } else if (A > 245) {
                    Color newColor1 = shallowWater;
                    imagePixelated.setRGB(j, k, newColor1.getRGB());
                } else {
                    Color newColor1 = notCloud;
                    imagePixelated.setRGB(j, k, newColor1.getRGB());
                }
            }
        }
        return imagePixelated;
    }
}
