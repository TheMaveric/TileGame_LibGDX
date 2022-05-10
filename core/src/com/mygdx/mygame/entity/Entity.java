package com.mygdx.mygame.entity;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.mygame.textures.TileTexture;

abstract class Entity extends Sprite implements InputProcessor {
    //Stats
    public float health=10f;
    public float speed=5f;
    public boolean swimming;
    private Vector2 velocity=new Vector2();
    TileTexture texture=new TileTexture();

    //To get x y width height
    public Rectangle boundingBox;

    Entity(float xCentre, float yCentre,float width, float height,float speed)
    {
        swimming=false;
        this.speed = speed;
        this.boundingBox = new Rectangle(xCentre, yCentre, width, height);
    }

    public void update(float deltaTime) {
    }

    public void draw(Batch batch) {
        /*if (keyDown(Input.Keys.W)) {
            batch.draw(texture.snowyTree, boundingBox.x, boundingBox.y, 50, 50);
        }*/
    }

    public boolean intersects(Rectangle otherRectangle) {
        return boundingBox.overlaps(otherRectangle);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void translate(float xChange, float yChange) {
        boundingBox.setPosition(boundingBox.x+xChange, boundingBox.y+yChange);
    }

    public boolean isSwimming() {
        return swimming;
    }

    public void setSwimming(boolean swimming) {
        /*if(swimming==true)
            speed=speed/2;
        else if()
            speed=speed*2;
        System.out.println("Speed :"+speed);*/
        this.swimming = swimming;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }
}
