package com.mygdx.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.mygame.entity.Player;
import com.mygdx.mygame.terrain.Terrain;
import com.mygdx.mygame.textures.Tile;
import com.mygdx.mygame.textures.TileTexture;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class GameScreen implements Screen {
    //screen
    private OrthographicCamera camera;
    private Viewport viewport;
    //minimap
    private OrthographicCamera miniMapCamera;
    private Viewport minimapViewport;
    //fps
    private FrameRate frameRate;
    //graphics
    private SpriteBatch batch;
    private SpriteBatch batchMiniMap;
    private TileTexture texture;
    //HUD
    Hud hud;

    //world parameters
    public static final float WORLD_WIDTH = 800;
    public static final float WORLD_HEIGHT = 800;
    private final float TOUCH_MOVEMENT_THRESHOLD = 5f;

    //game objects
    private Player player;
    private World world;
    private Chunk chunk;

    //SaveGame
    private float sinceChange;
    File save=new File("save.txt");

    public GameScreen() {
        Gdx.graphics.setWindowedMode((int)WORLD_WIDTH,(int)WORLD_HEIGHT);
        //camera and viewport
        //camera = new OrthographicCamera();
        camera = new OrthographicCamera();
        /*camera.rotate(60,1,0,0);
        camera.rotate(45,0,0,1);
        camera.near=-10000;
        camera.far=10000;*/
/*        camera.rotateAround(new Vector3(0,0,0), new Vector3(1,0,0), 60);
        camera.near = -2000f;
        camera.far = 2000f;*/
        miniMapCamera = new OrthographicCamera();
        miniMapCamera.zoom = 4;
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        minimapViewport=new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, miniMapCamera);
        batch = new SpriteBatch();
        batchMiniMap = new SpriteBatch();
        //FPS
        frameRate=new FrameRate();
        //Texture
        texture=new TileTexture();
        chunk = new Chunk();
        //Player and World Commons
        world=new World(this,camera,batch);
        //Player
        Vector2 pt=new Vector2(Terrain.width*Tile.TILEWIDTH/2,Terrain.height*Tile.TILEHEIGHT/2);
        player=new Player(pt.x,pt.y, Tile.TILEWIDTH,
                Tile.TILEHEIGHT,100*Tile.TILEWIDTH,this); //speed is 4 tiles in game.
        //World
        world=new World(player,this,camera,batch);
        //Input processor is player
        Gdx.input.setInputProcessor(player);
        //HUD
        hud=new Hud(batch,player);
        //SaveGame
        sinceChange=0;
    }
    public Vector2 cartToISO(Vector2 point)
    {
        return new Vector2(point.x,point.y);
    }
    public Vector2 isoToCART(Vector2 point)
    {
        return new Vector2((2*point.y+point.x)/2,(2*point.y-point.x)/2);
    }
    @Override
    public void render(float deltaTime) {
        //clear screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //follow player
        viewport.apply();
        Vector2 pt=cartToISO(new Vector2(player.getBoundingBox().x,player.getBoundingBox().y));
        camera.position.x = pt.x;
        camera.position.y = pt.y;
        camera.update();
        //set camera
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //load World & load Player
        player.update(deltaTime);
        world.update(batch,deltaTime);
        world.render(batch,deltaTime);
        player.draw(batch,deltaTime);
        world.renderEntities(batch);
        //world.renderMiniMap(batch,deltaTime);
        batch.end();

        //draw minimap
        // update our camera
        //minimapViewport.apply();
        //miniMapCamera.update();
        //miniMapCamera.apply(Gdx.gl10);
        /*batchMiniMap.setProjectionMatrix(miniMapCamera.combined);
        miniMapCamera.position.x = player.getBoundingBox().x;
        miniMapCamera.position.y = player.getBoundingBox().y;
        miniMapCamera.update();
         //draw the player
        batchMiniMap.begin();
        batchMiniMap.draw(texture.button,player.boundingBox.x-WORLD_WIDTH/2,player.boundingBox.y-WORLD_HEIGHT/2,Tile.TILEWIDTH*100,Tile.TILEHEIGHT*100);
        world.render(batchMiniMap,deltaTime);
        world.renderEntities(batchMiniMap);
        batchMiniMap.end();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);*/
        hud.update(deltaTime);
        hud.stage.draw();

        //Show FPS
        frameRate.update();
        frameRate.render();
        //Autosave Feature
        autoSave(deltaTime);
    }
    public void autoSave(float deltaTime)
    {
        if((sinceChange+=deltaTime) > 10) {
            System.out.println("Autosaving:");
            try {
                saveGame();
            } catch (Exception e) {
                System.out.println(e);
            }
            sinceChange=0;
        }
    }
    public void writeToFile(String s) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(save));
        writer.write(s);
        writer.close();
    }
    public void saveGame() throws IOException {
        if(!save.createNewFile()) {
            writeToFile(player.boundingBox.toString()+" "+Tile.TILEWIDTH+" "+Tile.TILEHEIGHT);
        }
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }
}

