package com.mygdx.mygame.entity;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.mygame.textures.Tile;
import com.mygdx.mygame.textures.TileTexture;

public abstract class StaticEntity extends Sprite implements InputProcessor {
    //Stats
    public float health=10f;
    TileTexture texture=new TileTexture();

    //To get x y width height
    public Rectangle boundingBox;
    public Vector2 cartToISO(Vector2 point)
    {
        return new Vector2(point.x,point.y);
    }
    public Vector2 isoToCART(Vector2 point)
    {
        return new Vector2((2*point.y+point.x)/2,(2*point.y-point.x)/2);
    }
    public StaticEntity(float xCentre, float yCentre,float width, float height)
    {
        Vector2 pt=cartToISO(new Vector2(xCentre,yCentre)); //changed to ISo
        this.boundingBox = new Rectangle(pt.x, pt.y, width, height);
    }

    public void update(float deltaTime) {
    }

    public void draw(Batch batch) {

    }
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void translate(float xChange, float yChange) {
        boundingBox.setPosition(boundingBox.x+xChange, boundingBox.y+yChange);
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }
}

