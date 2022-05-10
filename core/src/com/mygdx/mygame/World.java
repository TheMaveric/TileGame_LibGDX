package com.mygdx.mygame;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.mygame.entity.Player;
import com.mygdx.mygame.entity.StaticEntity;
import com.mygdx.mygame.terrain.Terrain;
import com.mygdx.mygame.textures.Tile;
import com.mygdx.mygame.textures.TileTexture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class World{
    //Player
    private Player player;
    float animationTime=0;
    //Gamescreen
    private GameScreen gameScreen;
    //Tiles
    TileTexture texture = new TileTexture();
    //World values
    private  float spawnX, spawnY;
    private int ChunkX,ChunkY;
    static String tileNameCurr="";
    //Chunk
    //TODO: remove static from below
    static public ArrayList<ArrayList<Integer>> loaded= new ArrayList<>();
    public ArrayList<ArrayList<Integer>> entityList= new ArrayList<ArrayList<Integer>>();
    public static ArrayList<StaticEntity> staticEntities= new ArrayList<>();
    public Terrain t[] = new Terrain[9];
    Chunk chunk=new Chunk();
    //SaveGame
    File save=new File("save.txt");
    //Camera
    Camera camera;
    Batch batch;
    public World(Player player, GameScreen gameScreen, Camera camera,Batch batch){
        this.player=player;
        this.gameScreen=gameScreen;
        this.camera=camera;
        this.batch=batch;
        init();
    }
    public World(GameScreen gameScreen,Camera camera,Batch batch){
        this.gameScreen=gameScreen;
        this.camera=camera;
        this.batch=batch;
        init();
    }

    public String readSave(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.readLine();
        reader.close();
        return currentLine;
    }
    void loadValues(int x, int y, ArrayList<ArrayList<Integer>> load)
    {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(x);
        temp.add(y);
        load.add(temp);
    }
    void init()
    {
        ArrayList<ArrayList<Integer>> load= new ArrayList<>();
        try {
            String saveGame=readSave(save);
            String arr[]=saveGame.replace("[","").replace("]","").split(",");
            System.out.println(Float.parseFloat(arr[0])+"+"+Float.parseFloat(arr[1])+"+"+
                    Float.parseFloat(arr[2])+"+"+Float.parseFloat(arr[3]));
            Vector2 pt=new Vector2(Float.parseFloat(arr[0]),Float.parseFloat(arr[1]));
            player.setBoundingBox(new Rectangle(pt.x,pt.y,
                    Float.parseFloat(arr[2]),Float.parseFloat(arr[3])));
            spawnX=(int)pt.x;
            spawnY=(int)pt.y;
        }
        catch(Exception e) {
            //Spawning point
            spawnX = 0;
            spawnY = 0;
        }
        int x=(int)spawnX;
        int y=(int)spawnY;
        x=x/Terrain.width;
        y=y/Terrain.height;
        //Set initial chunk
        String chunk1[]=chunk.chunkFinder(x,y).split(" ");
        ChunkX=Integer.parseInt(chunk1[0]);
        ChunkY=Integer.parseInt(chunk1[1]);
        //Load chunk names
        loadValues(x-1,y-1,load);
        loadValues(x,y-1,load);
        loadValues(x+1,y-1,load);
        loadValues(x-1,y,load);
        loadValues(x,y,load);
        loadValues(x+1,y,load);
        loadValues(x-1,y+1,load);
        loadValues(x,y+1,load);
        loadValues(x+1,y+1,load);
        loaded.clear();
        loaded.addAll(load);
        //pwCommon.getEntityList().clear();
        entityList.clear();
        staticEntities.clear();
        System.out.println(loaded);
        //Load chunks and entities
        for(int i=0 ; i<load.size() ; i++)
        {
            int xpos=load.get(i).get(0);
            int ypos=load.get(i).get(1);
            t[i]=new Terrain();
            t[i].init(xpos*Terrain.width,ypos*Terrain.height,"WorldInit",batch);
            entityList.addAll(t[i].entityList);
            staticEntities.addAll(t[i].staticEntities);
        }
        //System.out.println("ENDED!!\n");
    }
    public void loadChunk(int x,int y)
    {
        ArrayList<ArrayList<Integer>> fload= new ArrayList<>();
        //Load chunk names
        loadValues(x-1,y-1,fload);
        loadValues(x,y-1,fload);
        loadValues(x+1,y-1,fload);
        loadValues(x-1,y,fload);
        loadValues(x,y,fload);
        loadValues(x+1,y,fload);
        loadValues(x-1,y+1,fload);
        loadValues(x,y+1,fload);
        loadValues(x+1,y+1,fload);
        loaded.clear();
        loaded.addAll(fload);
        //System.out.println(loaded);
        //pwCommon.getEntityList().clear();
        entityList.clear();
        staticEntities.clear();
        //Load chunks and entities
        for(int i=0 ; i<loaded.size() ; i++) //changed from load to loaded
        {
            int xpos=loaded.get(i).get(0);
            int ypos=loaded.get(i).get(1);
            t[i]=new Terrain();
            t[i].init(xpos*Terrain.width,ypos*Terrain.height,"World",batch);
            entityList.addAll(t[i].entityList);
            staticEntities.addAll(t[i].staticEntities);
        }
        //System.out.println("ENDED!!\n");
    }
    /*public void loadChunk(int x,int y)
    {

        ArrayList<ArrayList<Integer>> fload= new ArrayList<>(); //new chunk to be loaded
        ArrayList<ArrayList<Integer>> load= new ArrayList<>(); //previous chunk still needed
        //Load chunk names
        ArrayList<ArrayList<Integer>> temp= new ArrayList<>();
        if(loaded.contains(new ArrayList<>(Arrays.asList(x-1,y-1))))
            loadValues(x-1,y-1,load);
        else
            loadValues(x-1,y-1,fload);
        if(loaded.contains(new ArrayList<>(Arrays.asList(x,y-1))))
            loadValues(x,y-1,load);
        else
            loadValues(x,y-1,fload);
        if(loaded.contains(new ArrayList<>(Arrays.asList(x+1,y-1))))
            loadValues(x+1,y-1,load);
        else
            loadValues(x+1,y-1,fload);
        if(loaded.contains(new ArrayList<>(Arrays.asList(x-1,y))))
            loadValues(x-1,y,load);
        else
            loadValues(x-1,y,fload);
        if(loaded.contains(new ArrayList<>(Arrays.asList(x,y))))
            loadValues(x,y,load);
        else
            loadValues(x,y,fload);
        if(loaded.contains(new ArrayList<>(Arrays.asList(x+1,y))))
            loadValues(x+1,y,load);
        else
            loadValues(x+1,y,fload);
        if(loaded.contains(new ArrayList<>(Arrays.asList(x-1,y+1))))
            loadValues(x-1,y+1,load);
        else
            loadValues(x-1,y+1,fload);
        if(loaded.contains(new ArrayList<>(Arrays.asList(x,y+1))))
            loadValues(x,y+1,load);
        else
            loadValues(x,y+1,fload);
        if(loaded.contains(new ArrayList<>(Arrays.asList(x+1,y+1))))
            loadValues(x+1,y+1,load);
        else
            loadValues(x+1,y+1,fload);
        //loaded.clear();
        //loaded.addAll(load);

        //System.out.println(loaded);
        //pwCommon.getEntityList().clear();
        entityList.clear();
        staticEntities.clear();
        //Load chunks and entities
        List<ArrayList<Integer>> differences = new ArrayList<>(loaded); //chunks to be deleted
        differences.removeAll(load);
        for(int i=0 ; i<fload.size() ; i++) //changed from load to loaded
        {
            int index=loaded.indexOf(differences.get(i));
            loaded.set(index, fload.get(i));
            int xpos=fload.get(i).get(0);
            int ypos=fload.get(i).get(1);
            t[index]=new Terrain();
            t[index].init(xpos*Terrain.width,ypos*Terrain.height,"World",batch);
            entityList.addAll(t[index].entityList);
            staticEntities.addAll(t[index].staticEntities);
        }
        System.out.println("ENDED!!\n");
    }*/
    public boolean chunkChange()
    {
        int playerXCurrChunk=(int)player.getBoundingBox().x/(Terrain.width*Tile.TILEWIDTH);
        int playerYCurrChunk=(int)player.getBoundingBox().y/(Terrain.height*Tile.TILEHEIGHT);
        if(player.getBoundingBox().x<0)
        {
            playerXCurrChunk--;
        }
        if(player.getBoundingBox().y<0)
        {
            playerYCurrChunk--;
        }
        if(playerXCurrChunk==ChunkX && playerYCurrChunk==ChunkY)
            return false;
        else {
            ChunkX=playerXCurrChunk;
            ChunkY=playerYCurrChunk;
            loadChunk(ChunkX,ChunkY);
            return true;
        }
    }
    public void update(Batch batch,float deltaTime)
    {
        chunkChange();
        //getTile(player.boundingBox.x-player.boundingBox.width,player.boundingBox.y-player.boundingBox.height);
        /*if(getTile(player.boundingBox.x,player.boundingBox.y) ||
        getTile(player.boundingBox.x+ player.getWidth(),player.boundingBox.y) ||
        getTile(player.boundingBox.x+ player.getWidth(),player.boundingBox.y+ player.getHeight()) ||
        getTile(player.boundingBox.x,player.boundingBox.y+ player.getHeight()))*/
        if(getTile(player.boundingBox.x+Tile.TILEWIDTH/2,player.boundingBox.y+Tile.TILEHEIGHT/2))
        {
            player.setSwimming(true);
        }
        else
            player.setSwimming(false);
        animationTime+=deltaTime;
    }
    public void renderEntities(Batch batch){
        int paddingX=4*Tile.TILEWIDTH;
        int paddingY=4*Tile.TILEHEIGHT;
        int factor=12;
        int yStart = (int)( player.getBoundingBox().getX() - camera.viewportWidth/2)-paddingY;
        int yEnd = (int)(player.getBoundingBox().getX() + camera.viewportWidth/2)+paddingY;
        int xStart = (int)(player.getBoundingBox().getY() - camera.viewportWidth/2)-paddingX;
        int xEnd = (int)(player.getBoundingBox().getY() + camera.viewportHeight/2)+paddingX;
        for (int i = 0; i < entityList.size(); i++) {
            int xx= entityList.get(i).get(1);
            int yy= entityList.get(i).get(0);
            //int height=-entityList.get(i).get(3);
            Vector2 pt=new Vector2(xx,yy);

            //if (yy >= xStart && yy <= xEnd && xx >= yStart && xx <= yEnd) {
                int n = entityList.get(i).get(2);
                StaticEntity temp;
                switch (n) {
                    case 1:
                        batch.draw(texture.tree,pt.x,pt.y,Tile.TILEWIDTH*4, Tile.TILEHEIGHT*4);
                        //batch.draw(texture.boundingBox, xx+(6*Tile.TILEWIDTH/4), yy, Tile.TILEWIDTH, 2*Tile.TILEHEIGHT);
                        break;
                    case 2:
                        batch.draw(texture.grass,pt.x,pt.y,Tile.TILEWIDTH, Tile.TILEHEIGHT);
                        //batch.draw(texture.tree,pt.x,pt.y,Tile.TILEWIDTH*4, Tile.TILEHEIGHT*4);
                        break;
                    case 3:
                        //currently does flower but should FERn
                        batch.draw(texture.tree,pt.x,pt.y,Tile.TILEWIDTH*4, Tile.TILEHEIGHT*4);
                        //batch.draw(texture.boundingBox, xx+(6*Tile.TILEWIDTH/4), yy, Tile.TILEWIDTH, 2*Tile.TILEHEIGHT);
                        break;
                    case 4:
                        batch.draw(texture.cactus,pt.x,pt.y,Tile.TILEWIDTH*2, Tile.TILEHEIGHT*2);
                        break;
                    case 5:
                        batch.draw(texture.grass,pt.x,pt.y,Tile.TILEWIDTH, Tile.TILEHEIGHT);
                        //batch.draw(texture.tree,pt.x,pt.y,Tile.TILEWIDTH*2, Tile.TILEHEIGHT*2);
                        break;
                    case 6:
                        batch.draw(texture.flower,pt.x,pt.y,Tile.TILEWIDTH, Tile.TILEHEIGHT);
                        //batch.draw(texture.tree,pt.x,pt.y,Tile.TILEWIDTH*4, Tile.TILEHEIGHT*4);
                        break;
                    case 7:
                        batch.draw(texture.snowyTree,pt.x,pt.y,Tile.TILEWIDTH*4, Tile.TILEHEIGHT*4);
                        //batch.draw(texture.boundingBox, xx+(6*Tile.TILEWIDTH/4), yy, Tile.TILEWIDTH, 2*Tile.TILEHEIGHT);
                        break;
                }
            //}
        }
        for (int i = 0; i < staticEntities.size(); i++) {
            StaticEntity temp = staticEntities.get(i);
            int yy= (int)temp.boundingBox.x;
            int xx= (int)temp.boundingBox.y;
            Vector2 pt=new Vector2(xx,yy);
            //if (yy >= xStart && yy <= xEnd && xx >= yStart && xx <= yEnd) {
                //batch.draw(texture.boundingBox,pt.x,pt.y, temp.boundingBox.height,temp.boundingBox.width);
                //batch.draw(texture.boundingBox, xx, yy, temp.boundingBox.height,temp.boundingBox.width);
            //}
        }
    }
    public void render(Batch batch,float deltaTime) {
        int paddingX=4*Tile.TILEWIDTH;
        int paddingY=4*Tile.TILEHEIGHT;
        int factor=12;
        int yStart = (int)( player.getBoundingBox().getX() - camera.viewportWidth/2)-paddingY;
        int yEnd = (int)(player.getBoundingBox().getX() + camera.viewportWidth/2)+paddingY;
        int xStart = (int)(player.getBoundingBox().getY() - camera.viewportWidth/2)-paddingX;
        int xEnd = (int)(player.getBoundingBox().getY() + camera.viewportHeight/2)+paddingX;
        for (int l = 0; l < 9; l++) {
            int x=loaded.get(l).get(0)*Terrain.width;
            int y=loaded.get(l).get(1)*Terrain.height;
            for (int k = 0; k < Terrain.width; k++) {
                for (int j = 0; j < Terrain.height; j++) {
                    int xx=(x + k) * Tile.TILEWIDTH;
                    int yy= (y + j) * Tile.TILEHEIGHT;
                    //if(yy >= xStart && yy <= xEnd && xx >= yStart && xx <= yEnd) {
                        int tileIndex = t[l].biome[j][k];
                        int height=-(int)t[l].islandArr[j][k];
                        //batch.draw(texture.map.get(tileIndex), (int)(x + k) * Tile.TILEWIDTH,(int)(y + j) * Tile.TILEHEIGHT);
                        Vector2 pt=new Vector2((x + k) * Tile.TILEWIDTH,(y + j) * Tile.TILEHEIGHT);
                        batch.draw(texture.map.get(tileIndex),pt.x,(float)(pt.y));
                    /*try {
                        int tileIndexRight=t[l].biome[j+1][k];
                        int tileIndexLeft=t[l].biome[j-1][k];
                        int tileIndexDown=t[l].biome[j][k-1];
                        int tileIndexUp=t[l].biome[j][k+1];
                        if (tileIndex == 0 || tileIndex == 1) {
                            if (tileIndexUp != 0 && tileIndexUp != 1)
                                batch.draw(texture.water_t.getKeyFrame(animationTime), (x + k) * Tile.TILEWIDTH, (y + j) * Tile.TILEHEIGHT,
                                        0, 0, Tile.TILEWIDTH, Tile.TILEHEIGHT, 1, 1, 0, true);
                            if (tileIndexRight != 0 && tileIndexRight != 1)
                                batch.draw(texture.water_t.getKeyFrame(animationTime), (x + k) * Tile.TILEWIDTH+Tile.TILEWIDTH, (y + j) * Tile.TILEHEIGHT,
                                        0, 0, Tile.TILEWIDTH, Tile.TILEHEIGHT, 1, 1, 90, true);
                            if (tileIndexDown != 0 && tileIndexDown != 1)
                                batch.draw(texture.water_t.getKeyFrame(animationTime), (x + k) * Tile.TILEWIDTH+Tile.TILEWIDTH, (y + j) * Tile.TILEHEIGHT+Tile.TILEHEIGHT*//*-Tile.TILEHEIGHT*//*,
                                        0, 0, Tile.TILEWIDTH, Tile.TILEHEIGHT, 1, 1, 180, true);
                            if (tileIndexLeft != 0 && tileIndexLeft != 1)
                                batch.draw(texture.water_t.getKeyFrame(animationTime), (x + k) * Tile.TILEWIDTH, (y + j) * Tile.TILEHEIGHT+Tile.TILEHEIGHT,
                                        0, 0, Tile.TILEWIDTH, Tile.TILEHEIGHT, 1, 1, 270, true);
                        }
                    }
                    catch(Exception e)
                    {

                    }*/
                    //}
                }
            }
            /*for (int k = 0; k < Terrain.width; k++) {
                for (int j = 0; j < Terrain.height; j++) {
                    int tileIndex=t[l].biome[j][k];
                    batch.draw(texture.map.get(tileIndex),(x+k)*Tile.TILEWIDTH,(y+j)*Tile.TILEHEIGHT);
                }
            }*/
            //draw border
            /*for (int k = 0; k < Terrain.width; k++) {
                if(k==0)
                    batch.draw(texture.button,(x)*Tile.TILEWIDTH,(y)*Tile.TILEHEIGHT);
                for (int j = 0; j < Terrain.height; j++) {
                    if(j==0)
                        batch.draw(texture.button,(x+k)*Tile.TILEWIDTH,(y+j)*Tile.TILEHEIGHT);
                    if(j==Terrain.height-1)
                        batch.draw(texture.button,(x+k)*Tile.TILEWIDTH,(y+j)*Tile.TILEHEIGHT);
                }
                if(k==Terrain.width-1)
                    batch.draw(texture.button,(x+k)*Tile.TILEWIDTH,(y+Terrain.height)*Tile.TILEHEIGHT);
            }*/
        }
    }
    public Vector2 cartToISO(Vector2 point)
    {
        //return new Vector2(point.x-point.y,(point.x+point.y)/2); //original
        return new Vector2(point.x,point.y);
    }
    public Vector2 isoToCART(Vector2 point)
    {
        return new Vector2((2*point.y+point.x)/2,(2*point.y-point.x)/2);
    }
    /*public void renderMiniMap(Batch batch,float deltaTime) {
        int scale=Tile.TILEWIDTH;
        for (int l = 0; l < 9; l++) {
            *//*int x=loaded.get(l).get(0)*Terrain.width;
            int y=loaded.get(l).get(1)*Terrain.height;*//*
            int x=loaded.get(l).get(0);
            int y=loaded.get(l).get(1);
            for (int k = 0; k < Terrain.width; k++) {
                for (int j = 0; j < Terrain.height; j++) {
                    float camx = (( player.getBoundingBox().getX() - camera.viewportWidth/2)+(Tile.TILEWIDTH*Terrain.width/(scale)));*//*+(GameScreen.WORLD_WIDTH*3/4))*//*
                    float camy = ((player.getBoundingBox().getY() - camera.viewportWidth/2)+(Tile.TILEHEIGHT*Terrain.height/(scale)));*//*+(GameScreen.WORLD_HEIGHT*3/4))*//*
                    int tileIndex=t[l].biome[j][k];
                    switch(l) {
                        case 0:
                                batch.draw(texture.map.get(tileIndex),
                                camx + (-1) * (Terrain.width * Tile.TILEWIDTH / scale) + k * Tile.TILEWIDTH / scale,
                                camy + (-1) * (Terrain.height * Tile.TILEHEIGHT / scale) + j * Tile.TILEHEIGHT / scale,
                                Tile.TILEWIDTH / scale, Tile.TILEHEIGHT / scale);
                                break;
                        case 1:
                            batch.draw(texture.map.get(tileIndex),
                                    camx + (0) * (Terrain.width * Tile.TILEWIDTH / scale) + k * Tile.TILEWIDTH / scale,
                                    camy + (-1) * (Terrain.height * Tile.TILEHEIGHT / scale) + j * Tile.TILEHEIGHT / scale,
                                    Tile.TILEWIDTH / scale, Tile.TILEHEIGHT / scale);
                            break;
                        case 2:
                            batch.draw(texture.map.get(tileIndex),
                                    camx + (+1) * (Terrain.width * Tile.TILEWIDTH / scale) + k * Tile.TILEWIDTH / scale,
                                    camy + (-1) * (Terrain.height * Tile.TILEHEIGHT / scale) + j * Tile.TILEHEIGHT / scale,
                                    Tile.TILEWIDTH / scale, Tile.TILEHEIGHT / scale);
                            break;
                        case 3:
                            batch.draw(texture.map.get(tileIndex),
                                    camx + (-1) * (Terrain.width * Tile.TILEWIDTH / scale) + k * Tile.TILEWIDTH / scale,
                                    camy + (0) * (Terrain.height * Tile.TILEHEIGHT / scale) + j * Tile.TILEHEIGHT / scale,
                                    Tile.TILEWIDTH / scale, Tile.TILEHEIGHT / scale);
                            break;
                        case 4:
                            batch.draw(texture.map.get(tileIndex),
                                    camx + (0) * (Terrain.width * Tile.TILEWIDTH / scale) + k * Tile.TILEWIDTH / scale,
                                    camy + (0) * (Terrain.height * Tile.TILEHEIGHT / scale) + j * Tile.TILEHEIGHT / scale,
                                    Tile.TILEWIDTH / scale, Tile.TILEHEIGHT / scale);
                            break;
                        case 5:
                            batch.draw(texture.map.get(tileIndex),
                                    camx + (+1) * (Terrain.width * Tile.TILEWIDTH / scale) + k * Tile.TILEWIDTH / scale,
                                    camy + (0) * (Terrain.height * Tile.TILEHEIGHT / scale) + j * Tile.TILEHEIGHT / scale,
                                    Tile.TILEWIDTH / scale, Tile.TILEHEIGHT / scale);
                            break;
                        case 6:
                            batch.draw(texture.map.get(tileIndex),
                                    camx + (-1) * (Terrain.width * Tile.TILEWIDTH / scale) + k * Tile.TILEWIDTH / scale,
                                    camy + (+1) * (Terrain.height * Tile.TILEHEIGHT / scale) + j * Tile.TILEHEIGHT / scale,
                                    Tile.TILEWIDTH / scale, Tile.TILEHEIGHT / scale);
                            break;
                        case 7:
                            batch.draw(texture.map.get(tileIndex),
                                    camx + (0) * (Terrain.width * Tile.TILEWIDTH / scale) + k * Tile.TILEWIDTH / scale,
                                    camy + (+1) * (Terrain.height * Tile.TILEHEIGHT / scale) + j * Tile.TILEHEIGHT / scale,
                                    Tile.TILEWIDTH / scale, Tile.TILEHEIGHT / scale);
                            break;
                        case 8:
                            batch.draw(texture.map.get(tileIndex),
                                    camx + (+1) * (Terrain.width * Tile.TILEWIDTH / scale) + k * Tile.TILEWIDTH / scale,
                                    camy + (+1) * (Terrain.height * Tile.TILEHEIGHT / scale) + j * Tile.TILEHEIGHT / scale,
                                    Tile.TILEWIDTH / scale, Tile.TILEHEIGHT / scale);
                            break;
                    }
                    batch.draw(texture.boundingBox,camx,camy,9*Tile.TILEWIDTH,9*Tile.TILEHEIGHT);
                    //batch.draw(texture.map.get(tileIndex),100+(x)*Tile.TILEWIDTH+k*Tile.TILEWIDTH/scale,100+(y)*Tile.TILEHEIGHT+j*Tile.TILEHEIGHT/scale,Tile.TILEWIDTH/scale,Tile.TILEHEIGHT/scale);
                }
            }
        }
    }
    public World getWorld()
    {
        return this;
    }*/
    boolean getTile(float x,float y)
    {
        //Chunk
        int xChunk=(int)x/(Terrain.width*Tile.TILEWIDTH);
        int yChunk=(int)y/(Terrain.height*Tile.TILEHEIGHT);
        if(x<0)
        {
            xChunk--;
        }
        if(y<0)
        {
            yChunk--;
        }
        //Finding Index
        int xIndex=(int)(Math.abs(xChunk*Terrain.width*Tile.TILEWIDTH-x)/ Tile.TILEWIDTH);
        int yIndex=(int)(Math.abs(yChunk*Terrain.height*Tile.TILEHEIGHT-y)/ Tile.TILEHEIGHT);
        ArrayList<Integer> find=new ArrayList<>();
        find.add(xChunk);
        find.add(yChunk);
        //Get Type of Tile
        int tileIndex=t[loaded.indexOf(find)].biome[yIndex][xIndex];
        find.clear();
        String tileName=texture.map.get(tileIndex).toString();
        tileNameCurr=tileName;
        if(tileName.equals("shallowWater") || tileName.equals("deepWater"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}








