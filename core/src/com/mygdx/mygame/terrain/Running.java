package com.mygdx.mygame.terrain;

import java.util.Arrays;

public class Running implements Runnable {
    int x, y;
    double arr[][];

    public Running(int x, int y, double[][] arr) {
        this.x = x;
        this.y = y;
        this.arr = arr;
    }

    public void run() {
        if (Thread.currentThread().getName().equals("ISLAND")) {
            arr = MakeIsland.init(Terrain.width, Terrain.height, "Island Noise", 0.0008, x, y); // make island noise profile
            //System.out.println("ISLAND GENERATED");
            //BufferedImage island = Island.island(islandArr); // give colors to island
            //ImageIO.write(island, "jpg", new File("D:\\Map\\Output\\island.jpg"));
        } else if (Thread.currentThread().getName().equals("HUMIDITY")) {

            arr = MakeHumidity.init(Terrain.width, Terrain.height, "Humidity Noise", 0.0001, 0, 100, 5000, x, y); // make humidity noise profile
            //System.out.println("HUMIDITY GENERATED");
        } else if (Thread.currentThread().getName().equals("TEMPERATURE")) {

            arr = MakeTemperature.init(Terrain.width, Terrain.height, "Moisture Noise", 0.0001, 0, 100, 1, x, y); // make temperature noise profile
            //System.out.println("TEMPERATURE GENERATED");
        } else if (Thread.currentThread().getName().equals("RIVER")) {
            arr = RiverGeneration.init(Terrain.width, Terrain.height, "River Noise", 0.002, 0, x, y);
            //System.out.println("RIVER GENERATED");
            //BufferedImage river = River.river(riverArr);
            //ImageIO.write(river, "jpg", new File("D:\\Map\\Output\\River.jpg"));
        }
    }
    public double[][] getArr()
    {
        return arr;
    }
}
