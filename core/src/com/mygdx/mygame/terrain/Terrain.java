package com.mygdx.mygame.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.mygame.entity.StaticEntity;
import com.mygdx.mygame.textures.Tile;

import java.util.ArrayList;

public class Terrain implements Runnable {
  public static final int width = Gdx.graphics.getWidth() / Tile.TILEWIDTH,
      height = Gdx.graphics.getHeight() / (Tile.TILEHEIGHT);
  // public static int width=29,height=29;
  // public static int width=5120,height=5120;
  public int x, y;
  // making below static sends over the data to init otherwise 0 filled array
  public static double[][] islandArr = new double[width][height],
      cloudArr,
      humidity = new double[width][height],
      temperature = new double[width][height],
      riverArr = new double[width][height];
  public int[][] biome = new int[width][height];
  public ArrayList<ArrayList<Integer>> entityList = new ArrayList<ArrayList<Integer>>();
  public ArrayList<StaticEntity> staticEntities = new ArrayList<>();

  public void run() {
    if (Thread.currentThread().getName().equals("ISLAND")) {
      islandArr =
          MakeIsland.init(width, height, "Island Noise", 0.0008, x, y); // make island noise profile
    } else if (Thread.currentThread().getName().equals("HUMIDITY")) {
      humidity =
          MakeHumidity.init(
              width,
              height,
              "Humidity Noise",
              0.0001,
              0,
              100,
              20000,
              x,
              y); // make humidity noise profile
    } else if (Thread.currentThread().getName().equals("TEMPERATURE")) {
      temperature =
          MakeTemperature.init(
              width,
              height,
              "Moisture Noise",
              0.0001,
              0,
              100,
              15,
              x,
              y); // make temperature noise profile
    } else if (Thread.currentThread().getName().equals("RIVER")) {
      riverArr = RiverGeneration.init(width, height, "River Noise", 0.002, 5, x, y);
    }
  }

  /*void temp(int[][] biome)
  {
      for(int i=0 ; i<width ; i++)
      {
          for(int j=0 ; j<height ; j++) {
             if(biome[i][j]==1) {
                 ArrayList<Integer> list = new ArrayList<Integer>();
                 list.add((y) + i);
                 list.add((x) + j);
                 list.add(1);
                 StaticEntity temp=new Tree((y+i)*Tile.TILEHEIGHT,(x+j)*Tile.TILEWIDTH,Tile.TILEWIDTH*4,Tile.TILEHEIGHT*4);
                 staticEntities.add(temp);
                 //checkpoint
                 //System.out.println("CREATED: "+temp.boundingBox.toString());
                 entityList.add(list);
             }
          }
      }
  }*/
  public ArrayList<StaticEntity> getStaticEntities() {
    return staticEntities;
  }

  public void setStaticEntities(ArrayList<StaticEntity> staticEntities) {
    staticEntities.clear();
    staticEntities.addAll(staticEntities);
  }

  public void terrain(String s, Batch batch) {
    try {
      Biome b = new Biome();
      biome = b.generateBiome(islandArr, temperature, humidity, riverArr);
      // System.out.println("BIOME GENERATED");
      StaticEntityGenerator c = new StaticEntityGenerator();
      entityList =
          c.init(width, height, "Tree Noise", 50, 0, 100, 0, x, y, biome, batch, islandArr);
      // System.out.println("EntityListBefore: "+entityList.toString());
      /*Collections.sort(entityList, new Comparator<ArrayList<Integer>>(){
          @Override
          public int compare(ArrayList<Integer> a, ArrayList<Integer> b) {
              return a.get(0)-b.get(0);
          }

      });
      Collections.reverse(entityList);*/
      // System.out.println("EntityListAfter: "+entityList.toString());
      staticEntities = c.staticEntities;
      /*Collections.sort(staticEntities, new Comparator(){
          @Override
          public int compare(Object o, Object t1) {
              return 0;
          }

          @Override
          public int compare(ArrayList<String> arg0, ArrayList<String> arg1) {
              return arg0.get(0).compareTo(arg1.get(0));
          }

      });*/
      /*for(int i=0 ;i< staticEntities.size();i++)
      System.out.print("StaticList: "+staticEntities.get(i).getBoundingBox().toString());*/
      // System.out.println();
      // ImageIO.write(b.image, "jpg", new File("D:\\Map\\Output\\Chunk\\"+s+y+" "+x+".jpg"));
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void init(int xx, int yy, String s, Batch batch) {
    x = xx;
    y = yy;
    Running r1 = new Running(x, y, islandArr);
    Thread t1 = new Thread(r1);
    t1.setName("ISLAND");
    t1.start();
    Running r2 = new Running(x, y, humidity);
    Thread t2 = new Thread(r2);
    t2.setName("HUMIDITY");
    t2.start();
    Running r3 = new Running(x, y, temperature);
    Thread t3 = new Thread(r3);
    t3.setName("TEMPERATURE");
    t3.start();
    Running r4 = new Running(x, y, riverArr);
    Thread t4 = new Thread(r4);
    t4.setName("RIVER");
    t4.start();
    while (t1.isAlive() || t2.isAlive() || t3.isAlive() || t4.isAlive());
    islandArr = r1.getArr();
    humidity = r2.getArr();
    temperature = r3.getArr();
    riverArr = r4.getArr();
    terrain(s, batch);
  }

  public static void main(String[] args) {
    /*Scanner in= new Scanner(System.in);
    System.out.println("Enter Initial Coordinates");
    int x=in.nextInt();
    int y=in.nextInt();
    Running r1=new Running(x,y,islandArr);
    Thread t1= new Thread(r1);
    t1.setName("ISLAND");
    t1.start();
    Running r2=new Running(x,y,humidity);
    Thread t2= new Thread(r2);
    t2.setName("HUMIDITY");
    t2.start();
    Running r3=new Running(x,y,temperature);
    Thread t3= new Thread(r3);
    t3.setName("TEMPERATURE");
    t3.start();
    Running r4=new Running(x,y,riverArr);
    Thread t4= new Thread(r4);
    t4.setName("RIVER");
    t4.start();
    while(t1.isAlive() || t2.isAlive() || t3.isAlive() || t4.isAlive());
    islandArr=r1.getArr();
    humidity=r2.getArr();
    temperature=r3.getArr();
    riverArr=r4.getArr();
    Terrain t =new Terrain();
    t.terrain("");*/
  }
}
